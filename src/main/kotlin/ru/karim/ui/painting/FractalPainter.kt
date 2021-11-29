package ru.karim.ui.painting

import org.kotlinmath.complex
import ru.karim.math.fractals.Fractal
import ru.karim.math.fractals.Mandelbrot
import ru.karim.math.fractals.Mandelbrot.isInSet
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.concurrent.thread
import kotlin.random.Random

class FractalPainter(private val plane: CartesianPlane, val frac: Fractal) : Painter {
    var colorizer: (Double) -> Color = ::getDefaultColor
    fun getDefaultColor(x: Double) = Color(
        (1 - x.coerceIn(0.0, 1.0)).toFloat(),
        (1 - x.coerceIn(0.0, 1.0)).toFloat(),
        (1 - x.coerceIn(0.0, 1.0)).toFloat()
    )

    override var size: Dimension
        get() = Dimension(plane.width, plane.height)
        set(value) {
            plane.width = value.width
            plane.height = value.height
        }

    private var pool: ExecutorService? = null
    override fun paint(g: Graphics) {
        if (plane.width == 0 || plane.height == 0) return
        val threadCount = 8
        val poolTaskCount = threadCount * 4
        if (pool?.isShutdown == false)
            pool?.shutdown()
        pool = Executors.newFixedThreadPool(threadCount)
        val stripWidth = plane.width / poolTaskCount
        List(poolTaskCount) {
            pool?.submit(Callable {
                val start = it * stripWidth
                val end = (it + 1) * stripWidth - 1 + if (it + 1 == poolTaskCount) plane.width % poolTaskCount else 0
                val img = BufferedImage(end - start + 1, plane.height, BufferedImage.TYPE_INT_RGB)
                val ig = img.graphics
                for (i in start..end) {
                    for (j in 0..plane.height) {
                        val fc = frac.isInSet(
                            complex(
                                plane.xScr2Crt(i),
                                plane.yScr2Crt(j)
                            )
                        )
                        ig.color = colorizer(fc)
                        ig.fillRect(i - start, j, 1, 1)
                    }
                }
                //g.drawImage(img, start, 0, null)
                img
            })
        }.forEachIndexed { i, v -> g.drawImage(v?.get(), i * stripWidth, 0, null) }
    }

//    fun getColor(x: Double): Color {
//        var k = 0.6
//        if (x == 1.0) return Color.BLACK
//        return Color(
//            Math.abs(Math.sin(x)*k).toFloat(),
//            Math.abs(Math.cos(x)*k).toFloat(),
//            Math.abs(Math.cos(9000000000*x)*k).toFloat()
//        )
//    }

}