/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir

import java.io.File
import java.util.Scanner
import scala.collection.JavaConversions.asScalaIterator

object InvertedIndexBuilder extends IndexBuilder {

  /**
    * Returns inverted index built on the given corpus.
    *
    * @param directory path to the directory with a set of documents.
    */
  override def buildIndex(directory: String): SearchIndex = {
    val index = new InvertedIndex()
    getCollection(directory).foreach {
      file => tokenizeFile(file).foreach {
        term => index.addIndex(term, new FilePosition(file))
      }
    }
    index
  }

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
  def tokenizeFile(file: File): Set[String] = {
    new Scanner(file).map(_.trim).map(token => token.toLowerCase).toSet
  }

}
