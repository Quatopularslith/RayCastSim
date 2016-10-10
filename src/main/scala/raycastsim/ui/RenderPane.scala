package raycastsim.ui

import raycastsim.core.RayCastSim
import raycastsim.drawable.CoordSys.{FocalPoint, SingleAxis}
import raycastsim.drawable.{CoordSys, Point, Ray}

import scala.swing.{Graphics2D, Panel}

/**
  * Created by Mnenmenth Alkaborin
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Mnenmenth
  */
class RenderPane extends Panel {

  val graph = new CoordSys(RayCastSim.windowSize)

  val ray1 = new Ray.BeginEnd(
    Point[Double](40, 30),
    Point[Double](80, 60)
  )
  val ray2 = new Ray.BeginEnd(
    Point[Double](61, 31),
    Point[Double](41, 81)
  )

  val ray3 = new Ray.BeginEnd(
    Point[Double](-40, 30),
    Point[Double](-80, 60)
  )
  val ray4 = new Ray.BeginEnd(
    Point[Double](-61, 31),
    Point[Double](-41, 81)
  )

  ray2.continue(40, SingleAxis.X)
  ray3.continue(40, SingleAxis.X)
  val circ = new FocalPoint(ray1.intersection(ray2), 10)

  override def paint(g: Graphics2D): Unit = {
    super.paint(g)
    graph.draw(g)
    ray1.draw(g)
    ray2.draw(g)
    ray3.draw(g)
    ray4.draw(g)
    circ.draw(g)
    repaint()
  }

}
