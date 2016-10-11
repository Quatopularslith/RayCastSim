package raycastsim.drawable

import java.awt.BasicStroke
import java.awt.geom.Line2D

import raycastsim.drawable.CoordSys.SingleAxis
import raycastsim.drawable.Ray.DottedRay.DotLoc

import scala.swing.Graphics2D

/**
  * Created by Mnenmenth Alkaborin
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Mnenmenth
  */

object Ray {

  class BeginEnd(var begin: Point[Double], var end: Point[Double]) extends Line {

  }

  class Through(var begin: Point[Double], through: Point[Double], axis: SingleAxis.Value, length: Double = 0) extends Line {
    var end: Point[Double] = through
    if(length == 0) continue(100, axis)
    else continue(length, axis)
  }

  object DottedRay {
    object DotLoc extends Enumeration {
      val BEGIN, END, BOTH = Value
    }
  }
  //Make the ray continue infinitly in any of the specified directions to the end of the screen
  class DottedRay(var begin: Point[Double], var end: Point[Double], dotLoc: DotLoc.Value) extends Line {

    var dottedBegin = begin
    var dottedEnd = end
    var dottedLine = new Line2D.Double(CoordSys.c2p(begin.x, SingleAxis.X), CoordSys.c2p(begin.y, SingleAxis.Y), CoordSys.c2p(end.x, SingleAxis.X), CoordSys.c2p(end.y, SingleAxis.Y))

    def continueDotted(axis: SingleAxis.Value, length: Double = 100.0): Unit = {
      if(axis == SingleAxis.X) {
        if (begin.x > end.x) {
          if(dotLoc == DotLoc.BEGIN) {
            dottedBegin.x = begin.x + length
            dottedBegin.y = m * begin.x + b
          } else if (dotLoc == DotLoc.END) {
            dottedEnd.x = end.x - length
            dottedEnd.y = m * end.x + b
          } else if (dotLoc == DotLoc.BOTH) {
            dottedBegin.x = begin.x + length
            dottedBegin.y = m * begin.x + b
            dottedEnd.x = end.x - length
            dottedEnd.y = m * end.x + b
          }
        } else if (begin.x < end.x) {
          if (dotLoc == DotLoc.BEGIN) {
            dottedBegin.x = begin.x - length
            dottedBegin.y = m * begin.x + b
          } else if (dotLoc == DotLoc.END) {
            dottedEnd.x = end.x + length
            dottedEnd.y = m * end.x + b
          } else if (dotLoc == DotLoc.BOTH) {
            dottedBegin.x = begin.x - length
            dottedBegin.y = m * begin.x + b
            dottedEnd.x = end.x + length
            dottedEnd.y = m * end.x + b
          }
        }
      } else if (axis == SingleAxis.Y){
        if (begin.y > end.y) {
          if(dotLoc == DotLoc.BEGIN) {
            dottedBegin.y = begin.y + length
            dottedBegin.x = (begin.y-b)/m
          } else if (dotLoc == DotLoc.END) {
            dottedEnd.y = end.y - length
            dottedEnd.x = (end.y-b)/m
          } else if (dotLoc == DotLoc.BOTH) {
            dottedBegin.y = begin.y + length
            dottedBegin.x = (begin.y-b)/m
            dottedEnd.y = end.y - length
            dottedEnd.x = (end.y-b)/m
          }
        } else if (begin.y < end.y) {
          if(dotLoc == DotLoc.BEGIN) {
            dottedBegin.y = begin.y - length
            dottedBegin.x = (begin.y-b)/m
          } else if (dotLoc == DotLoc.END) {
            dottedEnd.y = end.y + length
            dottedEnd.x = (end.y-b)/m
          } else if (dotLoc == DotLoc.BOTH) {
            dottedBegin.y = begin.y - length
            dottedBegin.x = (begin.y-b)/m
            dottedEnd.y = end.y + length
            dottedEnd.x = (end.y-b)/m
          }
        }
      }
      val newBegin = CoordSys.c2p(dottedBegin)
      val newEnd = CoordSys.c2p(dottedEnd)
      dottedLine = new Line2D.Double(newBegin.x, newBegin.y, newEnd.x, newEnd.y)
    }

    val stroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, Array(20.0f), 0.0f)

    override def draw(g: Graphics2D): Unit = {
      val g2 = g.create().asInstanceOf[Graphics2D]
      g2.setStroke(stroke)
      g2.draw(dottedLine)
      g2.dispose()
      g.draw(line)
    }

  }

}