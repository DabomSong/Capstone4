package com.dabom.capstone4

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.IOException

class EmployeeDetailFragment : DialogFragment() {

    private lateinit var nameTextView: TextView
    private lateinit var idTextView: TextView
    private lateinit var dormitoryTextView: TextView
    private lateinit var positionTextView: TextView
    private lateinit var profileImageView: ImageView

    private var storageReference: StorageReference? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_employee_detail, container, false)

        nameTextView = rootView.findViewById(R.id.nameTextView)
        idTextView = rootView.findViewById(R.id.idTextView)
        dormitoryTextView = rootView.findViewById(R.id.dormitoryTextView)
        positionTextView = rootView.findViewById(R.id.positionTextView)
        profileImageView = rootView.findViewById(R.id.profileImage)

        // Firebase Storage의 레퍼런스 가져오기
        storageReference = FirebaseStorage.getInstance().reference

        // 가져온 정보를 텍스트뷰에 설정
        val args = arguments
        if (args != null) {
            val id = args.getString("id")
            val name = args.getString("name")
            val dormitory = args.getString("dept")
            val position = args.getString("age")

            idTextView.text = id
            nameTextView.text = name
            dormitoryTextView.text = dormitory
            positionTextView.text = position

            // 이미지를 Firebase Storage에서 가져와서 ImageView에 설정
            val imageReference = storageReference?.child("Pictures/$id.jpg")

            // 임시 파일 생성
            val localFile: File = File.createTempFile("tempImage", "jpg")

            imageReference?.getFile(localFile)?.addOnSuccessListener {
                // 이미지 다운로드가 성공했을 때
                // 로컬 파일에서 이미지를 비트맵으로 로드하여 ImageView에 설정
                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                profileImageView.setImageBitmap(bitmap)
            }?.addOnFailureListener { exception ->
                // 이미지 다운로드가 실패했을 때
                profileImageView.setImageResource(R.drawable.ic_baseline_person_24)
            }
        }

        return rootView
    }
}
