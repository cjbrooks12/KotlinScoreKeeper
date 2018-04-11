package com.caseyjbrooks.games.basic

import android.os.Handler
import android.util.Log
import com.caseyjbrooks.scorekeeper.core.api.BaseComponent
import com.caseyjbrooks.scorekeeper.core.api.BaseUserViewModel
import com.caseyjbrooks.scorekeeper.core.db.users.GameUser
import com.caseyjbrooks.scorekeeper.core.db.users.User
import org.json.JSONArray
import org.json.JSONObject


class TallyUserViewModel(
        component: BaseComponent,
        gameViewModel: TallyGameViewModel,
        user: User,
        gameUser: GameUser
) : BaseUserViewModel<TallyGameViewModel>(component, gameViewModel, user, gameUser) {

    val id: Long = user.id

    var score = 0
    var tempScore = 0
    lateinit var history: MutableList<Int>
    var selected = false

    override fun initFromData(savedData: JSONObject) {
        if(savedData.has("score")) {
            score = savedData.getInt("score")
        }
        if(savedData.has("history")) {
            history = emptyList<Int>().toMutableList()
            val historyArray = savedData.getJSONArray("history")
            for (i in 0 until historyArray.length()) {
                history.add(historyArray.getInt(i))
            }
        }
        else {
            history = listOf(0).toMutableList()
        }
    }

    override fun getSaveData() : JSONObject {
        val data = JSONObject()
        data.put("score", score)

        val historyArray = JSONArray()
        history.forEach { historyArray.put(it) }

        data.put("history", historyArray)

        Log.v("getSaveData", data.toString())

        return data
    }

    fun increment(i: Int) {
        tempScore += gameViewModel.buttonValues[i]
        startAutosave()
    }

    fun decrement(i: Int) {
        tempScore -= gameViewModel.buttonValues[i]
        startAutosave()
    }

    fun commitTempScore() {
        selected = false
        if(tempScore != 0) {
            history.add(tempScore)
            score += tempScore
            tempScore = 0
            saveUser()
        }
    }

    fun toggleSelected() {
        selected = !selected
    }

    override fun reset() {
        score = 0
        tempScore = 0
        history = listOf(0).toMutableList()
        selected = false
        super.reset()
    }

    var handler: Handler? = null
    var autosaveRunnable: Runnable? = null

    private fun startAutosave() {
        if(handler != null && autosaveRunnable != null) {
            handler!!.removeCallbacks(autosaveRunnable!!)
        }
        if(handler == null) {
            handler = Handler()
        }
        if(autosaveRunnable == null) {
            autosaveRunnable = Runnable {
                commitTempScore()
                gameViewModel.adapter.notifyDataSetChanged()
            }
        }
        handler!!.postDelayed(autosaveRunnable, TIMEOUT)
    }

    companion object {
        const val TIMEOUT = 6000L
    }

}
