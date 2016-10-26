/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.biword_retrieval

import com.github.alexey_n_chernyshov.iu.ir._
import com.github.alexey_n_chernyshov.iu.ir.inverted_index.{FilePosition, InvertedIndex}
import com.github.alexey_n_chernyshov.iu.ir.token_processing.TokenProcessor

/**
  * Index where each pair of tokens is a term.
  */
class BiwordIndexBuilder(var tokenProcessor: TokenProcessor) extends IndexBuilder {

  /** Returns index built on the given corpus.
   * @param directory path to the directory with a set of documents.
   */
  override def buildIndex(directory: String): SearchIndex = {
    val index = new InvertedIndex()
    getCollection(directory).foreach { file =>
      BiwordSplitter.splitToBiwords(tokenizeFile(file)).foreach(token =>
        index.addPosition(tokenProcessor.processToken(token), new FilePosition(file)))
    }
    index
  }

}
