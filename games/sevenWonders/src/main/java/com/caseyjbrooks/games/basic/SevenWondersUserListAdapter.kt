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
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.margin
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.padding
import org.jetbrains.anko.sdk25.coroutines.onLongClick
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.view

class SevenWondersUserListAdapter(
        activity: BaseActivity,
        component: BaseComponent,
        gameViewModel: SevenWondersGameViewModel
) : BaseGameAdapter<SevenWondersUserViewModel>(activity, component, gameViewModel) {

    override fun getViewContents(i : Int, context: Context) : View {
        val vm = getItem(i)

        return with(context) {

            verticalLayout {
                textView(vm.user.name) {
                    onLongClick {
                        gameViewModel.renameUser(AnkoContext.create(context, this), vm.user)
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
            }
        }
    }

    override fun getItemId(position : Int) : Long {
        return getItem(position).id
    }

}
