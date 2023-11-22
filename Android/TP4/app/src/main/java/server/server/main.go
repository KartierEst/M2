/**
 * Simple websocket server storing graffitis
 * author: chilowi@u-pem.fr
 * license: Apache 2
 */

package main

import (
    "fmt"
    "time"
    "strings"
    "strconv"
    "unicode"
    "unicode/utf8"
    "os"
    "log"
    "sync"
    "sync/atomic"
    "net/http"
    "encoding/json"
    "github.com/gorilla/websocket"
)

const (
    topSize = 64
    channelLifetime = 300
    initialLines = 2
    maxLines = 256
    maxLinePoints = 512
)

var upgrader = websocket.Upgrader{
    ReadBufferSize:  1024,
    WriteBufferSize: 1024,
}

type SketchLine struct {
    date int64
    color uint32
    points []float32
}

type TimestampGiver struct {
    lastTimestamp int64
    mutex sync.Mutex
}

type GraffitiChannel struct {
    parent *GraffitiChannels
    name string
    timestampGiver TimestampGiver
    connNumber int
    connReceiver chan *websocket.Conn
    channelCloser chan bool
    lines *RingArray[SketchLine]
    closed atomic.Bool
    timeoutSerial atomic.Uint32
}

type NewConn struct {
    channelName string
    conn *websocket.Conn
}

type GraffitiChannels struct {
    channels map[string]*GraffitiChannel
    top []string // top channels
    topSize uint32
    topUpdateReceiver chan string
    connAdder chan NewConn
    channelCloser chan string
}

func (giver *TimestampGiver) GiveTimestamp() int64 {
    t := time.Now().UnixMilli()
    giver.mutex.Lock()
    defer giver.mutex.Unlock()
    if t <= giver.lastTimestamp {
        t = giver.lastTimestamp + 1
    }
    giver.lastTimestamp = t
    return t
}

func MakeGraffitiChannels(topSize uint32) *GraffitiChannels {
    var channels GraffitiChannels
    channels.channels = make(map[string]*GraffitiChannel)
    channels.top = []string{}
    channels.topSize = topSize
    channels.connAdder = make(chan NewConn)
    channels.topUpdateReceiver = make(chan string)
    channels.channelCloser = make(chan string)
    go channels.ManageChannels()
    return &channels
}

func (channels *GraffitiChannels) MakeGraffitiChannel(name string, lifetime int) *GraffitiChannel {
    var channel GraffitiChannel
    channel.parent = channels
    channel.name = name
    channel.connReceiver = make(chan *websocket.Conn)
    channel.lines = CreateRingArray[SketchLine](initialLines, maxLines)
    go channel.manageChannel(lifetime)
    return &channel
}

func (channels *GraffitiChannels) ManageChannels() {
    defer close(channels.channelCloser)
    defer close(channels.topUpdateReceiver)
    defer close(channels.connAdder)
    closing := false
    log.Println("Managing channels")
    for {
        select {
            case newConn := <- channels.connAdder:
                log.Printf("New connection on channel %s", newConn.channelName)
                if ! closing {
                    channel, ok := channels.channels[newConn.channelName]
                    if !ok {
                        channel = channels.MakeGraffitiChannel(newConn.channelName, channelLifetime)
                        channels.channels[newConn.channelName] = channel
                    }
                    channel.connReceiver <- newConn.conn
                }
            case channelName := <- channels.topUpdateReceiver :
                channels.updateTop(channelName, true)
            case channelName := <- channels.channelCloser:
                if channelName == "" {
                    log.Println("Closing all the channels")
                    closing = true
                    for _, v := range channels.channels {
                        v.channelCloser <- true
                    }
                } else {
                    log.Printf("Cleaning channel %s", channelName)
                    channels.updateTop(channelName, false)
                    delete(channels.channels, channelName)
                    if closing && len(channels.channels) == 0 {
                       return
                    }
                }
        }
    }
    log.Println("End of managing channels")
}

func ParseLine(s string) SketchLine {
    var result SketchLine
    elements := strings.Split(s, ";")
    if len(elements) >= 3 {
        if timestamp, err := strconv.ParseInt(elements[0], 10, 64); err == nil {
            result.date = int64(timestamp)
        }
        if color, err := strconv.ParseInt(elements[1], 10, 32); err == nil {
            result.color = uint32(color)
        }
        points := strings.Split(elements[2], ",")
        result.points = make([]float32, len(points))
        for i, v := range points {
            if i > maxLinePoints {
                break
            }
            if value, err := strconv.ParseFloat(v, 32); err == nil {
                result.points[i] = float32(value)
            } else {
                result.date = 0
                return result
            }
        }
    }
    return result
}

func (line *SketchLine) ToText() string {
    result := fmt.Sprintf("%d;%d;", line.date, line.color)
    for i, point := range line.points {
        if i != 0 {
            result += ","
        }
        result += fmt.Sprintf("%f", point)
    }
    return result
}

