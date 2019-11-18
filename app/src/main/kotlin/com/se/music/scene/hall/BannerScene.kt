package com.se.music.scene.hall

import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bytedance.scene.Scene
import com.se.music.R
import com.se.music.scene.extend.initViewModel
import com.se.music.utils.manager.GlideImageLoader
import com.youth.banner.Banner
import com.youth.banner.listener.OnBannerListener
import kotlinx.coroutines.delay

/**
 *Author: gaojin
 *Time: 2019-10-21 19:56
 */

class BannerScene : Scene(), OnBannerListener {
    private lateinit var banner: Banner
    private lateinit var rootContainer: FrameLayout
    private val images = ArrayList<String>()
    private var height = 0
    private lateinit var viewModel: HallViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {
        banner = Banner(sceneContext)
        height = sceneContext?.resources?.getDimensionPixelOffset(R.dimen.online_banner_height) ?: 0
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
        banner.layoutParams = params

        rootContainer = FrameLayout(sceneContext!!)
        rootContainer.addView(banner)
        ViewCompat.setElevation(rootContainer, -10f)
        return rootContainer
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        banner.run {
            setImageLoader(GlideImageLoader())
            setOnBannerListener(this@BannerScene)
            rootContainer.setBackgroundColor(Color.BLACK)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = initViewModel()
        viewModel.hall.observe(this, Observer { data ->
            if (data != null) {
                view.visibility = View.VISIBLE
                data.data?.slider?.forEach { slider -> slider.picUrl?.let { image -> images.add(image) } }
                banner.update(images)
            } else {
                view.visibility = View.GONE
            }
        })
        viewModel.scrollEvent.observe(this, Observer { event ->
            if (event.dy < height) {
                val radio = (1 - event.dy / (height - 20))
                rootContainer.translationY = (event.dy / 2)
                banner.alpha = radio
            }
        })
    }

    override fun OnBannerClick(position: Int) {
        Toast.makeText(sceneContext, "$position", Toast.LENGTH_SHORT).show()
    }
}