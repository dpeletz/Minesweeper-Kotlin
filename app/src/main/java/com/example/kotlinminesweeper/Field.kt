package com.example.kotlinminesweeper

data class Field(var type: Int, var minesAround: Int, var isMine: Boolean,
                 var isFlagged: Boolean, var wasClicked: Boolean) {

    fun getNumberMinesAround(field: Field): Int {
        return 0
    }

    fun flag(field: Field) {
        field.isFlagged = !field.isFlagged
    }

    fun click(field: Field) {
        field.wasClicked = true
    }

    fun setAsMine(field: Field) {
        field.isMine = true
    }




}
