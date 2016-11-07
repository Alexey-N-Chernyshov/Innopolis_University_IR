/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir

import java.io.File

import scala.collection.mutable.ListBuffer
import scala.io.Source

trait IndexBuilder {

  val delimeters = "[\\s\\*\\?!.,;:=\\-\"\\(\\)]"
  var blackList = ListBuffer[String]()

  /** Returns an array of files in a directory. */
  def getCollection(directory: String): Array[File] = {
    getFilesRecursively(new File(directory)).filter(_.isFile).filter(!blackList.contains(_))
  }

  def getFilesRecursively(f: File): Array[File] = {
    val res = f.listFiles
    res ++ res.filter(_.isDirectory).flatMap(getFilesRecursively)
  }

  /** Returns set of tokens in a file. */
  def tokenizeFile(file: File): List[String] = {
    var res = ListBuffer[String]()
    for (line <- Source.fromFile(file).getLines()) {
      res ++= line.split(delimeters).map(_.trim).map(token => token.toLowerCase).toList
    }
    res.toList
  }

}
