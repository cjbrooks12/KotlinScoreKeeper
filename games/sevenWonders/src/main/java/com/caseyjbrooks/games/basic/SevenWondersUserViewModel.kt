package com.caseyjbrooks.games.basic

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.caseyjbrooks.games.basic.models.BasicScoreKey
import com.caseyjbrooks.games.basic.models.ScoreKey
import com.caseyjbrooks.games.sevenWonders.R
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

    companion object {
        val KEYS_BASE = listOf("coin", "wonder", "military", "civilian", "commercial", "guild", "science")
        val KEYS_BABEL = listOf("coin", "wonder", "military", "civilian", "commercial", "guild", "science")
        val KEYS_DUEL = listOf("coin", "wonder", "token", "military", "civilian", "commercial", "guild")

        val scoreKeys = mapOf<String, ScoreKey>(
                "coin"       to BasicScoreKey("coin",       { cxt -> cxt.getDrawable(R.drawable.bg_coin)        }),
                "wonder"     to BasicScoreKey("wonder",     { cxt -> cxt.getDrawable(R.drawable.bg_wonder)      }),
                "token"      to BasicScoreKey("token",      { cxt -> cxt.getDrawable(R.drawable.bg_token)       }),
                "military"   to BasicScoreKey("military",   { _   -> ColorDrawable(Color.parseColor("#B56965")) }),
                "civilian"   to BasicScoreKey("civilian",   { _   -> ColorDrawable(Color.parseColor("#6275C9")) }),
                "commercial" to BasicScoreKey("commercial", { _   -> ColorDrawable(Color.parseColor("#FFB700")) }),
                "guild"      to BasicScoreKey("guild",      { _   -> ColorDrawable(Color.parseColor("#AB88A5")) }),
                "science"    to BasicScoreKey("science",    { _   -> ColorDrawable(Color.parseColor("#614B75")) })
        )
    }

    val id: String = user.id

    lateinit var scores: JSONObject

    override fun initFromData(savedData: JSONObject) {
        scores = JSONObject()

        for(key in gameViewModel.keys) {
            scores.put(key, savedData.optInt(key) ?: 0)
        }
    }

    override fun getSaveData() : JSONObject {
        val data = JSONObject()

        for(key in gameViewModel.keys) {
            data.put(key, scores.optInt(key) ?: 0)
        }

        return data
    }

    fun setScoreItem(scoreType: String, score: Int) {
        for(key in gameViewModel.keys) {
            if(key == scoreType) {
                scores.put(key, score)
            }
        }

        saveUser()
    }

    fun getScoreItem(scoreType: String): Int {
        for(key in gameViewModel.keys) {
            if(key == scoreType) {
                return scores.optInt(key) ?: 0
            }
        }

        throw IllegalArgumentException("Score type '$scoreType' is not part of this game")
    }

    override fun reset() {
        scores = JSONObject()
        saveUser()
    }

    fun getScore(): Int {
        return gameViewModel.keys.map { scores.optInt(it) ?: 0 }.sum()
    }

}
