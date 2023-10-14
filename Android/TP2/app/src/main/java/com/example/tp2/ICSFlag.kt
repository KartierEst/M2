package com.example.TP2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

sealed class ICSFlag(val codeWord: String) {
    // Letter of the flag (first letter of the code word in uppercase) */
    val letter = codeWord[0].uppercaseChar()

    // Meaning of the flag if raised on a ship */
    abstract val message: String

    // Implementation of a component to display the flag.
    @Composable
    abstract fun Flag(modifier: Modifier)

    companion object {
        /*

        This property will lazily get all the defined flags.
        It can work only if you have added the Kotlin reflection library in your dependencies section
        of the build.gradle file of the app:*
        implementation "org.jetbrains.kotlin:kotlin-reflect:$kotlin_version"*/
        val allFlags by lazy { ICSFlag::class.sealedSubclasses.mapNotNull { it.objectInstance }.associateBy{ it.letter } }

        /** Find a flag for a given letter */
        fun findFlag(letter: Char) = allFlags[letter]
    }
}

object DeltaFlag: ICSFlag("delta") {
    override val message = "Ne me gênez pas, je manœuvre avec difficulté"

    @Composable
    override fun Flag(modifier: Modifier) {
        Column(modifier) {
            Box(Modifier.fillMaxWidth().weight(1f).background(Color.Yellow))
            Box(Modifier.fillMaxWidth().weight(2f).background(Color.Blue))
            Box(Modifier.fillMaxWidth().weight(1f).background(Color.Yellow))
        }
    }
}

object HotelFlag: ICSFlag("hotel") {
    override val message = "J'ai un pilote à bord"

    @Composable
    override fun Flag(modifier: Modifier) {
        Column(modifier) {
            Row(modifier) {
                Box(Modifier.fillMaxHeight().weight(1f).background(Color.White))
                Box(Modifier.fillMaxHeight().weight(1f).background(Color.Red))
            }
        }
    }
}

object LimaFlag: ICSFlag("lima") {
    override val message = "Stoppez votre navire immédiatement"

    @Composable
    override fun Flag(modifier: Modifier) {
        Column(modifier) {
            Box(Modifier.fillMaxWidth().weight(1f)) {
                Row(modifier) {
                    Box(Modifier.fillMaxHeight().weight(1f).background(Color.Yellow))
                    Box(Modifier.fillMaxHeight().weight(1f).background(Color.Black))
                }
            }
            Box(Modifier.fillMaxWidth().weight(1f)) {
                Row(modifier) {
                    Box(Modifier.fillMaxHeight().weight(1f).background(Color.Black))
                    Box(Modifier.fillMaxHeight().weight(1f).background(Color.Yellow))
                }
            }
        }
    }
}