package com.se.music.fragment

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment

/**
 *Author: gaojin
 *Time: 2018/5/13 下午6:30
 */

class SEWebViewFragment : Fragment() {

    private lateinit var mWebView: WebView
    private var mWebUrl: String? = null

    companion object {
        private const val mWebUrl = "WebView_WEBURL"
        fun newInstance(url: String?): SEWebViewFragment {
            val fragment = SEWebViewFragment()
            val args = Bundle()
            args.putString(mWebUrl, url)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
            mWebUrl = bundle.getString(mWebUrl)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val setting = mWebView.settings
        // 是否支持和JS交互
        setting.javaScriptEnabled = true
        // 是否支持缩放
        setting.setSupportZoom(false)
        // 是否显示缩放工具
        setting.builtInZoomControls = false

        mWebView.run {
            loadUrl(mWebUrl)
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    view.loadUrl(url)
                    return true
                }
            }
            webChromeClient = object : WebChromeClient() {
                override fun onReceivedTitle(view: WebView, title: String) {
                }
            }
        }
    }
}