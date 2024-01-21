package com.example.examen

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.examen.ui.theme.ExamenTheme
import kotlinx.coroutines.delay
import java.lang.Math.pow
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}

fun sortByCenter(list: Array<String>, centerIndex: Int): List<String> {
    var start = list.slice(0..<centerIndex).toMutableList();
    var end = list.slice(centerIndex+1..<list.size).toMutableList()

    if(start.size > 3){
        end.addAll(end.size,start.slice(0..<maxOf(start.size - 3, 3 - start.size)))
        start = start.subList(start.size - 3, start.size)
    }
    else if(end.size > 3){
        start.addAll(0, end.subList(3, end.size))
        end = end.subList(0,3)
    }

    return start + list[centerIndex] + end
}

@Composable
fun SlotMachineRoll(symbols: Array<String>, fontSize: TextUnit, centerIndex: Int){
    Column {
        sortByCenter(symbols, centerIndex).forEach {
            Box(
                Modifier
                    .size(40.dp)
                    .background(
                        if (it == symbols[centerIndex]) {
                            Color.Yellow
                        } else {
                            Color.Transparent
                        }
                    )
                    .border(
                        width = if (it == symbols[centerIndex]) {
                            5.dp
                        } else {
                            (-1).dp
                        }, color = Color.Red
                    )
                    .padding(5.dp), contentAlignment = Alignment.Center){
                Text(text = it,fontSize = fontSize)
            }
        }
    }
}

@Composable
fun SlotMachineRolls(symbols: Array<String>, fontSize: TextUnit, centerIndices: List<Int>){
    Row {
        centerIndices.forEachIndexed{ index, element ->
            SlotMachineRoll(symbols = symbols, fontSize = fontSize, centerIndex = element)
            Spacer(Modifier.width(5.dp))
            if(index != centerIndices.size - 1) {
                Box(
                    modifier = Modifier
                        .background(Color.Blue)
                        .height(280.dp)
                        .width(5.dp)
                )
                Spacer(Modifier.width(5.dp))
            }
        }
    }
}

@Composable
fun SlotMachine(symbols: Array<String>, fontSize: TextUnit, rollNumber: Int, running: Boolean, onDraw: (List<String>) -> Unit){
    var centerIndice by remember { mutableStateOf((0..<rollNumber).map{(0..6).random()})}
    if(running){
        Box(modifier = Modifier
            .size(280.dp)
            .background(Color.Green)){
            Text("Le jeu est en cours")
        }
        centerIndice = (0..<rollNumber).map{(0..6).random()}
    }
    else {
        SlotMachineRolls(symbols = symbols, fontSize = fontSize, centerIndices = centerIndice)
        onDraw(centerIndice.map { x -> symbols[x] })
    }
}

@Preview
@Composable
fun SlotMachinePreview(){
    var running by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf(listOf<String>()) }
    val SYMBOLS = arrayOf("🎲", "🏦", "🍒", "🍓", "💰", "🏇", "🥹")
    Column {
        SlotMachine(symbols = SYMBOLS, fontSize = 20.sp, rollNumber = 4, running = running, onDraw = {r -> result = r} )
        Column(Modifier.align(Alignment.CenterHorizontally)) {
            Button(onClick = { running = true}) {
                Text(text = "Start")
            }
            Row {
                Text(text = "result :")
                result.forEach {
                    Text(text = it)
                }
            }
        }
    }

    LaunchedEffect(running, result){
        if(running){
            delay(1000)
            running = false
        }
    }
}

@Composable
fun VerticalGauge(fillRatio: Float, modifier: Modifier = Modifier){
    Column(
        Modifier
            .border(5.dp, Color.Black)
            .width(50.dp)
            .height(500.dp)){
        Box(modifier = Modifier
            .fillMaxHeight(1 - fillRatio)
            .background(Color.White)
            .width(50.dp))
        Box(
            Modifier
                .fillMaxHeight()
                .width(50.dp)
                .background(Color.Blue))
    }
}

@Composable
fun Handle(onReleasedHandle: (Float) -> Unit, modifier: Modifier = Modifier){
    var fillRatio by remember { mutableFloatStateOf(0f) }
    Box(
        modifier.pointerInput(Unit) {
            awaitPointerEventScope {
                while (true) {
                    val event = awaitPointerEvent()
                    when (event.type) {
                        PointerEventType.Press -> {
                            if (((fillRatio * 100.0).roundToInt() / 100.0) < 1.00) {
                                fillRatio += 0.01f
                            }
                        }
                        PointerEventType.Move -> {
                            if(((fillRatio * 100.0).roundToInt() / 100.0) < 1.00) {
                                fillRatio += 0.01f
                            }
                        }
                        PointerEventType.Release -> {
                            onReleasedHandle(fillRatio)
                            fillRatio = 0f
                        }
                    }
                }
            }
        }
    ){
        VerticalGauge(fillRatio = fillRatio)
    }
    LaunchedEffect(fillRatio){
        delay(5000)
    }
}

