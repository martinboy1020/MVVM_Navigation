package com.example.mvvm_navigation.ui.match_detail

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.base.components.LayoutId
import com.example.mvvm_navigation.BR
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.base.BaseFragment
import com.example.mvvm_navigation.databinding.FragmentMatchDetailBinding
import com.example.mvvm_navigation.datacenter.network.response.MatchList
import com.example.mvvm_navigation.datacenter.sharedPreferences.UserSharePreferences
import com.example.mvvm_navigation.di.matchDetailModule
import com.example.mvvm_navigation.di.matchListModule
import com.example.mvvm_navigation.ui.match.matchlist.viewmodel.MatchListContract
import com.example.mvvm_navigation.ui.match_detail.viewmodel.MatchDetailContract
import com.example.mvvm_navigation.utils.DisplayUtils
import com.example.mvvm_navigation.widget.stream_video_widget.StreamVideoView
import org.kodein.di.Kodein
import org.kodein.di.KodeinContext
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext
import org.kodein.di.generic.singleton

@LayoutId(R.layout.fragment_match_detail)
class MatchDetailFragment : BaseFragment() {

    /** Dependency Injection **/
    override val kodeinContext: KodeinContext<*> get() = kcontext(activity)

    override val kodein = Kodein.lazy {
        bind<MatchDetailFragment>(MatchDetailFragment::class.java.simpleName) with singleton { this@MatchDetailFragment }
        import(matchDetailModule)
    }

    private val viewModel by kodein.instance<MatchDetailContract.ViewModelImpl>()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        val matchId = bundle?.getInt("matchId", -1)
        if (matchId != null && matchId != -1) this.viewModel.getMatchDetail(matchId)
        this.activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onResume() {
        super.onResume()
        this.activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.binding.setVariable(BR.viewModel, this.viewModel.getSubmitter())
        (this.binding as FragmentMatchDetailBinding).streamVideoView.setStreamVideo("https://cph-p2p-msl.akamaized.net/hls/live/2000341/test/master.m3u8", 300)
    }

    override fun onDestroy() {
        super.onDestroy()
        this.activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        (this.binding as FragmentMatchDetailBinding).streamVideoView.autoChangeVideoDirection(
            newConfig,
            this.activity as AppCompatActivity
        )
        if (StreamVideoView.checkMobileRotationIsLandScape(
                newConfig,
                this.activity as AppCompatActivity
            )
        ) {
            (this.binding as FragmentMatchDetailBinding).tvMatchDetail.visibility = View.GONE
        } else {
            (this.binding as FragmentMatchDetailBinding).tvMatchDetail.visibility = View.VISIBLE
        }
    }
}