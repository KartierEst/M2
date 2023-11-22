package com.example.tp4

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import java.sql.Timestamp

class Line(var points : MutableList<Pair<Float, Float>>, var color: Color, val timestamp: Long) {
}