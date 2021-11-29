package ru.karim.ui.painting

import java.awt.Color
import java.lang.Math.log

fun yellowFractal(x: Double): Color {
    if (x == 1.0) return Color.BLACK
    return Color(
        Math.abs(Math.sin(10 * x-3)).toFloat(),
        Math.abs(Math.cos(x+5)).toFloat(),
        Math.abs(Math.sin(x+1000-7)).toFloat()
    )
}
fun purpleFractal(x: Double): Color {
    if (x == 1.0) return Color.BLACK
    return Color(
        Math.abs(Math.sin(log(x*6))).toFloat(),
        Math.abs(Math.cos(x+5)).toFloat(),
        Math.abs(Math.sin(Math.exp(x))).toFloat()
    )
}

fun bwFractal(x: Double) = Color(
    (1 - x.coerceIn(0.0, 1.0)).toFloat(),
    (1 - x.coerceIn(0.0, 1.0)).toFloat(),
    (1 - x.coerceIn(0.0, 1.0)).toFloat()
)
fun blackFractal(x: Double): Color {
    if (x == 1.0) return Color.BLACK
    return Color(
        Math.abs(Math.sin(x)).toFloat(),
        Math.abs(Math.sin(x)).toFloat(),
        Math.abs(Math.sin(x)).toFloat()
    )
}
fun whiteFractal(x: Double): Color {
    if (x == 1.0) return Color.BLACK
    return Color(
        Math.abs(Math.cos(x)).toFloat(),
        Math.abs(Math.cos(x)).toFloat(),
        Math.abs(Math.cos(x)).toFloat()
    )
}

fun customFractal(x: Double): Color {
    if (x == 1.0) return Color.BLACK
    return Color(
        Math.abs(Math.sin(x)).toFloat(),
        Math.abs(Math.sin(10*x)).toFloat(),
        Math.abs(Math.sin(x)).toFloat()
    )
}