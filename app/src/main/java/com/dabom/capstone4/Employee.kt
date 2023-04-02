package com.dabom.capstone4

<<<<<<< HEAD
import android.os.Bundle
=======
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
>>>>>>> 6a5aa6c (Initial commit)
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import com.google.firebase.storage.FirebaseStorage
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileInputStream

class Employee : Fragment() {
=======
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class Employee : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var employeeAdapter: EmployeeAdapter
    private lateinit var recyclerView: RecyclerView

>>>>>>> 6a5aa6c (Initial commit)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
<<<<<<< HEAD
        return inflater.inflate(R.layout.fragment_employee, container, false)
//        val storageRef = FirebaseStorage.getInstance().reference
//        val excelRef = storageRef.child("gs://capstone-e566b.appspot.com/Employee/Red_직원정보.xlsx")
//
//        excelRef.getBytes(1024 * 1024)
//            .addOnSuccessListener { bytes ->
//                // 파일 다운로드 성공
//                // bytes 변수에 파일 데이터가 들어 있습니다.
//            }
//            .addOnFailureListener { exception ->
//                // 파일 다운로드 실패
//            }
//        fun readExcelFile(excelRef: String) {
//            val inputStream = FileInputStream(excelRef)
//            val workbook = XSSFWorkbook(inputStream)
//            val sheet = workbook.getSheetAt(0)
//
//            for (row in sheet) {
//                val name = row.getCell(1)?.stringCellValue ?: continue
//                val department = row.getCell(2)?.stringCellValue ?: continue
//                val position = row.getCell(3)?.stringCellValue ?: continue
//                val attendance = row.getCell(4)?.stringCellValue ?: continue
//                // 데이터 처리
//            }
//        }
    }
}
=======
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
        employeeAdapter = EmployeeAdapter()

        // RecyclerView에 어댑터를 설정
        recyclerView.adapter = employeeAdapter

        // Firebase 실시간 데이터베이스에서 데이터를 가져와서 EmployeeAdapter에 추가
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                employeeAdapter.clearItems()

                for (userSnapshot in dataSnapshot.children) {
                    val id = userSnapshot.child("ID").value.toString()
                    val name = userSnapshot.child("name").value.toString()
                    val dept = userSnapshot.child("dept").value.toString()
                    val age = userSnapshot.child("age").value.toString()
                    employeeAdapter.addItem(EmployeeData(id, name, dept, age))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // 실패 시 로그 출력
                Log.e(TAG, "Failed to read value.", error.toException())
            }
        })
    }
}
>>>>>>> 6a5aa6c (Initial commit)
