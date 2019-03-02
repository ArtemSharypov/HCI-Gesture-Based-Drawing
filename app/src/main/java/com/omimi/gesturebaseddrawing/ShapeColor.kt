package com.omimi.gesturebaseddrawing

import android.graphics.Color

sealed class ShapeColor {
    object Black: ShapeColor() {
        const val color = Color.BLACK
    }

    object Red: ShapeColor() {
        const val color = Color.RED
    }

    object Green: ShapeColor() {
        const val color = Color.GREEN
    }

    object Blue: ShapeColor() {
        const val color = Color.BLUE
    }
}