package com.example.calendar_app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.coroutines.coroutineContext

class TaskAddActivity : AppCompatActivity(), TimePicker.OnTimeChangedListener{
    private lateinit var titleEdit: EditText
    private lateinit var startBtn: Button
    private lateinit var endBtn: Button
    private lateinit var contentEdit: EditText
    private lateinit var cancelBtn: Button
    private lateinit var saveBtn: Button
    private lateinit var timePicker: TimePicker
    private lateinit var datePicker: DatePicker
    private var startCheckCount:Int = 0
    private var endCheckCount:Int = 0
    private var endTimeHour:Int = 0
    private var endTimeMin:Int = 0
    private var startTime:Int = 0
    private var endTime:Int = 0
    private var checkStartBtn:Boolean = false
    private var checkEndBtn:Boolean = false
    private var currentDate:String = ""
    private lateinit var taskViewModel: TaskViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_add)
        /** TODO: 여기부터 코드 작성 시작 **/
        // View Binding
        titleEdit = findViewById(R.id.task_title)
        currentDate = intent.getStringExtra("selectedDate") ?: SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().time)
        startBtn = findViewById(R.id.startTaskTime)
        endBtn = findViewById(R.id.endTaskTime)
        contentEdit = findViewById(R.id.task_content)
        cancelBtn = findViewById(R.id.cancel_btn)
        saveBtn = findViewById(R.id.save_btn)
        timePicker = findViewById(R.id.timePicker)
        timePicker.setOnTimeChangedListener(this)
        datePicker = findViewById(R.id.datePicker)
        val currentDateInfo:List<String> = currentDate.split('-')
        datePicker.init(currentDateInfo[0].toInt(), currentDateInfo[1].toInt() - 1, currentDateInfo[2].toInt(), null)
        datePicker.setOnDateChangedListener{ _, year, month, dayOfMonth ->  //데이트 피커에서 날짜 받아오기
            val calendar = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }
            currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
        }   //날짜 선택
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        checkClickStartBtn()
        checkClickEndBtn()
        cancelBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            setResult(RESULT_OK, intent)
            finish()
        }
        saveBtn.setOnClickListener {
            // ViewModel 인스턴스 가져오기
            val title = titleEdit.text.toString()
            val startTimeText = startBtn.text.toString().substringAfter(" ")
            val endTimeText = endBtn.text.toString().substringAfter(" ")
            val content = contentEdit.text.toString()


            if (title.isNotEmpty() && startTimeText.isNotEmpty() && endTimeText.isNotEmpty() && startTime <= endTime) {
                val newTask = Task(title = title, startTime = startTimeText, endTime = endTimeText, content = content, date = currentDate)

                CoroutineScope(Dispatchers.IO).launch {
                    taskViewModel.insert(newTask)

                    withContext(Dispatchers.Main) {
                        val intent = Intent(this@TaskAddActivity, MainActivity::class.java)
                        setResult(RESULT_OK, intent)
                        finish()
                    }
                }
            }
            else if (title.isEmpty()) {
                Toast.makeText(applicationContext, "제목을 작성해야 합니다.", Toast.LENGTH_SHORT).show()
            }
            else if (startTime > endTime) {
                Toast.makeText(applicationContext, "종료 시간은 시작 시간 이후여야 합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun checkClickStartBtn(){   //StartBtn 활성화/비활성화 Action
        startBtn.setOnClickListener {
            if(!checkStartBtn) {
                if(checkEndBtn){
                    timePicker.visibility = View.GONE
                    checkEndBtn = false
                }
                timePicker.visibility = View.VISIBLE
                checkStartBtn = true
                if(startCheckCount < 1)
                    startCheckCount++
            }
            else {
                timePicker.visibility = View.GONE
                checkStartBtn = false
            }
            if(startCheckCount < 1){
                timePicker.hour = 17
                timePicker.minute = 0
            }
            else{
                val currentSetStartTime:List<String> = startBtn.text.toString().split(':')
                val currentSetStartHour:List<String> = currentSetStartTime[0].split(' ')
                if(currentSetStartHour[0] == "오후")
                    timePicker.hour = currentSetStartHour[1].toInt() + 12
                else
                    timePicker.hour = currentSetStartHour[1].toInt()
                timePicker.minute = currentSetStartTime[1].toInt()
            }
        }
    }

    private fun checkClickEndBtn(){ //EndBtn 활성화/비활성화 Action
        endBtn.setOnClickListener {
            if(!checkEndBtn) {
                if(checkStartBtn){
                    timePicker.visibility = View.GONE
                    checkStartBtn = false
                }
                timePicker.visibility = View.VISIBLE
                checkEndBtn = true
                if(endCheckCount < 1)
                    endCheckCount++
            }
            else {
                timePicker.visibility = View.GONE
                checkEndBtn = false
            }
            if(endCheckCount < 1){
                timePicker.hour = 18
                timePicker.minute = 0
            }
            else{
                val currentSetEndTime:List<String> = endBtn.text.toString().split(':')
                val currentSetEndHour:List<String> = currentSetEndTime[0].split(' ')
                if(currentSetEndHour[0] == "오후")
                    timePicker.hour = currentSetEndHour[1].toInt() + 12
                else
                    timePicker.hour = currentSetEndHour[1].toInt()
                timePicker.minute = currentSetEndTime[1].toInt()
            }
        }
    }
    @SuppressLint("SetTextI18n")
    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int){
        //TimePicker의 시간 변경 이벤트 발생 시 실행되는 함수
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
            val strMin:String
            if(minute < 10)
                strMin = "0" + "${minute}"
            else
                strMin = "${minute}"
            startBtn.text = "$meridiem ${hour}:${strMin}"
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
            endTimeHour = hourOfDay
            endTimeMin = minute
            val strMin:String
            if(minute < 10)
                strMin = "0" + "${minute}"
            else
                strMin = "${minute}"
            endBtn.text = "$meridiem ${hour}:${strMin}"
        }
    }
}