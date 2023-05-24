package com.dabom.capstone4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

class EmployeeDetailFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_employee_detail, container, false)

        // 팝업 창 내용 및 동작 처리
        // 필요한 뷰들을 rootView.findViewById로 참조하고 원하는 동작을 구현합니다.

        return rootView
    }
}
