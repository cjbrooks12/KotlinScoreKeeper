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
import org.jetbrains.anko.alignParentBottom
import org.jetbrains.anko.alignParentLeft
import org.jetbrains.anko.alignParentRight
import org.jetbrains.anko.alignParentTop
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.dip
import org.jetbrains.anko.horizontalMargin
import org.jetbrains.anko.horizontalScrollView
import org.jetbrains.anko.leftOf
import org.jetbrains.anko.leftPadding
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.padding
import org.jetbrains.anko.relativeLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onLongClick
import org.jetbrains.anko.support.v4.nestedScrollView
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
                    if(vm.selected) {
                        vm.commitTempScore()
                    }
                    else {
                        vm.toggleSelected()
                    }
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
                }

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

                    lparams {
                        width = matchParent
                    }
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
                }

                horizontalScrollView {
                    linearLayout {
                        vm.history.reversed().forEach {
                            textView("${it}") { }.lparams {
                                horizontalMargin = dip(4)
                            }
                        }
                    }.lparams {
                        padding = dip(4)
                    }
                }
            }
        }
    }

    override fun getCompactListItemView(i: Int, context: Context): View {
        val vm = getItem(i)

        return with(context) {
            relativeLayout {
                onClick {
                    if(vm.selected) {
                        vm.commitTempScore()
                    }
                    else {
                        vm.toggleSelected()
                    }
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
                }.lparams {
                    alignParentLeft()
                    alignParentTop()
                    alignParentBottom()
                    leftPadding = dip(12)
                }

                val history = nestedScrollView {
                    id = R.id.history
                    verticalLayout {
                        gravity = Gravity.CENTER
                        vm.history.reversed().forEach {
                            textView("${it}") { }.lparams {
                                horizontalMargin = dip(4)
                            }
                        }
                    }.lparams {
                        width = dip(48)
                        height = matchParent
                    }
                }.lparams {
                    alignParentRight()
                    alignParentTop()
                    alignParentBottom()
                }

                val divider = view {
                    id = R.id.divider

                    if (vm.selected) {
                        backgroundColor = HoloColor.blueLight()
                    }
                    else {
                        backgroundColor = HoloColor.orangeDark()
                    }

                }.lparams {
                    width = dip(1)
                    height = matchParent
                    leftOf(history)
                }

                val score = linearLayout {
                    id = R.id.score
                    gravity = Gravity.CENTER

                    if (vm.selected) {
                        backgroundColor = Color.LTGRAY
                    }
                    else {
                        backgroundColor = Color.TRANSPARENT
                    }

                    textView {
                        textSize = dip(14).toFloat()

                        if (vm.tempScore != 0) {
                            text = "${vm.tempScore}"
                            textColor = HoloColor.blueLight()
                        }
                        else {
                            text = "${vm.score}"
                        }
                    }.lparams {
                        horizontalMargin = dip(12)
                        padding = 0
                    }
                }.lparams {
                    height = matchParent
                    leftOf(divider)
                }

                view {
                    if (vm.selected) {
                        backgroundColor = HoloColor.blueLight()
                    }
                    else {
                        backgroundColor = Color.TRANSPARENT
                    }
                }.lparams {
                    width = dip(1)
                    height = matchParent
                    leftOf(score)
                }

                lparams {
                    width = matchParent
                    height = dip(72)
                }
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

}
