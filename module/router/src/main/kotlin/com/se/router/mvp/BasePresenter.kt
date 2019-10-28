package com.se.router.mvp

import android.app.Activity
import android.os.Bundle
import android.util.SparseArray
import androidx.fragment.app.Fragment
import java.lang.reflect.InvocationTargetException
import kotlin.reflect.KFunction
import kotlin.reflect.KType
import kotlin.reflect.full.declaredMemberFunctions

/**
 * Created by gaojin on 2018/2/4.
 * MVP - Presenter实现
 */
class BasePresenter(private var page: MvpPage) : MvpPresenter {

    private val mvpViewMap = SparseArray<MvpView>()
    private val mvpModelMap = SparseArray<MvpModel>()
    private val modelTypeMap = SparseArray<KType>()

    init {
        require(!(page !is Fragment && page !is Activity)) { "FoodMvpPage must be implemented by Activity or Fragment" }
    }

    override fun <D : Any> onViewChanged(id: Int, data: D) {
        require(data::class != Any::class) { "Please don't dispatch data whose Class type is Any !!!" }

        val declaredMemberFunctions = page::class.declaredMemberFunctions
        for (item: KFunction<*> in declaredMemberFunctions) {
            if (item.name == "onViewChanged") {
                try {
                    item.call(page, id, data)
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

    override fun <D : Any> onModelChanged(id: Int, data: D) {
        if (data::class == Any::class) {
            throw IllegalArgumentException("Please don't dispatch data whose Class type is Any !!!")
        }

        val declaredMemberFunctions = page::class.declaredMemberFunctions
        for (item: KFunction<*> in declaredMemberFunctions) {
            if (item.name == "onModelChanged") {
                try {
                    item.call(page, id, data)
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

    override fun onError(exception: Exception) {
        page.onPageError(exception)
    }

    override fun add(view: MvpView) {
        mvpViewMap.put(view.getId(), view)
    }

    override fun add(model: MvpModel) {
        mvpModelMap.put(model.getId(), model)
    }

    override fun getView(viewId: Int): MvpView? {
        return if (mvpViewMap.indexOfKey(viewId) >= 0) {
            mvpViewMap.get(viewId)
        } else null
    }

    override fun getActivity(): Activity? {
        return page.getActivity()
    }

    override fun getPage(): MvpPage {
        return page
    }

    override fun <D : Any> dispatchModelDataToView(modelId: Int, data: D, vararg viewIds: Int) {
        for (id in viewIds) {
            mvpViewMap.get(id)?.onDataChanged(data)
        }
    }

    override fun <D : Any> dispatchModelDataToModel(modelId: Int, data: D, vararg modelIds: Int) {
        for (id in modelIds) {
            mvpModelMap.get(id)?.onDataChanged(data)
        }
    }

    override fun <D : Any> dispatchViewDataToView(viewId: Int, data: D, vararg viewIds: Int) {
        for (id in viewIds) {
            mvpViewMap.get(id)?.onDataChanged(data)
        }
    }

    override fun <D : Any> dispatchViewDataToModel(viewId: Int, data: D, vararg modelIds: Int) {
        for (id in modelIds) {
            mvpModelMap.get(id)?.onDataChanged(data)
        }
    }

    override fun start(vararg modelIds: Int) {
        modelIds
                .map { mvpModelMap.get(it) }
                .forEach { it?.load() }
    }

    override fun onCreate(savedInstanceState: Bundle) {
        val onCreate = MvpOnCreate()
        onCreate.savedInstanceState = savedInstanceState
        dispatchLifeCycle(onCreate)
    }

    override fun onStart() {
        dispatchLifeCycle(MvpOnStart())
    }

    override fun onResume() {
        dispatchLifeCycle(MvpOnResume())
    }

    override fun onPause() {
        dispatchLifeCycle(MvpOnPause())
    }

    override fun onStop() {
        dispatchLifeCycle(MvpOnStop())
    }

    override fun onDestroy() {
        dispatchLifeCycle(MvpOnDestroy())
    }

    private fun dispatchLifeCycle(lifeCycle: MvpLifeCycle) {
        (0 until mvpViewMap.size())
                .map { mvpViewMap.valueAt(it) }
                .forEach { it.onDataChanged(lifeCycle) }

        (0 until mvpModelMap.size())
                .map { mvpModelMap.valueAt(it) }
                .forEach { it.onDataChanged(lifeCycle) }
    }
}