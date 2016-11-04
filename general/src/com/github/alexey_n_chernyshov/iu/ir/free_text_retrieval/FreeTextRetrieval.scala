/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.free_text_retrieval

import java.io.File

import com.github.alexey_n_chernyshov.iu.ir.tfidf_index.{TfIdfDocumentPosition, TfIdfIndex, TfIdfSearchIndexBuilder}
import com.github.alexey_n_chernyshov.iu.ir.token_processing.NoTokenProcessor
import com.github.alexey_n_chernyshov.iu.ir.{Retrieval, SearchIndexPosition}

/** Handles free text queries. */
class FreeTextRetrieval(corpus: String) extends Retrieval {

  // defines how tokens are processed in indexBuilder and queries
  val tokenProcessor = new NoTokenProcessor()

  val indexBuilder = new TfIdfSearchIndexBuilder(tokenProcessor)
  var index: TfIdfIndex = indexBuilder.buildIndex(corpus).asInstanceOf[TfIdfIndex]

  def executeQuery(query: String): Set[SearchIndexPosition] = {
    val queryTokens = query.split(" ").map(tokenProcessor.processToken(_)).toList

    val scores = scala.collection.mutable.Map[File, Double]()
    // compute VSM coordinates for the query
    index.getCoordinates(queryTokens)
      .filter(t => t._2 != 0.0)
      .foreach(
        t => index.getPositions(t._1).foreach(
          pos => {
            val file = pos.asInstanceOf[TfIdfDocumentPosition].file
            val weight = pos.asInstanceOf[TfIdfDocumentPosition].weight * t._2
            if (scores.contains(file))
              scores(file) += weight
            else
              scores += (file -> weight)
          }
        )
      )

    var res = Set[SearchIndexPosition]()
    scores.foreach( t => res += new TfIdfDocumentPosition(t._1, t._2) )
    res
  }

}
