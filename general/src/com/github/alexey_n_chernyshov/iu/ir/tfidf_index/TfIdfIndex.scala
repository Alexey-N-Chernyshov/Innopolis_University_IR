/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.tfidf_index

import com.github.alexey_n_chernyshov.iu.ir.inverted_index.InvertedIndex

/** Represents tf.idf index. */
class TfIdfIndex extends InvertedIndex {

  val docFrequencies = scala.collection.mutable.Map[String, Int]()
  var totalDocs = 0

  def getCoordinates(terms: List[String]): Map[String, Double] = {
    val vect = terms.foldLeft(Map.empty[String, Int]){ (count, word) => count + (word -> (count.getOrElse(word, 0) + 1)) }
      // filter terms that are both in query and documents (q AND d)
      .filterKeys(docFrequencies.contains(_))
      // compute tf.idf weights
      .map { case (t, f) =>
        (t, scala.math.log10(1 + f.toDouble) * scala.math.log10(totalDocs.toDouble / docFrequencies(t)))
      }

    // normalize vectors
    val magnitude = scala.math.sqrt(vect.foldLeft(0.0){ case (sum, (k, v)) => sum + v * v })
    vect.map{ case (t, f) => (t, f / magnitude) }
  }

}
