package com.se.music.online.banner

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.Keep
import androidx.core.view.ViewCompat
import com.se.music.R
import com.se.music.base.BaseActivity
import com.se.music.fragment.SEWebViewFragment
import com.se.music.online.event.ScrollEvent
import com.se.music.online.model.HallModel
import com.se.music.utils.manager.GlideImageLoader
import com.youth.banner.Banner
import com.youth.banner.listener.OnBannerListener

/**
 * Created by gaojin on 2018/2/4.
 * Banner模块
 */
class OnLineBannerView(presenter: com.se.router.mvp.MvpPresenter, viewId: Int) : com.se.router.mvp.BaseView(presenter, viewId), OnBannerListener {

    private lateinit var banner: Banner
    private lateinit var container: FrameLayout
    private val images = ArrayList<String>()
    private var bannerList: List<HallModel.Data.Slider>? = null
    private var height = 0

    @SuppressLint("InflateParams")
    override fun createView(): View {
        banner = Banner(getContext())
        height = getContext()?.resources?.getDimensionPixelOffset(R.dimen.online_banner_height) ?: 0
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
        banner.layoutParams = params

        container = FrameLayout(getContext())
        container.addView(banner)
        ViewCompat.setElevation(container, -10f)
        return container
    }

    @Keep
    fun onDataChanged(data: HallModel) {
        data.data?.slider?.forEach { it.picUrl?.let { it1 -> images.add(it1) } }
        bannerList = data.data?.slider
        initBanner()
    }

    @Keep
    fun onDataChanged(event: ScrollEvent) {
        if (event.dy < height) {
            val radio = (1 - event.dy / (height - 20))
            container.translationY = (event.dy / 2)
            banner.alpha = radio
        }
    }

    private fun initBanner() {
        banner.run {
            setImageLoader(GlideImageLoader())
            setImages(images)
            setOnBannerListener(this@OnLineBannerView)
            start()
            container.setBackgroundColor(Color.BLACK)
        }
    }

    override fun OnBannerClick(position: Int) {
        (getActivity() as BaseActivity).supportFragmentManager
                .beginTransaction().run {
                    addToBackStack(null)
                    setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out)
                    add(R.id.se_main_content, SEWebViewFragment.newInstance(bannerList?.get(position)?.linkUrl))
                    commit()
                }
    }
}