func (channels *GraffitiChannels) updateTop(channelName string, present bool) {
    initialRune, _ := utf8.DecodeRuneInString(channelName)
    if ! unicode.IsUpper(initialRune) {
        return
    }
    // is channelName in top?
    var newTop = make([]string, channels.topSize)
    i := 0
    if present {
        newTop[0] = channelName
        i = 1
    }
    for _, v := range channels.top {
        if v == "" {
            break
        } else if v != channelName {
            initialRune, _ := utf8.DecodeRuneInString(v)
            if unicode.IsUpper(initialRune) {
                newTop[i] = v
                i += 1
            }
        }
    }
    channels.top = newTop
}

func (channel *GraffitiChannel) manageChannel(lifetime int) {
    defer func() { channel.parent.channelCloser <- channel.name }()
    defer close(channel.connReceiver)
    type ConnInfo struct {
        conn *websocket.Conn
        sender chan string
    }
    lineReceiver := make(chan SketchLine)
    defer close(lineReceiver)
    closer := make(chan bool)
    defer close(closer)
    connInfos := make(map[*websocket.Conn]ConnInfo)
    manageConnectionReceive := func (connInfo *ConnInfo) {
        var lastClientTimestamp int64
        var lastServerTimestamp int64
        for {
            msgType, msg, err := connInfo.conn.ReadMessage()
            if err == nil {
                if msgType == websocket.TextMessage {
                    line := ParseLine(string(msg))
                    if line.date != 0 {
                        if line.date != lastClientTimestamp {
                            lastClientTimestamp = line.date
                            lastServerTimestamp = channel.timestampGiver.GiveTimestamp()
                        }
                        line.date = lastServerTimestamp
                        lineReceiver <- line
                    }
                }
            } else {
                channel.connReceiver <- connInfo.conn
                break
            }
        }
    }
    manageConnectionSend := func (connInfo *ConnInfo) {
        for msg := range connInfo.sender {
            if err := connInfo.conn.WriteMessage(websocket.TextMessage, []byte(msg)); err != nil {
                return
            }
        }
    }
    for {
        select {
            case conn := <- channel.connReceiver:
                connInfo, ok := connInfos[conn]
                if ok {
                    channel.connNumber -= 1
                    close(connInfo.sender)
                    delete(connInfos, conn)
                    if channel.connNumber == 0 {
                        // trigger the countdown to close the channel
                        timeout := channel.timeoutSerial.Add(1)
                        go func() {
                            time.Sleep(time.Duration(lifetime) * time.Second)
                            if channel.timeoutSerial.Load() == timeout && ! channel.closed.Load() {
                                closer <- false
                            }
                        }()
                    }
                } else {
                    channel.connNumber += 1
                    var connInfo ConnInfo
                    connInfo.conn = conn
                    connInfo.sender = make(chan string)
                    connInfos[conn] = connInfo
                    go manageConnectionReceive(&connInfo)
                    go manageConnectionSend(&connInfo)
                    go func() {
                        for i := 0 ; i < channel.lines.Len(); i++ {
                            connInfo.sender <- channel.lines.Get(i).ToText()
                        }
                    }()
                }
            case line := <- lineReceiver:
                t := line.ToText()
                // update the stored lines
                replaced := false
                for i := channel.lines.Len() - 1; !replaced && i >= 0; i-- {
                    if channel.lines.Get(i).date == line.date {
                        channel.lines.Set(i, line)
                        replaced = true
                    }
                }
                if !replaced {
                    channel.lines.Push(line)
                }
                for channel.lines.Len() > 0 {
                    if line.date - channel.lines.Get(0).date > int64(lifetime * 1000) {
                        channel.lines.Pop()
                    } else {
                        break
                    }
                }
                for _, connInfo := range connInfos {
                    connInfo.sender <- t
                }
                channel.parent.topUpdateReceiver <- channel.name // update the top
            case closeForce := <- closer:
                if closeForce || channel.connNumber == 0 {
                    channel.closed.Store(true)
                    return
                }
        }
    }
}

func main() {
    address := os.Args[1]
    channels := MakeGraffitiChannels(topSize)
    defer func() { channels.channelCloser <- "" }() // end the channels

    http.HandleFunc("/ws/", func(w http.ResponseWriter, r *http.Request) {
        channelName := strings.TrimPrefix(r.URL.Path, "/ws/")
        if len(channelName) > 0 {
            conn, err := upgrader.Upgrade(w, r, nil)
            if err == nil {
                log.Printf("New websocket initialized from %s", r)
                channels.connAdder <- NewConn { channelName: channelName, conn: conn }
            }
        } else {
            w.WriteHeader(404)
        }
    })

    http.HandleFunc("/public-channels", func(w http.ResponseWriter, r *http.Request) {
        result := []interface{}{}
        for _, channelName := range channels.top {
            channel, ok := channels.channels[channelName]
            if ok {
                var modifiedOn int64
                if channel.lines.Len() > 0 {
                    modifiedOn = channel.lines.Get(channel.lines.Len()-1).date
                }
                result = append(result, map[string]interface{}{
                    "name": channel.name,
                    "connectionNumber": channel.connNumber,
                    "lineNumber": channel.lines.Len(),
                    "modifiedOn": modifiedOn})
            }
        }
        w.Header().Set("Content-Type", "application/json")
        w.WriteHeader(http.StatusCreated)
        json.NewEncoder(w).Encode(result)
    })
    log.Printf("Starting server on %s", address)
    http.ListenAndServe(address, nil)
}