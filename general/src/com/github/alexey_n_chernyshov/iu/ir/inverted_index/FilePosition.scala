/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.inverted_index

import java.io.File

import com.github.alexey_n_chernyshov.iu.ir.SearchIndexPosition

/** Stores a file from the corpus. */
case class FilePosition(file: File) extends SearchIndexPosition {

  override def toString: String = {
    file.toString
  }

}
