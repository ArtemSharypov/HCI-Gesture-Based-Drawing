package com.omimi.gesturebaseddrawing

import android.graphics.Color

sealed class Shape {
    class Line(var startX: Float = -1f, var startY: Float = -1f, var endX: Float = -1f, var endY: Float = -1f, var color: Int = Color.BLUE) : Shape()
    class Square(var left: Float = -1f, var top: Float = -1f, var right: Float = -1f, var bottom: Float = -1f, var color: Int = Color.BLUE) : Shape()
    class Circle(var left: Float = -1f, var top: Float = -1f, var right: Float = -1f, var bottom: Float = -1f, var color: Int = Color.BLUE) : Shape()
    class Triangle(var minX: Float = -1f, var minY: Float = -1f, var maxX: Float = -1f, var topPointX: Float = -1f, var topPointY: Float = -1f, var color: Int = Color.BLUE) : Shape()
}