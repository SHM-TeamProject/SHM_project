package com.example.calendar_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale



class MainActivity : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var inputText: EditText
    private lateinit var addButton: Button
    private val tasksMap = mutableMapOf<String, List<String>>()
    private var selectedDate: String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calendarView = findViewById(R.id.calendarView)
        recyclerView = findViewById(R.id.recyclerView)
        inputText = findViewById(R.id.inputText)
        addButton = findViewById(R.id.addButton)

        recyclerView.layoutManager = LinearLayoutManager(this)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }
            selectedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
            updateTaskList()
        }

        /** 오늘의 날짜 추가하기 **/
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("M'월' d'일'", Locale.getDefault())
        val todayDate = dateFormat.format(calendar.time)

        inputText.hint = todayDate + "에 일정 추가"

        /** 플러스 버튼을 클릭했을 때, 프레그먼트 이동시키기 **/
        addButton.setOnClickListener {
            val intent = Intent(this, TaskAddActivity::class.java) // 액티비티 이동
            startActivity(intent)
        }
    }

    // + 버튼을 눌렀을 때, 일정 추가 함수가 필요함.
    private fun addEvents() {

    }

    private fun updateTaskList() {
        val tasks = tasksMap[selectedDate] ?: emptyList()
        taskAdapter = TaskAdapter(tasks)
        recyclerView.adapter = taskAdapter
    }
}