package com.omimi.gesturebaseddrawing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ShapeDrawnCallback {
    private var currDrawingShapeType = DrawingShapeType.DrawingSquare

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dsv_user_draw_input.setShapeDrawnCallback(this)
        dsv_user_draw_input.changeDrawingShapeType(currDrawingShapeType)
    }

    override fun shapeDrawn(shape: Shape) {
        //todo just pass this shape into the other view once created
    }
}
