package ru.karim.ui.painting

import java.awt.Dimension
import java.awt.Graphics

interface Painter {
    var size : Dimension
    fun paint(g:Graphics)
}