package com.example.tp3

import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
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
        Box(
            modifier
                .fillMaxHeight()
                .weight(fillRatio)
                .background(foregroundColor));
        Box(
            modifier
                .fillMaxHeight()
                .weight((1.0 - fillRatio).toFloat())
                .background(backgroundColor));
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
    r.value +=1
}


@Composable
fun AlphaGrid(modifier: Modifier, letterClicked: MutableState<Char?>, flagClicked: MutableState<ICSFlag?>, r: MutableState<Int>, c: MutableState<Int>, tab: List<List<Char>>, constraints:Constraints){
    var borderColor by remember { mutableStateOf(Color.White) }
    Column {
        for (lotLetter in tab) {
            Row {
                for (letter in lotLetter){
                    LetterDisplayer(
                        letter,
                        modifier.size(
                            // * 2 en plus car moitié moitié
                            width = (constraints.maxWidth / (c.value * 3 * 2)).pxToDp().dp,
                            height = (constraints.maxHeight / (r.value * 3)).pxToDp().dp,
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

@Composable
fun FlagGrid(modifier: Modifier, letterClicked: MutableState<Char?>, flagClicked: MutableState<ICSFlag?>, r: MutableState<Int>, c: MutableState<Int>, tab: List<List<Char>>, constraints:Constraints){
    /*val letterClicked = remember { mutableStateOf<Char?>(null) }*/
    var borderColor by remember { mutableStateOf(Color.White) }
    Column(modifier) {
        for (lotLetter in tab) {
            Row(modifier) {
                for (letter in lotLetter) {
                    //val flag = ICSFlag.findFlag(letter)
                    //if(flag == null){
                    LetterDisplayer(
                        '?',
                        modifier.size(
                            width = (constraints.maxWidth / (c.value * 3 * 2)).pxToDp().dp,
                            height = (constraints.maxHeight / (r.value * 3)).pxToDp().dp,
                        ),
                        Color.Black,
                        borderColor
                    )
                }
                    /*} else {
                        flag.Flag(
                            modifier
                                .size(
                                    width = (constraints.maxWidth / (c.value * 3)).pxToDp().dp,
                                    height = (constraints.maxHeight / (r.value * 3)).pxToDp().dp
                                )
                                .clickable(onClick = {
                                    if (flagClicked.value != flag) {
                                        flagClicked.value = flag
                                        borderColor = Color.Red
                                    } else {
                                        borderColor = Color.White
                                        flagClicked.value = null
                                    }
                                })
                                .border(width = 1.dp, color = borderColor)
                        )
                    }
                }*/
            }
        }
    }
}


@Composable
fun Int.pxToDp() = with(LocalDensity.current) {this@pxToDp}

@Composable
fun FlagLetterPairer(){
    val letterClicked = remember { mutableStateOf<Char?>(null) }
    val flagClicked = remember { mutableStateOf<ICSFlag?>(null) }
    BoxWithConstraints(modifier=Modifier.fillMaxSize()) {
        val constraints = this.constraints
        val r = remember { mutableStateOf(1) }
        val c = remember { mutableStateOf((((maxWidth/2) * r.value) / maxHeight).toInt()) }
        Chunck(constraints.maxWidth/2, constraints.maxHeight, r, c)
        val tab = ('A'..'Z').chunked(c.value)
        Row(modifier = Modifier){
            BoxWithConstraints(modifier = Modifier
                .fillMaxHeight()
                .weight(1f)){
                AlphaGrid(modifier = Modifier, letterClicked, flagClicked, r, c, tab, constraints)
            }
/*            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp),
                color = Color.Black // Couleur de la ligne
            )*/

            BoxWithConstraints(modifier = Modifier.fillMaxHeight().weight(1f)){
                FlagGrid(modifier = Modifier, letterClicked, flagClicked, r, c, tab, constraints)
            }
        }
    }
}

@Composable
fun LetterDisplayer(letter: Char, modifier: Modifier = Modifier, color: Color, borderColor: Color) {
    Box(modifier.border(width = 1.dp ,color = borderColor)){
        AutoText( // texte aligné verticalement
            text = "$letter",
            color = color
        )
    }
}

@Composable
fun AutoText(text: String, modifier: Modifier = Modifier, color: Color = Color.Black) {
    // on rentre dans un contexte de composition virtuelle
    SubcomposeLayout { constraints ->
        var i = 0
        // cette fonction trouve le Text avec la bonne fontSize pour maximiser l'occupation de l'espace
        fun findWellSizedText(range: IntRange): Placeable {
            val med = (range.last + range.first) / 2
            val c = subcompose(i++) {
                Text(text, modifier=modifier, fontSize = med.sp, color = color)
            }[0]
            val placeable = c.measure(constraints)
            return if (med == range.first) {
                Log.i("AutoText","Size of text: $med sp")
                placeable
            } else if (placeable.measuredHeight >= constraints.maxHeight || placeable.measuredWidth >= constraints.maxWidth) {
                findWellSizedText(range.first until med)
            } else {
                findWellSizedText(med..range.last)
            }
        }
        // on trouver la bonne taille entre 1.sp et 1000.sp
        val t = findWellSizedText(1..1000)
        // on utilise la taille obtenue pour le Text optimal retourné par findWellSizedText
        layout(t.width, t.height) {
            // on place le Text en haut à gauche du composant
            t.place(0, 0)
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
        //var running = true
        //Greeting2("Android"
        //VerticalFillBar(fillRatio = 0.2f)
        //CountdownBar(50,10)
        FlagLetterPairer()
    }
}