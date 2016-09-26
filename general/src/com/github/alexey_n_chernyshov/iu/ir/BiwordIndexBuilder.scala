/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir

/**
  * Index where each pair of tokens is a term.
  */
object BiwordIndexBuilder extends IndexBuilder {

  /** Returns index built on the given corpus.
   * @param directory path to the directory with a set of documents.
   */
  override def buildIndex(directory: String): SearchIndex = {
    val index = new InvertedIndex()
    getCollection(directory).foreach { file =>
      BiwordSplitter.splitToBiwords(tokenizeFile(file)).foreach(term => index.addIndex(term, new FilePosition(file)))
    }
    index
  }

}
