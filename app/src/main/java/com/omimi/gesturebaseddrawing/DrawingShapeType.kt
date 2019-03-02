package com.omimi.gesturebaseddrawing

sealed class DrawingShapeType {
    object DrawingLine : DrawingShapeType()
    object DrawingTriangle : DrawingShapeType()
    object DrawingCircle : DrawingShapeType()
    object DrawingSquare : DrawingShapeType()
}