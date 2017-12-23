package com.caseyjbrooks.scorekeeper.core

import android.content.SharedPreferences
import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int){
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { replace(frameId, fragment) }
}

class HoloColor {
    companion object {
        fun blueBright()  = Color.parseColor("#00ddff")
        fun blueLight()   = Color.parseColor("#33b5e5")
        fun blueDark()    = Color.parseColor("#0099cc")
        fun greenLight()  = Color.parseColor("#99cc00")
        fun greenDark()   = Color.parseColor("#669900")
        fun redLight()    = Color.parseColor("#ff4444")
        fun redDark()     = Color.parseColor("#cc0000")
        fun purple()      = Color.parseColor("#aa66cc")
        fun orangeLight() = Color.parseColor("#ffbb33")
        fun orangeDark()  = Color.parseColor("#ff8800")
    }
}


fun SharedPreferences.findBoolean(key: String, default: () -> Boolean): Boolean {
    return if(this.contains(key)) { this.getBoolean(key, false) } else { default() }
}
fun SharedPreferences.findFloat(key: String, default: () -> Float): Float {
    return if(this.contains(key)) { this.getFloat(key, 0.0f) } else { default() }
}
fun SharedPreferences.findInt(key: String, default: () -> Int): Int {
    return if(this.contains(key)) { this.getInt(key, 0) } else { default() }
}
fun SharedPreferences.findLong(key: String, default: () -> Long): Long {
    return if(this.contains(key)) { this.getLong(key, 0) } else { default() }
}
fun SharedPreferences.findString(key: String, default: () -> String): String {
    return if(this.contains(key)) { this.getString(key, "") } else { default() }
}
fun SharedPreferences.findStringSet(key: String, default: () -> Set<String>): Set<String> {
    return if(this.contains(key)) { this.getStringSet(key, emptySet<String>()) } else { default() }
}
