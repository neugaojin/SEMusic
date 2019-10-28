package com.se.music.scene.mine

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.se.music.R
import java.lang.ref.WeakReference

/**
 *Author: gaojin
 *Time: 2019-10-27 17:44
 */

class HeadSceneBehavior(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<RecyclerView>(context, attrs) {

    private lateinit var dependentView: WeakReference<View>
    private var dependencyHeight = 0

    override fun layoutDependsOn(parent: CoordinatorLayout, child: RecyclerView, dependency: View): Boolean {
        dependentView = WeakReference(dependency)
        return dependency is FrameLayout
    }

    override fun onLayoutChild(parent: CoordinatorLayout, child: RecyclerView, layoutDirection: Int): Boolean {
        dependencyHeight = parent.resources.getDimensionPixelOffset(R.dimen.mine_header_scene_height)
        val lp = child.layoutParams as CoordinatorLayout.LayoutParams
        if (lp.height == CoordinatorLayout.LayoutParams.MATCH_PARENT) {
            child.layout(0, dependencyHeight, parent.width, parent.height)
            return true
        }
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: RecyclerView, dependency: View): Boolean {
        //依赖的View变化
        child.translationY = dependency.translationY
        return super.onDependentViewChanged(parent, child, dependency)
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: RecyclerView, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScrollAccepted(coordinatorLayout: CoordinatorLayout, child: RecyclerView, directTargetChild: View, target: View, axes: Int, type: Int) {
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, axes, type)
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: RecyclerView, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        Log.e("gaojin:onNestedPreScroll", "  $dy")
        val dependentView = dependentView.get() ?: return
        val newTranslateY = dependentView.translationY - dy
        dependentView.translationY = newTranslateY
        consumed[1] = dy
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: RecyclerView, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int, consumed: IntArray) {
        val dependentView = dependentView.get() ?: return
        val newTranslateY = dependentView.translationY - dyUnconsumed
        dependentView.translationY = newTranslateY
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed)
    }
}