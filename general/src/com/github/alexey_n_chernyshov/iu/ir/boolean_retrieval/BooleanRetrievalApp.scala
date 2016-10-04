/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.boolean_retrieval

import scala.io.StdIn.readLine

object BooleanRetrievalApp {

  def main(args: Array[String]): Unit = {
    val corpus = "data/ex1"
    println("Boolean retrieval.")
    println("Building corpus from " + corpus)
    val booleanRetrieval = new BooleanRetrieval(corpus)

    while (true) {
      print("\nInput query: ")
      val query = readLine()
      println("Result:")
      try {
        booleanRetrieval.executeQuery(query).foreach(println(_))
      } catch {
        case e: UnsupportedOperationException => println("Wrong query: " + e)
      }
    }
  }

}
