package fr.uge.graffiti

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.cancel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import okio.ByteString.Companion.toByteString
import java.nio.ByteBuffer

sealed interface WebsocketMessage
sealed interface WebsocketInputMessage: WebsocketMessage
sealed interface WebsocketOutputMessage: WebsocketMessage
data class TextMessage(val content: String): WebsocketInputMessage, WebsocketOutputMessage
data class BinaryMessage(val content: ByteArray): WebsocketInputMessage, WebsocketOutputMessage
object OpenedMessage: WebsocketOutputMessage
object ClosedMessage: WebsocketOutputMessage
data class FailureMessage(val cause: Throwable): WebsocketOutputMessage

typealias WebsocketFlows = Pair<MutableSharedFlow<WebsocketInputMessage>, Flow<WebsocketOutputMessage>>

/**
 * This function returns immediately a pair of flows:
 * - when we subscribe to the first flow by collecting it, a new connection is made with the websocket
 *   server. The flow emits the messages sent by the server.
 * - the second flow is used to transmit messages to the server
 *
 * This function uses the okhttp library; do not forget to add the appropriate dependency in your
 * build.gradle file:
 * implementation "com.squareup.okhttp3:okhttp:4.10.0"
 */
fun communicateWithWebsocket(url: String): WebsocketFlows  {
    Log.d("websocket", "Connecting to websocket $url")
    val input = MutableSharedFlow<WebsocketInputMessage>(extraBufferCapacity = 16)
    val output = MutableSharedFlow<WebsocketOutputMessage>(extraBufferCapacity = 16)
    val client = OkHttpClient()
    val request = Request.Builder().url(url = url).build()
    var ws: WebSocket? = null
    var collectJob: Job? = null
    return input to output.onSubscription {
        Log.d("websocket", "New subscription on websocket")
        ws = client.newWebSocket(request, object: WebSocketListener() {
            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                output.tryEmit(ClosedMessage)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.d("websocket", "onFailure: $t")
                super.onFailure(webSocket, t, response)
                val emitting = output.tryEmit(FailureMessage(t))
                Log.d("websocket", "onFailure: $t $emitting")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                output.tryEmit(TextMessage(text))
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
                output.tryEmit(BinaryMessage(bytes.toByteArray()))
            }

            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d("websocket", "onOpen")
                super.onOpen(webSocket, response)
                output.tryEmit(OpenedMessage)
            }
        })
        collectJob = withContext(Dispatchers.Main) { launch(Job()) {
            Log.d("websocket", "Starting the input collecting job")
            try {
                input.collect {
                    Log.d("websocket", "Sending $it")
                    when (it) {
                        is TextMessage -> ws?.send(it.content)
                        is BinaryMessage -> ws?.send(it.content.toByteString())
                    }
                }
            } finally {
                Log.i("websocket", "Ending the input collecting job")
            }
        } }
    }.onCompletion {
        Log.i("websocket", "Closing the websocket")
        collectJob?.cancel()
        ws?.close(1000, "closing")
    }
}