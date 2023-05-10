package ru.netology.nmedia3.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object AndroidUtils {
    fun hideKeyboard(view: View){
        val inputMethodManager =
            view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken,0)
    }
    fun rwr(): Int{
        34+43
        return 2
    }

}