package com.example.calendar_app

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val tasks: MutableList<Task>) :
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

        // 클릭한 item을 클랙해서 뜬 dialog에서 수정을 클릭했을 때의 함수!
        private fun showEditDialog(position: Int) {
            /*TODO: 수정했을 때 동작 적어야 함.*/

        }

        // 클릭한 item을 클랙해서 뜬 dialog에서 삭제 클릭했을 때의 함수!
        private fun deleteTask(position: Int) {
            tasks.removeAt(position)
            notifyItemRemoved(position)
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