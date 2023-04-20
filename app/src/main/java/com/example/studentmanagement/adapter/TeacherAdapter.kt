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
import com.example.studentmanagement.add_teacherActivity
import com.example.studentmanagement.model.TeacherList

class TeacherAdapter (tasklist: List<TeacherList>, internal var context: Context) : RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder> ()
{
    internal var tasklist:List<TeacherList> = ArrayList()
    init {
        this.tasklist = tasklist
    }
    inner class TeacherViewHolder(view: View): RecyclerView.ViewHolder(view){
        var name : TextView = view.findViewById(R.id.TName)
        var dig : TextView = view.findViewById(R.id.Tdig)
        var cid : TextView = view.findViewById(R.id.Tcid)
        var id : TextView = view.findViewById(R.id.Tid)
        var subject : TextView = view.findViewById(R.id.tsubject)
        var btn_ebit: Button = view.findViewById(R.id.editDetails1)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.teacher_list_view,parent,false)
        return TeacherViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasklist. size
    }

    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int) {
        val task = tasklist[position]
        holder.name.setText(task.name)
        holder.id.setText(task.id.toString())
        holder.cid.setText(task.courseid)
        holder.subject.setText(task.subject)
        holder.dig.setText(task.desig)

        holder.btn_ebit.setOnClickListener {
            val intent = Intent(context, add_teacherActivity::class.java)
            intent.putExtra("Mode","E")
            intent.putExtra("Id",task.id)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }


    }

}