package com.dabom.capstone4

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import android.widget.VideoView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class CCTV : Fragment() {
    private lateinit var webView1: WebView
    private lateinit var webView2: WebView
    private lateinit var button: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_c_c_t_v, container, false)

        webView1 = rootView.findViewById(R.id.webView1)
        webView2 = rootView.findViewById(R.id.webView2)

        webView1.settings.javaScriptEnabled = true
        webView1.loadUrl("http://172.18.82.47/")

        webView2.settings.javaScriptEnabled = true
        webView2.loadUrl("http://172.18.197.204/")

        button = rootView.findViewById(R.id.button)
        button.setOnClickListener {

            // 다른 Fragment 띄우기
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.cardViewWebView1, CCTVListView())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()


        }
        return rootView
    }
}