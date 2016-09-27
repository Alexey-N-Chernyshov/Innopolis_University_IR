/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.inverted_index

import com.github.alexey_n_chernyshov.iu.ir.{IndexBuilder, SearchIndex}

object InvertedIndexBuilder extends IndexBuilder {

  /**
    * Returns inverted index built on the given corpus.
    *
    * @param directory path to the directory with a set of documents.
    */
  override def buildIndex(directory: String): SearchIndex = {
    val index = new InvertedIndex()
    getCollection(directory).foreach {
      file => tokenizeFile(file).foreach { term =>
          index.addPosition(term, new FilePosition(file))
      }
    }
    index
  }

}
