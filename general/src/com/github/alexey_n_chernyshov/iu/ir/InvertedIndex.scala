/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir

/** Stores a list of occurrences of each term. */
class InvertedIndex extends SearchIndex {

  /** Internal representation of the index. */
  var internalIndex = scala.collection.mutable.Map[String, Set[SearchIndexPosition]]()

  /** Adds entry to index.
    *
    * @param term     is a key of index
    * @param position is an index entry
    */
  override def addIndex(term: String, position: SearchIndexPosition): Unit = {
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

}
