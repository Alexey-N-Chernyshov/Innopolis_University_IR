/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir

import com.github.alexey_n_chernyshov.iu.ir.boolean_retrieval.BooleanRetrieval

import scala.io.StdIn.readLine

trait RetrievalApp {

  val retrievalName: String
  val retrievalModel: Retrieval


  def main(args: Array[String]): Unit = {
    val corpus = "data/ex1"
    println(retrievalName + ".")
    println("Building corpus from " + corpus)

    while (true) {
      print("\nInput query: ")
      val query = readLine()
      println("Result:")
      try {
        retrievalModel.executeQuery(query).foreach(println(_))
      } catch {
        case e: UnsupportedOperationException => println("Wrong query: " + e)
      }
    }
  }

}