@Preview
@Composable
fun HandlePreview(){
    var lastRatio by remember { mutableFloatStateOf(0f) }
    Column(Modifier.fillMaxHeight()) {
        Handle(onReleasedHandle = {f -> lastRatio = f})
        Text("${((lastRatio * 100.0).roundToInt() / 100.0)}")
    }
}

@Composable
fun SlotMachineWithHandle(symbols: Array<String>, fontSize: TextUnit, rollNumber: Int, onDraw: (List<String>) -> Unit){
    var lastRatio by remember { mutableFloatStateOf(0f) }
    var running by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf(listOf<String>()) }
    Row {
        Column(Modifier.fillMaxHeight()) {
            Handle(onReleasedHandle = {f -> lastRatio = f; running = true})
            Text("${((lastRatio * 100.0).roundToInt() / 100.0)}")
        }
        Column {
            SlotMachine(symbols = symbols, fontSize = fontSize, rollNumber = rollNumber, running = running, onDraw = { r -> result = r; onDraw(r) })
            Row(Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = "result :")
                result.forEach {
                    Text(text = it)
                }
            }
        }
    }
    LaunchedEffect(running, result){
        if(running){
            delay((lastRatio * 10000f).toLong())
            running = false
        }
    }
}

@Composable
fun MoneySlotMachine(symbols: Array<String>, fontSize: TextUnit, rollNumber: Int, initialAmount: Int, onGameEnd: (Int) -> Unit){
    var amount by remember { mutableIntStateOf(initialAmount) }
    var result by remember { mutableStateOf(listOf<String>()) }
    Column {
        Box(
            Modifier
                .height(50.dp)
                .fillMaxWidth()){
            Text(text = "$amount", textAlign = TextAlign.Center)
        }
        SlotMachineWithHandle(symbols = symbols, fontSize = fontSize, rollNumber = rollNumber, onDraw = {r -> result = r; amount--;})
        Button(onClick = { onGameEnd(amount) }) {}
    }
    LaunchedEffect(amount, result){
        if(amount == 0){
            onGameEnd(amount)
        }
        amount += calculMoney(result)
    }
}

fun calculMoney(result: List<String>): Int {
    val tmp = result.sortedByDescending { result.count{ r -> r == it} }
    return 2.0.pow((tmp[0].count() - 1).toDouble()).toInt();
}

@Composable
fun EndScreen(amount: Int){
    Column(Modifier.fillMaxSize()) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Red)
                .align(Alignment.CenterHorizontally)) {
            Text(text = "Your amount is $amount", textAlign = TextAlign.Center)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameManager(){
    var amount by remember { mutableIntStateOf(0) }
    val SYMBOLS = arrayOf("🎲", "🏦", "🍒", "🍓", "💰", "🏇", "🥹")
    val navController = rememberNavController()
    BoxWithConstraints(modifier=Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {Text("Tu veux gagner des millions ?")},
                )
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                )  {
                    NavHost(navController = navController, startDestination = "Game") {
                        composable("Game") { MoneySlotMachine(
                            symbols = SYMBOLS,
                            fontSize = 20.sp,
                            rollNumber = 4,
                            initialAmount = 5,
                            onGameEnd = {r -> amount = r; navController.navigate("GameOver")}
                        ) }
                        composable("GameOver") { EndScreen(amount); Button(onClick = { navController.navigate("Game") }) {
                            Text("Restart")
                        } }
                    }
                }
            })
    }

}

/*    Button(onClick = {}) {
        Text("Play Again")
    }*/

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val SYMBOLS = arrayOf("🎲", "🏦", "🍒", "🍓", "💰", "🏇", "🥹")
    ExamenTheme {
        //SlotMachineRoll(symbols = SYMBOLS, fontSize = 12.sp, centerIndex = 6)
        //SlotMachineRolls(symbols = SYMBOLS, fontSize = 20.sp, centerIndices = (0..6).toList())
        //VerticalGauge(fillRatio = 0.25f)
        //SlotMachineWithHandle(symbols = SYMBOLS, fontSize = 20.sp, rollNumber = 4, onDraw = {} )
        GameManager()
    }
}