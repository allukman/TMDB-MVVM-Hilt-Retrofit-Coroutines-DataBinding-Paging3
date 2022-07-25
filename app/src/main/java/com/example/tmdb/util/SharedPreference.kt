package com.example.tmdb.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(private val context: Context) {
    companion object {
        const val PREF_NAME = "ORDER_AND_GO"
        const val GENRE = "GENRE"
        const val TO_GENRE = "TO_GENRE"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun saveGenre(genre: String) {
        editor.putString(GENRE, genre)
        editor.commit()
    }

    fun saveNavigateGenre(filter: Boolean) {
        editor.putBoolean(TO_GENRE, filter)
        editor.commit()
    }

    fun getGenre(): String {
        return sharedPreferences.getString(GENRE, "")!!
    }

    fun getNavigateGenre(): Boolean {
        return sharedPreferences.getBoolean(TO_GENRE, false)
    }
}