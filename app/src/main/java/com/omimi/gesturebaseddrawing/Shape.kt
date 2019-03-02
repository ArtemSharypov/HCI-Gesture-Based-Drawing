package com.omimi.gesturebaseddrawing

sealed class Shape {
    class Line(var startX: Float = -1f, var startY: Float = -1f, var endX: Float = -1f, var endY: Float = -1f) : Shape()
    class Square(var minX: Float = -1f, var maxY: Float = -1f, var maxX: Float = -1f, var minY: Float = -1f) : Shape()
    class Circle(var minX: Float = -1f, var maxY: Float = -1f, var maxX: Float = -1f, var minY: Float = -1f) : Shape()
    class Triangle(var minX: Float = -1f, var minY: Float = -1f, var maxX: Float = -1f, var topPointX: Float = -1f, var topPointY: Float = -1f) : Shape()
}