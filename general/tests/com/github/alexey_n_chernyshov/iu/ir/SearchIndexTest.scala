/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir

import com.github.alexey_n_chernyshov.iu.ir.inverted_index.InvertedIndex
import org.scalatest.{BeforeAndAfter, FlatSpec}

class SearchIndexTest extends FlatSpec with BeforeAndAfter {

  val index = new InvertedIndex()
  val doc1 = new TestPosition("doc1")
  val doc2 = new TestPosition("doc2")

  before {
    index.addPosition("apple", doc1)
    index.addPosition("apple", doc2)
    index.addPosition("orange", doc1)
  }

  behavior of "A SearchIndex"

  it should "build index and return entries by keys" in {
    assert(index.getPositions("apple") == Set[TestPosition](doc1, doc2))
    assert(index.getPositions("orange") == Set[TestPosition](doc1))
  }

  it should "return empty set for a term that is not in the index" in {
    assert(index.getPositions("tomato") == Set[TestPosition]())
  }

}
