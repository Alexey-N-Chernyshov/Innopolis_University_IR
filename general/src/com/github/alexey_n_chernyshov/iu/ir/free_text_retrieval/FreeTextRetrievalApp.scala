/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.free_text_retrieval

import com.github.alexey_n_chernyshov.iu.ir.tfidf_index.TfIdfDocumentPosition
import com.github.alexey_n_chernyshov.iu.ir.{RetrievalApp, SearchIndexPosition}

object FreeTextRetrievalApp extends RetrievalApp {

  val corpus = "data/COHA"

  override val retrievalName = "Free text retrieval"
  override val retrievalModel = new FreeTextRetrieval(corpus)

  override def printResult(res: Set[SearchIndexPosition]): Unit = {
    res
      .toList
      .sortWith(_.asInstanceOf[TfIdfDocumentPosition].weight > _.asInstanceOf[TfIdfDocumentPosition].weight)
      .foreach(println(_))
  }

}
