package com.dabom.capstone4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class CCTVListView : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CCTVAdapter
    private lateinit var switch: Switch

    private var fileList = mutableListOf<StorageReference>()
    private var filteredFileList = mutableListOf<StorageReference>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_c_c_t_v_list_view, container, false)

        recyclerView = view.findViewById(R.id.cctvlistView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference.child("videos/C:/Users/user/Desktop/Capstone/Jupyter/2022_2_Capstone/Video")
        switch = view.findViewById(R.id.switch1)

        switch.setOnCheckedChangeListener { _, isChecked ->
            filterFiles(isChecked)
            adapter.notifyDataSetChanged()
        }

        storageRef.listAll().addOnSuccessListener { listResult ->
            fileList.clear()
            for (item in listResult.items) {
                fileList.add(item)
            }
            filterFiles(switch.isChecked)
            adapter = CCTVAdapter(filteredFileList)
            recyclerView.adapter = adapter
        }.addOnFailureListener { exception ->
            // 예외 처리 코드 작성
        }
        return view
    }

    private fun filterFiles(isChecked: Boolean) {
        filteredFileList.clear()
        if (isChecked) {
            for (file in fileList) {
                val fileName = file.name
                if (fileName.contains("Emergency", ignoreCase = true)) {
                    filteredFileList.add(file)
                }
            }
        } else {
            filteredFileList.addAll(fileList)
        }
    }
}





