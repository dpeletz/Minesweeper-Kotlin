package com.example.kotlinminesweeper.view

import android.content.Context
import android.graphics.*
import android.graphics.Color.RED
import android.support.design.widget.Snackbar
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.kotlinminesweeper.R
import com.example.kotlinminesweeper.data.Field
import com.example.kotlinminesweeper.model.MinesweeperModel
import kotlinx.android.synthetic.main.activity_minesweeper.view.*
import java.util.*
import kotlin.concurrent.schedule

class MinesweeperView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paintBackground = Paint()
    private val paintLine = Paint()
    private val paintText = Paint()

    private var flag = BitmapFactory.decodeResource(
        resources,
        R.drawable.flag
    )

    private var mine = BitmapFactory.decodeResource(
        resources,
        R.drawable.mine
    )

    init {
        paintBackground.color = resources.getColor(R.color.colorMinesweeper)
        paintBackground.style = Paint.Style.FILL

        paintLine.color = resources.getColor(R.color.colorSecondaryDark)
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 8f

        paintText.color = resources.getColor(R.color.colorSecondaryDark)
        resetModel()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        paintText.textSize = height / (MinesweeperModel.numRowsAndColumns.toFloat())
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)
        drawGameBoard(canvas)
        displayNumberMines(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = View.MeasureSpec.getSize(widthMeasureSpec)
        val h = View.MeasureSpec.getSize(heightMeasureSpec)
        val d = if (w == 0) h else if (h == 0) w else if (w < h) w else h
        setMeasuredDimension(d, d)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN && !MinesweeperModel.flagMode) {
            clickField(
                (event.x.toInt() / (width / MinesweeperModel.numRowsAndColumns)),
                (event.y.toInt() / (height / MinesweeperModel.numRowsAndColumns))
            )
        } else if (event?.action == MotionEvent.ACTION_DOWN && MinesweeperModel.flagMode) {
            markFieldAsFlagged(
                (event.x.toInt() / (width / MinesweeperModel.numRowsAndColumns)),
                (event.y.toInt() / (height / MinesweeperModel.numRowsAndColumns))
            )
        }
        return true
    }

    private fun drawGrid(canvas: Canvas?, i: Int, heightOverCols: Int, widthOverRows: Int) {
        canvas?.drawLine(
            0f,
            (i * heightOverCols).toFloat(),
            width.toFloat(),
            (i * heightOverCols).toFloat(),
            paintLine
        )
        canvas?.drawLine(
            (i * widthOverRows).toFloat(),
            0f,
            (i * widthOverRows).toFloat(),
            height.toFloat(),
            paintLine
        )
    }

    private fun drawLines(canvas: Canvas?) {
        val heightOverCols = height / MinesweeperModel.numRowsAndColumns
        val widthOverRows = width / MinesweeperModel.numRowsAndColumns
        for (i in 0..MinesweeperModel.numRowsAndColumns) drawGrid(canvas, i, heightOverCols, widthOverRows)
    }

    private fun drawGameBoard(canvas: Canvas?) {
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)
        drawLines(canvas)
    }

    private fun generateSnackbar(message: String) {
        Snackbar.make(
            minesweeper_view,
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun resetModel() {
        val fieldMatrix = MinesweeperModel.plantMines(
            Array(MinesweeperModel.numRowsAndColumns) {
                Array(MinesweeperModel.numRowsAndColumns) {
                    Field(
                        0, 0, isMine = false, isFlagged = false, wasClicked = false, drawMine = false
                    )
                }
            }, MinesweeperModel.maxMines, MinesweeperModel.numRowsAndColumns
        )
        MinesweeperModel.fieldMatrix = fieldMatrix
        MinesweeperModel.numUnclickedFields = MinesweeperModel.numRowsAndColumns * MinesweeperModel.numRowsAndColumns
    }

    private fun drawFlag(canvas: Canvas?, i: Int, j: Int) {
        flag = Bitmap.createScaledBitmap(
            flag, width / MinesweeperModel.numRowsAndColumns, height / MinesweeperModel.numRowsAndColumns, false
        )
        canvas?.drawBitmap(
            flag,
            ((width / MinesweeperModel.numRowsAndColumns.toFloat()) * (i)),
            (((height / MinesweeperModel.numRowsAndColumns.toFloat()) * (j))),
            null
        )
    }

    private fun drawNumberSurroundingMines(canvas: Canvas?, i: Int, j: Int) {
        canvas?.drawText(
            "${MinesweeperModel.fieldMatrix[i][j].minesAround}",
            ((width / MinesweeperModel.numRowsAndColumns.toFloat() * i) + (paintText.textSize / MinesweeperModel.numRowsAndColumns)),
            ((height / MinesweeperModel.numRowsAndColumns.toFloat() * (j + 1)) - paintLine.textSize),
            paintText
        )
    }

    private fun drawFields(canvas: Canvas?, i: Int, j: Int) {
        if (!MinesweeperModel.fieldMatrix[i][j].isMine && MinesweeperModel.fieldMatrix[i][j].wasClicked) {
            drawNumberSurroundingMines(canvas, i, j)
        } else if (MinesweeperModel.fieldMatrix[i][j].drawMine) {
            drawMine(canvas, i, j)
        } else if (MinesweeperModel.fieldMatrix[i][j].isFlagged) {
            drawFlag(canvas, i, j)
        } else if (MinesweeperModel.fieldMatrix[i][j].isMine && MinesweeperModel.fieldMatrix[i][j].wasClicked) {
            finishGameWithLoss(canvas)
        }

        if ((MinesweeperModel.numUnclickedFields == MinesweeperModel.maxMines) && (!MinesweeperModel.fieldMatrix[i][j].isMine) && (MinesweeperModel.fieldMatrix[i][j].wasClicked)) {
            finishGameWithWin(canvas)
        }
    }

    private fun finishGameWithWin(canvas: Canvas?) {
        replaceFlagWithMineOnWin()
        generateSnackbar(resources.getString(R.string.game_won))
        delayGameEnding(6000, canvas)
    }

    private fun delayGameEnding(delay: Long, canvas: Canvas?) {
        showMines(canvas)
        Timer().schedule(delay) {
            resetModel()
            invalidate()
        }
    }

    private fun finishGameWithLoss(canvas: Canvas?) {
        if (MinesweeperModel.numUnclickedFields != MinesweeperModel.maxMines) loseGame(canvas)
    }

    private fun loseGame(canvas: Canvas?) {
        replaceFlagWithMineOnWin()
        generateSnackbar(resources.getString(R.string.game_over))
        delayGameEnding(4000, canvas)
    }

    private fun clickField(tX: Int, tY: Int) {
        if (!MinesweeperModel.flagMode) clickWhenNotInFlagMode(tX, tY)
    }

    private fun clickWhenNotInFlagMode(tX: Int, tY: Int) {
        if (tX < MinesweeperModel.numRowsAndColumns && tY < MinesweeperModel.numRowsAndColumns && !MinesweeperModel.fieldMatrix[tX][tY].wasClicked && !MinesweeperModel.fieldMatrix[tX][tY].isFlagged) {
            MinesweeperModel.fieldMatrix[tX][tY].wasClicked = !MinesweeperModel.fieldMatrix[tX][tY].wasClicked
            MinesweeperModel.numUnclickedFields -= 1
            invalidate()
        }
        invalidate()
    }

    private fun generateMine(i: Int, j: Int, canvas: Canvas?) {
        if (MinesweeperModel.fieldMatrix[i][j].isMine) {
            if (!MinesweeperModel.fieldMatrix[i][j].isFlagged) {
                drawMine(canvas, i, j)
            } else {
                MinesweeperModel.fieldMatrix[i][j].drawMine = true
            }
        }
    }

    private fun displayNumberMines(canvas: Canvas?) {
        showMinesOrDisplayMineCount(0, canvas)
    }

    private fun showMines(canvas: Canvas?) {
        showMinesOrDisplayMineCount(1, canvas)
    }

    private fun showMinesOrDisplayMineCount(function: Int, canvas: Canvas?) {
        for (i in 0..MinesweeperModel.numRowsAndColumns - 1) {
            for (j in 0..MinesweeperModel.numRowsAndColumns - 1) {
                if (function == 0) drawFields(canvas, i, j)
                if (function == 1) generateMine(i, j, canvas)
            }
        }
    }

    private fun drawMineOverFlag(i: Int, j: Int) {
        if (MinesweeperModel.fieldMatrix[i][j].isMine) {
            if (MinesweeperModel.fieldMatrix[i][j].isFlagged) {
                MinesweeperModel.fieldMatrix[i][j].drawMine = true
            }
        }
    }

    private fun replaceFlagWithMineOnWin() {
        for (i in 0..MinesweeperModel.numRowsAndColumns - 1) {
            for (j in 0..MinesweeperModel.numRowsAndColumns - 1) {
                drawMineOverFlag(i, j)
            }
        }
    }

    private fun drawMine(canvas: Canvas?, i: Int, j: Int) {
        mine = Bitmap.createScaledBitmap(
            mine, width / MinesweeperModel.numRowsAndColumns, height / MinesweeperModel.numRowsAndColumns, false
        )
        canvas?.drawBitmap(
            mine,
            ((width / MinesweeperModel.numRowsAndColumns.toFloat()) * (i)),
            (((height / MinesweeperModel.numRowsAndColumns.toFloat()) * (j))),
            null
        )
    }

    private fun markFieldAsFlagged(tX: Int, tY: Int) {
        if (MinesweeperModel.flagMode) flagField(tX, tY)
    }

    private fun flagField(tX: Int, tY: Int) {
        if (tX < MinesweeperModel.numRowsAndColumns && tY < MinesweeperModel.numRowsAndColumns && !MinesweeperModel.fieldMatrix[tX][tY].wasClicked) {
            MinesweeperModel.fieldMatrix[tX][tY].isFlagged = !MinesweeperModel.fieldMatrix[tX][tY].isFlagged
            invalidate()
        }
        invalidate()
    }
}