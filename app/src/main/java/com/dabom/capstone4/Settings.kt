package com.dabom.capstone4

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class Settings : Fragment() {

    private lateinit var attendanceTime: Button
    private lateinit var attendanceTimeResult: TextView
    private lateinit var cctv1Button: Button
    private lateinit var cctv2Button: Button
    private lateinit var cctv3Button: Button
    private lateinit var cctv1IP: EditText
    private lateinit var cctv2IP: EditText
    private lateinit var cctv3IP: EditText
    private lateinit var home: Home
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        attendanceTime = view.findViewById(R.id.attendenceTimeButton)
        attendanceTimeResult = view.findViewById(R.id.attendenceTimeResult)

        var timeString = ""

        attendanceTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                var convertedHour = hourOfDay
                if (hourOfDay > 12 ) {
                    convertedHour -= 12
                    timeString = "오후 ${convertedHour}시 ${minute}분"
                }
                else timeString = "오전 ${hourOfDay}시 ${minute}분"

                attendanceTimeResult.text = "출근 시간: $timeString"

                val database = FirebaseDatabase.getInstance()
                val timeReference = database.getReference("Time")
                timeReference.setValue(timeString)

            }
            TimePickerDialog(
                requireContext(),
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }
        cctv1IP = view.findViewById(R.id.cctv1IP)
        cctv2IP = view.findViewById(R.id.cctv2IP)
        cctv3IP = view.findViewById(R.id.cctv3IP)

        cctv1IP.hint = "CCTV1 IP를 입력하세요"
        cctv2IP.hint = "CCTV2 IP를 입력하세요"
        cctv3IP.hint = "CCTV3 IP를 입력하세요"

        cctv1Button = view.findViewById(R.id.cctv1Button)
        cctv2Button = view.findViewById(R.id.cctv2Button)
        cctv3Button = view.findViewById(R.id.cctv3Button)

//        home = (activity?.supportFragmentManager?.findFragmentByTag("homeFragmentTag") as? Home)!!
        cctv1Button.setOnClickListener{

            val url1 = cctv1IP.text.toString()
            home.webView1.loadUrl("http//" + url1 + "/")
        }
        cctv2Button.setOnClickListener{
            val url2 = cctv1IP.text.toString()
            home.webView1.loadUrl("http//" + url2 + "/")
        }
        cctv3Button.setOnClickListener{
            val url3 = cctv1IP.text.toString()
            home.webView1.loadUrl("http//" + url3 + "/")
        }




        return view
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        home = (activity as MainActivity).homeFragment
    }
}
