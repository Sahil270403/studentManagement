package com.example.studentmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.studentmanagement.database.DatabaseHelper
import com.example.studentmanagement.databinding.ActivityAddStudentBinding
import com.example.studentmanagement.model.StudentList

class add_student : AppCompatActivity() {
    private lateinit var binding: ActivityAddStudentBinding
    var dbHandler: DatabaseHelper? = null
    var isEditMode: Boolean = false

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityAddStudentBinding.inflate(layoutInflater)
    setContentView(binding.root)

    dbHandler = DatabaseHelper(this)

    Glide.with(this)
        .load(R.drawable.student1)
        .into(binding.imageview);

    if (intent != null && intent.getStringExtra("Mode") == "E") {
        //update
        isEditMode = true
        binding.save.text = "Update Data"
        binding.delete.visibility = View.VISIBLE

        val task : StudentList = dbHandler!!.getTask(intent.getIntExtra("Id",0))
        binding.sname.setText(task.name)
        binding.sdob.setText(task.dob)
        binding.email.setText(task.email)
        binding.sphone.setText(task.phone.toString())
        binding.sid.setText(task.id.toString())
    } else {
        //insert
        isEditMode = false
        binding.save.text = "Save Data"
        binding.delete.visibility = View.GONE
    }

    binding.save.setOnClickListener {
        var success: Boolean = false
        val task: StudentList = StudentList()

        if (isEditMode) {
            //update
            task.id = intent.getIntExtra("Id",0)
            task.name = binding.sname.text.toString()
            task.dob = binding.sdob.text.toString()
            task.email = binding.email.text.toString()
            task.phone = binding.sphone.text.toString().toLong()

            success = dbHandler?.updateStudent(task) as Boolean

        } else {
            //insert
            task.name = binding.sname.text.toString()
            task.id = binding.sid.text.toString().toInt()
            task.dob = binding.sdob.text.toString()
            task.email = binding.email.text.toString()
            task.phone = binding.sphone.text.toString().toLong()

            success = dbHandler?.addStudent(task) as Boolean
        }

        if (success) {
            Toast.makeText(applicationContext, "Data has been added successfully", Toast.LENGTH_SHORT).show()
            val intent  = Intent(applicationContext,studentActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(applicationContext, "Error occurred while adding data", Toast.LENGTH_SHORT).show()
        }
    }

    binding.delete.setOnClickListener {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Info")
            .setMessage("Click Yes If You Want to Delete the Data")
            .setPositiveButton("No") { dialog, i ->
            dialog.dismiss()
        }
            .setNegativeButton("Yes"){ dialog, i ->
                val success = dbHandler?.deleteStudent(intent.getIntExtra("Id", 0)) as kotlin.Boolean
                if (success) {
                    android.widget.Toast.makeText(applicationContext, "Data has been deleted successfully", android.widget.Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    android.widget.Toast.makeText(applicationContext, "Error occurred while deleting data", android.widget.Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
             dialog.show()
        }

    }
}
