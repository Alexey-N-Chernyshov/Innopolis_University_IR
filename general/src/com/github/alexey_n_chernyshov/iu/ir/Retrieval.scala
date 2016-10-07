/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir

/** Represents different retrieval models. */
trait Retrieval {

  def executeQuery(query: String): Set[SearchIndexPosition]

}
