package com.example.kotlinminesweeper

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_minesweeper.*


class MinesweeperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minesweeper)

        val txtWelcome: TextView = findViewById(R.id.txt_welcome)
        txtWelcome.setText("Welcome to the level!")

        switch_button.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
//                text_view.text = "Flag Mode On"
                Toast.makeText(
                    this,
                    "Flag Mode On",
                    Toast.LENGTH_LONG
                ).show()

            } else {
                Toast.makeText(
                    this,
                    "Flag Mode Off",
                    Toast.LENGTH_LONG
                ).show()
//                text_view.text = ""
            }
        }


        // Set a click listener for root layout object
//        minesweeper_layout.setOnClickListener {
//            if (switch_button.isChecked) {
//                // If switch button is checked/on then
//                // The switch is enabled/checked
//                text_view.text = "Flag Mode On"
//            } else {
//                // The switch is unchecked
//                text_view.text = "Flag Mode Off"
//            }
//        }
//
//
//        button.setOnClickListener {
//            // Change the switch button checked state on button click
//            switch_button.isChecked = if (switch_button.isChecked) false else true
//        }
    }

}


//        if (intent.extras.containsKey(MainActivity.KEY_NAME) and intent.extras.containsKey(MainActivity.KEY_GRADE)) {
//            val name = intent.getStringExtra(MainActivity.KEY_NAME)
//            val grade = intent.getStringExtra(MainActivity.KEY_GRADE)
//
////            tvData.text = getString(R.string.text_grade, name, grade)
//
//        }
//
//        backBtn.setOnClickListener {
//            finish()
//        }
//

//        btnOk.setOnClickListener{
//            var intentGradeResult = Intent()
//            intentGradeResult.putExtra(MainActivity.KEY_COMMENT, etComment.text.toString())
//
//            setResult(Activity.RESULT_OK, intentGradeResult)
//            finish()
//
//        }


//    override fun onBackPressed() {
//        Toast.makeText(this, "You can never exit", Toast.LENGTH_LONG).show()
//    }


