package com.dabom.capstone4

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class Settings : Fragment() {

    private lateinit var attendanceTime: Button
    private lateinit var temperature: Button
    private lateinit var attendanceTimeResult: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        attendanceTime = view.findViewById(R.id.attendenceTimeButton)
        temperature = view.findViewById(R.id.tempuratureButton)
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


        return view
    }
}
