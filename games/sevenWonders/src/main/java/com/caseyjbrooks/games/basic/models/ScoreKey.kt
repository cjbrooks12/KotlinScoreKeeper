package com.caseyjbrooks.games.basic.models

import android.content.Context
import android.graphics.drawable.Drawable
import com.caseyjbrooks.games.basic.SevenWondersUserViewModel
import org.jetbrains.anko.AnkoContext

abstract class ScoreKey {

    abstract val name: String

    abstract fun getBackground(context: Context): Drawable

    abstract fun <UI> updateScore(context: AnkoContext<UI>, scoreType: String, user: SevenWondersUserViewModel, notifyDataSetChanged: () -> Unit)

}