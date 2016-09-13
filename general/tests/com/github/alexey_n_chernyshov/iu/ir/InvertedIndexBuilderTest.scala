/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir

import org.scalatest.FlatSpec

class InvertedIndexBuilderTest extends FlatSpec {

  behavior of "InvertedIndexBuilder"

  it should "returns an index of corpus" in {

    val index = InvertedIndexBuilder.buildIndex("data/ex1")
    assert(index.isInstanceOf[InvertedIndex])
  }

}
