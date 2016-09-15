/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir

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
          index.addIndex(term, new FilePosition(file))
      }
    }
    index
  }

}
