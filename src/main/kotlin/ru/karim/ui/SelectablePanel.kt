package ru.karim.ui

import ru.karim.ui.painting.Painter
import java.awt.Color
import java.awt.Point
import java.awt.Rectangle
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter

class SelectablePanel(vararg painters: Painter) : GraphicsPanel(*painters) {

    private var pt1: Point? = null
    private var pt2: Point? = null

    private var selectListeners: MutableList<(Rectangle)->Unit> = mutableListOf()
    fun addSelectListener(l:(Rectangle)->Unit){
        selectListeners.add(l)
    }
    fun removeSelectListener(l:(Rectangle)->Unit){
        selectListeners.remove(l)
    }
    init {
        addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent?) {
                super.mousePressed(e)
                pt1 = e?.point
            }



            override fun mouseReleased(e: MouseEvent?) {
                super.mouseReleased(e)
                ////////////////////////////////////////////
                drawSelectRect()
                pt1?.let { p1 ->
                    pt2?.let { p2 ->
                        selectListeners.forEach { it(Rectangle(Math.min(p1.x,p2.x), Math.min(p1.y,p2.y),Math.abs(p2.x-p1.x), Math.abs(p2.y-p1.y))) }
                    }
                }
                pt1 = null
                pt2 = null
            }
        })
        addMouseMotionListener(object : MouseMotionAdapter() {
            override fun mouseDragged(e: MouseEvent?) {
                super.mouseDragged(e)
                repeat(2) {
                    drawSelectRect()
                    pt2 = e?.point
                }
            }
            })
        }
    private fun drawSelectRect() {
        with(graphics) {
            setXORMode(Color.WHITE)
            pt1?.let { p1 ->
                pt2?.let { p2 ->
                    drawRect(Math.min(p1.x,p2.x), Math.min(p1.y,p2.y),Math.abs(p2.x-p1.x), Math.abs(p2.y-p1.y))
                }
            }
            setPaintMode()
        }
    }
}