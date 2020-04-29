package com.se.music.scene.sub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.se.music.R
import com.se.music.scene.base.DialogScene

/**
 *Author: gaojin
 *Time: 2020/4/29 4:17 PM
 */

class BottomListScene : DialogScene() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateContentView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.scene_bottom_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireViewById<View>(R.id.dialog_close_button).setOnClickListener {
            dismiss()
        }
        recyclerView = requireViewById(R.id.dialog_list_view)
    }
}