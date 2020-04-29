package com.se.music.scene.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bytedance.scene.group.GroupScene
import com.bytedance.scene.navigation.OnBackPressedListener
import com.se.music.R

/**
 *Author: gaojin
 *Time: 2019-12-08 21:56
 * 带有Se主题Title的Scene
 */

abstract class SeCompatScene : GroupScene() {

    private lateinit var toolbarTitle: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): ViewGroup {
        val root = inflater.inflate(R.layout.fragment_base, container, false)
        val baseToolbar = root.findViewById<Toolbar>(R.id.base_toolbar)
        toolbarTitle = root.findViewById(R.id.toolbar_title)
        (root as ViewGroup).addView(createContentView(inflater, container, savedInstanceState))
        (activity as AppCompatActivity).setSupportActionBar(baseToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowTitleEnabled(false)
        baseToolbar.setNavigationOnClickListener {
            requireNavigationScene().pop()
        }
        requireNavigationScene().addOnBackPressedListener(this, OnBackPressedListener {
            requireNavigationScene().pop()
            true
        })
        return root
    }

    fun setTitle(title: String) {
        toolbarTitle.text = title
    }

    abstract fun createContentView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View
}