package com.dabom.capstone4

import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.TextView
import com.dabom.capstone4.databinding.FragmentHomeBinding
import com.google.firebase.database.*
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel

class Home : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: DatabaseReference
    private lateinit var pieChart: PieChart
    private lateinit var presentCountTextView: TextView
    private lateinit var absentCountTextView: TextView
    private lateinit var lateCountTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pieChart = binding.piechart
        presentCountTextView = binding.present
        absentCountTextView = binding.absent
        lateCountTextView = binding.late

        val webView1: WebView = binding.webView1
        val webView2: WebView = binding.webView2
        val webView3: WebView = binding.webView3


        // WebView1 설정
        webView1.settings.javaScriptEnabled = true
        webView1.settings.loadWithOverviewMode = true
        webView1.settings.useWideViewPort = true

        webView1.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                val params = webView1.layoutParams as LinearLayout.LayoutParams
                params.width = LinearLayout.LayoutParams.MATCH_PARENT
                params.height = LinearLayout.LayoutParams.MATCH_PARENT
                webView1.layoutParams = params
            }
        }

// WebView2 설정
        webView2.settings.javaScriptEnabled = true
        webView2.settings.loadWithOverviewMode = true
        webView2.settings.useWideViewPort = true

        webView2.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                val params = webView2.layoutParams as LinearLayout.LayoutParams
                params.width = LinearLayout.LayoutParams.MATCH_PARENT
                params.height = LinearLayout.LayoutParams.MATCH_PARENT
                webView2.layoutParams = params
            }
        }

// WebView3 설정
        webView3.settings.javaScriptEnabled = true
        webView3.settings.loadWithOverviewMode = true
        webView3.settings.useWideViewPort = true

        webView3.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                val params = webView3.layoutParams as LinearLayout.LayoutParams
                params.width = LinearLayout.LayoutParams.MATCH_PARENT
                params.height = LinearLayout.LayoutParams.MATCH_PARENT
                webView3.layoutParams = params
            }
        }

        // WebView1 설정

        webView1.loadUrl("http://192.168.219.129/")
        webView1.settings.javaScriptEnabled = true
        // WebView2 설정
        webView2.loadUrl("http://192.168.219.120:8000/")
        webView2.settings.javaScriptEnabled = true

        // WebView2 설정
        webView3.loadUrl("http://192.168.219.120:9000/")
        webView3.settings.javaScriptEnabled = true


        // Firebase 실시간 데이터베이스의 "users" 레퍼런스를 가져옴
        database = FirebaseDatabase.getInstance().getReference("users")

        // Firebase 실시간 데이터베이스에서 데이터를 가져와서 출결한 사람 수를 표시
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var presentCount = 0
                var absentCount = 0
                var lateCount = 0

                for (userSnapshot in dataSnapshot.children) {
                    val attendance = userSnapshot.child("Attendance").getValue(Boolean::class.java)
                    if (attendance != null) {
                        if (attendance) {
                            presentCount++
                        } else {
                            absentCount++
                        }
                    }
                }

                updateAttendanceChart(presentCount, absentCount, lateCount)
            }

            override fun onCancelled(error: DatabaseError) {
                // 실패 시 로그 출력
                Log.e(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    private fun updateAttendanceChart(presentCount: Int, absentCount: Int, lateCount: Int) {
        val totalAttendance = presentCount + absentCount + lateCount

        presentCountTextView.text = "출근: $presentCount"
        absentCountTextView.text = "결근: $absentCount"
        lateCountTextView.text = "지각: $lateCount"

        pieChart.clearChart()
        pieChart.addPieSlice(
            PieModel(
                "Present",
                presentCount.toFloat(),
                Color.parseColor("#66BB6A")
            )
        )
        pieChart.addPieSlice(
            PieModel(
                "Absent",
                absentCount.toFloat(),
                Color.parseColor("#EF5350")
            )
        )
        pieChart.addPieSlice(
            PieModel(
                "Late",
                lateCount.toFloat(),
                Color.parseColor("#FFA726")
            )
        )

        pieChart.startAnimation()
    }
}
