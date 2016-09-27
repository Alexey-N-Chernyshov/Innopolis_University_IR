/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir

import java.io.File

import com.github.alexey_n_chernyshov.iu.ir.boolean_retrieval.BooleanRetrieval
import org.scalatest.FlatSpec

class BooleanRetrievalTest extends FlatSpec {

  behavior of "BooleanRetrieval"

  it should "search words in corpus ex1" in {
    val doc1 = new File("data/ex1/Doc1.txt")
    val doc2 = new File("data/ex1/Doc2.txt")
    val doc3 = new File("data/ex1/Doc3.txt")
    val doc4 = new File("data/ex1/Doc4.txt")

    val booleanRetrieval = new BooleanRetrieval("data/ex1")

    var res = booleanRetrieval.executeQuery("home")
    assert(res.map(_.toString) contains doc1.toString)
    assert(res.map(_.toString) contains doc2.toString)
    assert(res.map(_.toString) contains doc3.toString)
    assert(res.map(_.toString) contains doc4.toString)

    res = booleanRetrieval.executeQuery("in")
    assert(res.map(_.toString) contains doc2.toString)
    assert(res.map(_.toString) contains doc3.toString)
  }

  it should "search words in corpus ex2" in {
    val doc1 = new File("data/ex2/Doc 1.txt")
    val doc2 = new File("data/ex2/Doc 2.txt")
    val doc3 = new File("data/ex2/Doc 3.txt")
    val doc4 = new File("data/ex2/Doc 4.txt")

    val booleanRetrieval = new BooleanRetrieval("data/ex2")

    var res = booleanRetrieval.executeQuery("schizophrenia AND drug")
    assert(res.map(_.toString) contains doc1.toString)
    assert(res.map(_.toString) contains doc2.toString)

    res = booleanRetrieval.executeQuery("NOT drug")
    assert(res.map(_.toString) contains doc3.toString)
    assert(res.map(_.toString) contains doc4.toString)

    res = booleanRetrieval.executeQuery("for AND NOT(drug OR approach)")
    assert(res.map(_.toString) contains doc4.toString)
  }

}
