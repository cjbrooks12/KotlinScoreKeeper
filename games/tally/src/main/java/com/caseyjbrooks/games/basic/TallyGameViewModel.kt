package com.caseyjbrooks.games.basic

import android.text.InputType
import android.widget.Button
import com.caseyjbrooks.scorekeeper.core.api.BaseActivity
import com.caseyjbrooks.scorekeeper.core.api.BaseComponent
import com.caseyjbrooks.scorekeeper.core.api.BaseGameViewModel
import com.caseyjbrooks.scorekeeper.core.db.users.GameUser
import com.caseyjbrooks.scorekeeper.core.db.users.User
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.json.JSONArray
import org.json.JSONObject

class TallyGameViewModel(
        activity: BaseActivity,
        component: BaseComponent,
        gameId: Long
) : BaseGameViewModel<TallyUserViewModel>(activity, component, "tally", gameId) {

    val buttonValues: MutableList<Int> = emptyList<Int>().toMutableList()

    val plusButtons: MutableList<Button> = emptyList<Button>().toMutableList()
    val minusButtons: MutableList<Button> = emptyList<Button>().toMutableList()

    override fun setup() {
        adapter = TallyUserListAdapter(activity, component, this)

        super.setup()
    }

    override fun mapper(user: User, gameUser: GameUser): TallyUserViewModel {
        return TallyUserViewModel(component, this, user, gameUser)
    }

    override fun resetGame() {
        adapter.users.forEach { it.reset() }
        adapter.notifyDataSetChanged()
    }

    fun updateVal(index: Int, increment: Boolean) {
        adapter.users.filter { it.selected }.forEach {
            if(increment) {
                it.increment(index)
            }
            else {
                it.decrement(index)
            }
        }
        adapter.notifyDataSetChanged()
    }

    fun <UI> changeButtonValue(ui: AnkoContext<UI>, index: Int, increment: Boolean) = with(ui) {
        val currentValue = buttonValues[index]

        alert(Appcompat, "Change Button Value", "Change value from $currentValue") {
            customView {
                verticalLayout {
                    val newValue = editText {
                        hint = "New Value"
                        inputType = InputType.TYPE_CLASS_NUMBER
                    }
                    positiveButton("Save") {
                        if (newValue.text.isNotEmpty()) {
                            val newButtonValue = Math.abs(Integer.parseInt(newValue.text.toString()))
                            changeButtonValue(index, newButtonValue)
                            toast("Changed from $currentValue to $newButtonValue")
                        }
                    }
                    negativeButton("Cancel") { }
                }
            }
        }.show()
    }

    protected open fun changeButtonValue(index: Int, newButtonValue: Int) {
        buttonValues[index] = newButtonValue
        buttonValues.sort()

        for ((i, buttonVal) in buttonValues.withIndex()) {
            plusButtons[i].text = "+$buttonVal"
            minusButtons[i].text = "-$buttonVal"
        }

        saveGame()
    }

    override fun initFromData(savedData: JSONObject) {
        if(savedData.has("buttonValues")) {
            buttonValues.clear()
            val buttonValuesArray = savedData.getJSONArray("buttonValues")
            for (i in 0 until buttonValuesArray.length()) {
                buttonValues.add(buttonValuesArray.getInt(i))
            }
        }
        else {
            buttonValues.clear()
            buttonValues.addAll(listOf(1, 5, 10, 25, 50))
        }

        buttonValues.sort()
    }

    override fun getSaveData(): JSONObject {
        var data = JSONObject()

        var buttonValuesJson = JSONArray()
        buttonValues.forEach { buttonValuesJson.put(it) }

        data.put("buttonValues", buttonValuesJson)

        return data
    }
}
