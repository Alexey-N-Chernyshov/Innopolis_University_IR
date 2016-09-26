/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir

import scala.collection.mutable.ListBuffer

object BiwordSplitter {

  /**
    * Split list of strings into list of biwords
    *
    * @param i
    * @return
    */
  def splitToBiwords(i: List[String]): List[String] = {
    var res = ListBuffer[String]()
    i.reduceLeft((l, r) => {
      res += (l + " " + r)
      r
    })
    res.toList
  }

}
