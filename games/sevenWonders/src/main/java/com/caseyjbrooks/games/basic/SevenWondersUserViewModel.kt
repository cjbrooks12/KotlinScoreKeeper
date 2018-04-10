package com.caseyjbrooks.games.basic

import com.caseyjbrooks.scorekeeper.core.api.BaseComponent
import com.caseyjbrooks.scorekeeper.core.api.BaseUserViewModel
import com.caseyjbrooks.scorekeeper.core.db.users.GameUser
import com.caseyjbrooks.scorekeeper.core.db.users.User
import org.json.JSONObject

class SevenWondersUserViewModel(
        component: BaseComponent,
        gameViewModel: SevenWondersGameViewModel,
        user: User,
        gameUser: GameUser
) : BaseUserViewModel<SevenWondersGameViewModel>(component, gameViewModel, user, gameUser) {

    val id: Long = user.id

    override fun initFromData(savedData: JSONObject) {

    }

    override fun getSaveData() : JSONObject {
        val data = JSONObject()

        return data
    }

}
