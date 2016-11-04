/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.boolean_retrieval

import com.github.alexey_n_chernyshov.iu.ir.ast.QueryParser
import com.github.alexey_n_chernyshov.iu.ir.inverted_index.InvertedSearchIndexBuilder
import com.github.alexey_n_chernyshov.iu.ir.token_processing.NoTokenProcessor
import com.github.alexey_n_chernyshov.iu.ir.{Retrieval, SearchIndex, SearchIndexPosition}

/**
  * Boolean retrieval is a classical model based on logical operations:
  * - OR
  * - AND
  * - NOT
  * It uses inverted index under the hood.
  */
class BooleanRetrieval(corpus: String) extends Retrieval {

  // defines how tokens are processed in indexBuilder and queries
  val tokenProcessor = new NoTokenProcessor()

  val queryParser = new QueryParser(tokenProcessor)

  val invertedIndexBuilder = new InvertedSearchIndexBuilder(tokenProcessor)
  var index: SearchIndex = invertedIndexBuilder.buildIndex(corpus)

  def executeQuery(query: String): Set[SearchIndexPosition] = {
    val ast = queryParser.buildAST(query)
    val visitor = new BooleanRetrievalVisitor(index)
    ast.accept(visitor)
    visitor.result
  }

}