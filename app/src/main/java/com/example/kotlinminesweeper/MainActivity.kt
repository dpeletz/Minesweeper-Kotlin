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

        startEasyGame()
        startMediumGame()
        startHardGame()
    }

    private fun startEasyGame() {
        val btnEasyActivity: Button = findViewById(R.id.btn_easy)
        val easyIntent = Intent(this, MinesweeperActivity::class.java)
        btnEasyActivity.setOnClickListener {
            MinesweeperModel.numRowsAndColumns = 5
            MinesweeperModel.maxMines = 3
            MinesweeperModel.resetFieldMatrix()
            startActivity(easyIntent)
        }
    }

    private fun startMediumGame() {
        val btnMediumActivity: Button = findViewById(R.id.btn_medium)
        val mediumIntent = Intent(this, MinesweeperActivity::class.java)
        btnMediumActivity.setOnClickListener {
            MinesweeperModel.numRowsAndColumns = 6
            MinesweeperModel.maxMines = 6
            MinesweeperModel.resetFieldMatrix()
            startActivity(mediumIntent)
        }
    }

    private fun startHardGame() {
        val btnHardActivity: Button = findViewById(R.id.btn_hard)
        val hardIntent = Intent(this, MinesweeperActivity::class.java)
        btnHardActivity.setOnClickListener {
            MinesweeperModel.numRowsAndColumns = 7
            MinesweeperModel.maxMines = 9
            MinesweeperModel.resetFieldMatrix()
            startActivity(hardIntent)
        }
    }


}
