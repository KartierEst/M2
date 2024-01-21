package com.example.tp4

import android.graphics.Color.parseColor
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tp4.ui.theme.Tp4Theme
import fr.uge.graffiti.TextMessage
import fr.uge.graffiti.WebsocketOutputMessage
import fr.uge.graffiti.com.example.tp4.SketchChannel
import fr.uge.graffiti.communicateWithWebsocket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL


class Graffitis : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tp4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun BorderColorSelected(selectedColor: Color?, widthBorder: MutableState<Int>, color: Color){
    if(selectedColor == null || selectedColor != color){
        widthBorder.value = 0
    }
    else {
        widthBorder.value = 5
    }
}

@Composable
fun ColorPalette(selectedColor: Color?, colorList: List<Color>, onClickedColor: (Color) -> Unit){
    LazyRow(modifier = Modifier.fillMaxHeight(), content = {
        items(colorList){
            Box(
                Modifier
                    .size(width = 50.dp, height = 50.dp)
                    .background(it)
                    .border(
                        if (it == selectedColor) {
                            5.dp
                        } else {
                            0.dp
                        }, Color.Black
                    )
                    .clickable {
                        onClickedColor(it)
                    })
        }
    })
}

@Composable
fun SketchViewer(sketch: Sketch){
    Canvas(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        for(line in sketch.lines){
            val time = System.currentTimeMillis() - line.timestamp
            val alpha = 1 - (time / (5 * 60 * 1000f))
            for(i in 0 until (line.points.size-1)){
                val actualLine = line.points[i]
                val nextLine = line.points[i+1]
                drawLine(line.color.copy(alpha = alpha),
                    start = Offset(actualLine.first * size.width, actualLine.second * size.height),
                    end = Offset(nextLine.first * size.width, nextLine.second * size.height),
                    strokeWidth = 10f
                )
            }
        }
    }
}

@Composable
fun DrawEventer(onDrawEvent: (Pair<Float, Float>, Boolean) -> Unit, modifier: Modifier){
    val line = Pair(-1f, -1f)
    Column {
        Box(modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        when (event.type) {
                            PointerEventType.Press -> {
                                val pair = event.changes.first().position
                                // position donne la position direct du tel alors que Canva prend des points plus petit
                                // qu'on multiplie après par la taille donc ici on divise
                                val tmp = Pair(pair.x / size.width, pair.y / size.height)
                                onDrawEvent(tmp, true)
                            }

                            PointerEventType.Move -> {
                                val pair = event.changes.last().position
                                val tmp = Pair(pair.x / size.width, pair.y / size.height)
                                onDrawEvent(tmp, true)
                            }

                            PointerEventType.Release -> {
                                onDrawEvent(line, false)
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun SketchViewerWithEventer(sketch: MutableState<Sketch>, selectedColor:MutableState<Color>, channelName: String){
    var allLine = Line(mutableListOf(), selectedColor.value, System.currentTimeMillis())
    val tmpSketch = Sketch(sketch.value.lines)
    val onDrawEvent: (Pair<Float, Float>, Boolean) -> Unit = { line, onGoing ->
        allLine.color = selectedColor.value
        if (onGoing) {
            allLine.points.add(line)
        }
        else {
            tmpSketch.lines.add(allLine)
            writeToChannelSketch(channelName, allLine)
            sketch.value = Sketch(tmpSketch.lines)
            // ici que la couleur change vraiment
            allLine = Line(mutableListOf(), selectedColor.value, System.currentTimeMillis())
        }
    }

    LaunchedEffect(sketch.value.lines){
        while(true){
            delay(1000)
            sketch.value.lines = sketch.value.lines.filter { (System.currentTimeMillis() - it.timestamp) < 5 * 60 * 1000 }.toMutableList()
        }
    }

    Box(modifier = Modifier.fillMaxSize()){
        DrawEventer(onDrawEvent, Modifier.fillMaxSize())
        SketchViewer(sketch = sketch.value)
    }
}

@Composable
fun LocalSketchManager(sketch: MutableState<Sketch>, channelName: String){
    //val sketch = remember { mutableStateOf(Sketch(mutableListOf())) }
    val selectedColor = remember { mutableStateOf(Color.Blue) }
    val onClickedColor = { color:Color -> selectedColor.value = color }
    Column(modifier = Modifier
        .fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(0.1f)
            .background(Color.White)) {
            ColorPalette(
                selectedColor.value,
                listOf(Color.Black, Color.Blue, Color.Cyan, Color.Magenta, Color.Green),
                onClickedColor
            )
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)) {
            SketchViewerWithEventer(sketch, selectedColor, channelName)
        }
    }
}

private fun fetchPublicChannelsInfo(apiUrl: String) {
    val client = OkHttpClient()
    val request = Request.Builder().url(apiUrl).build()

    client.newCall(request).enqueue(object : okhttp3.Callback {
        override fun onFailure(call: okhttp3.Call, e: java.io.IOException) {
            println("Échec de la requête HTTP : ${e.message}")
        }

        override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                println("Réponse du serveur : $responseBody")
            } else {
                println("Erreur de réponse HTTP : ${response}")
            }
        }
    })
}

fun sendUpdatedArray(updatedArray: JSONArray) {
    val apiUrl = "https://graffiti.plade.org/public-channels"

    // Créer une connexion HTTP avec l'API
    val url = URL(apiUrl)
    val connection = url.openConnection() as HttpURLConnection

    try {
        // Configurer la connexion pour une requête de type PUT (ou POST selon les besoins)
        connection.requestMethod = "PUT"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.doOutput = true

        // Écrire le JSONArray dans le corps de la requête
        val outputStream = connection.outputStream
        val writer = OutputStreamWriter(outputStream)
        writer.write(updatedArray.toString())
        writer.flush()

        // Lire la réponse du serveur
        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            println("Mise à jour réussie.")
        } else {
            println("Échec de la mise à jour. Réponse du serveur : $responseCode")
        }
    } finally {
        // Fermer la connexion
        connection.disconnect()
    }
}


