package com.example.studentmanagement

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagement.adapter.StudentAdapter
import com.example.studentmanagement.database.DatabaseHelper
import com.example.studentmanagement.databinding.ActivityStudentBinding
import com.example.studentmanagement.model.StudentList

class studentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentBinding
    private lateinit var recyclerView: RecyclerView
    var studentAdapter:StudentAdapter ?= null
    var dbHandler:DatabaseHelper ?= null
    var studentList:List<StudentList> = ArrayList<StudentList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = findViewById(R.id.recyclerView)

        dbHandler = DatabaseHelper(this)
        fetchlist()

        binding.AddStudent.setOnClickListener {
            val intent = Intent(applicationContext,add_student::class.java)
            startActivity(intent)
        }

    }

    private fun fetchlist(){
        studentList = dbHandler?.getAllStudent() ?: emptyList()
        studentAdapter = StudentAdapter(studentList,applicationContext)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = studentAdapter
        studentAdapter?.notifyDataSetChanged()
    }

}