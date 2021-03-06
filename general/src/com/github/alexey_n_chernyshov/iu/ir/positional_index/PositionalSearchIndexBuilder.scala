/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.positional_index

import com.github.alexey_n_chernyshov.iu.ir.{SearchIndex, SearchIndexBuilder}
import com.github.alexey_n_chernyshov.iu.ir.inverted_index.InvertedIndex
import com.github.alexey_n_chernyshov.iu.ir.token_processing.{TokenFilter, TokenProcessor}

class PositionalSearchIndexBuilder(var tokenProcessor: TokenProcessor, var tokenFilter: TokenFilter) extends SearchIndexBuilder {

  /**
    * Returns positional inverted index built on the given corpus.
    *
    * @param directory path to the directory with a set of documents.
    */
  override def buildIndex(directory: String): SearchIndex = {
    val index = new InvertedIndex()
    getCollection(directory).foreach {
      file => tokenizeFile(file).zipWithIndex.foreach {
        case (term, i) => index.addPosition(tokenProcessor.processToken(term), new FileWithPosition(i, file))
      }
    }
    index
  }

}
