package com.se.music.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.loader.content.Loader
import androidx.recyclerview.widget.RecyclerView
import com.se.music.R
import com.se.music.base.BasePageFragment
import com.se.music.base.picBaseUrl_300
import com.se.music.databinding.FragmentOnlineSingerBinding
import com.se.music.mvvm.Singer
import com.se.music.mvvm.SingerEntity
import com.se.music.retrofit.MusicRetrofit
import com.se.music.retrofit.callback.CallLoaderCallbacks
import com.se.music.utils.GET_SINGER_LIST
import com.se.music.utils.inflate
import com.se.music.utils.loadUrl
import com.se.music.widget.CircleImageView
import retrofit2.Call

/**
 * Created by gaojin on 2018/1/3.
 */
class OnLineSingerFragment : BasePageFragment() {

    private lateinit var singerAdapter: SingerAdapter
    private var singerList: MutableList<Singer> = ArrayList()

    private lateinit var mBinding: FragmentOnlineSingerBinding

    companion object {
        fun newInstance() = OnLineSingerFragment()
    }

    override fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_online_singer, null, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setTitle(context!!.getString(R.string.classify_singer))
        singerAdapter = SingerAdapter()
        mBinding.onlineSinger.setHasFixedSize(true)
        mBinding.onlineSinger.adapter = singerAdapter
        mLoaderManager.initLoader(GET_SINGER_LIST, null, buildSingerCallback())
    }

    private fun buildSingerCallback(): CallLoaderCallbacks<SingerEntity> {
        return object : CallLoaderCallbacks<SingerEntity>(context!!) {
            override fun onCreateCall(id: Int, args: Bundle?): Call<SingerEntity> {
                return MusicRetrofit.instance.getSinger(100, 1)
            }

            override fun onSuccess(loader: Loader<*>, data: SingerEntity) {
                if (data.data?.list?.isEmpty() == false) {
                    singerList.addAll(data.data.list)
                }
                singerAdapter.notifyDataSetChanged()
            }

            override fun onFailure(loader: Loader<*>, throwable: Throwable) {
                Log.e("OnLineSingerFragment", throwable.toString())
            }
        }
    }

    inner class SingerAdapter : RecyclerView.Adapter<ItemViewHolder>() {
        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            holder.singerName.text = singerList[position].Fsinger_name
            holder.singerAvatar.loadUrl(String.format(picBaseUrl_300, singerList[position].Fsinger_mid))
        }

        override fun getItemCount(): Int {
            return singerList.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            return ItemViewHolder(parent.inflate(R.layout.online_singer_item))
        }
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var singerAvatar: CircleImageView = itemView.findViewById(R.id.singer_avatar)
        var singerName: TextView = itemView.findViewById(R.id.singer_name)
    }
}