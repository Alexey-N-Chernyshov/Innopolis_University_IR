/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir

import com.github.alexey_n_chernyshov.iu.ir.inverted_index.{InvertedIndex, InvertedSearchIndexBuilder}
import com.github.alexey_n_chernyshov.iu.ir.token_processing.NoTokenProcessor
import org.scalatest.FlatSpec

class InvertedSearchIndexBuilderTest extends FlatSpec {

  behavior of "InvertedIndexBuilder"

  it should "returns an index of corpus" in {

    val tokenProcessor = new NoTokenProcessor()
    val index = new InvertedSearchIndexBuilder(tokenProcessor)
      .buildIndex("data/ex1")
    assert(index.isInstanceOf[InvertedIndex])
  }

}
