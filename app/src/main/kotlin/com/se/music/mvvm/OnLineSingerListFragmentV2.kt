package com.se.music.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.se.music.R
import com.se.music.base.BasePageFragment
import com.se.music.base.picBaseUrl_300
import com.se.music.databinding.FragmentOnlineSingerBinding
import com.se.music.utils.inflate
import com.se.music.utils.loadUrl
import com.se.music.widget.CircleImageView

/**
 *Author: gaojin
 *Time: 2019-07-04 19:03
 */

class OnLineSingerListFragmentV2 : BasePageFragment() {

    companion object {
        fun newInstance() = OnLineSingerListFragmentV2()
    }

    private lateinit var mBinding: FragmentOnlineSingerBinding

    override fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_online_singer, null, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setTitle(context!!.getString(R.string.classify_singer))
        val viewModel = ViewModelProviders.of(this).get(SingerListViewModel::class.java)
        viewModel.mObservableSingers.observe(this, Observer {
            if (it != null) {
                mBinding.onlineSinger.adapter = SingerAdapter(it)
            } else {
                mBinding.isLoading = true
            }
        })
        super.onViewCreated(view, savedInstanceState)
    }

}