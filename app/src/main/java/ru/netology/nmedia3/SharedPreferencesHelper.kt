package ru.netology.nmedia3

import android.content.Context

object SharedPreferencesHelper {
    private const val PREF_NAME = "nmedia_preferences"
    private const val KEY_DRAFT_CONTENT = "draftContent"

    lateinit var value: String
    fun saveDraftContent(context: Context, draftContent: String) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_DRAFT_CONTENT, draftContent)
        editor.apply()
    }

    fun getDraftContent(context: Context): String {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_DRAFT_CONTENT, "") ?: ""
    }
}
