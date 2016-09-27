/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.biword_retrieval

import com.github.alexey_n_chernyshov.iu.ir.{SearchIndex, SearchIndexPosition}

class BiwordRetrieval(corpus: String) {

  var index: SearchIndex = BiwordIndexBuilder.buildIndex(corpus)

  def executeQuery(query: String): Set[SearchIndexPosition] = {
    val queryBiwords = BiwordSplitter.splitToBiwords(query.split(" ").toList)
    var res = index.getAllPositions()
    queryBiwords.foreach { q =>
      res &= index.getPositions(q)
    }
    res
  }

}
