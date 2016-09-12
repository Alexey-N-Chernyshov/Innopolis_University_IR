/**
  * @author tixxit
  * @see https://gist.github.com/tixxit/1246894/e79fa9fbeda695b9e8a6a5d858b61ec42c7a367d
  */

package com.github.alexey_n_chernyshov.iu.ir

import scala.math.min

/** Determines edit distance between two strings. */
object EditDistance {

  /** Returns edit distance between two strings. */
  def editDist[A](a: Iterable[A], b: Iterable[A]) =
    ((0 to b.size).toList /: a)((prev, x) =>
      (prev zip prev.tail zip b).scanLeft(prev.head + 1) {
        case (h, ((d, v), y)) => min(min(h + 1, v + 1), d + (if (x == y) 0 else 1))
      }) last

}