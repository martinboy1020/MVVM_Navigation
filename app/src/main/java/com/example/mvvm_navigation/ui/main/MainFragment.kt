package com.example.mvvm_navigation.ui.main

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.base.components.LayoutId
import com.example.mvvm_navigation.BR
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseFragment
import com.example.mvvm_navigation.di.mainModule
import com.example.mvvm_navigation.ui.main.vm.main.MainContract
import com.example.mvvm_navigation.widget.LogoToolBarWidget
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
        val mainLogoToolBar: LogoToolBarWidget = binding.root.main_logo_tool_bar
        (this.activity as AppCompatActivity).setSupportActionBar(mainLogoToolBar.getToolBar())
        mainLogoToolBar.getToolBar().inflateMenu(R.menu.menu_main_drawer_item)
        (this.activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (this.activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        (this.activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        val drawLayout: DrawerLayout = binding.root.drawer_main
        val mActionBarDrawerToggle = ActionBarDrawerToggle(
            this.activity,
            drawLayout,
            mainLogoToolBar.getToolBar(),
            R.string.drawer_open,
            R.string.drawer_close
        )
        mActionBarDrawerToggle.drawerArrowDrawable.color = ContextCompat.getColor(this.requireContext(), R.color.white)
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