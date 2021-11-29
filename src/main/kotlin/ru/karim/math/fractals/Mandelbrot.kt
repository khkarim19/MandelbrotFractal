package ru.karim.math.fractals

import org.kotlinmath.Complex
import org.kotlinmath.complex
import ru.karim.math.complex.mod2

object Mandelbrot:Fractal {
    private var R2 = 4.0
        set(value) {
            field = Math.max(Math.abs(value), 2 * Double.MIN_VALUE)
            R2 = value * value
        }
    val maxIters: Int = 1000

    override fun isInSet(c: Complex): Double {
        var z = complex(0, 0)
        for( i in 0 until maxIters) {
            z = z * z + c
            if (z.mod2 >= R2) return i.toDouble() / maxIters
        }
        return 1.0
    }
}