/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.biword_retrieval

import com.github.alexey_n_chernyshov.iu.ir.token_processing.NoTokenProcessor
import com.github.alexey_n_chernyshov.iu.ir.{Retrieval, SearchIndex, SearchIndexPosition}

class BiwordRetrieval(corpus: String) extends Retrieval {

  // defines how tokens are processed in indexBuilder and queries
  val tokenProcessor = new NoTokenProcessor()

  val biwordIndexBuilder = new BiwordIndexBuilder(tokenProcessor)
  var index: SearchIndex = biwordIndexBuilder.buildIndex(corpus)

  def executeQuery(query: String): Set[SearchIndexPosition] = {
    val queryBiwords = BiwordSplitter.splitToBiwords(query.split(" ").map(tokenProcessor.processToken(_)).toList)
    var res = index.getAllPositions()
    queryBiwords.foreach { q =>
      res &= index.getPositions(q)
    }
    res
  }

}
