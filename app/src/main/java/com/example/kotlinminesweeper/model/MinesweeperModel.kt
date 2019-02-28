package com.example.kotlinminesweeper.model

import com.example.kotlinminesweeper.Field

object MinesweeperModel {

    val EMPTY: Short = 0

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

//    public fun resetModel() {
//        for (i in 0..4) {
//            for (j in 0..4) {
//                fieldMatrix[i][j] = Field(type = EMPTY.toInt())
//            }
//        }
//    }


}