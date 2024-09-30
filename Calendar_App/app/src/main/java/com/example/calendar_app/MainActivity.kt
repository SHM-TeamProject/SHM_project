package com.example.calendar_app

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale



class MainActivity : AppCompatActivity() {

    private lateinit var getAddedTaskResult: ActivityResultLauncher<Intent>
    private lateinit var calendarView: CalendarView
    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var inputText: EditText
    private lateinit var addButton: Button
    private var selectedDate: String = ""
    private lateinit var viewModel: TaskViewModel
    private lateinit var allTasks: List<Task>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calendarView = findViewById(R.id.calendarView)
        recyclerView = findViewById(R.id.recyclerView)
        inputText = findViewById(R.id.inputText)
        addButton = findViewById(R.id.addButton)

        // ViewModel 인스턴스 가져오기
        viewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(this)

        // 일정이 변경되면 RecyclerView 업데이트
        viewModel.allTasks.observe(this) { tasks ->
            allTasks = tasks
            updateTaskList()
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }
            val dateFormat = SimpleDateFormat("M'월' d'일'", Locale.getDefault())
            val todayDate = dateFormat.format(calendar.time)

            inputText.hint = todayDate + "에 일정 추가"
            selectedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
            updateTaskList()
        }


        val tasks = mutableListOf<String>()
        getAddedTaskResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val title = result.data?.getStringExtra("title") ?: ""
                selectedDate = result.data?.getStringExtra("chagedDate") ?: ""
                val startTime = result.data?.getStringExtra("startTime") ?: ""
                val endTime = result.data?.getStringExtra("endTime") ?: ""
                val content = result.data?.getStringExtra("content") ?: ""
                val time = "$startTime ~ $endTime"

                // 새로운 Task 객체 생성
                val newTask = Task(title = title, startTime = startTime, endTime = endTime, content = content, date = selectedDate)

                // ViewModel을 통해 Task를 데이터베이스에 저장
                viewModel.insert(newTask)

                // 일정이 추가된 후 UI 업데이트
                updateTaskList()
            }
        }

        /** 플러스 버튼을 클릭했을 때, 프레그먼트 이동시키기 **/
        addButton.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                taskViewModel.deleteAllTasks() // 모든 일정 삭제
//            }
            //앱 첫 실행 시 날짜 선택을 하지 않아 CalendarView의 setOnDateChangeListner가 동작하지 않으므로 날짜를 받아옴.
            selectedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendarView.date)
            if(inputText.text.toString() != "") {
                val taskTitle = inputText.text.toString()

                // 데이터베이스에 새로운 일정 추가
                CoroutineScope(Dispatchers.IO).launch {
                    val newTask = Task(title = taskTitle, startTime = "", endTime = "", content = "", date = selectedDate) // Task 객체 생성
                    viewModel.insert(newTask) // ViewModel을 통해 데이터베이스에 삽입
                }

                tasks.add(inputText.text.toString())
                updateTaskList()
                inputText.setText(null)
            }
            else{
                val intent = Intent(this, TaskAddActivity::class.java) // 엑티비티 이동
                intent.putExtra("selectedDate", selectedDate) // 선택된 날짜 전달
                getAddedTaskResult.launch(intent)
            }

        }
    }

    private fun updateTaskList() {
        val filteredTasks: MutableList<Task> = allTasks.filter { it.date == selectedDate }.toMutableList() // filter로 나오는 불변 리스트를 MutableList로 변경.
        taskAdapter = TaskAdapter(filteredTasks, viewModel)
        recyclerView.adapter = taskAdapter
    }
}