package com.dabom.capstone4

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dabom.capstone4.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView



class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)

        replaceFragment(Home())

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.cctv -> replaceFragment(CCTV())
                R.id.home -> replaceFragment(Home())
                R.id.employee -> replaceFragment(Employee())
                R.id.settings -> replaceFragment(Settings())
            }
            true
        }

        }

        private fun replaceFragment(fragment: Fragment){
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frame_layout, fragment)
                transaction.commit()

        }

    }

