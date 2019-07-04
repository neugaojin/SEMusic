package com.se.music.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.se.music.R
import com.se.music.base.BasePageFragment
import com.se.music.base.picBaseUrl_300
import com.se.music.online.model.SingerModel
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

    private lateinit var recycleView: RecyclerView
    private lateinit var singerAdapter: SingerAdapter
    private var singerList: MutableList<SingerModel.Data.Singer> = ArrayList()

    companion object {
        fun newInstance(): OnLineSingerFragment {
            return OnLineSingerFragment()
        }
    }

    override fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_online_singer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setTitle(context!!.getString(R.string.classify_singer))
        recycleView = view.findViewById(R.id.online_singer)
        recycleView.layoutManager = LinearLayoutManager(activity)
        recycleView.setHasFixedSize(true)
        singerAdapter = SingerAdapter()
        recycleView.adapter = singerAdapter

        mLoaderManager.initLoader(GET_SINGER_LIST, null, buildSingerCallback())
    }

    private fun buildSingerCallback(): CallLoaderCallbacks<SingerModel> {
        return object : CallLoaderCallbacks<SingerModel>(context!!) {
            override fun onCreateCall(id: Int, args: Bundle?): Call<SingerModel> {
                return MusicRetrofit.instance.getSinger(100, 1)
            }

            override fun onSuccess(loader: Loader<*>, data: SingerModel) {
                if (data.data?.list?.isEmpty() == false) {
                    singerList.addAll(data.data?.list!!)
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