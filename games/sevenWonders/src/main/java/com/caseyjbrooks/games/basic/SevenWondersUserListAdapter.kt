package com.caseyjbrooks.games.basic

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.caseyjbrooks.scorekeeper.core.HoloColor
import com.caseyjbrooks.scorekeeper.core.api.BaseActivity
import com.caseyjbrooks.scorekeeper.core.api.BaseComponent
import com.caseyjbrooks.scorekeeper.core.api.BaseGameAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk25.coroutines.onLongClick

class SevenWondersUserListAdapter(
        activity: BaseActivity,
        component: BaseComponent,
        gameViewModel: SevenWondersGameViewModel
) : BaseGameAdapter<SevenWondersUserViewModel>(activity, component, gameViewModel) {

    override fun getView(i : Int, v : View?, parent : ViewGroup?) : View {
        val vm = getItem(i)

        return with(parent!!.context) {
            frameLayout {
                cardView {
                    cardElevation = dip(2).toFloat()
                    useCompatPadding = true

                    verticalLayout {
                        textView(vm.user.name) {
                            onLongClick {
                                gameViewModel.renameUser(AnkoContext.create(parent.context, this), vm.user)
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

                                text = "0"
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

}
