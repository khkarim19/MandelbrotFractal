package ru.karim.ui

import ru.karim.math.fractals.Mandelbrot
import ru.karim.ui.painting.*
import ru.karim.ui.painting.Painter
import java.awt.Color
import java.awt.Dimension
import java.awt.Rectangle
import java.awt.event.*
import javax.swing.*
import javax.swing.JFrame

class MainFrame : JFrame() {
    private val minDim = Dimension(600, 400)
    private val fractalPanel: SelectablePanel
    private val plane: CartesianPlane

    init {

        minimumSize = minDim
        defaultCloseOperation = EXIT_ON_CLOSE
        plane = CartesianPlane(
            -2.0,
            1.0,
            -1.0,
            1.0
        )


        fractalPanel = SelectablePanel(
            FractalPainter(plane, Mandelbrot).apply {
                colorizer = ::customFractal
            }
        ).apply {
            background = Color.WHITE
            addSelectListener { r ->
                with(plane) {
                    xSegment = Pair(xScr2Crt(r.x), xScr2Crt(r.x + r.width))
                    ySegment = Pair(yScr2Crt(r.y), yScr2Crt(r.y + r.height))
                }
            this@MainFrame.repaint()
        }

    }


    layout = GroupLayout(contentPane).apply{
        setVerticalGroup(
            createSequentialGroup()
                .addComponent(fractalPanel)

        )
        setHorizontalGroup(
            createParallelGroup()
                .addComponent(fractalPanel)

        )
    }
}


}