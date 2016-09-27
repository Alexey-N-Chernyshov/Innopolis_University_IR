/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.biword_retrieval

import com.github.alexey_n_chernyshov.iu.ir.token_processing.NoTokenProcessor
import com.github.alexey_n_chernyshov.iu.ir.{SearchIndex, SearchIndexPosition}

class BiwordRetrieval(corpus: String) {

  val biwordIndexBuilder = new BiwordIndexBuilder(new NoTokenProcessor)
  var index: SearchIndex = biwordIndexBuilder.buildIndex(corpus)
  val tokenProcessor = new NoTokenProcessor()

  def executeQuery(query: String): Set[SearchIndexPosition] = {
    val queryBiwords = BiwordSplitter.splitToBiwords(query.split(" ").map(tokenProcessor.processToken(_)).toList)
    var res = index.getAllPositions()
    queryBiwords.foreach { q =>
      res &= index.getPositions(q)
    }
    res
  }

}
