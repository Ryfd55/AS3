package ru.netology.nmedia3

object Shortening {
    fun shortening(number: Long): String {
        return when (number.toString().length) {
            in 1..3 -> number.toString()
            4 -> ((number.toInt() / 100).toDouble() / 10).toString().dropLastWhile {
                it == '0'
            }.dropLastWhile {
                it == '.'
            } + "K"

            in 5..6 -> (number.toInt() / 1000).toString() + "K"
            7 -> ((number.toInt() / 100000).toDouble() / 10).toString().dropLastWhile {
                it == '0'
            }.dropLastWhile {
                it == '.'
            } + "M"

            in 8..9 -> (number.toInt() / 1000000).toString() + "M"

            else -> "> 1B"
        }
    }
}