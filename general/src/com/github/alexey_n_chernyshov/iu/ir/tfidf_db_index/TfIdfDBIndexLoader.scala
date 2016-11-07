/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.tfidf_db_index

import com.github.alexey_n_chernyshov.iu.ir.{SearchIndex, SearchIndexBuilder}
import com.github.alexey_n_chernyshov.iu.ir.token_processing.{TokenFilter, TokenProcessor}

/** Build index (actually loads) from DB. */
class TfIdfDBIndexLoader(var tokenProcessor: TokenProcessor, var tokenFilter: TokenFilter)
  extends SearchIndexBuilder {

  /** Loads index from DB. */
  override def buildIndex(directory: String): SearchIndex = {
    val index = new TfIdfDBSearchIndex
    index.load()
    index
  }
}
