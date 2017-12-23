package com.caseyjbrooks.games.basic

import android.graphics.Color
import android.os.Bundle
import android.view.*
import com.caseyjbrooks.scorekeeper.core.api.BaseActivity
import com.caseyjbrooks.scorekeeper.core.api.BaseApplication
import com.caseyjbrooks.scorekeeper.core.api.BaseComponent
import com.caseyjbrooks.scorekeeper.core.api.BaseFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onLongClick

class TallyGameFragment : BaseFragment() {

    private lateinit var component: BaseComponent

    lateinit var gameViewModel : TallyGameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        component = (activity.application as BaseApplication).component
        component.inject(this)

        gameViewModel = TallyGameViewModel(activity as BaseActivity, component, 1)
        gameViewModel.setup()

        return setupView()
    }

    private fun setupView() : View {
        val ankoContext = AnkoContext.create(context, this)
        return with(ankoContext) {
            relativeLayout {
                val minusBar = linearLayout {
                    id = R.id.minusBar

                    weightSum = gameViewModel.tallyService.buttonValues.size.toFloat()

                    for ((index, buttonVal) in gameViewModel.tallyService.buttonValues.withIndex()) {
                        val btn = button("-$buttonVal") {
                            onClick { gameViewModel.updateVal(index, false) }
                            onLongClick { gameViewModel.changeButtonValue(ankoContext, index, false) }
                        }.lparams { weight = 1.0f }
                        gameViewModel.minusButtons.add(btn)
                    }
                }.lparams {
                    alignParentLeft()
                    alignParentRight()
                    alignParentBottom()
                }

                val plusBar = linearLayout {
                    id = R.id.plusBar
                    weightSum = gameViewModel.tallyService.buttonValues.size.toFloat()
                    for ((index, buttonVal) in gameViewModel.tallyService.buttonValues.withIndex()) {
                        val btn = button("+$buttonVal") {
                            onClick { gameViewModel.updateVal(index, true) }
                            onLongClick { gameViewModel.changeButtonValue(ankoContext, index, true) }
                        }.lparams { weight = 1.0f }
                        gameViewModel.plusButtons.add(btn)
                    }
                }.lparams {
                    alignParentLeft()
                    alignParentRight()
                    above(minusBar)
                }

                val dividerView = view {
                    id = R.id.divider
                    backgroundColor = Color.LTGRAY
                }.lparams {
                    height = dip(1)
                    alignParentLeft()
                    alignParentRight()
                    above(plusBar)
                }

                listView {
                    adapter = gameViewModel.adapter
                    divider = null
                }.lparams {
                    alignParentTop()
                    alignParentLeft()
                    alignParentRight()
                    above(dividerView)
                }
            }
        }
    }

// Menu
//--------------------------------------------------------------------------------------------------

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.tally_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addNewUser      -> gameViewModel.addNewUser(AnkoContext.create(context, this))
            R.id.addExistingUser -> gameViewModel.addExistingUser(AnkoContext.create(context, this))
            R.id.resetGame       -> gameViewModel.resetGame(AnkoContext.create(context, this))
        }
        return true
    }

}
