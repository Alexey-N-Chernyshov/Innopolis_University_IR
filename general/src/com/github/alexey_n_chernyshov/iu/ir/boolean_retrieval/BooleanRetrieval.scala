/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.boolean_retrieval

import com.github.alexey_n_chernyshov.iu.ir.ast.QueryParser
import com.github.alexey_n_chernyshov.iu.ir.inverted_index.InvertedIndexBuilder
import com.github.alexey_n_chernyshov.iu.ir.{SearchIndex, SearchIndexPosition}

/**
  * Boolean retrieval is a classical model based on logical operations:
  * - OR
  * - AND
  * - NOT
  * It uses inverted index under the hood.
  */
class BooleanRetrieval(corpus: String) {

  var index: SearchIndex = InvertedIndexBuilder.buildIndex(corpus)

  def executeQuery(query: String): Set[SearchIndexPosition] = {
    val ast = QueryParser.buildAST(query)
    val visitor = new BooleanRetrievalVisitor(index)
    ast.accept(visitor)
    visitor.result
  }

}