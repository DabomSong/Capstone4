package com.dabom.capstone4

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import com.dabom.capstone4.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var emergencyRef: DatabaseReference
    private lateinit var attentionRef: DatabaseReference
    private lateinit var emergencyListener: ValueEventListener
    private lateinit var attentionListener: ValueEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        database = FirebaseDatabase.getInstance()
        emergencyRef = database.reference.child("Emergency")
        attentionRef = database.reference.child("Attention")

        replaceFragment(Home())

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.cctv -> replaceFragment(CCTV())
                R.id.home -> replaceFragment(Home())
                R.id.employee -> replaceFragment(Employee())
                R.id.settings -> replaceFragment(Settings())
            }
            true
        }

        emergencyListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val emergencyValue = dataSnapshot.getValue(Boolean::class.java)
                if (emergencyValue == true) {
                    // Emergency 값이 true로 변경되었을 때 알림을 보내는 로직을 여기에 구현합니다.
                    sendEmergencyNotification()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 데이터베이스 읽기가 취소되었을 때 처리할 내용을 여기에 구현합니다.
            }
        }
        attentionListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val attentionValue = dataSnapshot.getValue(Boolean::class.java)
                if (attentionValue == true) {
                    // Emergency 값이 true로 변경되었을 때 알림을 보내는 로직을 여기에 구현합니다.
                    sendAttentionNotification()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 데이터베이스 읽기가 취소되었을 때 처리할 내용을 여기에 구현합니다.
            }
        }

        emergencyRef.addValueEventListener(emergencyListener)
        attentionRef.addValueEventListener(attentionListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        emergencyRef.removeEventListener(emergencyListener)
        attentionRef.removeEventListener(attentionListener)
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, fragment)
        transaction.commit()
    }

    private fun sendEmergencyNotification() {
        val channelId = "emergency_channel_id" // 알림 채널 ID
        val title = "Emergency" // 알림 제목
        val message = "Emergency situation detected" // 알림 메시지

        val intent = Intent(this, MainActivity::class.java) // 알림 클릭 시 실행할 액티비티 지정
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE) // FLAG_IMMUTABLE 추가

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Emergency Channel", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }
    private fun sendAttentionNotification() {
        val channelId = "attention_channel_id" // 알림 채널 ID
        val title = "Attention" // 알림 제목
        val message = "Attention situation detected" // 알림 메시지

        val intent = Intent(this, MainActivity::class.java) // 알림 클릭 시 실행할 액티비티 지정
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE) // FLAG_IMMUTABLE 추가

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Attention Channel", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }
}


