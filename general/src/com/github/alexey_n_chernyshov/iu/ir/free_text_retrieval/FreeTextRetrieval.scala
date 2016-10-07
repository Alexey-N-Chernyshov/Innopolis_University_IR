/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.free_text_retrieval

import com.github.alexey_n_chernyshov.iu.ir.{Retrieval, SearchIndex, SearchIndexPosition}
import com.github.alexey_n_chernyshov.iu.ir.inverted_index.InvertedIndexBuilder
import com.github.alexey_n_chernyshov.iu.ir.token_processing.NoTokenProcessor

/** Handles queries in free text */
class FreeTextRetrieval(corpus: String) extends Retrieval {

  // defines how tokens are processed in indexBuilder and queries
  val tokenProcessor = new NoTokenProcessor()

  val invertedIndexBuilder = new InvertedIndexBuilder(tokenProcessor)
  var index: SearchIndex = invertedIndexBuilder.buildIndex(corpus)

  def executeQuery(query: String): Set[SearchIndexPosition] = {
    val queryTokens = query.split(" ").map(tokenProcessor.processToken(_)).toList
    var res = Set[SearchIndexPosition]()
    queryTokens.foreach { t =>
      res |= index.getPositions(t)
    }
    res
  }

}
