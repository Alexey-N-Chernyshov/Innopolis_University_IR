/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.free_text_retrieval

import com.github.alexey_n_chernyshov.iu.ir.tfidf_db_index.TfIdfDBSearchIndexBuilder
import com.github.alexey_n_chernyshov.iu.ir.token_processing.{NoTokenFilter, NoTokenProcessor, StrangeSymbolFilter}

/** Indexer builds index and saves it. */
object FreeTextRetrievalIndexerApp {

  val corpus = "data/ex1"
  val tokenProcessor = new NoTokenProcessor()
  val tokenFilter = new StrangeSymbolFilter(new NoTokenFilter())
  val indexBuilder = new TfIdfDBSearchIndexBuilder(tokenProcessor, tokenFilter)

  val retrievalName = "Free text retrieval"

  def main(args: Array[String]): Unit = {
    new FreeTextRetrieval(corpus, indexBuilder)
  }

}
