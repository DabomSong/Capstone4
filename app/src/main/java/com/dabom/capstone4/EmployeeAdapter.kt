package com.dabom.capstone4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

class EmployeeAdapter(private val activity: FragmentActivity?) :
    RecyclerView.Adapter<EmployeeAdapter.ViewHolder>() {

    private val employeeList = mutableListOf<EmployeeData>()

    // Employee 목록 설정
    fun setEmployeeList(list: List<EmployeeData>) {
        employeeList.clear()
        employeeList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.employee_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employee = employeeList[position]
        holder.bind(employee)

        holder.itemView.setOnClickListener {
            val employeeDetailFragment = EmployeeDetailFragment()

            val bundle = Bundle()
            bundle.putString("id", employee.id)
            bundle.putString("name", employee.name)
            bundle.putString("dept", employee.dept)
            bundle.putString("age", employee.age)
            bundle.putBoolean("attendance", employee.attendance)
            employeeDetailFragment.arguments = bundle

            activity?.supportFragmentManager?.let { it1 ->
                employeeDetailFragment.show(
                    it1,
                    "EmployeeDetailFragment"
                )
            }
        }
    }


    override fun getItemCount() = employeeList.size

    fun addItem(item: EmployeeData) {
        employeeList.add(item)
        notifyDataSetChanged()
    }

    fun clearItems() {
        employeeList.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val ageTextView: TextView = itemView.findViewById(R.id.ageTextView)
        private val deptTextView: TextView = itemView.findViewById(R.id.deptTextView)
        private val attendanceStatusImageView: ImageView =
            itemView.findViewById(R.id.attendanceStatusImageView)

        fun bind(item: EmployeeData) {
            nameTextView.text = item.name
            ageTextView.text = item.age
            deptTextView.text = item.dept
            attendanceStatusImageView.setImageResource(
                if (item.attendance) R.drawable.ic_green_circle else R.drawable.ic_red_circle
            )
        }
    }
}
