package com.example.mvvm_navigation.ui.main

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.base.components.LayoutId
import com.example.mvvm_navigation.BR
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseFragment
import com.example.mvvm_navigation.di.mainModule
import com.example.mvvm_navigation.ui.main.vm.main.MainContract
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_main.view.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinContext
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext
import org.kodein.di.generic.singleton

@LayoutId(R.layout.fragment_main)
class MainFragment : BaseFragment() {
    val TAG = "MainFragment"

    /** Dependency Injection **/
    override val kodeinContext: KodeinContext<*> get() = kcontext(activity)

    override val kodein = Kodein.lazy {
        bind<MainFragment>(TAG) with singleton { this@MainFragment }
        import(mainModule)
    }

    private val viewModel by kodein.instance<MainContract.ViewModelImpl>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolBarAndDrawLayout()
        this.binding.setVariable(BR.viewModel, this.viewModel.getSubmitter())
        viewModel.getSubmitter().navHostFragment.value =
            this.activity?.supportFragmentManager?.findFragmentById(R.id.mainActivityNavHostFragment)
    }

    private fun setToolBarAndDrawLayout() {
        val toolbar: Toolbar = binding.root.toolbar_main
        (this.activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.inflateMenu(R.menu.menu_main_drawer_item)
        (this.activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (this.activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        (this.activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        val drawLayout: DrawerLayout = binding.root.drawer_main
        val mActionBarDrawerToggle = ActionBarDrawerToggle(
            this.activity,
            drawLayout,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )
        mActionBarDrawerToggle.syncState()
        drawLayout.addDrawerListener(mActionBarDrawerToggle)

        val navigationView: NavigationView = binding.root.navigation_view
        navigationView.setNavigationItemSelectedListener {
            drawLayout.closeDrawer(GravityCompat.START)
            this.viewModel.drawerNavigationClick(it.itemId)
            false
        }
    }

}