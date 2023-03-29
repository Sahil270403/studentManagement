package com.example.studentmanagement.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagement.R
import com.example.studentmanagement.add_student
import com.example.studentmanagement.model.StudentList

class StudentAdapter(tasklist: List<StudentList>,internal var context: Context) :RecyclerView.Adapter<StudentAdapter.StudentViewHolder> ()
{
    internal var tasklist:List<StudentList> = ArrayList()
    init {
        this.tasklist = tasklist
    }
    inner class StudentViewHolder(view: View):RecyclerView.ViewHolder(view){
        var name :TextView = view.findViewById(R.id.sName)
        var id :TextView = view.findViewById(R.id.srollNo)
        var dob :TextView = view.findViewById(R.id.dob)
        var phone :TextView = view.findViewById(R.id.phone)
        var email :TextView = view.findViewById(R.id.semail)
        var btn_ebit:Button = view.findViewById(R.id.editDetails)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.student_list_view,parent,false)
        return StudentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasklist. size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val task = tasklist[position]
        holder.name.setText(task.name)
        holder.id.setText(task.id.toString())
        holder.dob.setText(task.dob)
        holder.phone.setText(task.phone.toString())
        holder.email.setText(task.email)

        holder.btn_ebit.setOnClickListener {
            val intent = Intent(context,add_student::class.java)
            intent.putExtra("Mode","E")
            intent.putExtra("Id",task.id)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }


    }

}