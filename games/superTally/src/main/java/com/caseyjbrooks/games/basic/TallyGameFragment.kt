package com.caseyjbrooks.games.basic

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.caseyjbrooks.scorekeeper.core.api.BaseActivity
import com.caseyjbrooks.scorekeeper.core.api.BaseApplication
import com.caseyjbrooks.scorekeeper.core.api.BaseComponent
import com.caseyjbrooks.scorekeeper.core.api.BaseFragment
import com.caseyjbrooks.scorekeeper.core.api.BaseRecyclerViewGameAdapter
import com.caseyjbrooks.scorekeeper.core.db.CorePreferences
import com.caseyjbrooks.scorekeeper.core.findLong
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.above
import org.jetbrains.anko.alignParentBottom
import org.jetbrains.anko.alignParentLeft
import org.jetbrains.anko.alignParentRight
import org.jetbrains.anko.alignParentTop
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.button
import org.jetbrains.anko.dip
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.relativeLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onLongClick
import org.jetbrains.anko.view



class TallyGameFragment : BaseFragment() {

    private lateinit var component: BaseComponent
    lateinit var gameViewModel: TallyGameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        component = (activity!!.application as BaseApplication).component
        component.inject(this)

        val gameId = CorePreferences(activity!!, "tally").get { findLong("lastGame", { 0 }) }
        gameViewModel = TallyGameViewModel(activity!! as BaseActivity, component, gameId)
        gameViewModel.setup()

        return setupView()
    }

    private fun setupView(): View {
        val ankoContext = AnkoContext.create(context!!, this)
        return with(ankoContext) {
            relativeLayout {
                val minusBar = linearLayout {
                    id = R.id.minusBar

                    weightSum = gameViewModel.buttonValues.size.toFloat()

                    for ((index, buttonVal) in gameViewModel.buttonValues.withIndex()) {
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
                    weightSum = gameViewModel.buttonValues.size.toFloat()
                    for ((index, buttonVal) in gameViewModel.buttonValues.withIndex()) {
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

                recyclerView {
                    layoutManager = when (gameViewModel.layout) {
                        "List (Comfy)"   -> LinearLayoutManager(context)
                        "List (Compact)" -> {addItemDecoration(DividerItemDecoration(activity!!, DividerItemDecoration.VERTICAL));LinearLayoutManager(context)}
                        "Grid"           -> {if (activity!!.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) { GridLayoutManager(context, 2) } else { GridLayoutManager(context, 3) }}
                        else             -> LinearLayoutManager(context)
                    }
                    adapter = BaseRecyclerViewGameAdapter(gameViewModel.adapter)
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
            R.id.addNewUser      -> gameViewModel.addNewUser(AnkoContext.create(context!!, this))
            R.id.addExistingUser -> gameViewModel.addExistingUser(AnkoContext.create(context!!, this))
            R.id.resetGame       -> gameViewModel.resetGame(AnkoContext.create(context!!, this))
            R.id.changeLayout    -> gameViewModel.changeLayout(AnkoContext.create(context!!, this))
        }
        return true
    }

}
