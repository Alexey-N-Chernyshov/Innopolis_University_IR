/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir

import java.io.File

import org.scalatest.FlatSpec

class BiwordRetrievalTest extends FlatSpec {

  behavior of "BiwordRetrieval"

  it should "search words in corpus ex1" in {
    val doc1 = new File("data/ex1/Doc1.txt")
    val doc2 = new File("data/ex1/Doc2.txt")
    val doc3 = new File("data/ex1/Doc3.txt")
    val doc4 = new File("data/ex1/Doc4.txt")

    val biwordRetrieval = new BiwordRetrieval("data/ex1")

    var res = biwordRetrieval.executeQuery("home sales")

    assert(res.map(_.toString) contains doc1.toString)
    assert(res.map(_.toString) contains doc2.toString)
    assert(res.map(_.toString) contains doc3.toString)
    assert(res.map(_.toString) contains doc4.toString)

    res = biwordRetrieval.executeQuery("new home sales")
    assert(res.map(_.toString) contains doc1.toString)
  }

  it should "search words in corpus ex2" in {
    val doc1 = new File("data/ex2/Doc 1.txt")
    val doc2 = new File("data/ex2/Doc 2.txt")
    val doc3 = new File("data/ex2/Doc 3.txt")
    val doc4 = new File("data/ex2/Doc 4.txt")

    val biwordRetrieval = new BiwordRetrieval("data/ex2")

    var res = biwordRetrieval.executeQuery("schizophrenia drug")
    assert(res.map(_.toString) contains doc2.toString)
  }

}
