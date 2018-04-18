package com.caseyjbrooks.games.basic

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import com.caseyjbrooks.scorekeeper.core.api.BaseActivity
import com.caseyjbrooks.scorekeeper.core.api.BaseComponent
import com.caseyjbrooks.scorekeeper.core.api.BaseGameAdapter
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.button
import org.jetbrains.anko.dip
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onLongClick
import org.jetbrains.anko.textView
import org.jetbrains.anko.topPadding
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.view

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

                view {
                    backgroundColor = Color.parseColor("#cccccc")
                }.lparams {
                    width = matchParent
                    height = dip(1)
                }

                linearLayout {
                    weightSum = vm.gameViewModel.keys.size.toFloat()

                    for(key in vm.gameViewModel.keys) {
                        button("${vm.getScoreItem(key)}") {
                            background = SevenWondersUserViewModel.scoreKeys[key]!!.getBackground(context)
                            onClick { SevenWondersUserViewModel.scoreKeys[key]!!.updateScore(ankoContext, key, vm, SevenWondersUserListAdapter@::notifyDataSetChanged) }
                        }.lparams {
                            height = dip(48)
                            weight = 1.0f
                        }
                    }
                }
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

}
