package com.example.tp3

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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

object FoxtrotFlag: ICSFlag("foxtrot") {
    override val message = "Je suis désemparé ; communiquez avec moi."
    @Composable
    override fun Flag(modifier: Modifier) {
        val losange = GenericShape { size, _ ->
            moveTo(size.width/2, 0f)
            lineTo(0f, size.height/2)
            lineTo(size.width/2, size.height)
            lineTo(size.width, size.height/2)
            close()
        }
        Column(modifier) {
            Box(Modifier.fillMaxWidth().weight(1f, fill=true).background(color=Color.Red, shape=losange))
        }
        Column(modifier) {
            Box(Modifier.fillMaxWidth().weight(1f, fill=true).background(color=Color.White))
        }
    }
}

object QuebecFlag: ICSFlag("quebec") {
    override val message = "Mon navire est « indemne » et je demande la libre-pratique. "

    @Composable
    override fun Flag(modifier: Modifier) {
        Column(modifier) {
            Box(Modifier.fillMaxWidth().weight(1f, fill=true).background(color=Color.Yellow))
        }
    }
}

object TangoFlag: ICSFlag("tango") {
    override val message = "Ne me gênez pas : je fais du chalutage jumelé"

    @Composable
    override fun Flag(modifier: Modifier) {
        Row(modifier) {
            Box(
                Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .background(Color.Red))
            Box(
                Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .background(Color.White))
            Box(
                Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .background(Color.Blue))
        }
    }
}

object WhiskeyFlag: ICSFlag("whiskey") {
    override val message = "J’ai besoin d’assistance médicale"

    @Composable
    override fun Flag(modifier: Modifier) {
        val whiteSquare = GenericShape { size, _ ->
            moveTo(size.width/5, size.height/5)
            lineTo(size.width/5, size.height*4/5)
            lineTo(size.width*4/5, size.height*4/5)
            lineTo(size.width*4/5, size.height/5)
            close()
        }
        val redSquare = GenericShape { size, _ ->
            moveTo(size.width*2/5, size.height*2/5)
            lineTo(size.width*2/5, size.height*3/5)
            lineTo(size.width*3/5, size.height*3/5)
            lineTo(size.width*3/5, size.height*2/5)
            close()
        }
        Column(modifier) {
            Box(Modifier.fillMaxWidth().weight(1f, fill=true).background(color=Color.Blue))
        }
        Column(modifier) {
            Box(Modifier.fillMaxWidth().weight(1f, fill=true).background(color=Color.White, shape=whiteSquare))
        }
        Column(modifier) {
            Box(Modifier.fillMaxWidth().weight(1f, fill=true).background(color=Color.Red, shape=redSquare))
        }

    }
}

object ZuluFlag: ICSFlag("zulu") {
    override val message = "J’ai besoin d’un remorqueur."

    fun createTriangle(xStart: Float, yStart: Float, xEnd:Float, yEnd: Float): GenericShape{
        return GenericShape { size, _ ->
            moveTo(xStart, yStart)
            lineTo(size.width / 2, size.height / 2)
            lineTo(xEnd, yEnd)
            lineTo(xStart, yStart)
            close()
        }
    }

    @Composable
    override fun Flag(modifier: Modifier) {
        val toUpTriangle = GenericShape { size, _ ->
            moveTo(0f, size.height)
            lineTo(size.width / 2, size.height / 2)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        val toDownTriangle = GenericShape { size, _ ->
            moveTo(0f, 0f)
            lineTo(size.width / 2, size.height / 2)
            lineTo(size.width, 0f)
            lineTo(0f, 0f)
            close()
        }
        val toRightTriangle = GenericShape { size, _ ->
            moveTo(0f, 0f)
            lineTo(size.width / 2, size.height / 2)
            lineTo(0f, size.height)
            lineTo(0f, 0f)
            close()
        }
        val toLeftTriangle = GenericShape { size, _ ->
            moveTo(size.width, 0f)
            lineTo(size.width / 2, size.height / 2)
            lineTo(size.width, size.height)
            lineTo(size.width, 0f)
            close()
        }
        Column(modifier=modifier) {
            Box(Modifier.fillMaxWidth().weight(1f, fill=true).background(color = Color.Red, shape = toUpTriangle))
        }
        Column(modifier=modifier) {
            Box(Modifier.fillMaxWidth().weight(1f, fill=true).background(color = Color.Yellow, shape = toDownTriangle))
        }
        Column(modifier=modifier) {
            Box(Modifier.fillMaxWidth().weight(1f, fill=true).background(color = Color.Black, shape = toRightTriangle))
        }
        Column(modifier=modifier) {
            Box(Modifier.fillMaxWidth().weight(1f, fill=true).background(color = Color.Blue, shape = toLeftTriangle))
        }
    }
}

object OscarFlag: ICSFlag("oscar") {
    override val message = "Un homme à la mer."

