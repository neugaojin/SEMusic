package com.se.router.mvp

import android.app.Activity
import android.content.Context
import java.lang.reflect.InvocationTargetException
import kotlin.reflect.KFunction
import kotlin.reflect.full.declaredMemberFunctions

/**
 * Created by gaojin on 2018/2/4.
 * MVP - Model实现
 */
abstract class BaseModel<in T : Any>(private var presenter: MvpPresenter, private val modelId: Int) : MvpModel {
    override fun setPresenter(presenter: MvpPresenter) {
        this.presenter = presenter
    }

    override fun <D : Any> onDataChanged(data: D) {
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

    override fun getId(): Int {
        return modelId
    }

    protected fun getActivity(): Activity? {
        return presenter.getActivity()
    }

    protected fun getContext(): Context? {
        return getActivity()
    }

    protected fun dispatchData(data: T) {
        presenter.onModelChanged(getId(), data)
    }
}