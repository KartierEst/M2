package com.example.tp3

enum class MorseSymbol { DOT, DASH }

val MORSE_ALPHABET = mapOf(
    'A' to arrayOf(MorseSymbol.DOT, MorseSymbol.DASH),
    'B' to arrayOf(MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT),
    'C' to arrayOf(MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DOT),
    'D' to arrayOf(MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT),
    'E' to arrayOf(MorseSymbol.DOT),
    'F' to arrayOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DOT),
    'G' to arrayOf(MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DOT),
    'H' to arrayOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT),
    'I' to arrayOf(MorseSymbol.DOT, MorseSymbol.DOT),
    'J' to arrayOf(MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH),
    'K' to arrayOf(MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DASH),
    'L' to arrayOf(MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT),
    'M' to arrayOf(MorseSymbol.DASH, MorseSymbol.DASH),
    'N' to arrayOf(MorseSymbol.DASH, MorseSymbol.DOT),
    'O' to arrayOf(MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH),
    'P' to arrayOf(MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DOT),
    'Q' to arrayOf(MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DASH),
    'R' to arrayOf(MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DOT),
    'S' to arrayOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT),
    'T' to arrayOf(MorseSymbol.DASH),
    'U' to arrayOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DASH),
    'V' to arrayOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DASH),
    'W' to arrayOf(MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DASH),
    'X' to arrayOf(MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DASH),
    'Y' to arrayOf(MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DASH),
    'Z' to arrayOf(MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT),
)