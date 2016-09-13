/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir

import java.io.File

/** Stores a file from the corpus. */
case class FilePosition(file: File) extends SearchIndexPosition {

  override def toString: String = {
    file.toString
  }

}
