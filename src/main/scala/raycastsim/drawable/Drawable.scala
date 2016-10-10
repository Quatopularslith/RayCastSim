package raycastsim.drawable

import java.awt.geom.{Ellipse2D, Line2D}

import raycastsim.drawable.CoordSys.SingleAxis

import scala.swing.Graphics2D

/**
  * Created by Mnenmenth Alkaborin
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Mnenmenth
  */

case class Point[T:Numeric](var x: T, var y: T)

trait Drawable {
  def draw(g: Graphics2D): Unit = {}
}

trait Line extends Drawable {
  var begin: Point[Double]
  var end: Point[Double]
  var line = new Line2D.Double(CoordSys.c2p(begin.x, SingleAxis.X), CoordSys.c2p(begin.y, SingleAxis.Y), CoordSys.c2p(end.x, SingleAxis.X), CoordSys.c2p(end.y, SingleAxis.Y))

  def m = (begin.y - end.y) / (begin.x - end.x)
  def b = begin.y - (m*begin.x)

  def intersection(line: Line): Point[Double] = {
    val x = (line.b - b) / (m - line.m)
    val y = m * x + b
    Point[Double](x, y)
  }

  def continue(length: Double, axis: SingleAxis.Value): Unit ={
    if(axis == SingleAxis.X) {
      if (begin.x > end.x) {
        end.x = end.x - length
        end.y = m * end.x + b
      } else if (begin.x < end.x) {
        end.x = end.x + length
        end.y = m * end.x + b
      }
      line = new Line2D.Double(CoordSys.c2p(begin.x, SingleAxis.X), CoordSys.c2p(begin.y, SingleAxis.Y), CoordSys.c2p(end.x, SingleAxis.X), CoordSys.c2p(end.y, SingleAxis.Y))
    } else if (axis == SingleAxis.Y){
      if (begin.y > end.y) {
        end.y = end.y - length
        end.x = (-(b / m)) + (end.y / m)
      } else if (begin.y < end.y) {
        end.y = end.y + length
        end.x = (-(b / m)) + (end.y / m)
      }
      line = new Line2D.Double(CoordSys.c2p(begin.x, SingleAxis.X), CoordSys.c2p(begin.y, SingleAxis.Y), CoordSys.c2p(end.x, SingleAxis.X), CoordSys.c2p(end.y, SingleAxis.Y))
    }
  }

  def intersects(_line: Line2D): Boolean = line.intersectsLine(_line)

  override def draw(g: Graphics2D): Unit = {
    g.draw(line)
  }
}

trait Circle extends Drawable {
  var pos: Point[Double]
  var diameter: Int
  val circle = new Ellipse2D.Double(CoordSys.c2p(pos.x, SingleAxis.X)-(diameter/2), CoordSys.c2p(pos.y, SingleAxis.Y)-(diameter/2), diameter, diameter)
  override def draw(g: Graphics2D) = {
    g.fill(circle)
  }
}