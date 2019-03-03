package com.omimi.gesturebaseddrawing

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

class ShapeInterpretationView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val bitmapPaint = Paint(Paint.DITHER_FLAG)
    private lateinit var canvasBitmap: Bitmap
    private lateinit var drawingCanvas: Canvas
    private var shapes = mutableListOf<Shape>()
    private var shapePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var gestureListener = DoubleClickGestureListener()
    private var gestureDetector = GestureDetector(context, gestureListener)
    private lateinit var shapeDeletedCallback: ShapeDeletedCallback

    init {
        shapePaint.color = Color.BLUE
        shapePaint.style = Paint.Style.FILL
        shapePaint.strokeWidth = 8f
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return true
    }

    fun addShape(shape: Shape) {
        shapes.add(shape)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(canvasBitmap, 0f, 0f, bitmapPaint)

        shapes.forEach { shape ->
            when(shape) {
                is Shape.Line -> drawLine(shape)
                is Shape.Square -> drawSquare(shape)
                is Shape.Triangle -> drawTriangle(shape)
                is Shape.Circle -> drawCircle(shape)
            }
        }
    }

    private fun drawLine(line: Shape.Line) {
        shapePaint.color = line.color

        drawingCanvas.drawLine(line.startX, line.startY, line.endX, line.endY, shapePaint)
    }

    private fun drawSquare(square: Shape.Square) {
        shapePaint.color = square.color

        drawingCanvas.drawRect(square.left, square.top, square.right, square.bottom, shapePaint)
    }

    private fun drawTriangle(triangle: Shape.Triangle) {
        shapePaint.color = triangle.color

        var trianglePath = Path()
        trianglePath.fillType = Path.FillType.EVEN_ODD

        trianglePath.moveTo(triangle.minX, triangle.minY)
        trianglePath.lineTo(triangle.maxX, triangle.minY)
        trianglePath.lineTo(triangle.topPointX, triangle.topPointY)
        trianglePath.close()

        drawingCanvas.drawPath(trianglePath, shapePaint)
    }

    private fun drawCircle(circle: Shape.Circle) {
        shapePaint.color = circle.color

        var centerX = circle.getXCenter()
        var centerY = circle.getYCenter()
        var largestRadius = circle.getRadius()

        drawingCanvas.drawCircle(centerX, centerY, largestRadius, shapePaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        drawingCanvas = Canvas(canvasBitmap)
    }

    private fun deleteGestureAt(x: Float, y:Float) {
        for(i in shapes.size-1 downTo 0) {
            if(shapes[i].pointInShape(x, y)) {
                shapeDeletedCallback.shapeDeletedAtIndex(i)
                shapes.removeAt(i)
                break
            }
        }

        canvasBitmap.eraseColor(Color.TRANSPARENT)
        invalidate()
    }

    fun setShapeDeletedCallback(shapeDeletedCallback: ShapeDeletedCallback) {
        this.shapeDeletedCallback = shapeDeletedCallback
    }

    inner class DoubleClickGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(event: MotionEvent): Boolean {
            Log.d("TEST", "onDoubleTap??")
            deleteGestureAt(event.x, event.y)

            return super.onDoubleTap(event)
        }
    }
}