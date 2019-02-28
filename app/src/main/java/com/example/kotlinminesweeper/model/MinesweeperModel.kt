package com.example.kotlinminesweeper.model

import com.example.kotlinminesweeper.Field

object MinesweeperModel {

    val fieldMatrix: Array<Array<Field>> = arrayOf(
        arrayOf(
            Field(0, 5, false, false, false),
            Field(1, 5, false, false, false),
            Field(2, 5, false, false, false),
            Field(3, 5, false, false, false),
            Field(4, 6, false, false, false)
        ),
        arrayOf(
            Field(5, 7, false, false, false),
            Field(6, 7, false, false, false),
            Field(7, 7, false, false, false),
            Field(8, 7, false, false, false),
            Field(9, 8, false, false, false)
        ),
        arrayOf(
            Field(10, 5, false, false, false),
            Field(11, 5, false, false, false),
            Field(12, 5, false, false, false),
            Field(13, 5, false, false, false),
            Field(14, 6, false, false, false)
        ),
        arrayOf(
            Field(15, 5, false, false, false),
            Field(16, 5, false, false, false),
            Field(17, 5, false, false, false),
            Field(18, 5, false, false, false),
            Field(19, 6, false, false, false)
        ),
        arrayOf(
            Field(20, 5, false, false, false),
            Field(21, 5, false, false, false),
            Field(22, 5, false, false, false),
            Field(23, 5, false, false, false),
            Field(24, 6, false, false, false)
        )
    )

    fun plantMines(fieldMatrix: Array<Array<Field>>) {

    }

}

//    public fun resetModel() {
//        for (i in 0..4) {
//            for (j in 0..4) {
//                fieldMatrix[i][j] = Field(type = EMPTY.toInt())
//            }
//        }
//    }


