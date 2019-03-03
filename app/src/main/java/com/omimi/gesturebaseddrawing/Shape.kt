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
        fun getRadius(): Float {
            var xRadius = Math.abs(left - right) / 2
            var yRadius = Math.abs(top - bottom) / 2
            return Math.max(xRadius, yRadius)
        }

        fun getXCenter(): Float {
            return Math.abs(left + right) / 2
        }

        fun getYCenter(): Float {
            return Math.abs(top + bottom) / 2
        }

        override fun pointInShape(x: Float, y: Float): Boolean {
            var radius = getRadius()
            var xCenter = getXCenter()
            var yCenter = getYCenter()

            var xDifference = x - xCenter
            var yDifference= y - yCenter

            return xDifference*xDifference + yDifference * yDifference < radius * radius
        }
    }

    class Triangle(var minX: Float = -1f, var minY: Float = -1f, var maxX: Float = -1f, var topPointX: Float = -1f, var topPointY: Float = -1f, var color: Int = Color.BLUE) : Shape() {
        private val triangleAreaBuffer = 2f

        override fun pointInShape(x: Float, y: Float): Boolean {
            var a = area(minX, minY, maxX, minY, topPointX, topPointY)
            var a1 = area(minX, minY, maxX, minY, x, y)
            var a2 = area(minX, minY, x, y, topPointX, topPointY)
            var a3 = area(x, y, maxX, minY, topPointX, topPointY)

            var calculatedArea = a1 + a2 + a3

            return a in calculatedArea-triangleAreaBuffer .. calculatedArea+triangleAreaBuffer
        }

        private fun area(x1: Float, y1: Float, x2: Float, y2: Float, x3: Float, y3: Float): Float {
            return Math.abs((x1*(y2-y3) + x2*(y3-y1)+ x3*(y1-y2))/2.0).toFloat()
        }
    }
}