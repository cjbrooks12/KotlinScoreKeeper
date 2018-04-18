package com.caseyjbrooks.games.basic.models

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.InputType
import com.caseyjbrooks.games.basic.SevenWondersUserViewModel
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.customView
import org.jetbrains.anko.dip
import org.jetbrains.anko.editText
import org.jetbrains.anko.horizontalMargin
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.toast
import org.jetbrains.anko.verticalLayout

class BasicScoreKey(
        override val name: String,
        val bgFun: (context: Context) -> Drawable
) : ScoreKey() {

    override fun getBackground(context: Context): Drawable {
        return bgFun(context)
    }

    override fun <UI> updateScore(context: AnkoContext<UI>, scoreType: String, user: SevenWondersUserViewModel, notifyDataSetChanged: () -> Unit) {
        with(context) {
            alert(Appcompat, title = "${scoreType.capitalize()} - ${user.user.name}", message = "") {
                customView {
                    verticalLayout {
                        val name = editText {
                            hint = "${scoreType.capitalize()} score"
                            inputType = InputType.TYPE_CLASS_NUMBER
                        }.lparams {
                            horizontalMargin = dip(20)
                            width = matchParent
                        }
                        positiveButton("Save") {
                            if (name.text.isNotEmpty()) {
                                val score = Integer.parseInt(name.text.toString())
                                user.setScoreItem(scoreType, score)
                                user.saveUser()
                                notifyDataSetChanged()
                            }
                            else {
                                toast("Score cannot be empty")
                            }
                        }
                        negativeButton("Cancel") { }
                    }
                }
            }.show()
        }
    }


}