/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir

/** Builds index from given collection. */
trait IndexBuilder {

  /**
    * Returns index built on the given corpus.
    * @param directory path to the directory with a set of documents.
    */
  def buildIndex(directory: String): SearchIndex

}
