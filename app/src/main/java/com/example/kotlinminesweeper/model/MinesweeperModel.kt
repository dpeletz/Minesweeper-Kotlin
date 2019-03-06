package com.example.kotlinminesweeper.model

import com.example.kotlinminesweeper.data.Field
import kotlin.random.Random

object MinesweeperModel {

    var numRowsAndColumns = 5
    var maxMines = 3
    var flagMode = false

    var fieldMatrix = plantMines(Array<Array<Field>>(numRowsAndColumns) {
        Array<Field>(numRowsAndColumns) {
            Field(
                0,
                0,
                false,
                false,
                false
            )
        }
    }, maxMines, numRowsAndColumns)

    fun resetFieldMatrix() {
        fieldMatrix = plantMines(Array<Array<Field>>(numRowsAndColumns) {
            Array<Field>(numRowsAndColumns) {
                Field(
                    0,
                    0,
                    false,
                    false,
                    false
                )
            }
        }, maxMines, numRowsAndColumns)
    }


    private fun incrementMinesAround(
        fieldMatrix: Array<Array<Field>>,
        numRowsAndColumns: Int
    ) {

        val random = Random.nextInt(numRowsAndColumns * numRowsAndColumns)
        val randRow = random / numRowsAndColumns
        val randCol = random.rem(numRowsAndColumns)

        if (!fieldMatrix[randRow][randCol].isMine) {
            fieldMatrix[randRow][randCol].isMine = !fieldMatrix[randRow][randCol].isMine

            if (isValid(randRow - 1, randCol, numRowsAndColumns)) {
                fieldMatrix[randRow - 1][randCol].minesAround =
                    fieldMatrix[randRow - 1][randCol].minesAround + 1
            }
            if (isValid(randRow + 1, randCol, numRowsAndColumns)) {
                fieldMatrix[randRow + 1][randCol].minesAround =
                    fieldMatrix[randRow + 1][randCol].minesAround + 1
            }

            if (isValid(randRow, randCol + 1, numRowsAndColumns)) {
                fieldMatrix[randRow][randCol + 1].minesAround =
                    fieldMatrix[randRow][randCol + 1].minesAround + 1
            }
            if (isValid(randRow, randCol - 1, numRowsAndColumns)) {
                fieldMatrix[randRow][randCol - 1].minesAround =
                    fieldMatrix[randRow][randCol - 1].minesAround + 1
            }
            if (isValid(randRow + 1, randCol - 1, numRowsAndColumns)) {
                fieldMatrix[randRow + 1][randCol - 1].minesAround =
                    fieldMatrix[randRow + 1][randCol - 1].minesAround + 1
            }
            if (isValid(randRow - 1, randCol + 1, numRowsAndColumns)) {
                fieldMatrix[randRow - 1][randCol + 1].minesAround =
                    fieldMatrix[randRow - 1][randCol + 1].minesAround + 1
            }
            if (isValid(randRow - 1, randCol - 1, numRowsAndColumns)) {
                fieldMatrix[randRow - 1][randCol - 1].minesAround =
                    fieldMatrix[randRow - 1][randCol - 1].minesAround + 1
            }
            if (isValid(randRow + 1, randCol + 1, numRowsAndColumns)) {
                fieldMatrix[randRow + 1][randCol + 1].minesAround =
                    fieldMatrix[randRow + 1][randCol + 1].minesAround + 1
            }
        }
    }

    fun plantMines(fieldMatrix: Array<Array<Field>>, maxMines: Int, numRowsAndColumns: Int): Array<Array<Field>> {
        for (i in 0..(maxMines - 1)) {
            incrementMinesAround(fieldMatrix, numRowsAndColumns)
        }
        return fieldMatrix
    }

    fun isValid(row: Int, col: Int, numRowsAndColumns: Int): Boolean {
        return (row >= 0) && (row < numRowsAndColumns) &&
                (col >= 0) && (col < numRowsAndColumns)
    }

    fun toggleFlagMode() {
        flagMode = !flagMode
    }


}


//        while (totalCurrentMines < maxMines) {
//            var randRow = Random.nextInt(numRowsAndColumns)
//            var randCol = Random.nextInt(numRowsAndColumns)
//
//                }

//                if (randRow < numRowsAndColumns - 1 && randRow > 0) {
//                    fieldMatrix[randRow - 1][randCol].minesAround = fieldMatrix[randRow - 1][randCol].minesAround + 1
//                    fieldMatrix[randRow + 1][randCol].minesAround = fieldMatrix[randRow + 1][randCol].minesAround + 1
//
//                } else if (randRow <= numRowsAndColumns) {
//                    fieldMatrix[randRow - 1][randCol].minesAround = fieldMatrix[randRow - 1][randCol].minesAround + 1
//
//                } else if (randRow >= 0) {
//                    fieldMatrix[randRow + 1][randCol].minesAround = fieldMatrix[randRow + 1][randCol].minesAround + 1
//                }
//
//                if (randCol < numRowsAndColumns - 1 && randCol > 0) {
//                    fieldMatrix[randRow][randCol - 1].minesAround = fieldMatrix[randRow][randCol - 1].minesAround + 1
//                    fieldMatrix[randRow][randCol + 1].minesAround = fieldMatrix[randRow][randCol + 1].minesAround + 1
//
//                } else if (randCol <= numRowsAndColumns) {
//                    fieldMatrix[randRow][randCol - 1].minesAround = fieldMatrix[randRow][randCol - 1].minesAround + 1
//
//                } else if (randCol >= 0) {
//                    fieldMatrix[randRow][randCol + 1].minesAround = fieldMatrix[randRow][randCol + 1].minesAround + 1
//                }

//                totalCurrentMines += 1

//    public fun getNumberMinesAround(x: Int, y: Int, numRowsAndColumns: Int): Int {
//        if (x < numRowsAndColumns - 1)
//            fieldMatrix[x][y].getNumberMinesAround(fieldMatrix[x][y])
//        return 3
//    }


//    public fun getFieldContent(x: Int, y: Int) = fieldMatrix[x][y].minesAround


//    public fun resetModel() {
//        for (i in 0..4) {
//            for (j in 0..4) {
//                fieldMatrix[i][j] = Field(type = EMPTY.toInt())
//            }
//        }
//    }


