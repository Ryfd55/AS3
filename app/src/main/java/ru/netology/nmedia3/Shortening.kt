package ru.netology.nmedia3

object Shortening {
    fun shortening(number:Long): String{
        return when (number.toString().length){
            in 1..3 -> number.toString()
            in 4..6 ->  ((number.toInt()/100).toDouble()/10).toString().dropLastWhile{
                it == '0'
            }.dropLastWhile{
                it == '.'
            }+"K"
            in 7..9 ->  ((number.toInt()/100000).toDouble()/10).toString().dropLastWhile{
                it == '0'
            }.dropLastWhile{
                it == '.'
            }+"M"
            else -> "> 1B"
        }
    }
}