    @Composable
    override fun Flag(modifier: Modifier) {
        val toDownLeftTriangle = GenericShape { size, _ ->
            moveTo(0f, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            lineTo(0f, 0f)
            close()
        }
        val toUpRightTriangle = GenericShape { size, _ ->
            moveTo(0f, 0f)
            lineTo(size.width, size.height)
            lineTo(size.width, 0f)
            lineTo(0f, 0f)
            close()
        }
        Column(modifier=modifier) {
            Box(Modifier.fillMaxWidth().weight(1f, fill=true).background(color = Color.Yellow, shape = toDownLeftTriangle))
        }
        Column(modifier=modifier) {
            Box(Modifier.fillMaxWidth().weight(1f, fill=true).background(color = Color.Red, shape = toUpRightTriangle))
        }
    }
}

object NovemberFlag: ICSFlag("november") {
    override val message =
        "Non (réponse négative) ou Signal qui précède doit être compris sous forme négative."

    @Composable
    override fun Flag(modifier: Modifier) {
        Box(modifier = modifier) {
            for (i in 0..3) {
                for (y in 0..3) {
                    val square = GenericShape { size, _ ->
                        moveTo(0f + i * (size.width / 4), 0f + y * (size.height / 4))
                        lineTo((size.width / 4) + i * (size.width / 4), 0f + y * (size.height / 4))
                        lineTo((size.width / 4) + i * (size.width / 4), (size.height / 4) + y * (size.height / 4))
                        lineTo(0f + i * (size.width / 4), (size.height / 4) + y * (size.height / 4))
                        close()

                    }
                    if ((i % 2 == 0 && y % 2 == 0) || (i % 2 == 1 && y % 2 == 1)) {
                        Column() {
                            Box(
                                Modifier.fillMaxWidth()
                                    .weight(1f, fill = true)
                                    .background(color = Color.Blue, shape = square)
                            )
                        }
                    } else {
                        Column() {
                            Box(
                                Modifier.fillMaxWidth()
                                    .weight(1f, fill = true)
                                    .background(color = Color.White, shape = square)
                            )
                        }
                    }
                }
            }
        }
    }
}

object EchoFlag: ICSFlag("echo") {
    override val message = "Je viens sur tribord."

    @Composable
    override fun Flag(modifier: Modifier) {
        Column(modifier) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.Blue))
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.Red))
        }
    }
}

object IndiaFlag: ICSFlag("india") {
    override val message = "Ne me gênez pas, je manœuvre avec difficulté"

    @Composable
    override fun Flag(modifier: Modifier) {
        Column(modifier) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(2f)
                    .background(Color.Yellow))


        }
        Column (modifier = Modifier.wrapContentSize(Alignment.Center)){
            Box(
                Modifier
                    .clip(shape = CircleShape)
                    .background(Color.Black)
                    .size(250.dp)
            )
        }
    }
}

object YankeeFlag: ICSFlag("yankee") {
    override val message = "Mon ancre chasse."

    @Composable
    override fun Flag(modifier: Modifier) {
        fun makeTriangle() : GenericShape {
            return GenericShape { size, _ ->
                // 1)
                moveTo(0f, 0f)
                // 2)
                lineTo(size.width / 5, 0f)
                // 3)
                lineTo(0f, size.height / 5)
            }
        }
        fun makeLine(lineNumber: Int) : GenericShape {
            return GenericShape { size, _ ->
                val valueWidthSide = if(lineNumber <= 5) size.width/5 * lineNumber else size.width
                val valueHeightSide = if(lineNumber <= 5) 0f else size.height
                val valueWidthSideDouble = if(lineNumber <= 5) size.width/5 * 2 *  lineNumber else size.width
                val valueWidthFull = if(lineNumber <= 5) 0f else size.width/5
                val valueHeightFull = if(lineNumber <= 5)  (size.height/5) * (lineNumber) else size.height
                val valueHeightSideDouble = if(lineNumber <= 5)  (size.height/5) * (lineNumber) * 2 else size.height

                // 1)
                moveTo(valueWidthSide, valueHeightSide/5 * (lineNumber-5))

                // 2)
                lineTo(valueWidthSideDouble , valueHeightSide * (2) * (lineNumber-5))

                // 3)
                lineTo(valueWidthFull * (lineNumber-5) * (2), valueHeightSideDouble)

                // 4)
                lineTo(valueWidthFull * (lineNumber-5) , valueHeightFull)

            }
        }

        val triangleShape = makeTriangle()

        Column(modifier) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = true)
                    .background(color = Color.Red, shape = triangleShape))
        }
        for (i in 1..10) {
            val LineShape = makeLine(i)
            if(i % 2 == 0 ) {
                Column {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f, fill = true)
                            .background(color = Color.Red, shape = LineShape)
                    )
                }
            } else {
                Column {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f, fill = true)
                            .background(color = Color.Yellow, shape = LineShape))
                }
            }
        }
    }
}

object UniformFlag : ICSFlag("uniform") {
    override val message = "Vous courez vers un danger."

