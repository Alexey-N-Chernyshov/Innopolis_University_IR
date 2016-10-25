/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.inverted_index

import com.github.alexey_n_chernyshov.iu.ir.token_processing.TokenProcessor
import com.github.alexey_n_chernyshov.iu.ir.{SearchIndex, SearchIndexPosition}

/** Stores a list of occurrences of each term. */
class InvertedIndex extends SearchIndex {

  /** Internal representation of the index. */
  var internalIndex = scala.collection.mutable.Map[String, Set[SearchIndexPosition]]()

  /** Adds entry to index.
    *
    * @param term     is a key of index
    * @param position is an index entry
    */
  override def addPosition(term: String, position: SearchIndexPosition): Unit = {
    if (!internalIndex.contains(term))
      internalIndex += (term -> Set(position))
    else
      internalIndex(term) += position
  }

  /**
    * Searches the index for term.
    *
    * @param term is a term that is looked for
    * @return set of positions for term
    */
  override def getPositions(term: String): Set[SearchIndexPosition] = {
    if (!internalIndex.contains(term))
      return Set[SearchIndexPosition]()
    internalIndex(term)
  }

  /** Returns all terms in index. */
  override def getAllTerms(): Set[String] = {
    internalIndex.keys.toSet
  }

  override def getAllPositions(): Set[SearchIndexPosition] = {
    var res = Set[SearchIndexPosition]()
    internalIndex.values.foreach(s => res = res union s)
    res
  }

  override def toString(): String = {
    internalIndex.toString()
  }

}
