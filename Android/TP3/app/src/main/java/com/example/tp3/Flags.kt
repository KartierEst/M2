package com.example.tp3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tp3.ui.theme.TP3Theme
import kotlinx.coroutines.delay

class Flags : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}

@Composable
fun VerticalFillBar(backgroundColor: Color = Color.White, foregroundColor: Color = Color.Red, fillRatio: Float, modifier: Modifier=Modifier){
    Row(modifier.height(height = 10.dp)) {
        Box(modifier.fillMaxHeight().weight(fillRatio).background(foregroundColor));
        Box(modifier.fillMaxHeight().weight((1.0 - fillRatio).toFloat()).background(backgroundColor));
    }
}

@Composable
fun CountdownBar(initialCountdown: Int, elapsedTime: Int, modifier: Modifier = Modifier){
    val duration = (elapsedTime/initialCountdown)
    val minutes = initialCountdown / 60 // (-1)
    val secondes = initialCountdown - (60 * minutes)
    val text = if(minutes != 0) { "$minutes min $secondes s" } else { "$secondes s" }
    VerticalFillBar(fillRatio = duration.toFloat())
    Box(modifier.fillMaxWidth(),contentAlignment = Alignment.Center) {
        Text(
            text,
            textAlign = TextAlign.Center,
            fontSize = 7.sp,
            color = Color.Black
        )
    }
}

@Composable
fun Countdown(duration: Int, running: Boolean, onEnd: () -> Unit) {
    var counter by rememberSaveable() { mutableStateOf(0) }
    CountdownBar(counter, duration)
    if(running) {
        LaunchedEffect(counter) {
            while (counter != duration) {
                delay(1000)
                counter++
            }
            onEnd()
        }
    }
}

@Composable
fun DurationSelector(currentDuration: Int, presetDurations: List<Int>, onSelectedDuration: (Int) -> Unit) {
    var openMenu by remember { mutableStateOf(false) }
    Box(modifier = Modifier) {
        Text("$currentDuration s")
        DropdownMenu(expanded = openMenu, onDismissRequest = { openMenu = false }, modifier = Modifier) {
            presetDurations.forEach { s ->
                DropdownMenuItem(text = { Text("$s s") },onClick = {
                    onSelectedDuration(s)
                    openMenu = false
                })
            }
        }
    }
}

@Composable
fun Chunck(maxWidth: Int, maxHeight: Int, r: MutableState<Int>, c: MutableState<Int>){
    while(c.value*r.value < 26){
        r.value += 1
        c.value = (maxWidth*r.value)/maxHeight
    }
}


@Composable
fun AlphaGrid(modifier: Modifier, letterClicked: MutableState<Char?>){
    var borderColor by remember { mutableStateOf(Color.White) }
    BoxWithConstraints(modifier=Modifier.fillMaxSize()) {
        val constraints = this.constraints
        val r = remember { mutableStateOf(1) }
        val c = remember { mutableStateOf(((maxWidth*r.value)/maxHeight).toInt())}
        Chunck(constraints.maxWidth, constraints.maxHeight, r, c)
        val tab = ('A'..'Z').chunked(c.value)
        Column {
            for (lotLetter in tab) {
                Row {
                    for (letter in lotLetter){
                        LetterDisplayer(
                            letter,
                            modifier.size(
                                width = (constraints.maxWidth / c.value * 3).pxToDp().dp,
                                height = (constraints.maxHeight / r.value * 3).pxToDp().dp,
                            ).clickable(onClick = {
                                if(letterClicked.value == null || letterClicked.value != letter) {
                                    letterClicked.value = letter
                                    borderColor = Color.Red
                                }
                                else {
                                    borderColor = Color.Black
                                    letterClicked.value = null
                                }
                            }),
                            Color.Black,
                            borderColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FlagGrid(modifier: Modifier, flagClicked: MutableState<ICSFlag?>){
    /*val letterClicked = remember { mutableStateOf<Char?>(null) }*/
    var borderColor by remember { mutableStateOf(Color.White) }
    BoxWithConstraints(modifier=Modifier.fillMaxSize()) {
        val constraints = this.constraints
        val r = remember { mutableStateOf(1) }
        val c = remember { mutableStateOf(((maxWidth*r.value)/maxHeight).toInt())}
        Chunck(constraints.maxWidth, constraints.maxHeight, r, c)
        val tab = ('A'..'Z').chunked(c.value)
        Column(modifier) {
            for (lotLetter in tab) {
                Row(modifier) {
                    for (letter in lotLetter){
                        val flag = ICSFlag.findFlag(letter)
                        flag?.Flag(modifier.size(
                            width = (constraints.maxWidth / 12).pxToDp().dp,
                            height = (constraints.maxHeight / 24).pxToDp().dp
                        ).clickable(onClick = {
                            if(flagClicked.value != flag) {
                                flagClicked.value = flag
                                borderColor = Color.Red
                            } else {
                                borderColor = Color.Black
                                flagClicked.value = null
                            }
                        }).border(width = 1.dp, color = borderColor))
                        }
                    }
                }
            }
        }
    }

@Preview
@Composable
fun ParameterizedCountDown(){
    var duration = 10
    var running = false
    Countdown(10,running = running, onEnd = {running = false})
    DurationSelector(duration, listOf(20, 30, 60, 90), onSelectedDuration = { s -> duration = s })
    Button(onClick = { running = !running }){Text("RUN")}
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    TP3Theme {
        var running = true
        //Greeting2("Android"
        //VerticalFillBar(fillRatio = 0.2f)
        //CountdownBar(50,10)
    }
}