package com.dabom.capstone4

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.VideoView
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class CCTV : Fragment() {
    private lateinit var videoView: VideoView
    private lateinit var videoView2: VideoView
    private lateinit var button: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_c_c_t_v, container, false)
        videoView = rootView.findViewById(R.id.videoView)
        val StorageRef = FirebaseStorage.getInstance().reference
        val videoRef1 =
            StorageRef.child("videos/C:/Users/user/Desktop/Capstone/Jupyter/2022_2_Capstone/Video/recording_20230321_202749.avi")
        val localFile1: File = File.createTempFile("recording_20230321_202749", "avi")
        videoRef1.getFile(localFile1)
            .addOnSuccessListener { taskSnapshot ->

                val filepath = localFile1.path
                videoView.setVideoPath(filepath)
                videoView.start()

            }.addOnFailureListener { exception ->
                Log.e("CCTV", "File download failed: ${exception.message}")
                // 파일 다운로드 실패 시 예외 처리 코드를 작성합니다.
            }
        videoView2 = rootView.findViewById(R.id.videoView2)
        val videoRef2 =
            StorageRef.child("videos/C:/Users/user/Desktop/Capstone/Jupyter/2022_2_Capstone/Video/recording_20230321_202850.avi")
        val localFile2: File = File.createTempFile("recording_20230321_202850", "avi")
        videoRef2.getFile(localFile2)
            .addOnSuccessListener { taskSnapshot ->

                val filepath = localFile2.path
                videoView2.setVideoPath(filepath)
                videoView2.start()

            }.addOnFailureListener { exception ->
                Log.e("CCTV", "File download failed: ${exception.message}")
                // 파일 다운로드 실패 시 예외 처리 코드를 작성합니다.
            }

        button = rootView.findViewById(R.id.button)

        button.setOnClickListener {
            // 다른 Fragment 띄우기
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.cctv, CCTVListView())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()


        }
        return rootView
    }
}