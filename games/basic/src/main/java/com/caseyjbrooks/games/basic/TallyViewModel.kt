package com.caseyjbrooks.games.basic

import com.caseyjbrooks.scorekeeper.core.db.users.User

class TallyViewModel(
        var user: User,
        var service: TallyService,
        val initialScore: Int = 0
) {

    val id: Long = user.id

    var score = initialScore
    var tempScore = 0
    var history: MutableList<Int> = listOf(initialScore).toMutableList()
    var selected = false

    fun increment(i: Int) {
        tempScore += service.buttonValues[i]
    }

    fun decrement(i: Int) {
        tempScore -= service.buttonValues[i]
    }

    fun commitTempScore() {
        if(tempScore != 0) {
            history.add(tempScore)
            score += tempScore
            tempScore = 0
            selected = false
        }
    }

    fun toggleSelected() {
        selected = !selected
    }

    fun reset() {
        score = initialScore
        tempScore = 0
        history = listOf(initialScore).toMutableList()
        selected = false
    }

}
