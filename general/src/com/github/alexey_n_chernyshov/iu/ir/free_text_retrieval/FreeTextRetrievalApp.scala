/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.free_text_retrieval

import com.github.alexey_n_chernyshov.iu.ir.RetrievalApp
import com.github.alexey_n_chernyshov.iu.ir.boolean_retrieval.BooleanRetrieval

object FreeTextRetrievalApp extends RetrievalApp {

  val corpus = "data/ex1"

  override val retrievalName = "Free text retrieval"
  override val retrievalModel = new FreeTextRetrieval(corpus)

}
