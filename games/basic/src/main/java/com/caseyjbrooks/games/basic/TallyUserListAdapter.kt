package com.caseyjbrooks.games.basic

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.caseyjbrooks.scorekeeper.core.HoloColor
import com.caseyjbrooks.scorekeeper.core.api.BaseActivity
import com.caseyjbrooks.scorekeeper.core.api.BaseComponent
import com.caseyjbrooks.scorekeeper.core.games.BaseGameAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onLongClick
import android.widget.FrameLayout.LayoutParams as FLP

class TallyUserListAdapter(
        activity: BaseActivity,
        component: BaseComponent,
        gameViewModel: TallyGameViewModel
) : BaseGameAdapter<TallyViewModel>(activity, component, gameViewModel) {

    override fun getView(i : Int, v : View?, parent : ViewGroup?) : View {
        val vm = getItem(i)

        return with(parent!!.context) {
            frameLayout {
                cardView {
                    onClick {
                        vm.toggleSelected()
                        notifyDataSetChanged()
                    }
                    onLongClick {
                        gameViewModel.removeUser(AnkoContext.create(parent.context, this), vm.user)
                    }

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
                            id = R.id.divider
                            if(vm.selected) {
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
                            if(vm.selected) {
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

                                if(vm.tempScore != 0) {
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

                            if(vm.selected) {
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

                    }.layoutParams = FLP(FLP.MATCH_PARENT, FLP.WRAP_CONTENT)
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
