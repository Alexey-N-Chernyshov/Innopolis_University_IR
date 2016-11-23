/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.free_text_retrieval

import java.io.File

import com.github.alexey_n_chernyshov.iu.ir.tfidf_index.TfIdfDocumentPosition
import com.github.alexey_n_chernyshov.iu.ir.token_processing.{NoTokenFilter, NoTokenProcessor, StrangeSymbolFilter}
import com.github.alexey_n_chernyshov.iu.ir.{Retrieval, SearchIndexBuilder, SearchIndexPosition}
import com.github.alexey_n_chernyshov.iu.ir.tfidf_db_index.TfIdfDBSearchIndex

/** Handles free text queries. */
class FreeTextRetrieval(corpus: String, indexBuilder: SearchIndexBuilder) extends Retrieval {

  val delimeters = "[\\s\\*\\?!.,;:=\\-\"\\(\\)]"

  // defines how tokens are processed in indexBuilder and queries
  val tokenProcessor = indexBuilder.tokenProcessor
  val tokenFilter = indexBuilder.tokenFilter

//  val indexBuilder = new TfIdfSearchIndexBuilder(tokenProcessor, tokenFilter)
  var index: TfIdfDBSearchIndex = indexBuilder.buildIndex(corpus).asInstanceOf[TfIdfDBSearchIndex]

  def executeQuery(query: String): Set[SearchIndexPosition] = {
    val queryTokens = query.split(delimeters).map(tokenProcessor.processToken(_)).toList

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
