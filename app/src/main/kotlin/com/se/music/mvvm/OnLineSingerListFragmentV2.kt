package com.se.music.mvvm

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

/**
 *Author: gaojin
 *Time: 2019-07-04 19:03
 */

class OnLineSingerListFragmentV2 : Fragment() {

    companion object {
        fun newInstance() = OnLineSingerListFragmentV2()
    }

//    override fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View {
//        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_online_singer, null, false)
//        return mBinding.root
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        setTitle(context!!.getString(R.string.classify_singer))
        val viewModel = ViewModelProviders.of(this).get(SingerListViewModel::class.java)
        viewModel.mObservableSingers.observe(this, Observer {

        })
        super.onViewCreated(view, savedInstanceState)
    }

}