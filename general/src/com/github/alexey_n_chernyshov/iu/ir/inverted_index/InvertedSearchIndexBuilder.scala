/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.inverted_index

import com.github.alexey_n_chernyshov.iu.ir.token_processing.TokenProcessor
import com.github.alexey_n_chernyshov.iu.ir.{SearchIndexBuilder, SearchIndex}

class InvertedSearchIndexBuilder(var tokenProcessor: TokenProcessor) extends SearchIndexBuilder {

  /**
    * Returns inverted index built on the given corpus.
    *
    * @param directory path to the directory with a set of documents.
    */
  override def buildIndex(directory: String): SearchIndex = {
    val index = new InvertedIndex()
    getCollection(directory).foreach {
      file => tokenizeFile(file).foreach { term =>
          index.addPosition(tokenProcessor.processToken(term), new FilePosition(file))
      }
    }
    index
  }

}
