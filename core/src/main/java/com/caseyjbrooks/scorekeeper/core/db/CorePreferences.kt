package com.caseyjbrooks.scorekeeper.core.db

import android.content.Context
import android.content.SharedPreferences
import org.jetbrains.anko.defaultSharedPreferences


class CorePreferences(val context: Context, val prefsFile: String? = null) {

    fun set(cb: SharedPreferences.Editor.() -> Unit) {
        val sharedPref = if(!prefsFile.isNullOrBlank())
            { context.getSharedPreferences(prefsFile, Context.MODE_PRIVATE) }
        else
            { context.defaultSharedPreferences }

        val editor = sharedPref.edit()
        cb(editor)
        editor.apply()
    }

    fun <T> get(cb: SharedPreferences.() -> T): T {
        val sharedPref = if(!prefsFile.isNullOrBlank())
        { context.getSharedPreferences(prefsFile, Context.MODE_PRIVATE) }
        else
        { context.defaultSharedPreferences }

        return cb(sharedPref)
    }

}
