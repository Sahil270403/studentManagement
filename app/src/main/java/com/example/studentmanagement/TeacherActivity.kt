package com.example.studentmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagement.adapter.StudentAdapter
import com.example.studentmanagement.adapter.TeacherAdapter
import com.example.studentmanagement.database.DatabaseHelper
import com.example.studentmanagement.databinding.ActivityTeacherBinding
import com.example.studentmanagement.model.TeacherList

class TeacherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTeacherBinding
    private lateinit var recyclerView: RecyclerView
    var teacherAdapter: TeacherAdapter?= null
    var dbHandler: DatabaseHelper?= null
    var teacherList:List<TeacherList> = ArrayList<TeacherList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeacherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = findViewById(R.id.recyclerView1)

        dbHandler = DatabaseHelper(this)
        fetchlist()

        binding.AddTeacher.setOnClickListener {
            val intent = Intent(applicationContext,add_teacherActivity::class.java)
            startActivity(intent)
        }

    }

    private fun fetchlist(){
        teacherList = dbHandler?.getAllTeacher() ?: emptyList()
        teacherAdapter = TeacherAdapter(teacherList,applicationContext)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = teacherAdapter
        teacherAdapter?.notifyDataSetChanged()
    }

}