package com.example.kotlinminesweeper

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import com.example.kotlinminesweeper.model.MinesweeperModel
import com.example.kotlinminesweeper.view.MinesweeperView
import kotlinx.android.synthetic.main.activity_minesweeper.*


class MinesweeperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minesweeper)

        setUpFlagToggle()
    }

    private fun setUpFlagToggle() {
        switch_button.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                toggleOn()

            } else {
                toggleOff()
            }
        }
    }

    private fun toggleOff() {
        Snackbar.make(
            minesweeper_layout,
            resources.getString(R.string.flag_mode_off),
            Snackbar.LENGTH_LONG
        ).show()
        MinesweeperModel.toggleFlagMode()
    }

    private fun toggleOn() {
        Snackbar.make(
            minesweeper_layout,
            resources.getString(R.string.flag_mode_on),
            Snackbar.LENGTH_LONG
        ).show()
        MinesweeperModel.toggleFlagMode()
    }
}

