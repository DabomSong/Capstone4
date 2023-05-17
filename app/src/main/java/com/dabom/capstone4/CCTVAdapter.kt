package com.dabom.capstone4

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.StorageReference

// RecyclerView에서 사용할 Adapter를 작성합니다.
class CCTVAdapter(private val fileList: List<StorageReference>) :
    RecyclerView.Adapter<CCTVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cctv_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fileRef = fileList[position]
        val filename = fileRef.name

        holder.itemView.setOnClickListener {
            val videoView = VideoView(holder.itemView.context)
            val mediaController = MediaController(holder.itemView.context)
            mediaController.setAnchorView(videoView)
            videoView.setMediaController(mediaController)

            fileRef.downloadUrl.addOnSuccessListener { uri ->
                videoView.setVideoPath(uri.toString())
                videoView.start()
            }.addOnFailureListener { exception ->
                // URL 가져오기 실패 시 예외 처리 코드 작성
            }

            val alertDialogBuilder = AlertDialog.Builder(holder.itemView.context)
                .setView(videoView)
                .setPositiveButton("닫기") { dialog, _ -> dialog.dismiss() }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

        holder.bind(filename)
    }

    override fun getItemCount(): Int = fileList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val fileNameView: TextView = itemView.findViewById(R.id.cctv_title)

        fun bind(filename: String) {
            fileNameView.text = filename
        }
    }
}

