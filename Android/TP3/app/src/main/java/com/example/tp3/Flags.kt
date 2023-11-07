package com.example.tp3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tp3.ui.theme.TP3Theme
import kotlinx.coroutines.delay
import androidx.compose.ui.platform.LocalContext

class Flags : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}

@Composable
fun VerticalFillBar(backgroundColor: Color = Color.Blue, foregroundColor: Color = Color.Red, fillRatio: Float, modifier: Modifier=Modifier){
    Row(modifier.height(height = 30.dp)) {
        if (fillRatio > 0.0) {
            Box(
                modifier
                    .fillMaxHeight()
                    .weight(fillRatio)
                    .background(backgroundColor)
            );
        }
        if(1.0 - fillRatio > 0.0) {
            Box(
                modifier
                    .fillMaxHeight()
                    .weight((1.0 - fillRatio).toFloat())
                    .background(foregroundColor)
            );
        }
    }
}

@Composable
fun CountdownBar(initialCountdown: Int, elapsedTime: Int, modifier: Modifier = Modifier){
    val duration = (elapsedTime.toFloat()/initialCountdown.toFloat())
    val minutes = elapsedTime / 60 // (-1)
    val secondes = elapsedTime - (60 * minutes)
    val text = if(minutes != 0) { "$minutes min $secondes s" } else { "$secondes s" }
    Box() {
        VerticalFillBar(fillRatio = duration)
        Box(modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(
                text,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun Countdown(duration: Int, counter: MutableIntState,running: Boolean, onEnd: () -> Unit) {
    CountdownBar(duration, counter.intValue)
    if(running) {
        LaunchedEffect(counter, duration) {
            while (counter.intValue != 0) {
                delay(1000)
                counter.intValue--
            }
            onEnd()
        }
    }
}

@Composable
fun DurationSelector(currentDuration: Int, presetDurations: List<Int>, openMenu: MutableState<Boolean>, onSelectedDuration: (Int) -> Unit) {
    Box(modifier = Modifier) {
        DropdownMenu(expanded = openMenu.value, onDismissRequest = { openMenu.value = false }, modifier = Modifier) {
            presetDurations.forEach { s ->
                DropdownMenuItem(text = { Text("$s s") },onClick = {
                    onSelectedDuration(s)
                    openMenu.value = false
                })
            }
        }
    }
/*    Column {
        Box(modifier = Modifier
            .size(width = 150.dp, height = 30.dp)
            .border(width = 1.dp, Color.DarkGray)) {
            Text("Actual duration is ${currentDuration}")
        }
        Button(onClick = { openMenu = !openMenu }) {
            Text("Choose duration")
        }
    }*/
}

@Composable
fun Chunck(maxWidth: Int, maxHeight: Int, r: MutableState<Int>, c: MutableState<Int>){
    while(c.value*r.value < 26){
        r.value += 1
        c.value = (maxWidth*r.value)/maxHeight
    }
    r.value +=1
}

fun removeTab(tab: MutableState<List<List<Char>>>, letter: Char){
    tab.value = tab.value.map{ x -> x.filter { c -> c != letter } }
}


fun OnClick(tabFlag: MutableState<List<List<Char>>>, tabAlpha: MutableState<List<List<Char>>>, letter: Char, clickedValue: MutableState<Char?>, otherClickedValue: MutableState<Char?>, score: MutableState<Int>){
    if (clickedValue.value == null || clickedValue.value != letter) {
        clickedValue.value = letter
    } else {
        clickedValue.value = null
    }

    if(clickedValue.value != null && otherClickedValue.value != null){
        if (clickedValue.value == otherClickedValue.value) {
            removeTab(tab = tabAlpha, letter = letter)
            removeTab(tab = tabFlag, letter = letter)
            clickedValue.value = null
            otherClickedValue.value = null
            score.value++
        } else {
            clickedValue.value = null
            otherClickedValue.value = null
        }
    }
}

@Composable
fun AlphaGrid(modifier: Modifier, letterClicked: MutableState<Char?>, flagClicked: MutableState<Char?>, width: Dp, height: Dp, tabAlpha: MutableState<List<List<Char>>>, tabFlag: MutableState<List<List<Char>>>, score: MutableState<Int>){
    Column {
        for (lotLetter in tabAlpha.value) {
            Row {
                for (letter in lotLetter){
                    LetterDisplayer(
                        letter,
                        modifier
                            .size(width, height)
                            .clickable(onClick = {
                                OnClick(
                                    tabFlag = tabFlag,
                                    tabAlpha = tabAlpha,
                                    letter = letter,
                                    clickedValue = letterClicked,
                                    otherClickedValue = flagClicked,
                                    score = score
                                )
                            }),
                        Color.Black,
                        if (letter == letterClicked.value) Color.Red else Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun FlagGrid(modifier: Modifier, letterClicked: MutableState<Char?>, flagClicked: MutableState<Char?>, width: Dp, height: Dp, tabFlag: MutableState<List<List<Char>>>, tabAlpha: MutableState<List<List<Char>>>, score: MutableState<Int>){
    Column(modifier) {
        for (lotLetter in tabFlag.value) {
            Row(modifier) {
                for (letter in lotLetter) {
                    LetterDisplayer(
                        letter,
                        modifier
                            .size(width = width, height = height)
                            .clickable(onClick = {
                                OnClick(
                                    tabFlag = tabFlag,
                                    tabAlpha = tabAlpha,
                                    letter = letter,
                                    clickedValue = flagClicked,
                                    otherClickedValue = letterClicked,
                                    score = score
                                )
                            }),
                        Color.Black,
                        if (letter == flagClicked.value) Color.Red else Color.White
                    )
                }
            }
        }
    }
}


@Composable
fun Int.pxToDp() = with(LocalDensity.current) {this@pxToDp}

@Composable
fun FlagLetterPairer(
    letterClicked: MutableState<Char?>,
    flagClicked: MutableState<Char?>,
    score: MutableState<Int>,
    tabAlpha: MutableState<List<List<Char>>>,
    tabFlag: MutableState<List<List<Char>>>
){
    var isPlayingSounds by remember { mutableStateOf(true) }

    val context = LocalContext.current

    LaunchedEffect(isPlayingSounds, letterClicked.value, flagClicked.value) {
        val soundList = listOf(
            ResourceSound(R.raw.popeye),
        )
        while (isPlayingSounds) {
            if (letterClicked.value != null || flagClicked.value != null) {
                isPlayingSounds = false
            }
            context.playSounds(soundList)
        }
        if(letterClicked.value != null){
            context.playMorse(letterClicked.value!!)
        }
        if(flagClicked.value != null){
            context.playMorse(flagClicked.value!!)
        }
        if (letterClicked.value != null || flagClicked.value != null) {
            delay(500)
        }

    }


    BoxWithConstraints(modifier=Modifier.fillMaxSize()) {
        val constraints = this.constraints
        val r = remember { mutableIntStateOf(1) }
        val c = remember { mutableIntStateOf((((maxWidth/2) * r.intValue) / maxHeight).toInt()) }
        Chunck(constraints.maxWidth/2, constraints.maxHeight, r, c)

        val width = (constraints.maxWidth / (c.intValue * 3 * 2)).pxToDp().dp
        val height = (constraints.maxHeight / (r.intValue * 3)).pxToDp().dp

        Row(modifier = Modifier){
            // * 2 en plus car moitié moitié
            Box(modifier = Modifier
                .fillMaxHeight()
                .weight(1f)){
                AlphaGrid(modifier = Modifier, letterClicked, flagClicked, width, height, tabAlpha, tabFlag, score)
            }
            Box(modifier = Modifier
                .fillMaxHeight()
                .weight(0.01f)
                .background(Color.Black))
            Box(modifier = Modifier
                .fillMaxHeight()
                .weight(1f)){
                FlagGrid(modifier = Modifier, letterClicked, flagClicked,width, height, tabFlag, tabAlpha, score)
            }
        }
    }
}
@Preview
@Composable
fun ParameterizedCountDown(){
    val duration = 120
    var running by remember { mutableStateOf(false) }
    val counter = remember { mutableIntStateOf(duration) }

    Column {
        Countdown(duration, counter, running = running, onEnd = {running = false})
        //DurationSelector(duration, listOf(20, 30, 60, 90), openMenu) { s -> duration = s }
        Button(onClick = { running = true }){Text("RUN")}
        Button(onClick = { running = false }){Text("STOP")}
    }
}

/*@Preview
@Composable
fun GameSeaFlags(){
    val duration = 5
    var running = remember { mutableStateOf(false) }
    var runFirstTime by remember { mutableStateOf(true) }

    val letterClicked = remember { mutableStateOf<Char?>(null) }
    val flagClicked = remember { mutableStateOf<Char?>(null) }

    val score = remember { mutableIntStateOf(0) }
    val counter = remember { mutableIntStateOf(duration) }

    val onEnd = {running.value = false}

    Column(modifier = Modifier.fillMaxSize()) {
        if(runFirstTime || running.value){
            Countdown(duration, counter, running = running.value, onEnd)
            FlagLetterPairer(
                letterClicked = letterClicked,
                flagClicked = flagClicked,
                score,
                null
            )
            if(letterClicked.value != null || flagClicked.value != null){
                running.value = true
                runFirstTime = false
            }
        }
        else {
            Text("FIN")
        }
    }
}*/


@Composable
fun ButtonRestart(
    runFirstTime: MutableState<Boolean>,
    running: MutableState<Boolean>,
    letterClicked: MutableState<Char?>,
    flagClicked: MutableState<Char?>,
    score: MutableState<Int>,
    counter: MutableIntState,
    duration: Int,
    resetTab: () -> Unit,
) {
    // réinitialiser les tableaux
    Button(onClick = {
        runFirstTime.value = true; running.value = false;
        letterClicked.value = null; flagClicked.value = null; score.value = 0; counter.intValue = duration;
        resetTab();

    })
    { Text(text = "Restart") }
}
@Composable
fun GameFlags(
    runFirstTime: MutableState<Boolean>,
    running: MutableState<Boolean>,
    letterClicked: MutableState<Char?>,
    flagClicked: MutableState<Char?>,
    score: MutableState<Int>,
    counter: MutableIntState,
    duration: Int,
    resetTab: () -> Unit,
    tabAlpha: MutableState<List<List<Char>>>,
    tabFlag: MutableState<List<List<Char>>>,
) {
    if(runFirstTime.value || running.value){
        Column(modifier = Modifier.fillMaxSize()) {
            FlagLetterPairer(letterClicked = letterClicked, flagClicked = flagClicked, score, tabAlpha, tabFlag)
            if (letterClicked.value != null || flagClicked.value != null) {
                running.value = true
                runFirstTime.value = false
            }
            if(tabFlag.value.flatten().isEmpty() && tabAlpha.value.flatten().isEmpty()){
                running.value = false
            }
        }
    }
    else{
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            verticalArrangement = Arrangement.Center, // Centre le contenu verticalement
            horizontalAlignment = Alignment.CenterHorizontally // Centre le contenu horizontalement
        ) {
            Text(text = "ton score est de : ${score.value}", fontSize = 20.sp)
            ButtonRestart(
                runFirstTime,
                running,
                letterClicked,
                flagClicked,
                score,
                counter,
                duration,
                resetTab,
            )
        }
    }
}

@Preview
@Composable
fun MyApp() {
    val letterClicked = remember { mutableStateOf<Char?>(null) }
    val flagClicked = remember { mutableStateOf<Char?>(null) }
    val duration = remember { mutableIntStateOf(5) }
    val running = remember { mutableStateOf(false) }
    val runFirstTime = remember { mutableStateOf(true) }
    val openMenu = remember { mutableStateOf(false) }

    val score = remember { mutableIntStateOf(0) }
    val counter = remember { mutableIntStateOf(duration.intValue) }

    BoxWithConstraints(modifier=Modifier.fillMaxSize()) {
        val constraints = this.constraints
        val r = remember { mutableIntStateOf(1) }
        val c = remember { mutableIntStateOf((((maxWidth / 2) * r.intValue) / maxHeight).toInt()) }
        Chunck(constraints.maxWidth / 2, constraints.maxHeight, r, c)
        val tabAlpha = remember { mutableStateOf(('A'..'Z').shuffled().chunked(c.intValue)) }
        val tabFlag = remember { mutableStateOf(('A'..'Z').shuffled().chunked(c.intValue)) }

        val resetTab = {
            tabAlpha.value = ('A'..'Z').shuffled().chunked(c.intValue)
            tabFlag.value = ('A'..'Z').shuffled().chunked(c.intValue)
        }

        Scaffold(
            topBar = {
                if (runFirstTime.value || running.value) {
                    Countdown(
                        duration.intValue,
                        counter,
                        running = running.value,
                        onEnd = { running.value = false })
                    Text("score : ${score.intValue}")
                }
            },

            floatingActionButton = {
                if (runFirstTime.value) {
                    FloatingActionButton(onClick = { openMenu.value = true }) {
                        Icon(Icons.Default.List, contentDescription = "Add")
                        DurationSelector(
                            duration.intValue,
                            listOf(20, 30, 60, 90),
                            openMenu
                        ) { s -> duration.intValue = s; counter.intValue = s }
                    }
                } else {
                    ButtonRestart(
                        runFirstTime,
                        running,
                        letterClicked,
                        flagClicked,
                        score,
                        counter,
                        duration.intValue,
                        resetTab
                    )
                }
            })
        { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                GameFlags(
                    runFirstTime,
                    running,
                    letterClicked,
                    flagClicked,
                    score,
                    counter,
                    duration.intValue,
                    resetTab,
                    tabAlpha,
                    tabFlag
                )
            }
        }
    }
}

@Preview
@Composable
fun MyAppPreview() {
    MyApp()
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    TP3Theme {
        //var running = true
        //Greeting2("Android"
        //VerticalFillBar(fillRatio = 0.2f)
        //CountdownBar(50,10)
        //FlagLetterPairer()
    }
}