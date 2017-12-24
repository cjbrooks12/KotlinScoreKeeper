package com.caseyjbrooks.games.basic

import android.graphics.Color
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.caseyjbrooks.scorekeeper.core.HoloColor
import com.caseyjbrooks.scorekeeper.core.api.BaseActivity
import com.caseyjbrooks.scorekeeper.core.api.BaseComponent
import com.caseyjbrooks.scorekeeper.core.api.BaseGameAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onLongClick

class SevenWondersUserListAdapter(
        activity: BaseActivity,
        component: BaseComponent,
        gameViewModel: SevenWondersGameViewModel
) : BaseGameAdapter<SevenWondersUserViewModel>(activity, component, gameViewModel) {

    override fun getView(i : Int, v : View?, parent : ViewGroup?) : View {
        val vm = getItem(i)
        val ankoContext = AnkoContext.create(parent!!.context, this)

        return with(ankoContext) {
            frameLayout {
                cardView {
                    cardElevation = dip(2).toFloat()
                    useCompatPadding = true

                    verticalLayout {
                        textView(vm.user.name) {
                            onLongClick {
                                gameViewModel.renameUser(ankoContext, vm.user)
                            }

                            gravity = Gravity.CENTER
                            textSize = dip(8).toFloat()
                        }.lparams(width = matchParent) { setPadding(dip(8), dip(8), dip(8), 0) }

                        view {
                            backgroundColor = Color.TRANSPARENT
                        }.lparams {
                            height = dip(1)
                            width = matchParent
                            margin = dip(8)
                        }

                        linearLayout {
                            gravity = Gravity.CENTER
                            textView {
                                gravity = Gravity.CENTER
                                textSize = dip(18).toFloat()

                                text = "${vm.getScore()}"
                            }
                        }.lparams {
                            width = matchParent
                            padding = dip(8)
                        }

                        view {
                            backgroundColor = HoloColor.orangeDark()
                        }.lparams {
                            height = dip(1)
                            width = matchParent
                            margin = dip(8)
                        }

                        linearLayout {
                            weightSum = 7.0f

                            button("${vm.coin}") {
                                backgroundColor = Color.parseColor("#FFCF3A")
                                onClick { updateScore(ankoContext, "coin", vm) }
                            }.lparams {
                                height = dip(48)
                                weight = 1.0f
                            }

                            button("${vm.wonder}") {
                                backgroundColor = Color.parseColor("#FFF481")
                                onClick { updateScore(ankoContext, "wonder", vm) }
                            }.lparams {
                                height = dip(48)
                                weight = 1.0f
                            }

                            button("${vm.military}") {
                                backgroundColor = Color.parseColor("#B56965")
                                onClick { updateScore(ankoContext, "military", vm) }
                            }.lparams {
                                height = dip(48)
                                weight = 1.0f
                            }

                            button("${vm.civilian}") {
                                backgroundColor = Color.parseColor("#6275C9")
                                onClick { updateScore(ankoContext, "civilian", vm) }
                            }.lparams {
                                height = dip(48)
                                weight = 1.0f
                            }

                            button("${vm.commercial}") {
                                backgroundColor = Color.parseColor("#FFB700")
                                onClick { updateScore(ankoContext, "commercial", vm) }
                            }.lparams {
                                height = dip(48)
                                weight = 1.0f
                            }

                            button("${vm.guild}") {
                                backgroundColor = Color.parseColor("#619968")
                                onClick { updateScore(ankoContext, "guild", vm) }
                            }.lparams {
                                height = dip(48)
                                weight = 1.0f
                            }

                            button("${vm.science}") {
                                backgroundColor = Color.parseColor("#614B75")
                                onClick { updateScore(ankoContext, "science", vm) }
                            }.lparams {
                                height = dip(48)
                                weight = 1.0f
                            }
                        }

                    }.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
                }.lparams {
                    width = matchParent
                    horizontalMargin = dip(8)
                    verticalMargin = dip(4)
                }
            }
        }
    }

    override fun getItemId(position : Int) : Long {
        return getItem(position).id
    }

    fun <UI> updateScore(ui: AnkoContext<UI>, scoreType: String, user: SevenWondersUserViewModel) = with(ui) {
        alert(Appcompat, title = "${scoreType.capitalize()} - ${user.user.name}", message = "") {
            customView {
                verticalLayout {
                    val name = editText {
                        hint = "${scoreType.capitalize()} score"
                        inputType = InputType.TYPE_CLASS_NUMBER
                    }
                    positiveButton("Save") {
                        if(name.text.isNotEmpty()) {
                            val score = Integer.parseInt(name.text.toString())
                            user.setScoreItem(scoreType, score)
                            user.saveUser()
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
