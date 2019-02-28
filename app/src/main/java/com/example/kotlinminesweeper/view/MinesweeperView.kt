package com.example.kotlinminesweeper.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.kotlinminesweeper.R
import com.example.kotlinminesweeper.model.MinesweeperModel

class MinesweeperView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paintBackground = Paint()
    private val paintLine = Paint()
    private val crossPaint = Paint()
    private val circlePaint = Paint()
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

        crossPaint.color = Color.RED
        crossPaint.style = Paint.Style.STROKE
        crossPaint.strokeWidth = 8f

        circlePaint.color = Color.BLUE
        circlePaint.style = Paint.Style.STROKE
        circlePaint.strokeWidth = 8f

        paintText.color = Color.RED
        paintText.textSize = 50f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        paintText.textSize = height / 5f

        bitmapBg = Bitmap.createScaledBitmap(
            bitmapBg, width, height, false
        )
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)

        canvas?.drawBitmap(bitmapBg, 0f, 0f, null)

        canvas?.drawText("4", 0f, height / 5f, paintText)

        drawGameBoard(canvas)

//        drawPlayers(canvas)
    }

    private fun drawGameBoard(canvas: Canvas?) {
        val fifthOfHeight = height / 5
        val fifthOfWidth = width / 5

        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)

        canvas?.drawLine(0f, fifthOfHeight.toFloat(), width.toFloat(), fifthOfHeight.toFloat(), paintLine)
        canvas?.drawLine(0f, (2 * fifthOfHeight).toFloat(), width.toFloat(), (2 * fifthOfHeight).toFloat(), paintLine)
        canvas?.drawLine(0f, (3 * fifthOfHeight).toFloat(), width.toFloat(), (3 * fifthOfHeight).toFloat(), paintLine)
        canvas?.drawLine(0f, (4 * fifthOfHeight).toFloat(), width.toFloat(), (4 * fifthOfHeight).toFloat(), paintLine)

        canvas?.drawLine(fifthOfWidth.toFloat(), 0f, fifthOfWidth.toFloat(), height.toFloat(), paintLine)
        canvas?.drawLine((2 * fifthOfWidth).toFloat(), 0f, (2 * fifthOfWidth).toFloat(), height.toFloat(), paintLine)
        canvas?.drawLine((3 * fifthOfWidth).toFloat(), 0f, (3 * fifthOfWidth).toFloat(), height.toFloat(), paintLine)
        canvas?.drawLine((4 * fifthOfWidth).toFloat(), 0f, (4 * fifthOfWidth).toFloat(), height.toFloat(), paintLine)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = View.MeasureSpec.getSize(widthMeasureSpec)
        val h = View.MeasureSpec.getSize(heightMeasureSpec)
        val d = if (w == 0) h else if (h == 0) w else if (w < h) w else h

        setMeasuredDimension(d, d)
    }


}

//    private fun drawPlayers(canvas: Canvas?) {
//        for (i in 0..2) {
//            for (j in 0..2) {
//                if (TicTacToeModel.getFieldContent(i, j) == TicTacToeModel.CIRCLE) {
//                    val centerX = (i * width / 3 + width / 6).toFloat()
//                    val centerY = (j * height / 3 + height / 6).toFloat()
//                    val radius = height / 6 - 2
//
//                    canvas?.drawCircle(centerX, centerY, radius.toFloat(), circlePaint)
//                } else if (TicTacToeModel.getFieldContent(i, j) == TicTacToeModel.CROSS) {
//                    canvas?.drawLine(
//                        (i * width / 3).toFloat(), (j * height / 3).toFloat(),
//                        ((i + 1) * width / 3).toFloat(),
//                        ((j + 1) * height / 3).toFloat(), crossPaint
//                    )
//
//                    canvas?.drawLine(
//                        ((i + 1) * width / 3).toFloat(), (j * height / 3).toFloat(),
//                        (i * width / 3).toFloat(), ((j + 1) * height / 3).toFloat(), crossPaint
//                    )
//                }
//            }
//        }
//    }
//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        if (event?.action == MotionEvent.ACTION_DOWN) {
//            val tX = event.x.toInt() / (width / 3)
//            val tY = event.y.toInt() / (height / 3)
//
//            if (tX < 3 && tY < 3 && MinesweeperModel.getFieldContent(tX, tY) ==
//                MinesweeperModel.EMPTY
//            ) {
//                MinesweeperModel.setFieldContent(tX, tY, MinesweeperModel.nextPlayer)
//                MinesweeperModel.checkResult(context)
//
//                MinesweeperModel.switchPlayer()
//                invalidate()
//
//                var status = "Next player is X"
//                if (MinesweeperModel.nextPlayer == MinesweeperModel.CIRCLE) {
//                    status = "Next player is O"
//                }
//                (context as MainActivity).setStatusText(status)
//            }
//
//            invalidate()
//        }
//        return true
//    }

//    public fun resetGame() {
//        MinesweeperModel.resetModel()
//        invalidate()
//    }



