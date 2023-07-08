package ru.netology.nmedia3.util

object Shortening {
    fun shortening(number: Int): String {
        return when (number.toString().length) {
            in 1..3 -> number.toString()
            4 -> ((number/ 100).toDouble() / 10).toString().dropLastWhile {
                it == '0'
            }.dropLastWhile {
                it == '.'
            } + "K"

            in 5..6 -> (number/ 1000).toString() + "K"
            7 -> ((number / 100000).toDouble() / 10).toString().dropLastWhile {
                it == '0'
            }.dropLastWhile {
                it == '.'
            } + "M"

            in 8..9 -> (number / 1000000).toString() + "M"

            else -> "> 1B"
        }
    }
}