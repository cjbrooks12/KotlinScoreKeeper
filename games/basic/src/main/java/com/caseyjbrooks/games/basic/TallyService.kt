package com.caseyjbrooks.games.basic

import android.content.Context
import com.caseyjbrooks.scorekeeper.core.db.CorePreferences

class TallyService(val context: Context, buttonValues: Array<Int>? = null) {

    var buttonValues: Array<Int>
    val prefs: CorePreferences

    init {
        prefs = CorePreferences(context, "tallyService")

        if(buttonValues != null) {
            this.buttonValues = buttonValues
        }
        else {
            this.buttonValues = prefs.get {
                getStringSet("buttonValues", mutableSetOf("1", "5", "10", "25", "50"))
                        .map { Integer.parseInt(it) }
                        .toTypedArray()
            }
        }

        this.buttonValues.sort()
    }

    fun changeButtonValue(index: Int, value: Int) {
        buttonValues[index] = value

        this.buttonValues.sort()

        prefs.set {
            putStringSet("buttonValues", buttonValues.map { Integer.toString(it) }.toMutableSet())
        }
    }


}
