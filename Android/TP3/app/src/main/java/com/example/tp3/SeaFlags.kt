package com.example.tp3

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


import androidx.compose.ui.unit.sp
import com.example.TP2.ui.theme.TP2Theme

class SeaFlags : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
fun BoxAlphabet(modifier: Modifier = Modifier) {
    Box(modifier){
        ('A'..'Z').forEach {
            val randomColor = remember { Color.random()}
            // it = la lettre actuel
            LetterDisplayer(it, modifier,randomColor)
        }
    }
}

@Composable
fun AlphabetRow(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    Row(modifier = Modifier.horizontalScroll(scrollState)){
        ('A'..'Z').forEach {
            val randomColor = remember { Color.random()}
            // it = la lettre actuel
            LetterDisplayer(it, modifier,randomColor)
        }
    }
}

@Composable
fun AlphabetColumn(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    Row(modifier = Modifier.verticalScroll(scrollState)){
        ('A'..'Z').forEach {
            val randomColor = remember { Color.random()}
            // it = la lettre actuel
            LetterDisplayer(it, modifier,randomColor)
        }
    }
}





// value width = 1440 et max = 2308
// r = 2 => c = 1 => r * c = 2
// r = 5 => c = 2 => r * c = 10
// r = 8 => c = 3.6 = r * c = 28,8
// r = 8 et c = 4


@Composable
fun AlphabetGrid(modifier: Modifier = Modifier){
    val tab = ('A'..'Z').chunked(4)
    val letterClicked = remember { mutableStateOf<Char?>(null) }
    BoxWithConstraints(modifier=Modifier.fillMaxSize()) {
        val constraints = this.constraints
        Column {
            for (lotLetter in tab) {
                Row {
                    for (letter in lotLetter){
                        LetterDisplayer(
                            letter,
                            modifier.size(
                                width = (constraints.maxWidth / 12).pxToDp().dp,
                                height = (constraints.maxHeight / 24).pxToDp().dp,
                            ).clickable(onClick = { letterClicked.value = letter }),
                            Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FlagGrid(modifier: Modifier = Modifier, letterClicked: MutableState<Char?>){
    val tab = ('A'..'Z').chunked(4)
    /*val letterClicked = remember { mutableStateOf<Char?>(null) }*/
    BoxWithConstraints(modifier=Modifier.fillMaxSize()) {
        val constraints = this.constraints
        Column(modifier) {
            for (lotLetter in tab) {
                Row(modifier) {
                    for (letter in lotLetter){
                        val flag = ICSFlag.findFlag(letter)
                        if(flag != null && letterClicked.value != letter){
                            flag.Flag(modifier.size(
                                width = (constraints.maxWidth / 12).pxToDp().dp,
                                height = (constraints.maxHeight / 24).pxToDp().dp
                            ).clickable(onClick = {
                                if(letterClicked.value == null ){ letterClicked.value = letter }
                                else { if(letterClicked.value == '?' || letterClicked.value != letter) {letterClicked.value = letter}
                                    else {letterClicked.value = null}
                                }
                            }))
                        }
                        else if(letterClicked.value != null && letterClicked.value == letter){
                            LetterDisplayer(letterClicked.value!!,
                                modifier.size(
                                    width = (constraints.maxWidth / 12).pxToDp().dp,
                                    height = (constraints.maxHeight / 24).pxToDp().dp,
                                ).clickable(onClick = { letterClicked.value = null })
                                ,Color.Black)
                        }
                        else {
                            LetterDisplayer('?',
                                modifier.size(
                                    width = (constraints.maxWidth / 12).pxToDp().dp,
                                    height = (constraints.maxHeight / 24).pxToDp().dp,
                                ),Color.Black)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FlagInfoBox(modifier: Modifier = Modifier, flag: ICSFlag ){
    Row(modifier.height(intrinsicSize = IntrinsicSize.Max)){
        flag.Flag(modifier.weight(20f).fillMaxHeight())
        Column(modifier.weight(80f).padding(start = 10.dp)) {
            Text(flag.codeWord, fontSize = 40.sp, )
            Text(flag.message)
        }
    }
}


@Composable
fun FlagManager(modifier: Modifier){
    val letterClicked = remember { mutableStateOf<Char?>(null) }
    Column(modifier) {
        Box(Modifier.fillMaxWidth().weight(1f)){
            FlagGrid(modifier, letterClicked)
        }
        if(letterClicked.value != null){
            Box(Modifier.fillMaxWidth().height(intrinsicSize = IntrinsicSize.Max)){
                val flag = ICSFlag.findFlag(letterClicked.value!!)
                FlagInfoBox(modifier, flag!!)
            }
        }
    }
}

// ajoute une fonction statique à color
fun Color.Companion.random(alpha: Int = 50) = Color((0..255).random(),(0..255).random(), (0..255).random(), alpha)

@Composable
fun Int.pxToDp() = with(LocalDensity.current) {this@pxToDp}

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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    //TP3Theme {
        /*Greeting("Android")*/
        /*LetterDisplayer('A', modifier=Modifier.background(color= Color.Green))//.size(width = 400.dp, height = 400.dp).rotate(50f))*/
        //BoxAlphabet(modifier = Modifier.fillMaxSize())
        //AlphabetRow(modifier = Modifier.fillMaxSize());
        //AlphabetColumn(modifier = Modifier.fillMaxSize());
        //ConstraintsDisplayer();
        //AlphabetGrid()
        //DeltaFlag.Flag(modifier = Modifier)
        //HotelFlag.Flag(modifier = Modifier.size(width = 10.dp, height = 10.dp))
        //LimaFlag.Flag(modifier = Modifier.size(width = 100.dp, height = 100.dp))
        //FlagGrid()
        //FlagInfoBox(modifier = Modifier, DeltaFlag)
        //FlagManager(Modifier)
    //}
}