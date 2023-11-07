package com.example.tp3

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LetterDisplayer(letter: Char, modifier: Modifier = Modifier, color: Color, borderColor: Color) {
    Box(modifier.border(1.dp, borderColor)){
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
                Text(text, fontSize = med.sp, color = color)
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
