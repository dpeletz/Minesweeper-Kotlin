package com.example.kotlinminesweeper

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

class MinesweeperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minesweeper)

        val txtWelcome : TextView = findViewById(R.id.txt_welcome)
        txtWelcome.setText("Welcome to the level!")

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


