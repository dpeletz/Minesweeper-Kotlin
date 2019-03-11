package com.example.kotlinminesweeper.data

data class Field(
    var type: Int,
    var minesAround: Int,
    var isMine: Boolean,
    var isFlagged: Boolean,
    var wasClicked: Boolean,
    var drawMine: Boolean
)