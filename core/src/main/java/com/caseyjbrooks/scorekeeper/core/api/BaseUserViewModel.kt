package com.caseyjbrooks.scorekeeper.core.api

import com.caseyjbrooks.scorekeeper.core.db.users.GameUser
import com.caseyjbrooks.scorekeeper.core.db.users.User
import org.json.JSONObject

abstract class BaseUserViewModel<T>(
        var component: BaseComponent,
        var gameViewModel: T,
        var user: User,
        var gameUser: GameUser
) {

    init {
        val data = JSONObject(gameUser.gameData)
        initFromData(data)
    }

    abstract fun initFromData(savedData: JSONObject)

    abstract fun getSaveData() : JSONObject

    fun saveUser() {
        val data = getSaveData()
        gameUser.gameData = data.toString()
        component.db().gameUserDao().updateGameUser(gameUser)
    }

    open fun reset() {
        saveUser()
    }

}
