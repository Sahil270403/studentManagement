package com.example.studentmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.studentmanagement.database.DatabaseHelper
import com.example.studentmanagement.databinding.ActivityAddTeacherBinding
import com.example.studentmanagement.model.TeacherList


class add_teacherActivity : AppCompatActivity() {

    var dbHandler: DatabaseHelper? = null
    var isEditMode: Boolean = false
    private lateinit var binding:ActivityAddTeacherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTeacherBinding.inflate(layoutInflater)
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

            val task : TeacherList = dbHandler!!.getTaskT(intent.getIntExtra("Id",0))!!
            binding.tname.setText(task.name)
            binding.subj.setText(task.subject)
            binding.tdig.setText(task.desig)
            binding.cid.setText(task.courseid)
            binding.tid.setText(task.id.toString())
        } else {
            //insert
            isEditMode = false
            binding.save.text = "Save Data"
            binding.delete.visibility = View.GONE
        }

        binding.save.setOnClickListener {
            var success: Boolean = false
            val task: TeacherList = TeacherList()


            if (isEditMode) {
                //update
                task.id = intent.getIntExtra("Id",0)
                task.name = binding.tname.text.toString()
                task.subject = binding.subj.text.toString()
                task.desig = binding.tdig.text.toString()
                task.courseid = binding.cid.text.toString()

                success = dbHandler?.updateTeacher(task) as Boolean

            } else {
                //insert
                task.name = binding.tname.text.toString()
                task.id = binding.tid.text.toString().toInt()
                task.desig = binding.tdig.text.toString()
                task.subject = binding.subj.text.toString()
                task.courseid = binding.cid.text.toString()

                success = dbHandler?.addTeacher(task) as Boolean
            }

            if (success) {
                Toast.makeText(applicationContext, "Data has been added successfully", Toast.LENGTH_SHORT).show()
                val intent  = Intent(applicationContext,TeacherActivity::class.java)
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
                    val success = dbHandler?.deleteTeacher(intent.getIntExtra("Id", 0)) as kotlin.Boolean
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
