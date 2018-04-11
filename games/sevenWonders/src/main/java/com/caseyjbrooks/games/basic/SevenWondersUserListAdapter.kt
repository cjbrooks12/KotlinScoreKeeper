package com.caseyjbrooks.games.basic

import android.content.Context
import android.graphics.Color
import android.text.InputType
import android.view.Gravity
import android.view.View
import com.caseyjbrooks.scorekeeper.core.api.BaseActivity
import com.caseyjbrooks.scorekeeper.core.api.BaseComponent
import com.caseyjbrooks.scorekeeper.core.api.BaseGameAdapter
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.button
import org.jetbrains.anko.customView
import org.jetbrains.anko.dip
import org.jetbrains.anko.editText
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onLongClick
import org.jetbrains.anko.textView
import org.jetbrains.anko.toast
import org.jetbrains.anko.topPadding
import org.jetbrains.anko.verticalLayout

class SevenWondersUserListAdapter(
        activity: BaseActivity,
        component: BaseComponent,
        gameViewModel: SevenWondersGameViewModel
) : BaseGameAdapter<SevenWondersUserViewModel>(activity, component, gameViewModel) {

    override fun getViewContents(i: Int, context: Context): View {
        val vm = getItem(i)
        val ankoContext = AnkoContext.create(context, this)

        return with(ankoContext) {
            verticalLayout {
                textView(vm.user.name) {
                    onLongClick {
                        gameViewModel.renameUser(ankoContext, vm.user)
                    }

                    gravity = Gravity.CENTER
                    textSize = dip(8).toFloat()
                }

                linearLayout {
                    gravity = Gravity.CENTER
                    textView {
                        gravity = Gravity.CENTER
                        textSize = dip(18).toFloat()

                        text = "${vm.getScore()}"
                    }

                    lparams {
                        width = matchParent
                        topPadding = 0
                    }
                }

                linearLayout {
                    weightSum = 7.0f

                    button("${vm.coin}") {
                        backgroundColor = Color.parseColor("#FFCF3A")
                        onClick { updateScore("coin", vm) }
                    }.lparams {
                        height = dip(48)
                        weight = 1.0f
                    }

                    button("${vm.wonder}") {
                        backgroundColor = Color.parseColor("#FFF481")
                        onClick { updateScore("wonder", vm) }
                    }.lparams {
                        height = dip(48)
                        weight = 1.0f
                    }

                    button("${vm.military}") {
                        backgroundColor = Color.parseColor("#B56965")
                        onClick { updateScore("military", vm) }
                    }.lparams {
                        height = dip(48)
                        weight = 1.0f
                    }

                    button("${vm.civilian}") {
                        backgroundColor = Color.parseColor("#6275C9")
                        onClick { updateScore("civilian", vm) }
                    }.lparams {
                        height = dip(48)
                        weight = 1.0f
                    }

                    button("${vm.commercial}") {
                        backgroundColor = Color.parseColor("#FFB700")
                        onClick { updateScore("commercial", vm) }
                    }.lparams {
                        height = dip(48)
                        weight = 1.0f
                    }

                    button("${vm.guild}") {
                        backgroundColor = Color.parseColor("#619968")
                        onClick { updateScore("guild", vm) }
                    }.lparams {
                        height = dip(48)
                        weight = 1.0f
                    }

                    button("${vm.science}") {
                        backgroundColor = Color.parseColor("#614B75")
                        onClick { updateScore("science", vm) }
                    }.lparams {
                        height = dip(48)
                        weight = 1.0f
                    }
                }
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    fun <UI> AnkoContext<UI>.updateScore(scoreType: String, user: SevenWondersUserViewModel) = with(this) {
        alert(Appcompat, title = "${scoreType.capitalize()} - ${user.user.name}", message = "") {
            customView {
                verticalLayout {
                    val name = editText {
                        hint = "${scoreType.capitalize()} score"
                        inputType = InputType.TYPE_CLASS_NUMBER
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
