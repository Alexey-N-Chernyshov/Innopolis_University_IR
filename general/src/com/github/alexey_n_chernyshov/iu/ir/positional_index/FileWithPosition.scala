/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.positional_index

import java.io.File

import com.github.alexey_n_chernyshov.iu.ir.SearchIndexPosition

case class FileWithPosition(position: Integer, file: File) extends SearchIndexPosition {

  override def toString: String = {
    position.toString + " " + file.toString
  }

}
