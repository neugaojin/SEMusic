package com.se.music.base.mvp

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import java.lang.reflect.InvocationTargetException
import kotlin.reflect.KFunction
import kotlin.reflect.full.declaredMemberFunctions

/**
 * Created by gaojin on 2018/2/4.
 * MVP - View实现
 */
abstract class BaseView(private var presenter: MvpPresenter, private val viewId: Int) : MvpView {

    private var view: View? = null

    protected abstract fun createView(): View

    protected fun getActivity(): Activity? {
        return presenter.getActivity()
    }

    override fun setPresenter(presenter: MvpPresenter) {
        this.presenter = presenter
    }

    protected fun getContext(): Context? {
        return getActivity()
    }

    protected fun getPage(): MvpPage {
        return presenter.getPage()
    }

    protected fun <D : Any> dispatchData(data: D) {
        presenter.onViewChanged(getId(), data)
    }

    override fun <D : Any> onDataChanged(data: D) {
        initView()
        if (data::class == Any::class) {
            throw IllegalArgumentException("Please don't dispatch data whose Class type is Any !!!")
        }

        val declaredMemberFunctions = this::class.declaredMemberFunctions
        for (item: KFunction<*> in declaredMemberFunctions) {
            if (item.name == "onDataChanged") {
                try {
                    item.call(this, data)
                } catch (e: IllegalArgumentException) {
                    continue
                } catch (e: InvocationTargetException) {
                    continue
                } catch (e: IllegalAccessException) {
                    continue
                }
                break
            }
        }
    }

    override fun <D : Any> onDataChanged(data: D, view: View) {
        initView(view)
        if (data::class == Any::class) {
            throw IllegalArgumentException("Please don't dispatch data whose Class type is Any !!!")
        }

        val declaredMemberFunctions = this::class.declaredMemberFunctions
        for (item: KFunction<*> in declaredMemberFunctions) {
            if (item.name == "onDataChanged") {
                try {
                    item.call(this, data)
                } catch (e: IllegalArgumentException) {
                    continue
                } catch (e: InvocationTargetException) {
                    continue
                } catch (e: IllegalAccessException) {
                    continue
                }
                break
            }
        }
    }

    override fun getView(): View {
        initView()
        return view!!
    }

    override fun getId(): Int {
        return viewId
    }

    protected fun initView() {
        if (getActivity() == null) {
            return
        }
        if (view == null) {
            view = createView()
            replace(viewId, view)
        }
    }

    /**
     * getActivity().findViewById找不到View情况
     */
    protected fun initView(rootView: View) {
        if (view == null) {
            view = createView()
            replace(rootView, viewId, view)
        }
    }

    protected fun replace(rootView: View, @IdRes targetId: Int, source: View?) {
        val target: View = rootView.findViewById(targetId)
        replace(target, source)
    }

    protected fun replace(@IdRes targetId: Int, source: View?) {
        val target: View = getActivity()!!.findViewById(targetId)
        replace(target, source)
    }

    protected fun replace(target: View?, source: View?) {
        if (target == null) {
            return
        }
        if (source == null) {
            return
        }
        val viewParent = target.parent

        if (viewParent != null && viewParent is ViewGroup) {
            val index = viewParent.indexOfChild(target)
            viewParent.removeViewInLayout(target)
            source.id = target.id
            val layoutParams = target.layoutParams
            if (layoutParams != null) {
                viewParent.addView(source, index, layoutParams)
            } else {
                viewParent.addView(source, index)
            }
        } else {
            throw IllegalStateException("ViewStub must have a non-null ViewGroup viewParent")
        }
    }
}