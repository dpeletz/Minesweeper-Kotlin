package com.example.kotlinminesweeper.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.example.kotlinminesweeper.R
import com.example.kotlinminesweeper.data.Field
import com.example.kotlinminesweeper.model.MinesweeperModel

class MinesweeperView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paintBackground = Paint()
    private val paintLine = Paint()
    private val paintText = Paint()

    private var bitmapBg = BitmapFactory.decodeResource(
        resources,
        R.drawable.gradient
    )

    init {
        paintBackground.color = Color.BLACK
        paintBackground.style = Paint.Style.FILL

        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 8f

        paintText.color = Color.RED
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        paintText.textSize = height / (MinesweeperModel.numRowsAndColumns.toFloat())

        bitmapBg = Bitmap.createScaledBitmap(
            bitmapBg, width, height, false
        )
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)

        canvas?.drawBitmap(bitmapBg, 0f, 0f, null)

        drawGameBoard(canvas)

        displayNumberMines(canvas)

    }


    private fun displayNumberMines(canvas: Canvas?) {

        for (i in 0..MinesweeperModel.numRowsAndColumns - 1) {
            for (j in 0..MinesweeperModel.numRowsAndColumns - 1) {
                if (!MinesweeperModel.fieldMatrix[i][j].isMine && MinesweeperModel.fieldMatrix[i][j].wasClicked) {
                    canvas?.drawText(
                        "${MinesweeperModel.fieldMatrix[i][j].minesAround}",
                        ((width / MinesweeperModel.numRowsAndColumns.toFloat()) * (i)),
                        ((height / MinesweeperModel.numRowsAndColumns.toFloat()) * (j + 1)),
                        paintText
                    )
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val tX = event.x.toInt() / (width / MinesweeperModel.numRowsAndColumns)
            val tY = event.y.toInt() / (height / MinesweeperModel.numRowsAndColumns)

            if (tX < MinesweeperModel.numRowsAndColumns && tY < MinesweeperModel.numRowsAndColumns && !MinesweeperModel.fieldMatrix[tX][tY].wasClicked && !MinesweeperModel.fieldMatrix[tX][tY].isMine) {

                MinesweeperModel.fieldMatrix[tX][tY].wasClicked = !MinesweeperModel.fieldMatrix[tX][tY].wasClicked

                invalidate()

            } else if (MinesweeperModel.fieldMatrix[tX][tY].isMine) {
                Toast.makeText(
                    context,
                    "You chose a mine... game over",
                    Toast.LENGTH_LONG
                ).show()
                resetModel()
//                invalidate()

            }
            invalidate()
        }
        return true
    }

    private fun resetModel() {
        val fieldMatrix =
            Array<Array<Field>>(MinesweeperModel.numRowsAndColumns) {
                Array<Field>(MinesweeperModel.numRowsAndColumns) {
                    Field(
                        0,
                        0,
                        false,
                        false,
                        false
                    )
                }
            }

        MinesweeperModel.fieldMatrix =
            MinesweeperModel.plantMines(fieldMatrix, MinesweeperModel.maxMines, MinesweeperModel.numRowsAndColumns)

    }

    private fun drawGameBoard(canvas: Canvas?) {
        val heightOverCols = height / MinesweeperModel.numRowsAndColumns
        val widthOverRows = width / MinesweeperModel.numRowsAndColumns

        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)

        for (i in 0..MinesweeperModel.numRowsAndColumns) {
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


//            canvas?.drawLine(0f, (3 * heightOverCols).toFloat(), width.toFloat(), (3 * heightOverCols).toFloat(), paintLine)
//            canvas?.drawLine(0f, (4 * heightOverCols).toFloat(), width.toFloat(), (4 * heightOverCols).toFloat(), paintLine)
        }

//        canvas?.drawLine(widthOverRows.toFloat(), 0f, widthOverRows.toFloat(), height.toFloat(), paintLine)
//        canvas?.drawLine((2 * widthOverRows).toFloat(), 0f, (2 * widthOverRows).toFloat(), height.toFloat(), paintLine)
//        canvas?.drawLine((3 * widthOverRows).toFloat(), 0f, (3 * widthOverRows).toFloat(), height.toFloat(), paintLine)
//        canvas?.drawLine((4 * widthOverRows).toFloat(), 0f, (4 * widthOverRows).toFloat(), height.toFloat(), paintLine)
//
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = View.MeasureSpec.getSize(widthMeasureSpec)
        val h = View.MeasureSpec.getSize(heightMeasureSpec)
        val d = if (w == 0) h else if (h == 0) w else if (w < h) w else h

        setMeasuredDimension(d, d)
    }


}



