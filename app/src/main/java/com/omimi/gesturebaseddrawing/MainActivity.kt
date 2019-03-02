package com.omimi.gesturebaseddrawing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ShapeDrawnCallback {
    private var currDrawingShapeType: DrawingShapeType = DrawingShapeType.DrawingLine
    private var currShapeColor: ShapeColor = ShapeColor.Blue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dsv_user_draw_input.setShapeDrawnCallback(this)
        dsv_user_draw_input.changeDrawingShapeType(currDrawingShapeType)

        setSupportActionBar(toolbar)

        btn_line.backgroundTintList = ContextCompat.getColorStateList(this, R.color.selectedShapeType)
        btn_blue.backgroundTintList = ContextCompat.getColorStateList(this, R.color.selectedShapeType)

        btn_triangle.setOnClickListener { view ->
            drawingShapeTypeChanged(DrawingShapeType.DrawingTriangle)
        }

        btn_square.setOnClickListener { view ->
            drawingShapeTypeChanged(DrawingShapeType.DrawingSquare)
        }

        btn_line.setOnClickListener { view ->
            drawingShapeTypeChanged(DrawingShapeType.DrawingLine)
        }

        btn_circle.setOnClickListener { view ->
            drawingShapeTypeChanged(DrawingShapeType.DrawingCircle)
        }

        btn_blue.setOnClickListener { view ->
            shapeColorChanged(ShapeColor.Blue)
        }

        btn_black.setOnClickListener { view ->
            shapeColorChanged(ShapeColor.Black)
        }

        btn_orange.setOnClickListener { view ->
            shapeColorChanged(ShapeColor.Orange)
        }

        btn_green.setOnClickListener { view ->
            shapeColorChanged(ShapeColor.Green)
        }
    }

    private fun drawingShapeTypeChanged(shapeType: DrawingShapeType) {
        if(shapeType == currDrawingShapeType) {
            return
        }

        resetSelectedShapeTypeBtn()
        currDrawingShapeType = shapeType

        when(shapeType) {
            DrawingShapeType.DrawingLine -> btn_line.backgroundTintList = ContextCompat.getColorStateList(this, R.color.selectedShapeType)

            DrawingShapeType.DrawingCircle -> btn_circle.backgroundTintList = ContextCompat.getColorStateList(this, R.color.selectedShapeType)

            DrawingShapeType.DrawingSquare -> btn_square.backgroundTintList = ContextCompat.getColorStateList(this, R.color.selectedShapeType)

            DrawingShapeType.DrawingTriangle -> btn_triangle.backgroundTintList = ContextCompat.getColorStateList(this, R.color.selectedShapeType)
        }

        dsv_user_draw_input.changeDrawingShapeType(currDrawingShapeType)
    }

    private fun shapeColorChanged(shapeColor: ShapeColor) {
        if(shapeColor == currShapeColor) {
            return
        }

        resetSelectedShapeColorBtn()
        currShapeColor = shapeColor

        when(currShapeColor) {
            ShapeColor.Blue -> btn_blue.backgroundTintList = ContextCompat.getColorStateList(this, R.color.selectedShapeType)

            ShapeColor.Black -> btn_black.backgroundTintList = ContextCompat.getColorStateList(this, R.color.selectedShapeType)

            ShapeColor.Green -> btn_green.backgroundTintList = ContextCompat.getColorStateList(this, R.color.selectedShapeType)

            ShapeColor.Orange -> btn_orange.backgroundTintList = ContextCompat.getColorStateList(this, R.color.selectedShapeType)
        }

        //todo add function call here for interpretation view to change its color using currShapeColor
    }

    private fun resetSelectedShapeTypeBtn() {
        when(currDrawingShapeType) {
            DrawingShapeType.DrawingLine -> btn_line.backgroundTintList = ContextCompat.getColorStateList(this, R.color.defaultShapeType)

            DrawingShapeType.DrawingCircle -> btn_circle.backgroundTintList = ContextCompat.getColorStateList(this, R.color.defaultShapeType)

            DrawingShapeType.DrawingSquare -> btn_square.backgroundTintList = ContextCompat.getColorStateList(this, R.color.defaultShapeType)

            DrawingShapeType.DrawingTriangle -> btn_triangle.backgroundTintList = ContextCompat.getColorStateList(this, R.color.defaultShapeType)
        }
    }

    private fun resetSelectedShapeColorBtn() {
        when(currShapeColor) {
            ShapeColor.Blue -> btn_blue.backgroundTintList = ContextCompat.getColorStateList(this, R.color.defaultShapeType)

            ShapeColor.Black -> btn_black.backgroundTintList = ContextCompat.getColorStateList(this, R.color.defaultShapeType)

            ShapeColor.Green -> btn_green.backgroundTintList = ContextCompat.getColorStateList(this, R.color.defaultShapeType)

            ShapeColor.Orange -> btn_orange.backgroundTintList = ContextCompat.getColorStateList(this, R.color.defaultShapeType)
        }
    }

    override fun shapeDrawn(shape: Shape) {
        //todo just pass this shape into the other view once created
    }
}
