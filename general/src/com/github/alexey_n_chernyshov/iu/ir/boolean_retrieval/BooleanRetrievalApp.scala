/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.boolean_retrieval

import com.github.alexey_n_chernyshov.iu.ir.{Retrieval, RetrievalApp}

import scala.io.StdIn.readLine

object BooleanRetrievalApp extends RetrievalApp {

  val corpus = "data/ex1"

  override val retrievalName = "Boolean retrieval"
  override val retrievalModel = new BooleanRetrieval(corpus)

}
