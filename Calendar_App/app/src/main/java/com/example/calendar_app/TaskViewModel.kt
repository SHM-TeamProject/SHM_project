package com.example.calendar_app

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TaskRepository = TaskRepository(AppDatabase.getDatabase(application).taskDao())

    // 모든 Task 데이터를 저장하는 LiveData
    private val _allTasks = MutableLiveData<List<Task>>()
    val allTasks: LiveData<List<Task>> = _allTasks

    // ViewModel이 생성되면 모든 Task 목록을 가져옴
    init {
        getAllTasks()
    }

    // 모든 Task를 비동기적으로 가져오는 함수
    private fun getAllTasks() = viewModelScope.launch {
        try {
            val tasks = repository.getAllTasks() // suspend 함수 호출
            _allTasks.postValue(tasks)
        } catch (e: Exception) {
            // 오류 처리: 필요에 따라 로그나 UI에 메시지 표시 가능
        }
    }

    // Task 삽입
    fun insert(task: Task) = viewModelScope.launch {
        try {
            repository.insert(task)
            getAllTasks() // 삽입 후 Task 목록 갱신
        } catch (e: Exception) {
            // 오류 처리
        }
    }

    // Task 업데이트
    fun update(task: Task) = viewModelScope.launch {
        try {
            repository.update(task)
            getAllTasks()
        } catch (e: Exception) {
            // 오류 처리
        }
    }

    // Task 삭제
    fun delete(task: Task) = viewModelScope.launch {
        try {
            repository.delete(task)
            getAllTasks()
        } catch (e: Exception) {
            // 오류 처리
        }
    }

    // 특정 id로 Task 가져오기 (비동기적으로 처리하여 LiveData에 저장)
    fun getTaskById(id: Int): LiveData<Task?> {
        val taskLiveData = MutableLiveData<Task?>()
        viewModelScope.launch {
            try {
                val task = repository.getTaskById(id)
                taskLiveData.postValue(task)
            } catch (e: Exception) {
                // 오류 처리
            }
        }

        return taskLiveData
    }

    fun getTasksByDate(date: String): LiveData<List<Task?>> {
        val dateLiveData = MutableLiveData<List<Task?>>()
        viewModelScope.launch {
            try {
                val tasks = repository.getTaskByDate(date)
                dateLiveData.postValue(tasks)
            } catch (e: Exception) {
                // 오류 처리
                dateLiveData.postValue(emptyList()) // 오류 시 빈 리스트 반환
            }
        }
        return dateLiveData
    }

    fun deleteAllTasks() {
        viewModelScope.launch {
            repository.deleteAllTasks()
        }
    }
}