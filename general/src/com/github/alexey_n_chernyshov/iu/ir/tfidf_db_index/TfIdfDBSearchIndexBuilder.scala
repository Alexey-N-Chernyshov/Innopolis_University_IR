/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.tfidf_db_index

import com.github.alexey_n_chernyshov.iu.ir.tfidf_index.TfIdfDocumentPosition
import com.github.alexey_n_chernyshov.iu.ir.token_processing.{TokenFilter, TokenProcessor}
import com.github.alexey_n_chernyshov.iu.ir.{SearchIndex, SearchIndexBuilder}

class TfIdfDBSearchIndexBuilder(var tokenProcessor: TokenProcessor, var tokenFilter: TokenFilter)
  extends SearchIndexBuilder {

  override def buildIndex(directory: String): SearchIndex = {

    blackList += "postIDs"
    val collection = getCollection(directory)

    val index = new TfIdfDBSearchIndex
    index.setup()

    println("First pass")
    // first pass - get docs frequencies
    collection.foreach {
      file =>
        val termsInDoc = tokenizeFile(file).filter(tokenFilter.isOk).map(tokenProcessor.processToken(_)).toSet
        index.addDocument(file.getPath, termsInDoc)
    }
    println("Saving vocabulary")
    println(index.docFrequencies.size)
    index.commitVocabulary()
    println("Vocabulary is commited")

    println("Second pass")
    //second pass - weighting
    index.totalDocs = collection.length
    collection.foreach { file =>
      // add to index
      index.getCoordinates(tokenizeFile(file).filter(tokenFilter.isOk))
        .foreach {
        case (t, v) => index.addPosition(t, new TfIdfDocumentPosition(file, v))
      }
      index.commitPositions()
    }
    println("Index construction completed")

    index
  }

}