fun getColorByName(colorName: String): Color {
    var color = Color.Transparent
    try {
        color = Color(parseColor(colorName))
    } catch (e: IllegalArgumentException) {
        Log.e("Color", "la couleur est inconnue")
    }
    return color
}

fun writeToChannelSketch(channelName: String, line:Line){
    val test = communicateWithWebsocket("ws://localhost:8000/ws/$channelName")
    val pointsText = line.points.joinToString(",") { it ->
        "${it.first},${it.second}"
    }
    val msg = "${line.timestamp};" +
            "${line.color};" +
            pointsText
    test.first.tryEmit(TextMessage(msg))
}

@Composable
fun ReadToChannelSketch(channelName: String, sketch: MutableState<Sketch>){
    val test = communicateWithWebsocket("ws://localhost:8000/ws/$channelName")
    var msg: WebsocketOutputMessage = TextMessage("")
    LaunchedEffect(test, msg) {
        test.second.collect { message ->
            msg = message
        }
    }
    val tab = msg.toString().split(";")
    val color = tab[1]
    val timestamp = tab[0]
    val points: MutableList<Pair<Float, Float>> = mutableListOf()

    val allCoord = tab[3].split(",")

    for (i in allCoord.indices step 2) {
        points.add(Pair(allCoord[i].toFloat(), allCoord[i+1].toFloat()))
    }
    sketch.value.lines.add(Line(points, getColorByName(color),timestamp.toLong()))
}
@Composable
fun SendSketchToPublicSocket(channelName: String){
    LaunchedEffect(Dispatchers.IO) {
        val content = URL("https://graffiti.plade.org/public-channels").readText()
        val obj = JSONArray(content)
        obj.put(SketchChannel(obj.length().toLong(), 0, "${System.currentTimeMillis()}", channelName))
        sendUpdatedArray(obj)
    }
}

@Composable
fun SketchChannelDisplayer(channel: SketchChannel, name: MutableState<String>){
    Box(modifier = Modifier.clickable {
        name.value = channel.name;
    }){
        Text(text = "connection Number : ${channel.connectionNumber}", fontSize = 3.sp)
        Text(text = "line Number : ${channel.lineNumber}", fontSize = 3.sp)
        Text(text = "modified On : ${channel.modifiedOn}", fontSize = 3.sp)
        Text(text = "name : ${channel.name}", fontSize = 3.sp)
    }
}

@Composable
fun SketchChannelListDisplayer(channels: List<SketchChannel>){
    LazyColumn {
        items(channels) { channel ->
            val name = remember { mutableStateOf(channel.name) }
            SketchChannelDisplayer(channel, name)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SketchChannelManager(channelName:MutableState<String>){
    val channels = remember { mutableListOf<SketchChannel>() }
    LaunchedEffect(Dispatchers.IO, channels) {
        while (true) {
            val content = URL("https://graffiti.plade.org/public-channels").readText()
            val objects = JSONArray(content)
            for (i in 0..objects.length()) {
                val obj = objects.getJSONObject(i)
                val name = obj.getString("name")
                if(!name[0].isUpperCase()) {
                    val channel = SketchChannel(
                        obj.getLong("connectionNumber"), obj.getInt("lineNumber"),
                        obj.getString("modifiedOn"), obj.getString("name")
                    )
                    channels.add(channel)
                }
            }
            delay(60_000)
        }
    }
    var tmpValue = ""
    SketchChannelListDisplayer(channels = channels)
    TextField(value = tmpValue, onValueChange = {it -> tmpValue = it})
    Button(onClick = {channelName.value = tmpValue}) {
        SendSketchToPublicSocket(tmpValue)
        Text(text = "Create")
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
/*    var selectedColor by remember { mutableStateOf(Color.Blue) }
    val onClickedColor = { color:Color -> selectedColor = color }
    val allLine = Line(mutableListOf(), Color.Black, 0)
    val onDrawEvent: (Pair<Float, Float>, Boolean) -> Unit = { line, onGoing ->
        if (onGoing) {
            allLine.points.add(line)
        }
    }*/
    Tp4Theme {
        //Greeting("Android")
        //ColorPalette(selectedColor, listOf(Color.Blue, Color.Cyan, Color.Magenta, Color.Green), onClickedColor)
        //DrawEventer(onDrawEvent = onDrawEvent, allLine, Modifier)
        //LocalSketchManager()
        //sendSketchToSocket()
        //val allLine = Line(mutableListOf(Pair(0f,0f), Pair(1f,1f)), Color.Black, 0)
        //writeToChannelSketch("channel", allLine)
        //ReadToChannelSketch( "channel")
        val channelName = remember { mutableStateOf("") }
        val sketch = remember { mutableStateOf(Sketch(mutableListOf())) }
        Scaffold(
            topBar = {
                if(channelName.value.isEmpty()) {
                    SketchChannelManager(channelName)
                }
                else {
                    ReadToChannelSketch(channelName.value, sketch)
                }
            },
            floatingActionButton = {
                if(channelName.value.isNotEmpty()) {
                    // reviens en arriere
                    // supp
                    FloatingActionButton(onClick = {
                        channelName.value = ""
                        sketch.value = Sketch(mutableListOf())
                    }) {
                        Text(text = "Retour")
                    }
                }
            })
        { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                if(channelName.value.isNotEmpty()) {
                    LocalSketchManager(sketch = sketch, channelName.value)
                }
            }
        }
    }
}