    @Composable
    override fun Flag(modifier: Modifier) {
        Column(modifier) {
            Box(Modifier.fillMaxWidth().weight(1f)) {
                Row(modifier) {
                    Box(Modifier.fillMaxHeight().weight(1f).background(Color.Red))
                    Box(Modifier.fillMaxHeight().weight(1f).background(Color.White))
                }
            }
            Box(Modifier.fillMaxWidth().weight(1f)) {
                Row(modifier) {
                    Box(Modifier.fillMaxHeight().weight(1f).background(Color.White))
                    Box(Modifier.fillMaxHeight().weight(1f).background(Color.Red))
                }
            }
        }
    }
}

object CharlieFlag: ICSFlag("charlie") {
    override val message =
        "Oui. (réponse affirmative, ou le groupe qui" +
                " précède doit être compris comme une affirmation)"

    @Composable
    override fun Flag(modifier: Modifier) {
        Column(modifier) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.Blue))
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.White))
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.Red))
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.White))
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.Blue))
        }
    }
}

object KiloFlag: ICSFlag("kilo") {
    override val message = "Je désire communiquer avec vous ou je vous invite à transmettre."

    @Composable
    override fun Flag(modifier: Modifier) {
        Column(modifier) {
            Row(modifier) {
                Box(Modifier.fillMaxHeight().weight(1f).background(Color.Yellow))
                Box(Modifier.fillMaxHeight().weight(1f).background(Color.Blue))
            }
        }
    }
}

object PapaFlag: ICSFlag("papa") {
    override val message = "Au port : Toutes les personnes doivent se présenter à bord, le navire va prendre la mer."

    @Composable
    override fun Flag(modifier: Modifier) {
        Column(modifier) {
            Box(Modifier.fillMaxWidth().weight(1f)) {
                Row(modifier) {
                    Box(Modifier.fillMaxHeight().weight(1f).background(Color.Blue))
                    Box(Modifier.fillMaxHeight().weight(1f).background(Color.Blue))
                    Box(Modifier.fillMaxHeight().weight(1f).background(Color.Blue))
                }
            }
            Box(Modifier.fillMaxWidth().weight(1f)) {
                Row(modifier) {
                    Box(Modifier.fillMaxHeight().weight(1f).background(Color.Blue))
                    Box(Modifier.fillMaxHeight().weight(1f).background(Color.White))
                    Box(Modifier.fillMaxHeight().weight(1f).background(Color.Blue))
                }
            }
            Box(Modifier.fillMaxWidth().weight(1f)) {
                Row(modifier) {
                    Box(Modifier.fillMaxHeight().weight(1f).background(Color.Blue))
                    Box(Modifier.fillMaxHeight().weight(1f).background(Color.Blue))
                    Box(Modifier.fillMaxHeight().weight(1f).background(Color.Blue))
                }
            }
        }
    }
}

object Sierra : ICSFlag("sierra") {
    override val message = "Je bats en arrière."

    @Composable
    override fun Flag(modifier: Modifier) {
        Box(modifier = modifier.background(Color.White)) {
            val square = GenericShape { size, _ ->
                moveTo((size.width / 3), (size.height / 3))
                lineTo((2 * size.width / 3), (size.height / 3))
                lineTo((2 * size.width / 3), (2 * size.height / 3))
                lineTo((size.width / 3), (2 * size.height / 3))
                close()
            }
            Column() {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = true)
                        .background(color = Color.Blue, shape = square)
                )
            }
        }
    }
}

object Alpha : ICSFlag("alpha") {
    override val message = "J'ai un scaphandrier en plongée; tenez-vous à distance et avancez lentement."

    private val TriangleCut = GenericShape { size, layoutDirection ->
        moveTo(0f, 0f)
        lineTo(size.width, 0f)
        lineTo(size.width / 2, size.height / 2)
        lineTo(size.width, size.height)
        lineTo(0f, size.height)
        lineTo(0f, 0f)
        close()
    }

    @Composable
    override fun Flag(modifier: Modifier) {
        Row {
            Box (modifier=Modifier.background(Color.White)
                .weight(1f)
                .fillMaxHeight())
            Box (modifier=Modifier.background(color=Color.Blue, shape= TriangleCut)
                .weight(1f)
                .fillMaxHeight())
        }
    }
}

object Bravo : ICSFlag("bravo") {
    override val message = "Je charge, ou décharge, ou je transporte des marchandises dangereuses."

    private val TriangleCut = GenericShape { size, layoutDirection ->
        moveTo(0f, 0f)
        lineTo(size.width, 0f)
        lineTo(size.width / 2, size.height / 2)
        lineTo(size.width, size.height)
        lineTo(0f, size.height)
        lineTo(0f, 0f)
        close()
    }

    @Composable
    override fun Flag(modifier: Modifier) {
        Row {
            Box (modifier=Modifier.background(Color.Red)
                .weight(1f)
                .fillMaxHeight())
            Box (modifier=Modifier.background(color=Color.Red, shape= TriangleCut)
                .weight(1f)
                .fillMaxHeight())
        }
    }
}





