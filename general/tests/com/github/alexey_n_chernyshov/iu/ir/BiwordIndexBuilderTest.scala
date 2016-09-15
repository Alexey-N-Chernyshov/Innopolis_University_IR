/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir

import org.scalatest.FlatSpec

class BiwordIndexBuilderTest extends FlatSpec {

  behavior of "BiwordIndexBuilderTest"

  it should "returns an index of corpus" in {

    val index = BiwordIndexBuilder.buildIndex("data/ex1")
    assert(index.isInstanceOf[InvertedIndex])
  }

  it should "list of string string to biwords" in {
    val str = List("one", "two", "three", "four")
    val res = BiwordIndexBuilder.splitToBiwords(str)
    assert(res(0) == "one two")
    assert(res(1) == "two three")
    assert(res(2) == "three four")
  }

}
