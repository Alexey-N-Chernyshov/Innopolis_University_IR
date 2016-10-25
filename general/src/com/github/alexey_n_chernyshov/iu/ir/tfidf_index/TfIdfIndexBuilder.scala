/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.tfidf_index

import com.github.alexey_n_chernyshov.iu.ir.token_processing.TokenProcessor
import com.github.alexey_n_chernyshov.iu.ir.{IndexBuilder, SearchIndex}

class TfIdfIndexBuilder(var tokenProcessor: TokenProcessor) extends IndexBuilder {

  override def buildIndex(directory: String): SearchIndex = {
    val index = new TfIdfIndex()
    val collection = getCollection(directory)

    // first pass - get docs frequencies
    collection.foreach {
      file =>
        var termsInDoc = Set[String]()
        tokenizeFile(file).map(tokenProcessor.processToken(_)).foreach { term =>
        if (!termsInDoc.contains(term)) {
          if (!index.docFrequencies.contains(term))
            index.docFrequencies += (term -> 1)
          else
            index.docFrequencies(term) += 1
          termsInDoc += term
        }
      }
    }

    //second pass - weighting
    index.totalDocs = collection.length
    collection.foreach { file =>
      // add to index
      index.getCoordinates(tokenizeFile(file)).foreach {
        case (t, v) => index.addPosition(t, new TfIdfDocumentPosition(file, v))
      }
    }
    index
  }

}
