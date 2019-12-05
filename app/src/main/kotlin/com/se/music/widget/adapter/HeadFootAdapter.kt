package com.se.music.widget.adapter

import android.content.ClipData
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Space
import androidx.recyclerview.widget.RecyclerView

/**
 *Author: gaojin
 *Time: 2019-12-02 16:19
 */

abstract class HeadFootAdapter<T, in VH : RecyclerView.ViewHolder>(val context: Context, val data: MutableList<T>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val HEADER = 0x01
        private const val ITEM = 0x02
        private const val FOOTER = 0x03
    }

    private var header: View? = null
    private var footer: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER -> {
                if (header == null) {
                    header = Space(context)
                }
                HeadFootHolder(header!!)
            }
            FOOTER -> {
                if (footer == null) {
                    footer = Space(context)
                }
                HeadFootHolder(footer!!)
            }
            else -> createItem(parent, viewType)
        }
    }

    override fun getItemCount() = data.size.plus(2) ?: 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position in firstIndex() + 1 until lastIndex()) {
            bindItem(holder as VH, position - 1)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            firstIndex() -> HEADER
            lastIndex() -> FOOTER
            else -> ITEM
        }
    }

    fun addHeader(header: View) {
        this.header = header
        notifyItemChanged(firstIndex())
    }

    fun addFooter(footer: View) {
        this.footer = footer
        notifyItemChanged(lastIndex())
    }

    fun addData(list: List<T>) {
        data.addAll(list)
    }

    abstract fun createItem(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    abstract fun bindItem(holder: VH, position: Int)

    open fun firstIndex() = 0
    open fun lastIndex() = itemCount - 1

    internal class HeadFootHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}