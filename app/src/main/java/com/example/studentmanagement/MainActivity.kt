package com.example.studentmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.studentmanagement.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.carview1.setOnClickListener {
            val intent =  Intent(this,studentActivity::class.java)
            startActivity(intent)
        }

        binding.carview2.setOnClickListener {
            val intent =  Intent(this,TeacherActivity::class.java)
            startActivity(intent)
        }

    }
}