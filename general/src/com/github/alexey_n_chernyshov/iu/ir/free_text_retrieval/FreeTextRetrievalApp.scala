/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.free_text_retrieval

import com.github.alexey_n_chernyshov.iu.ir.tfidf_index.TfIdfDocumentPosition
import com.github.alexey_n_chernyshov.iu.ir.token_processing.{NoTokenFilter, NoTokenProcessor, StrangeSymbolFilter}
import com.github.alexey_n_chernyshov.iu.ir.{RetrievalApp, SearchIndexPosition}
import com.github.alexey_n_chernyshov.iu.ir.tfidf_db_index.{TfIdfDBIndexLoader, TfIdfDBSearchIndexBuilder}

object FreeTextRetrievalApp extends RetrievalApp {

//  val corpus = "data/COHA"
  val corpus = "data/lj"
  val tokenProcessor = new NoTokenProcessor()
  val tokenFilter = new StrangeSymbolFilter(new NoTokenFilter())
//  val indexBuilder = new TfIdfDBSearchIndexBuilder(tokenProcessor, tokenFilter)
  val indexBuilder = new TfIdfDBIndexLoader(tokenProcessor, tokenFilter)

  override val retrievalName = "Free text retrieval"
  override val retrievalModel = new FreeTextRetrieval(corpus, indexBuilder)

  override def printResult(res: Set[SearchIndexPosition]): Unit = {
    res
      .toList
      .sortWith(_.asInstanceOf[TfIdfDocumentPosition].weight > _.asInstanceOf[TfIdfDocumentPosition].weight)
      .take(10)
      .foreach(println(_))
  }

}
