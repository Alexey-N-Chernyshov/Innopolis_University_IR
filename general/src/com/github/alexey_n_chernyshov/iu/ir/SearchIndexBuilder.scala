/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir

import com.github.alexey_n_chernyshov.iu.ir.token_processing.TokenProcessor

/** Builds index from given collection. */
trait SearchIndexBuilder extends IndexBuilder {

  var tokenProcessor: TokenProcessor

  /**
    * Returns index built on the given corpus.
    * @param directory path to the directory with a set of documents.
    */
  def buildIndex(directory: String): SearchIndex

}
