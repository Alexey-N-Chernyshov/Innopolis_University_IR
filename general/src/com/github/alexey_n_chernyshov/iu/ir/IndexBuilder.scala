/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir

import java.io.File
import java.util.Scanner
import scala.collection.JavaConversions.asScalaIterator

trait IndexBuilder {

  /** Returns an array of files in a directory. */
  def getCollection(directory: String): Array[File] = {
    val d = new File(directory)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile)
    } else {
      Array[File]()
    }
  }

  /** Returns set of tokens in a file. */
  def tokenizeFile(file: File): List[String] = {
    new Scanner(file).map(_.trim).map(token => token.toLowerCase).toList
  }

}
