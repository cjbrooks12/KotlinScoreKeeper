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

    var coin: Int = 0
    var wonder: Int = 0
    var military: Int = 0
    var civilian: Int = 0
    var commercial: Int = 0
    var guild: Int = 0
    var science: Int = 0

    override fun initFromData(savedData: JSONObject) {
        coin       = if(savedData.has("coin"))       { savedData.getInt("coin")       } else { 0 }
        wonder     = if(savedData.has("wonder"))     { savedData.getInt("wonder")     } else { 0 }
        military   = if(savedData.has("military"))   { savedData.getInt("military")   } else { 0 }
        civilian   = if(savedData.has("civilian"))   { savedData.getInt("civilian")   } else { 0 }
        commercial = if(savedData.has("commercial")) { savedData.getInt("commercial") } else { 0 }
        guild      = if(savedData.has("guild"))      { savedData.getInt("guild")      } else { 0 }
        science    = if(savedData.has("science"))    { savedData.getInt("science")    } else { 0 }
    }

    override fun getSaveData() : JSONObject {
        val data = JSONObject()

        data.put("coin", coin)
        data.put("wonder", wonder)
        data.put("military", military)
        data.put("civilian", civilian)
        data.put("commercial", commercial)
        data.put("guild", guild)
        data.put("science", science)

        return data
    }

    fun setScoreItem(scoreType: String, score: Int) {
        when(scoreType) {
            "coin" -> coin = score
            "wonder" -> wonder = score
            "military" -> military = score
            "civilian" -> civilian = score
            "commercial" -> commercial = score
            "guild" -> guild = score
            "science" -> science = score
        }

        saveUser()
    }

    override fun reset() {
        coin       = 0
        wonder     = 0
        military   = 0
        civilian   = 0
        commercial = 0
        guild      = 0
        science    = 0

        saveUser()
    }

    fun getScore(): Int {
        return coin + wonder + military + civilian + commercial + guild + science
    }

}
