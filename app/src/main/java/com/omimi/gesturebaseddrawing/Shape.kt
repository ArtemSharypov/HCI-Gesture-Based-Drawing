package com.omimi.gesturebaseddrawing

import android.graphics.Color

sealed class Shape {
    abstract fun pointInShape(x: Float, y: Float): Boolean

    class Line(var startX: Float = -1f, var startY: Float = -1f, var endX: Float = -1f, var endY: Float = -1f, var color: Int = Color.BLUE) : Shape() {
        private val slopeBufferAmount = 1f
        private val xyBufferAmount = 5f

        override fun pointInShape(x: Float, y: Float): Boolean {
            var minX: Float
            var minY: Float
            var maxX: Float
            var maxY: Float

            if(startX > endX) {
                minX = endX
                maxX = startX
            } else {
                minX = startX
                maxX = endX
            }

            if(startY > endY) {
                minY = endY
                maxY = startY
            } else {
                minY = startY
                maxY = endY
            }

            //more for horizontal / sloped lines
            var originalLineYSlope = (startY - endY) / (startX - endX)
            var touchLineYSlope = (y - endY) / (x - endX)

            //aimed for vertical lines
            var originalLineXSlope = (startX - endX) / (startY - endY)
            var touchLineXSlope = (x - endX) / (y - endY)

            return (x in minX-xyBufferAmount..maxX+xyBufferAmount && y in minY-xyBufferAmount..maxY+xyBufferAmount) &&
                    (touchLineYSlope in originalLineYSlope-slopeBufferAmount .. originalLineYSlope+slopeBufferAmount
                            || touchLineXSlope in originalLineXSlope-slopeBufferAmount .. originalLineXSlope+slopeBufferAmount)
        }
    }

    class Square(var left: Float = -1f, var top: Float = -1f, var right: Float = -1f, var bottom: Float = -1f, var color: Int = Color.BLUE) : Shape() {
        override fun pointInShape(x: Float, y: Float): Boolean {
            return x in left..right && y in top..bottom
        }
    }

    class Circle(var left: Float = -1f, var top: Float = -1f, var right: Float = -1f, var bottom: Float = -1f, var color: Int = Color.BLUE) : Shape() {
        override fun pointInShape(x: Float, y: Float): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    class Triangle(var minX: Float = -1f, var minY: Float = -1f, var maxX: Float = -1f, var topPointX: Float = -1f, var topPointY: Float = -1f, var color: Int = Color.BLUE) : Shape() {
        override fun pointInShape(x: Float, y: Float): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}