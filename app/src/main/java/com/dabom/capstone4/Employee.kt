package com.dabom.capstone4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.storage.FirebaseStorage
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileInputStream

class Employee : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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