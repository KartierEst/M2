package com.example.tp4

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp4.ui.theme.Tp4Theme

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
fun borderColorSelected(selectedColor: Color?, widthBorder: MutableState<Int>, color: Color){
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
    //val line1 = Line(mutableListOf(Pair(0.5F,0.0F),Pair(0.5F,0.5F)), Color.Black, System.currentTimeMillis())
    //val line2 = Line(mutableListOf(Pair(0.0F,0.0F),Pair(1.0F,1.0F)), Color.Black, System.currentTimeMillis())
    //val sketch = Sketch(listOf(line1,line2))
    Log.e("test", "zugulu")
    Canvas(modifier = Modifier.fillMaxSize()) {
        for(line in sketch.lines){
            for(i in 0 until (line.points.size-1) step 2){
                val actualLine = line.points[i]
                val nextLine = line.points[i+1]
                drawLine(line.color,
                    start = Offset(actualLine.first * size.width, actualLine.second * size.height),
                    end = Offset(nextLine.first * size.width, nextLine.second * size.height),
                    strokeWidth = 4f
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
                                val tmp = Pair(pair.x, pair.y)
                                onDrawEvent(tmp, true)
                            }

                            PointerEventType.Move -> {
                                val pair = event.changes.last()
                                val tmp = Pair(pair.position.x, pair.position.y);
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
fun SketchViewerWithEventer(sketch: MutableState<Sketch>, selectedColor: Color){
    var allLine = Line(mutableListOf(), selectedColor, 0)
    val tmpSketch = Sketch(mutableListOf(allLine))
    // modifier le sketch directement et pas l'interieur du contenu
    // pareil pour les line
    val onDrawEvent: (Pair<Float, Float>, Boolean) -> Unit = { line, onGoing ->
        if (onGoing) {
            Log.e("go", "go")
            allLine.points.add(line)
        }
        else {
            Log.e("add", "add")
            tmpSketch.lines.add(allLine)
            sketch.value = tmpSketch
            allLine = Line(mutableListOf(), selectedColor, 0)
        }
    }
    
    Box(modifier = Modifier.fillMaxSize()){
        DrawEventer(onDrawEvent, Modifier.fillMaxSize())
        SketchViewer(sketch = sketch.value)
    }
}

@Composable
fun LocalSketchManager(){
    val sketch = remember { mutableStateOf(Sketch(mutableListOf())) }
    var selectedColor by remember { mutableStateOf(Color.Black) }
    val onClickedColor = { color:Color -> selectedColor = color }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Red)) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(0.1f)
            .background(Color.Cyan)) {
            ColorPalette(
                selectedColor,
                listOf(Color.Black, Color.Blue, Color.Cyan, Color.Magenta, Color.Green),
                onClickedColor
            )
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .background(Color.Green)) {
            SketchViewerWithEventer(sketch, selectedColor)
        }
    }
}

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
        LocalSketchManager()
    }
}