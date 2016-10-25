/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.tfidf_index

import java.io.File

import com.github.alexey_n_chernyshov.iu.ir.SearchIndexPosition

case class TfIdfDocumentPosition(file: File, weight: Double) extends SearchIndexPosition{

  override def toString(): String = {
    file + " - " + weight.toString
  }

}
