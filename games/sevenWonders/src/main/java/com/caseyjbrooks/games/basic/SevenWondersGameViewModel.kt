package com.caseyjbrooks.games.basic

import com.caseyjbrooks.scorekeeper.core.api.BaseActivity
import com.caseyjbrooks.scorekeeper.core.api.BaseComponent
import com.caseyjbrooks.scorekeeper.core.api.BaseGameViewModel
import com.caseyjbrooks.scorekeeper.core.db.users.GameUser
import com.caseyjbrooks.scorekeeper.core.db.users.User
import org.json.JSONObject

class SevenWondersGameViewModel(
        activity: BaseActivity,
        component: BaseComponent,
        gameId: String
) : BaseGameViewModel<SevenWondersUserViewModel>(activity, component, "sevenWonders", gameId) {

    var keys = SevenWondersUserViewModel.KEYS_DUEL

    override fun setup() {
        adapter = SevenWondersUserListAdapter(activity, component, this)
        super.setup()
    }

    override fun mapper(user: User, gameUser: GameUser): SevenWondersUserViewModel {
        return SevenWondersUserViewModel(component, this, user, gameUser)
    }

    override fun resetGame() {
        adapter.users.forEach { it.reset() }
        adapter.notifyDataSetChanged()
    }

    override fun initFromData(savedData: JSONObject) {

    }

    override fun getSaveData(): JSONObject {
        var data = JSONObject()

        return data
    }
}
