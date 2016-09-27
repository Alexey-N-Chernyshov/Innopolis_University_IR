/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir

import com.github.alexey_n_chernyshov.iu.ir.biword_retrieval.BiwordSplitter
import org.scalatest.FlatSpec

class BiwordSplitterTest extends FlatSpec {

  behavior of "BiwordSplitterTest"

  it should "list of string string to biwords" in {
    val str = List("one", "two", "three", "four")
    val res = BiwordSplitter.splitToBiwords(str)
    assert(res(0) == "one two")
    assert(res(1) == "two three")
    assert(res(2) == "three four")
  }

}
