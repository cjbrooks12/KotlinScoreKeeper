package com.caseyjbrooks.games.basic

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import com.caseyjbrooks.scorekeeper.core.HoloColor
import com.caseyjbrooks.scorekeeper.core.api.BaseActivity
import com.caseyjbrooks.scorekeeper.core.api.BaseComponent
import com.caseyjbrooks.scorekeeper.core.api.BaseGameAdapter
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.dip
import org.jetbrains.anko.horizontalMargin
import org.jetbrains.anko.horizontalPadding
import org.jetbrains.anko.horizontalScrollView
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.margin
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.padding
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onLongClick
import org.jetbrains.anko.textColor
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.view
import android.widget.FrameLayout.LayoutParams as FLP

class TallyUserAdapter(
        activity: BaseActivity,
        component: BaseComponent,
        gameViewModel: TallyGameViewModel
) : BaseGameAdapter<TallyUserViewModel>(activity, component, gameViewModel) {

    override fun getViewContents(i: Int, context: Context): View {
        val vm = getItem(i)

        return with(context) {
            verticalLayout {
                onClick {
                    vm.toggleSelected()
                    notifyDataSetChanged()
                }
                onLongClick {
                    gameViewModel.removeUser(AnkoContext.create(context, this), vm.user)
                }

                textView(vm.user.name) {
                    onLongClick {
                        gameViewModel.renameUser(AnkoContext.create(context, this), vm.user)
                    }

                    gravity = Gravity.CENTER
                    textSize = dip(8).toFloat()
                }.lparams(width = matchParent) { setPadding(dip(8), dip(8), dip(8), 0) }

                view {
                    id = R.id.divider
                    if (vm.selected) {
                        backgroundColor = HoloColor.blueLight()
                    }
                    else {
                        backgroundColor = Color.TRANSPARENT
                    }
                }.lparams {
                    height = dip(1)
                    width = matchParent
                    margin = dip(8)
                }

                linearLayout {
                    gravity = Gravity.CENTER
                    if (vm.selected) {
                        backgroundColor = Color.LTGRAY
                    }
                    else {
                        backgroundColor = Color.TRANSPARENT
                    }
                    textView {
                        onClick {
                            vm.commitTempScore()
                            notifyDataSetChanged()
                        }
                        gravity = Gravity.CENTER
                        textSize = dip(18).toFloat()

                        if (vm.tempScore != 0) {
                            text = "${vm.tempScore}"
                            textColor = HoloColor.blueLight()
                        }
                        else {
                            text = "${vm.score}"
                        }
                    }
                }.lparams {
                    width = matchParent
                    padding = dip(8)
                }

                view {
                    id = R.id.divider

                    if (vm.selected) {
                        backgroundColor = HoloColor.blueLight()
                    }
                    else {
                        backgroundColor = HoloColor.orangeDark()
                    }

                }.lparams {
                    height = dip(1)
                    width = matchParent
                    margin = dip(8)
                }

                horizontalScrollView {
                    linearLayout {
                        vm.history.reversed().forEach {
                            textView("${it}") { }.lparams {
                                horizontalMargin = dip(4)
                            }
                        }
                    }.lparams {
                        horizontalPadding = dip(4)
                    }
                }
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

}
