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

}
