/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir

import com.github.alexey_n_chernyshov.iu.ir.boolean_retrieval.BooleanRetrieval

import scala.io.StdIn.readLine

trait RetrievalApp {

  val retrievalName: String
  val retrievalModel: Retrieval

  def printResult(res: Set[SearchIndexPosition]): Unit = {
    res.foreach(println(_))
  }

  def main(args: Array[String]): Unit = {
    val corpus = "data/ex1"
    println(retrievalName + ".")
    println("Building corpus from " + corpus)

    while (true) {
      print("\nInput query: ")
      val query = readLine()
      println("Result:")
      try {
        printResult(retrievalModel.executeQuery(query))
      } catch {
        case e: UnsupportedOperationException => println("Wrong query: " + e)
      }
    }
  }

}
