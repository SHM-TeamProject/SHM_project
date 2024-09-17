package com.example.calendar_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TaskAddActivity : AppCompatActivity(), TimePicker.OnTimeChangedListener {
    private lateinit var titleEdit: EditText
    private lateinit var startBtn: Button
    private lateinit var endBtn: Button
    private lateinit var contentEdit: EditText
    private lateinit var cancelBtn: Button
    private lateinit var saveBtn: Button
    private lateinit var timePicker: TimePicker
    private var startTime:Int = 0
    private var endTime:Int = 0
    private var checkStartBtn:Boolean = false
    private var checkEndBtn:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_add)
        /** TODO: 여기부터 코드 작성 시작 **/
        titleEdit = findViewById(R.id.task_title)
        startBtn = findViewById(R.id.startTaskTime)
        endBtn = findViewById(R.id.endTaskTime)
        contentEdit = findViewById(R.id.task_content)
        cancelBtn = findViewById(R.id.cancel_btn)
        saveBtn = findViewById(R.id.save_btn)
        timePicker = findViewById(R.id.timePicker)
        timePicker.setOnTimeChangedListener(this)

        startBtn.setOnClickListener {
            if(!checkStartBtn) {
                timePicker.visibility = View.VISIBLE
                checkStartBtn = true
            }
            else {
                timePicker.visibility = View.GONE
                checkStartBtn = false
            }
        }
        endBtn.setOnClickListener {
            if(!checkEndBtn) {
                timePicker.visibility = View.VISIBLE
                checkEndBtn = true
            }
            else {
                timePicker.visibility = View.GONE
                checkEndBtn = false
            }
        }
        cancelBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            setResult(RESULT_OK, intent)
            finish()
        }
        saveBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            if(titleEdit.text.toString() != "" && startTime <= endTime){
                intent.putExtra("title", titleEdit.text.toString())
                intent.putExtra("startTime", startBtn.text.toString())
                intent.putExtra("endTime", endBtn.text.toString())
                intent.putExtra("content", contentEdit.text.toString())
                setResult(RESULT_OK, intent)
                finish()
            }
            else if(titleEdit.text.toString() == ""){
                Toast.makeText(applicationContext, "제목을 작성해야 합니다.", Toast.LENGTH_SHORT).show()
            }
            else if(startTime > endTime)
                Toast.makeText(applicationContext, "죵료 시간은 시작 시간 이후여야 합니다ㅇ.", Toast.LENGTH_SHORT).show()

        }
    }
    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int){
        val meridiem:String
        val hour:Int
        if(checkStartBtn){
            if(hourOfDay > 12) {
                meridiem = "오후"
                hour = hourOfDay - 12
            }
            else {
                meridiem = "오전"
                hour = hourOfDay
            }
            startTime = hourOfDay * 60 + minute
            startBtn.text = meridiem + " ${hour}:${minute}"
        }
        else if(checkEndBtn){
            if(hourOfDay > 12) {
                meridiem = "오후"
                hour = hourOfDay - 12
            }
            else {
                meridiem = "오전"
                hour = hourOfDay
            }
            endTime = hourOfDay * 60 + minute
            endBtn.text = meridiem + " ${hour}:${minute}"
        }
    }
}