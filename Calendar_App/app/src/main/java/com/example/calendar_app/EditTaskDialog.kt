package com.example.calendar_app

import android.content.Context
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText

class EditTaskDialog(context: Context) : Dialog(context) {
    private var onEditClickListener: ((String, String) -> Unit)? = null

    init {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.edit_dialog, null)
        setContentView(view)
        
        // 제목, 내용, 버튼 초기화
        val editDialogTitle = view.findViewById<EditText>(R.id.editDialogTitle)
        val editDialogContent = view.findViewById<EditText>(R.id.editDialogContent)
        val editDialogButton = view.findViewById<Button>(R.id.editDialogButton)

        // Dialog의 확인 버튼을 누르면 adapter에 입력된 값을 전달하도록 설정하기
        editDialogButton.setOnClickListener {
            val title = editDialogTitle.text.toString()
            val content = editDialogContent.text.toString()
            onEditClickListener?.invoke(title, content) // 입력된 값 전달
            dismiss() // Dialog 닫기
        }
    }

    // 수정하려고 선택한 일정에 대해 람다식을 받는 함수.
    fun setOnEditClickListener(listener: (String, String) -> Unit) {
        onEditClickListener = listener
    }

    // Dialog에 기존 일정의 제목과 내용 값으로 초기화해주는 함수
    fun setInputValues(title: String, content: String) {
        val editDialogTitle = findViewById<EditText>(R.id.editDialogTitle)
        val editDialogContent = findViewById<EditText>(R.id.editDialogContent)
        editDialogTitle.setText(title)
        editDialogContent.setText(content)
    }
}
