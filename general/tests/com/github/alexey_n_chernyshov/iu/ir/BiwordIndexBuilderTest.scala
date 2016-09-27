/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir

import com.github.alexey_n_chernyshov.iu.ir.biword_retrieval.BiwordIndexBuilder
import com.github.alexey_n_chernyshov.iu.ir.inverted_index.InvertedIndex
import com.github.alexey_n_chernyshov.iu.ir.token_processing.NoTokenProcessor
import org.scalatest.FlatSpec

class BiwordIndexBuilderTest extends FlatSpec {

  behavior of "BiwordIndexBuilderTest"

  it should "returns an index of corpus" in {
    val biwordIndexBuilder = new BiwordIndexBuilder(new NoTokenProcessor)
    val index = biwordIndexBuilder.buildIndex("data/ex1")
    assert(index.isInstanceOf[InvertedIndex])
  }

}
