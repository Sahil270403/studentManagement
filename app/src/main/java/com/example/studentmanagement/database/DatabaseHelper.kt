package com.example.studentmanagement.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.studentmanagement.model.StudentList
import com.example.studentmanagement.model.TeacherList

class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION){

    companion object{

        private const val DB_NAME = "student_management_system"
        private const val DB_VERSION = 1

        private const val TABLE_STUDENT = "student"
        private const val STUDENT_ID = "_id"
        private const val STUDENT_NAME: String = "name"
        private const val STUDENT_EMAIL = "email"
        private const val STUDENT_DOB = "dob"
        private const val STUDENT_PHONE = "phone"

        private const val TABLE_TEACHER = "teacher"
        private const val TEACHER_ID = "_id"
        private const val TEACHER_NAME = "name"
        private const val TEACHER_SUBJECT = "subject"
        private const val TEACHER_DESIGNATION = "designation"
        private const val TEACHER_COURSE_ID = "course_id"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_STUDENT ($STUDENT_ID INTEGER PRIMARY KEY, $STUDENT_NAME TEXT, $STUDENT_EMAIL TEXT,$STUDENT_DOB TEXT,$STUDENT_PHONE TEXT)"
        db?.execSQL(CREATE_TABLE)
        val createTeacherTable = "CREATE TABLE $TABLE_TEACHER ($TEACHER_ID INTEGER PRIMARY KEY, $TEACHER_NAME TEXT, $TEACHER_SUBJECT TEXT,$TEACHER_DESIGNATION TEXT,$TEACHER_COURSE_ID TEXT)"
        db?.execSQL(createTeacherTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_STUDENT"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    @SuppressLint("Range")
    fun getAllStudent(): List<StudentList> {
        val studentList = ArrayList<StudentList>()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_STUDENT"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val task = StudentList()
                task.id = cursor.getInt(cursor.getColumnIndex(STUDENT_ID))
                task.name = cursor.getString(cursor.getColumnIndex(STUDENT_NAME))
                task.dob = cursor.getString(cursor.getColumnIndex(STUDENT_DOB))
                task.email = cursor.getString(cursor.getColumnIndex(STUDENT_EMAIL))
                task.phone = cursor.getLong(cursor.getColumnIndex(STUDENT_PHONE))
                studentList.add(task) // Add the task to the list
            } while (cursor.moveToNext())
            cursor.close()
        }
        return studentList
    }

    @SuppressLint("Range")
    fun getAllTeacher(): List<TeacherList> {
        val teacherList = ArrayList<TeacherList>()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_TEACHER"
        val cursor = db?.rawQuery(selectQuery,null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val task = TeacherList()
                task.id = cursor.getInt(cursor.getColumnIndex(TEACHER_ID))
                task.name = cursor.getString(cursor.getColumnIndex(TEACHER_NAME))
                task.subject = cursor.getString(cursor.getColumnIndex(TEACHER_SUBJECT))
                task.desig = cursor.getString(cursor.getColumnIndex(TEACHER_DESIGNATION))
                task.courseid = cursor.getString(cursor.getColumnIndex(TEACHER_COURSE_ID))
                teacherList.add(task) // Add the task to the list
            } while (cursor.moveToNext())
            cursor.close()
        }
        return teacherList
    }


    //insert

    fun addStudent(task:StudentList):Boolean{

        val db = this.writableDatabase
        val values = ContentValues()
        values.put(STUDENT_ID,task.id)
        values.put(STUDENT_NAME,task.name)
        values.put(STUDENT_PHONE,task.phone)
        values.put(STUDENT_DOB,task.dob)
        values.put(STUDENT_EMAIL,task.email)

        val success = db.insert(TABLE_STUDENT,null,values)
        db.close()
        return Integer.parseInt("${success}") != -1
    }

    fun addTeacher(task:TeacherList):Boolean{

        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TEACHER_ID,task.id)
        values.put(TEACHER_NAME,task.name)
        values.put(TEACHER_SUBJECT,task.subject)
        values.put(TEACHER_DESIGNATION,task.desig)
        values.put(TEACHER_COURSE_ID,task.courseid)

        val success = db.insert(TABLE_TEACHER,null,values)
        db.close()
        return Integer.parseInt("${success}") != -1
    }




    // select the data of particular id

    @SuppressLint("Range")
    fun getTask(id:Int):StudentList{
        val task = StudentList()
        val db = writableDatabase

        val selectQuery = "SELECT * FROM $TABLE_STUDENT WHERE $STUDENT_ID = $id"
        val cursor = db.rawQuery(selectQuery,null)

        cursor?.moveToFirst()
        task.id = cursor.getInt(cursor.getColumnIndex(STUDENT_ID))
        task.name = cursor.getString(cursor.getColumnIndex(STUDENT_NAME))
        task.dob = cursor.getString(cursor.getColumnIndex(STUDENT_DOB))
        task.email = cursor.getString(cursor.getColumnIndex(STUDENT_EMAIL))
        task.phone = cursor.getLong(cursor.getColumnIndex(STUDENT_PHONE))

        cursor.close()
        return task
    }

    @SuppressLint("Range")
    fun getTaskT(id: Int): TeacherList? {
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_TEACHER WHERE $TEACHER_ID = $id"
        val cursor = db.rawQuery(selectQuery, null)
        var taskT: TeacherList? = null
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(TEACHER_ID))
            val name = cursor.getString(cursor.getColumnIndex(TEACHER_NAME))
            val subject = cursor.getString(cursor.getColumnIndex(TEACHER_SUBJECT))
            val designation = cursor.getString(cursor.getColumnIndex(TEACHER_DESIGNATION))
            val courseId = cursor.getString(cursor.getColumnIndex(TEACHER_COURSE_ID))
            taskT = TeacherList(id, name, subject, designation, courseId)
        }
        cursor.close()
        db.close()
        return taskT
    }



    fun deleteStudent(id:Int):Boolean{

        val db = this.writableDatabase
        val _success = db.delete(TABLE_STUDENT, STUDENT_ID + "=?" , arrayOf(id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun deleteTeacher(id:Int):Boolean{

        val db = this.writableDatabase
        val _success = db.delete(TABLE_TEACHER, TEACHER_ID + "=?" , arrayOf(id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }


    fun updateStudent(task: StudentList):Boolean{

        val db = this.writableDatabase
        val values = ContentValues()
        values.put(STUDENT_ID,task.id)
        values.put(STUDENT_NAME,task.name)
        values.put(STUDENT_PHONE,task.phone)
        values.put(STUDENT_DOB,task.dob)
        values.put(STUDENT_EMAIL,task.email)
        val _success = db.update(TABLE_STUDENT, values, STUDENT_ID + "=?" , arrayOf(task.id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun updateTeacher(task: TeacherList):Boolean{

        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TEACHER_ID,task.id)
        values.put(TEACHER_NAME,task.name)
        values.put(TEACHER_SUBJECT,task.subject)
        values.put(TEACHER_DESIGNATION,task.desig)
        values.put(TEACHER_COURSE_ID,task.courseid)
        val _success = db.update(TABLE_TEACHER, values, TEACHER_ID + "=?" , arrayOf(task.id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }
}