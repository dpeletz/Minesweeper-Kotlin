package com.example.kotlinminesweeper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    val fieldMatrix: Array<Array<Field>> = arrayOf(
        arrayOf(
            Field(1, 5, true, true),
            Field(2, 6, false, false)
        ),
        arrayOf(
            Field(3, 7, true, true),
            Field(4, 8, false, false)
        )
    )
}
