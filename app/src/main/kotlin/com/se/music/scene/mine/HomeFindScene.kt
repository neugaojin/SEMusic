package com.se.music.scene.mine

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bytedance.scene.group.UserVisibleHintGroupScene
import com.se.music.R
import com.se.music.base.log.Loger

/**
 *Author: gaojin
 *Time: 2019-10-21 16:32
 */
class HomeFindScene : UserVisibleHintGroupScene() {

    private lateinit var view1: View

    private val animator: ValueAnimator = ValueAnimator.ofFloat(0F, 1F).apply {
        duration = 250
        addUpdateListener {
            val percent = 1 - it.animatedFraction
            Loger.e { percent }
            view1.scaleX = percent
            view1.scaleY = percent
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): ViewGroup {
        return inflater.inflate(R.layout.fragment_local_folder, container, false) as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.text_view).setOnClickListener {
            scaleImage()
        }
        view1 = requireViewById(R.id.image_view)
    }


    private fun scaleImage() {
        view1.pivotX = (view1.width / 2).toFloat()
        view1.pivotY = 0F

        if (view1.scaleX == 0F) {
            animator.reverse()
        } else {
            animator.start()
        }
    }
}