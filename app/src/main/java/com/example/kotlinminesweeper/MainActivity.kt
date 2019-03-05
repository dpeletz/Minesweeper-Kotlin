package com.example.kotlinminesweeper

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.kotlinminesweeper.model.MinesweeperModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnEasyActivity: Button = findViewById(R.id.btn_easy)

        btnEasyActivity.setOnClickListener {
            MinesweeperModel.numRowsAndColumns = 5
            MinesweeperModel.maxMines = 3
            MinesweeperModel.resetFieldMatrix()
            val easyIntent = Intent(this, MinesweeperActivity::class.java)
            startActivity(easyIntent)
        }

        val btnMediumActivity: Button = findViewById(R.id.btn_medium)

        btnMediumActivity.setOnClickListener {
            MinesweeperModel.numRowsAndColumns = 6
            MinesweeperModel.maxMines = 5
            MinesweeperModel.resetFieldMatrix()
            val mediumIntent = Intent(this, MinesweeperActivity::class.java)
            startActivity(mediumIntent)

        }

        val btnHardActivity: Button = findViewById(R.id.btn_hard)

        btnHardActivity.setOnClickListener {
            MinesweeperModel.numRowsAndColumns = 7
            MinesweeperModel.maxMines = 7
            MinesweeperModel.resetFieldMatrix()

            val hardIntent = Intent(this, MinesweeperActivity::class.java)
            startActivity(hardIntent)
        }
    }
}
