/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir

import com.github.alexey_n_chernyshov.iu.ir.biword_retrieval.BiwordSearchIndexBuilder
import com.github.alexey_n_chernyshov.iu.ir.inverted_index.InvertedIndex
import com.github.alexey_n_chernyshov.iu.ir.token_processing.{NoTokenProcessor, TokenProcessor}
import org.scalatest.FlatSpec

class BiwordSearchIndexBuilderTest extends FlatSpec {

  behavior of "BiwordIndexBuilderTest"

  it should "returns an index of corpus" in {
    var tokenProcessor: TokenProcessor = new NoTokenProcessor()
    val biwordIndexBuilder = new BiwordSearchIndexBuilder(tokenProcessor)

    val index = biwordIndexBuilder.buildIndex("data/ex1")
    assert(index.isInstanceOf[InvertedIndex])
  }

}
