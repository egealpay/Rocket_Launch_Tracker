package com.alpaye.rocketlaunchbrowser.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.alpaye.rocketlaunchbrowser.R
import com.alpaye.rocketlaunchbrowser.model.LaunchResponseModel
import com.facebook.drawee.backends.pipeline.Fresco
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {

    val mainPresenter: MainPresenter = MainPresenter(this, MainInteractor())

    var upcomingLaunchNum: Int = 10

    private lateinit var launchAdapter: LaunchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Fresco.initialize(this)

        launchAdapter = LaunchAdapter()
        with(activity_main_recyclerview_upcominglaunchs) {
            layoutManager = android.support.v7.widget.LinearLayoutManager(context)
            adapter = launchAdapter
        }

        mainPresenter.getUpcomingTen(upcomingLaunchNum)
    }

    override fun onDestroy() {
        mainPresenter.onDestroy()
        super.onDestroy()
    }

    override fun showUpcomingTenLaunch(launchResponse: LaunchResponseModel?) {
        launchAdapter.updateList(ArrayList(launchResponse?.launchObject))
    }

    override fun showError() = Toast.makeText(applicationContext, "Server Error", Toast.LENGTH_SHORT).show()

    override fun showAnimation() {
        activity_main_recyclerview_upcominglaunchs.visibility = View.GONE
        activity_main_lottieanimation.playAnimation()
        activity_main_lottieanimation.visibility = View.VISIBLE
    }

    override fun stopAnimation() {
        activity_main_lottieanimation.visibility = View.GONE
        activity_main_lottieanimation.pauseAnimation()
        activity_main_recyclerview_upcominglaunchs.visibility = View.VISIBLE
    }
}
