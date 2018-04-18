package com.caseyjbrooks.games.basic

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.caseyjbrooks.games.sevenWonders.R
import com.caseyjbrooks.scorekeeper.core.api.*
import com.caseyjbrooks.scorekeeper.core.db.CorePreferences
import com.caseyjbrooks.scorekeeper.core.findString
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class SevenWondersGameFragment : BaseFragment() {

    private lateinit var component: BaseComponent
    lateinit var gameViewModel : SevenWondersGameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        component = (activity!!.application as BaseApplication).component
        component.inject(this)

        val gameId = CorePreferences(activity!!, "sevenWonders").get { findString("lastGame", { "" }) }
        gameViewModel = SevenWondersGameViewModel(activity as BaseActivity, component, gameId)
        gameViewModel.setup()

        return setupView()
    }

    private fun setupView() : View {
        val ankoContext = AnkoContext.create(context!!, this)
        return with(ankoContext) {
            relativeLayout {
                recyclerView {
                    layoutManager = when (gameViewModel.layout) {
                        "List (Comfy)"   -> LinearLayoutManager(context)
                        "List (Compact)" -> {addItemDecoration(DividerItemDecoration(activity!!, DividerItemDecoration.VERTICAL));LinearLayoutManager(context) }
                        "Grid"           -> {if (activity!!.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) { GridLayoutManager(context, 2) } else { GridLayoutManager(context, 3) }}
                        else             -> LinearLayoutManager(context)
                    }
                    adapter = BaseRecyclerViewGameAdapter(gameViewModel.adapter)
                    isNestedScrollingEnabled = true
                }.lparams {
                    alignParentTop()
                    alignParentBottom()
                    alignParentLeft()
                    alignParentRight()
                }
            }
        }
    }

// Menu
//--------------------------------------------------------------------------------------------------

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.seven_wonders_fragment_menu, menu)
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
