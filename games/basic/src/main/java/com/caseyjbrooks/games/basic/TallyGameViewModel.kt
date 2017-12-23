package com.caseyjbrooks.games.basic

import android.text.InputType
import android.widget.Button
import com.caseyjbrooks.scorekeeper.core.api.BaseActivity
import com.caseyjbrooks.scorekeeper.core.api.BaseComponent
import com.caseyjbrooks.scorekeeper.core.db.users.User
import com.caseyjbrooks.scorekeeper.core.games.BaseGameViewModel
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.Appcompat

class TallyGameViewModel(
        activity: BaseActivity,
        component: BaseComponent,
        gameId: Long
) : BaseGameViewModel<TallyViewModel>(activity, component, "tally", gameId) {

    lateinit var tallyService: TallyService
    val plusButtons: MutableList<Button> = emptyList<Button>().toMutableList()
    val minusButtons: MutableList<Button> = emptyList<Button>().toMutableList()

    override fun setup() {
        tallyService = TallyService(activity)
        adapter = TallyUserListAdapter(activity, component, this)

        super.setup()
    }

    override fun mapper(user: User): TallyViewModel {
        return TallyViewModel(user, tallyService)
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
        val currentValue = tallyService.buttonValues[index]

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
        tallyService.changeButtonValue(index, newButtonValue)

        for ((i, buttonVal) in tallyService.buttonValues.withIndex()) {
            plusButtons[i].text = "+$buttonVal"
            minusButtons[i].text = "-$buttonVal"
        }
    }

}
