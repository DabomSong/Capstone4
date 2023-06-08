package com.dabom.capstone4

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class Employee : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var employeeAdapter: EmployeeAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_employee, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.userRecyclerView)

        // Firebase 실시간 데이터베이스의 "users" 레퍼런스를 가져옴
        database = FirebaseDatabase.getInstance().getReference("users")

        // RecyclerView에 LinearLayoutManager를 설정
        recyclerView.layoutManager = LinearLayoutManager(activity)

        // EmployeeAdapter 생성
        employeeAdapter = EmployeeAdapter(requireActivity())

        // RecyclerView에 어댑터를 설정 (null 체크 추가)
        if (recyclerView != null && employeeAdapter != null) {
            recyclerView.adapter = employeeAdapter
        }

        // RecyclerView에 어댑터를 설정
        recyclerView.adapter = employeeAdapter


        // Firebase 실시간 데이터베이스에서 데이터를 가져와서 EmployeeAdapter에 추가
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val employeeList = mutableListOf<EmployeeData>()

                for (userSnapshot in dataSnapshot.children) {
                    val id = userSnapshot.child("ID").value.toString()
                    val name = userSnapshot.child("name").value.toString()
                    val dept = userSnapshot.child("dept").value.toString()
                    val age = userSnapshot.child("age").value.toString()
                    val attendance = userSnapshot.child("Attendance").getValue() as Boolean
                    employeeList.add(EmployeeData(id, name, dept, age, attendance))
                }

                // attendance 값을 기준으로 정렬
                employeeList.sortWith(compareByDescending<EmployeeData> { it.attendance }.thenBy { it.name })

                employeeAdapter.setEmployeeList(employeeList)
            }

            override fun onCancelled(error: DatabaseError) {
                // 실패 시 로그 출력
                Log.e(TAG, "Failed to read value.", error.toException())
            }
        })
    }
}

