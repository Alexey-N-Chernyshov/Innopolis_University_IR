/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir

import com.github.alexey_n_chernyshov.iu.ir.biword_retrieval.BiwordSearchIndexBuilder
import com.github.alexey_n_chernyshov.iu.ir.inverted_index.InvertedIndex
import com.github.alexey_n_chernyshov.iu.ir.token_processing.{NoTokenFilter, NoTokenProcessor, NumberFilter, TokenProcessor}
import org.scalatest.FlatSpec

class BiwordSearchIndexBuilderTest extends FlatSpec {

  behavior of "BiwordIndexBuilderTest"

  it should "returns an index of corpus" in {
    val tokenProcessor: TokenProcessor = new NoTokenProcessor()
    val tokenFilter = new NumberFilter(new NoTokenFilter())

    val biwordIndexBuilder = new BiwordSearchIndexBuilder(tokenProcessor, tokenFilter   )

    val index = biwordIndexBuilder.buildIndex("data/ex1")
    assert(index.isInstanceOf[InvertedIndex])
  }

}
