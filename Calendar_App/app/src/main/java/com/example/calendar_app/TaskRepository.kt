package com.example.calendar_app

class TaskRepository(private val taskDao: TaskDao) {

    // 모든 Task를 가져오는 메서드
    suspend fun getAllTasks(): List<Task> {
        return taskDao.getAllTasks()    // suspend 함수 호출
    }

    // Task 삽입
    suspend fun insert(task: Task) {
        if(task.title.isNotEmpty()) {
            taskDao.insert(task)
        }
    }

    // Task 업데이트
    suspend fun update(taskId: Int, title: String, content: String) {
        taskDao.update(taskId, title, content)
    }

    // Task 삭제
    suspend fun delete(task: Task) {
        taskDao.delete(task)
    }

    // 특정 id로 Task 가져오기
    suspend fun getTaskById(id: Int): Task? {
        return taskDao.getTaskById(id)
    }

    suspend fun getTaskByDate(date: String): List<Task?> {
        return taskDao.getTaskByDate(date)
    }

    suspend fun deleteAllTasks() {
        taskDao.deleteAllTasks()
    }

}