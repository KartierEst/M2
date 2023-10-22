package com.example.tp3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tp3.ui.theme.TP3Theme

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
    VerticalFillBar(fillRatio = 0.2f)
    Box(modifier.fillMaxWidth(),contentAlignment = Alignment.Center) {
        Text(
            "20s",
            textAlign = TextAlign.Center,
            fontSize = 7.sp,
            color = Color.Black
        )
    }
}

@Composable
fun Countdown(duration: Int, running: Boolean, onEnd: () -> Unit) {


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    TP3Theme {
        //Greeting2("Android"
        //VerticalFillBar(fillRatio = 0.2f)
        CountdownBar(50,10)
    }
}