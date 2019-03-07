package com.example.kotlinminesweeper.view

import android.content.Context
import android.graphics.*
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
        paintBackground.color = resources.getColor(R.color.colorSecondaryLight)
        paintBackground.style = Paint.Style.FILL

        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 8f

        paintText.color = resources.getColor(R.color.colorPrimaryDark)
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

    private fun drawFields(canvas: Canvas?, i: Int, j: Int) {
        if (!MinesweeperModel.fieldMatrix[i][j].isMine && MinesweeperModel.fieldMatrix[i][j].wasClicked) {
            drawNumberSurroundingMines(canvas, i, j)
        } else if (MinesweeperModel.fieldMatrix[i][j].isFlagged) {
            drawFlag(canvas, i, j)
        } else if (MinesweeperModel.fieldMatrix[i][j].isMine && MinesweeperModel.fieldMatrix[i][j].wasClicked) {
            finishGame(canvas)
        }
    }

    private fun finishGame(canvas: Canvas?) {
        Snackbar.make(
            minesweeperView,
            resources.getString(R.string.game_over),
            Snackbar.LENGTH_LONG
        ).show()
        showMines(canvas)
        Timer().schedule(2000) {
            resetModel()
            invalidate()
        }
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
            (((width / MinesweeperModel.numRowsAndColumns.toFloat()) * (i))),
            ((height / MinesweeperModel.numRowsAndColumns.toFloat()) * (j + 1)) - paintLine.textSize,
            paintText
        )
    }

    private fun displayNumberMines(canvas: Canvas?) {
        for (i in 0 .. MinesweeperModel.numRowsAndColumns - 1) {
            for (j in 0 .. MinesweeperModel.numRowsAndColumns - 1) {
                drawFields(canvas, i, j)
            }
        }
    }

    private fun markFieldAsClicked(tX: Int, tY: Int) {
        if (!MinesweeperModel.flagMode) {
            if (tX < MinesweeperModel.numRowsAndColumns && tY < MinesweeperModel.numRowsAndColumns && !MinesweeperModel.fieldMatrix[tX][tY].wasClicked && !MinesweeperModel.fieldMatrix[tX][tY].isFlagged) {
                MinesweeperModel.fieldMatrix[tX][tY].wasClicked = !MinesweeperModel.fieldMatrix[tX][tY].wasClicked
                invalidate()
            }
            invalidate()
        }
    }

    private fun resetFlag() {
        flag.eraseColor(Color.TRANSPARENT)
    }

    private fun showMines(canvas: Canvas?) {
        mine = Bitmap.createScaledBitmap(
            mine, width / MinesweeperModel.numRowsAndColumns, height / MinesweeperModel.numRowsAndColumns, false
        )
        for (i in 0 .. MinesweeperModel.numRowsAndColumns - 1) {
            for (j in 0 .. MinesweeperModel.numRowsAndColumns - 1) {
                if (MinesweeperModel.fieldMatrix[i][j].isMine) {
                    if (!MinesweeperModel.fieldMatrix[i][j].isFlagged) {
//                        resetFlag()
                        drawMine(canvas, i, j)
                    } else {
                        resetFlag()
                        drawMine(canvas, i, j)
                    }
                }
            }
        }
    }

    private fun drawMine(canvas: Canvas?, i: Int, j: Int) {
        canvas?.drawBitmap(
            mine,
            ((width / MinesweeperModel.numRowsAndColumns.toFloat()) * (i)),
            (((height / MinesweeperModel.numRowsAndColumns.toFloat()) * (j))),
            null
        )
    }

    private fun markFieldAsFlagged(tX: Int, tY: Int) {
        if (MinesweeperModel.flagMode) {
            if (tX < MinesweeperModel.numRowsAndColumns && tY < MinesweeperModel.numRowsAndColumns && !MinesweeperModel.fieldMatrix[tX][tY].wasClicked) {
//            if (tX < MinesweeperModel.numRowsAndColumns && tY < MinesweeperModel.numRowsAndColumns && !MinesweeperModel.fieldMatrix[tX][tY].wasClicked && !MinesweeperModel.fieldMatrix[tX][tY].isMine) {
                MinesweeperModel.fieldMatrix[tX][tY].isFlagged = !MinesweeperModel.fieldMatrix[tX][tY].isFlagged
                invalidate()
            }
            invalidate()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN && !MinesweeperModel.flagMode) {
            markFieldAsClicked(
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

    private fun resetModel() {
        val fieldMatrix =
            Array(MinesweeperModel.numRowsAndColumns) {
                Array(MinesweeperModel.numRowsAndColumns) {
                    Field(
                        0,
                        0,
                        isMine = false,
                        isFlagged = false,
                        wasClicked = false
                    )
                }
            }

        MinesweeperModel.fieldMatrix =
            MinesweeperModel.plantMines(fieldMatrix, MinesweeperModel.maxMines, MinesweeperModel.numRowsAndColumns)

        flag = BitmapFactory.decodeResource(
            resources,
            R.drawable.flag
        )
    }


    private fun drawLines(canvas: Canvas?) {
        val heightOverCols = height / MinesweeperModel.numRowsAndColumns
        val widthOverRows = width / MinesweeperModel.numRowsAndColumns

        for (i in 0..MinesweeperModel.numRowsAndColumns) {
            drawGrid(canvas, i, heightOverCols, widthOverRows)
        }
    }

    private fun drawGrid(
        canvas: Canvas?,
        i: Int,
        heightOverCols: Int,
        widthOverRows: Int
    ) {
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

    private fun drawGameBoard(canvas: Canvas?) {
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)
        drawLines(canvas)
    }
}



