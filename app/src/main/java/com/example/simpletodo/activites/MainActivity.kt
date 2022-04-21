package com.example.simpletodo.activites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.simpletodo.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_todo)
    }
}