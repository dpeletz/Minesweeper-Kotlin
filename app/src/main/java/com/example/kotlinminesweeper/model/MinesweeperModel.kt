package com.example.kotlinminesweeper.model

import com.example.kotlinminesweeper.data.Field
import kotlin.random.Random

object MinesweeperModel {

    var numRowsAndColumns: Int = 5
    var maxMines: Int = 3
    var flagMode = false
    var numUnclickedFields = (7 * 7)

    var fieldMatrix = plantMines(Array(numRowsAndColumns) {
        Array(numRowsAndColumns) {
            Field(0, 0, isMine = false, isFlagged = false, wasClicked = false, drawMine = false)
        }
    }, maxMines, numRowsAndColumns)

    fun resetFieldMatrix() {
        fieldMatrix = plantMines(Array(numRowsAndColumns) {
            Array(numRowsAndColumns) {
                Field(0, 0, isMine = false, isFlagged = false, wasClicked = false, drawMine = false)
            }
        }, maxMines, numRowsAndColumns)
    }

    fun setNumberMinesAndRows(mines: Int, rows: Int) {
        MinesweeperModel.maxMines = mines
        MinesweeperModel.numRowsAndColumns = rows
        MinesweeperModel.numUnclickedFields = rows * rows
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
        } else {
            incrementMinesAround(fieldMatrix, numRowsAndColumns)
        }
    }

    private fun incrementMineCountByRow(
        randRow: Int,
        randCol: Int,
        numRowsAndColumns: Int,
        fieldMatrix: Array<Array<Field>>
    ) {
        if (isValid(randRow, randCol - 1, numRowsAndColumns)) incrementFieldMineCount(fieldMatrix, randRow, randCol - 1)
        if (isValid(randRow, randCol + 1, numRowsAndColumns)) incrementFieldMineCount(fieldMatrix, randRow, randCol + 1)
    }

    private fun incrementFieldMineCount(
        fieldMatrix: Array<Array<Field>>,
        randRow: Int,
        randCol: Int
    ) {
        fieldMatrix[randRow][randCol].minesAround =
            fieldMatrix[randRow][randCol].minesAround + 1
    }

    private fun incrementMineCountByColumn(
        randRow: Int,
        randCol: Int,
        numRowsAndColumns: Int,
        fieldMatrix: Array<Array<Field>>
    ) {
        if (isValid(randRow - 1, randCol, numRowsAndColumns)) incrementFieldMineCount(fieldMatrix, randRow - 1, randCol)
        if (isValid(randRow + 1, randCol, numRowsAndColumns)) incrementFieldMineCount(fieldMatrix, randRow + 1, randCol)
    }

    fun plantMines(fieldMatrix: Array<Array<Field>>, maxMines: Int, numRowsAndColumns: Int): Array<Array<Field>> {
        for (i in 0..(maxMines - 1)) incrementMinesAround(fieldMatrix, numRowsAndColumns)
        return fieldMatrix
    }

    private fun isValid(row: Int, col: Int, numRowsAndColumns: Int): Boolean {
        return ((row >= 0) && (row < numRowsAndColumns) &&
                (col >= 0) && (col < numRowsAndColumns))
    }

    fun toggleFlagMode() {
        flagMode = !flagMode
    }
}