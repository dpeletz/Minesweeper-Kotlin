package com.example.kotlinminesweeper.model

import com.example.kotlinminesweeper.data.Field
import kotlin.random.Random

object MinesweeperModel {

    var numRowsAndColumns = 5
    var maxMines = 3
    var flagMode = false
    var numberUnclickedFields = (numRowsAndColumns * numRowsAndColumns)

    var fieldMatrix = plantMines(Array(numRowsAndColumns) {
        Array(numRowsAndColumns) {
            Field(
                0,
                0,
                isMine = false,
                isFlagged = false,
                wasClicked = false,
                drawMine = false
            )
        }
    }, maxMines, numRowsAndColumns)

    fun resetFieldMatrix() {
        fieldMatrix = plantMines(Array(numRowsAndColumns) {
            Array(numRowsAndColumns) {
                Field(0, 0, isMine = false, isFlagged = false, wasClicked = false, drawMine = false)
            }
        }, maxMines, numRowsAndColumns)
    }

    private fun incrementMinesAround(fieldMatrix: Array<Array<Field>>, numRowsAndColumns: Int) {
        val random = Random.nextInt(numRowsAndColumns * numRowsAndColumns)
        countMinesAround(fieldMatrix, random / numRowsAndColumns, random.rem(numRowsAndColumns), numRowsAndColumns)
    }

    private fun countMinesAround(fieldMatrix: Array<Array<Field>>, randRow: Int, randCol: Int, numRowsAndColumns: Int) {
        if (!fieldMatrix[randRow][randCol].isMine) {
            fieldMatrix[randRow][randCol].isMine = !fieldMatrix[randRow][randCol].isMine
            incrementMineCountByColumn(randRow, randCol, numRowsAndColumns, fieldMatrix)
            incrementMineCountByColumn(randRow, randCol + 1, numRowsAndColumns, fieldMatrix)
            incrementMineCountByRow(randRow, randCol, numRowsAndColumns, fieldMatrix)
            incrementMineCountByColumn(randRow, randCol - 1, numRowsAndColumns, fieldMatrix)
        }
    }

    private fun incrementMineCountByRow(
        randRow: Int,
        randCol: Int,
        numRowsAndColumns: Int,
        fieldMatrix: Array<Array<Field>>
    ) {
        if (isValid(randRow, randCol - 1, numRowsAndColumns)) {
            fieldMatrix[randRow][randCol - 1].minesAround =
                fieldMatrix[randRow][randCol - 1].minesAround + 1
        }
        if (isValid(randRow, randCol + 1, numRowsAndColumns)) {
            fieldMatrix[randRow][randCol + 1].minesAround =
                fieldMatrix[randRow][randCol + 1].minesAround + 1
        }
    }

    private fun incrementMineCountByColumn(
        randRow: Int,
        randCol: Int,
        numRowsAndColumns: Int,
        fieldMatrix: Array<Array<Field>>
    ) {
        if (isValid(randRow - 1, randCol, numRowsAndColumns)) {
            fieldMatrix[randRow - 1][randCol].minesAround =
                fieldMatrix[randRow - 1][randCol].minesAround + 1
        }
        if (isValid(randRow + 1, randCol, numRowsAndColumns)) {
            fieldMatrix[randRow + 1][randCol].minesAround =
                fieldMatrix[randRow + 1][randCol].minesAround + 1
        }
    }

    fun plantMines(fieldMatrix: Array<Array<Field>>, maxMines: Int, numRowsAndColumns: Int): Array<Array<Field>> {
        for (i in 0..(maxMines - 1)) {
            incrementMinesAround(fieldMatrix, numRowsAndColumns)
        }
        return fieldMatrix
    }

    private fun isValid(row: Int, col: Int, numRowsAndColumns: Int): Boolean {
        return (row >= 0) && (row < numRowsAndColumns) &&
                (col >= 0) && (col < numRowsAndColumns)
    }

    fun toggleFlagMode() {
        flagMode = !flagMode
    }

}