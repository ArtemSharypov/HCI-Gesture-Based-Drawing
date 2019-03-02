package com.omimi.gesturebaseddrawing

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DrawShapesView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var currDrawingShapeType: DrawingShapeType = DrawingShapeType.DrawingLine
    private var currShape: Shape = Shape.Line()
    private lateinit var shapeDrawnCallback: ShapeDrawnCallback
    private val pathPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmapPaint = Paint(Paint.DITHER_FLAG)
    private lateinit var canvasBitmap: Bitmap
    private lateinit var drawingCanvas: Canvas
    private var shapesDrawn = mutableListOf<DrawnShape>()
    private var currShapePaths = mutableListOf<Path>()
    private var currPath = Path()

    init {
        pathPaint.color = Color.BLACK
        pathPaint.style = Paint.Style.STROKE
        pathPaint.strokeWidth = 8f
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(canvasBitmap, 0f, 0f, bitmapPaint)

        //For each shape input, draw the paths that the user made to create the shape
        shapesDrawn.forEach { drawnShape ->
            drawnShape.paths.forEach { path ->
                drawingCanvas.drawPath(path, pathPaint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var touchX = event.x
        var touchY = event.y

        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStart(touchX, touchY)
                invalidate()
            }

            MotionEvent.ACTION_MOVE -> {
                touchMove(touchX, touchY)
                invalidate()
            }

            MotionEvent.ACTION_UP -> {
                touchUp(touchX, touchY)
                invalidate()
            }
        }

        return true
    }

    private fun touchStart(touchX: Float, touchY: Float) {
        currShapePaths = mutableListOf()
        currPath = Path()

        currShapePaths.add(currPath)

        currPath.moveTo(touchX, touchY)

        shapesDrawn.add(DrawnShape(currShapePaths))

        //Create a new shape being drawn based off of the drawing shape type
        when(currDrawingShapeType) {
            DrawingShapeType.DrawingLine -> currShape = Shape.Line(touchX, touchY)
            DrawingShapeType.DrawingTriangle -> currShape = Shape.Triangle(touchX, touchY, touchX, touchX, touchY)
            DrawingShapeType.DrawingSquare -> currShape = Shape.Square(touchX, touchY, touchX, touchY)
            DrawingShapeType.DrawingCircle -> currShape = Shape.Circle(touchX, touchY, touchX, touchY)
        }
    }

    private fun touchMove(touchX: Float, touchY: Float) {
        currPath.lineTo(touchX, touchY)

        updateShapeFromMovement(touchX, touchY)
    }

    private fun touchUp(touchX: Float, touchY: Float) {
        currPath.lineTo(touchX, touchY)

        var shape = currShape

        if(shape is Shape.Line) {
            shape.endX = touchX
            shape.endY = touchY
        } else {
            updateShapeFromMovement(touchX, touchY)
        }

        shapeDrawn()
    }

    //updates the defining points of a shape if either newX or newY would change one of them
    private fun updateShapeFromMovement(newX: Float, newY: Float) {
        var shape = currShape

        when(shape) {
            is Shape.Square -> {
                updateSquareFromMovement(newX, newY, shape)
            }

            is Shape.Circle -> {
                updateCircleFromMovement(newX, newY, shape)
            }

            is Shape.Triangle -> {
                updateTriangleFromMovement(newX, newY, shape)
            }
        }
    }

    private fun updateSquareFromMovement(newX: Float, newY: Float, square: Shape.Square) {
        if(newX < square.left) {
            square.left = newX
        }

        if(newX > square.right) {
            square.right = newX
        }

        if(newY > square.bottom) {
            square.bottom = newY
        }

        if(newY < square.top) {
            square.top = newY
        }
    }

    private fun updateTriangleFromMovement(newX: Float, newY: Float, triangle: Shape.Triangle) {
        if(newX < triangle.minX) {
            triangle.minX = newX
        }

        if(newX > triangle.maxX) {
            triangle.maxX = newX
        }

        if(newY > triangle.minY) {
            triangle.minY = newY
        }

        if(newY < triangle.topPointY) {
            triangle.topPointY = newY
            triangle.topPointX = newX
        }
    }

    private fun updateCircleFromMovement(newX: Float, newY: Float, circle: Shape.Circle) {
        if(newX < circle.left) {
            circle.left = newX
        }

        if(newX > circle.right) {
            circle.right = newX
        }

        if(newY > circle.bottom) {
            circle.bottom = newY
        }

        if(newY < circle.top) {
            circle.top = newY
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        drawingCanvas = Canvas(canvasBitmap)
    }

    fun changeDrawingShapeType(drawingShapeType: DrawingShapeType) {
        currDrawingShapeType = drawingShapeType
    }

    private fun shapeDrawn() {
        shapeDrawnCallback.shapeDrawn(currShape)
    }

    fun setShapeDrawnCallback(shapeDrawnCallback: ShapeDrawnCallback) {
        this.shapeDrawnCallback = shapeDrawnCallback
    }
}