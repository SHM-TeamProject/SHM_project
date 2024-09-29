package com.example.calendar_app

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val tasks: MutableList<Task>, private val viewModel: TaskViewModel) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.task_title)
        val contentTextView: TextView = view.findViewById(R.id.task_content)

        init {
            view.setOnClickListener {
                showItemDialog(adapterPosition) // 수정할지? 삭제할지? 선택하게 하는 Dialog 띄우기
            }
        }

        // 클릭한 item에 대한 수정 또는 삭제를 선택할 수 있는 Dialog를 띄우는 함수.
        private fun showItemDialog(position: Int) {
            val options = arrayOf("수정", "삭제")
            AlertDialog.Builder(view.context)
                .setTitle("수정 또는 삭제 선택하기")
                .setItems(options) { _, select ->
                    when (select) {
                        0 -> showEditDialog(position) // 수정 클릭
                        1 -> deleteTask(position) // 삭제 클릭
                    }
                }.show()
        }

        // 클릭한 item을 클릭해서 뜬 dialog에서 수정을 클릭했을 때의 함수!
        private fun showEditDialog(position: Int) {
            val editTaskDialog = EditTaskDialog(view.context)
            val curTitle = tasks[position].title // 클릭한 item의 현재 제목값
            val curContent = tasks[position].content // 클릭한 item의 현재 내용값
            
            // 클릭한 item의 제목과 내용을 Dialog에 세팅하기
            editTaskDialog.setInputValues(curTitle, curContent)

            // 입력한 제목과 내용을 바꿀 리스너 세팅하기
            editTaskDialog.setOnEditClickListener { title, content ->
                val taskId = tasks[position].id // 해당 Task의 ID를 가져옴
                viewModel.update(taskId, title, content) // 데이터베이스에 업데이트
                editTask(position, title, content) // RecyclerView의 리스트도 업데이트
            }

            editTaskDialog.show()
        }

        // 클릭한 item을 클릭해서 뜬 dialog에서 삭제 클릭했을 때의 함수!
        private fun deleteTask(position: Int) {
            viewModel.delete(tasks[position])
            tasks.removeAt(position)
            notifyItemRemoved(position)
        }

        // 클릭한 item 객체(Task)를 수정하는 함수.
        private fun editTask(position: Int, editTitle: String, editContent: String) {
            tasks[position].title = editTitle
            tasks[position].content = editContent
            notifyItemChanged(position)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.titleTextView.text = task.title
        holder.contentTextView.text = task.content
    }

    override fun getItemCount() = tasks.size
}