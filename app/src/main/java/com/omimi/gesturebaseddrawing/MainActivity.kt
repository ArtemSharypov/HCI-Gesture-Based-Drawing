package com.omimi.gesturebaseddrawing

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ShapeDrawnCallback {
    private var currDrawingShapeType: DrawingShapeType = DrawingShapeType.DrawingLine
    private var currShapeColor: ShapeColor = ShapeColor.Blue
    private var paletteVisible = true
    private lateinit var drawingMenuItem: MenuItem
    private lateinit var interpretationMenuItem: MenuItem
    private lateinit var hidePaletteMenuItem: MenuItem
    private lateinit var showPaletteMenuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dsv_user_draw_input.setShapeDrawnCallback(this)
        dsv_user_draw_input.changeDrawingShapeType(currDrawingShapeType)

        setSupportActionBar(toolbar)

        btn_line.backgroundTintList = ContextCompat.getColorStateList(this, R.color.selectedShapeType)
        btn_blue.backgroundTintList = ContextCompat.getColorStateList(this, R.color.selectedShapeType)

        btn_triangle.setOnClickListener { drawingShapeTypeChanged(DrawingShapeType.DrawingTriangle) }

        btn_square.setOnClickListener { drawingShapeTypeChanged(DrawingShapeType.DrawingSquare) }

        btn_line.setOnClickListener { drawingShapeTypeChanged(DrawingShapeType.DrawingLine) }

        btn_circle.setOnClickListener { drawingShapeTypeChanged(DrawingShapeType.DrawingCircle) }

        btn_blue.setOnClickListener { shapeColorChanged(ShapeColor.Blue) }

        btn_black.setOnClickListener { shapeColorChanged(ShapeColor.Black) }

        btn_red.setOnClickListener { shapeColorChanged(ShapeColor.Red) }

        btn_green.setOnClickListener { shapeColorChanged(ShapeColor.Green) }
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

            ShapeColor.Red -> btn_red.backgroundTintList = ContextCompat.getColorStateList(this, R.color.selectedShapeType)
        }
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

            ShapeColor.Red -> btn_red.backgroundTintList = ContextCompat.getColorStateList(this, R.color.defaultShapeType)
        }
    }

    override fun shapeDrawn(shape: Shape) {
        var color = when(currShapeColor) {
            ShapeColor.Blue -> ShapeColor.Blue.color

            ShapeColor.Black -> ShapeColor.Black.color

            ShapeColor.Green -> ShapeColor.Green.color

            ShapeColor.Red -> ShapeColor.Red.color
        }

        when(shape) {
            is Shape.Line -> shape.color = color

            is Shape.Circle -> shape.color = color

            is Shape.Square -> shape.color = color

            is Shape.Triangle -> shape.color = color
        }

        shape_interpretation_view.addShape(shape)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)

        drawingMenuItem = menu.findItem(R.id.drawing)
        interpretationMenuItem = menu.findItem(R.id.interpretation)
        hidePaletteMenuItem = menu.findItem(R.id.hide_palette)
        showPaletteMenuItem = menu.findItem(R.id.show_palette)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.drawing -> {
                shape_interpretation_view.visibility = View.GONE
                cl_user_draw_input.visibility = View.VISIBLE

                drawingMenuItem.isVisible = false
                interpretationMenuItem.isVisible = true

                if(paletteVisible) {
                    hidePaletteMenuItem.isVisible = true
                } else {
                    showPaletteMenuItem.isVisible = true
                }

                true
            }

            R.id.interpretation -> {
                shape_interpretation_view.visibility = View.VISIBLE
                cl_user_draw_input.visibility = View.GONE

                drawingMenuItem.isVisible = true
                interpretationMenuItem.isVisible = false
                showPaletteMenuItem.isVisible = false
                hidePaletteMenuItem.isVisible = false

                true
            }

            R.id.hide_palette -> {
                cl_palette.visibility = View.GONE

                paletteVisible = false

                showPaletteMenuItem.isVisible = true
                hidePaletteMenuItem.isVisible = false

                true
            }

            R.id.show_palette -> {
                cl_palette.visibility = View.VISIBLE

                paletteVisible = true

                showPaletteMenuItem.isVisible = false
                hidePaletteMenuItem.isVisible = true

                true
            }

            else -> {
                 super.onOptionsItemSelected(item)
            }
        }
    }
}
