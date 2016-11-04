/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.naive_bayes

import com.github.alexey_n_chernyshov.iu.ir.IndexBuilder

class NaiveBayes extends IndexBuilder {

  /** Tag is a topic title. */
  class Topic(val tag: String, val size: Int) {

    /** Features with probabilities. */
    val features = scala.collection.mutable.Map[String, Double]()

    /** Returns weight for list of features. */
    def getWeight(terms: List[String]): Double = {
      features.filterKeys(terms.contains(_)).values.sum
    }

  }

  val topics = scala.collection.mutable.Set[Topic]()
  var totalDocs = 0
  val a = 1 // coefficient for Laplassian Smoothing
  var vocabularySize = 0

  /** Trains topic Cj from specified corpus. */
  def trainTopic(tag: String, corpus: String) = {
    val collection = getCollection(corpus)
    val collectionSize = collection.length
    totalDocs += collectionSize

    val topic = new Topic(tag, collectionSize)

    // count word frequency
    val bagOfWords = scala.collection.mutable.Map[String, Int]()
    collection.foreach {
      file => tokenizeFile(file).foreach {
        term =>
          bagOfWords.getOrElseUpdate(term, 0)
          bagOfWords(term) += 1
      }
    }

    // count word probability
    val totalWords = bagOfWords.values.sum
    bagOfWords.foreach{
      term => topic.features += (term._1 -> (scala.math.log(term._2.toDouble + a) / (totalWords + vocabularySize + a)))
    }

    topics += topic
  }

  def train(topics: scala.collection.mutable.Set[(String, String)]) = {
    // collect vocabulary
    val vocabulary = scala.collection.mutable.Set[String]()
    topics.foreach {
      topic =>
        getCollection(topic._2).foreach {
          file => tokenizeFile(file).foreach(vocabulary.add(_))
        }
    }
    vocabularySize = vocabulary.size

    topics.foreach {
      topic =>
        trainTopic(topic._1, topic._2)
    }
  }

  def classify(terms: List[String]): String = {
    topics.maxBy(topic => scala.math.log(topic.size.toDouble / totalDocs) + topic.getWeight(terms)).tag
  }

  case class TestResult(var positive: Int, var negative: Int)

  def test(topics: scala.collection.mutable.Set[(String, String)]): scala.collection.mutable.Map[String, TestResult] = {
    var res = scala.collection.mutable.Map[String, TestResult]()
    topics.foreach {
      topic =>
        res += (topic._1 -> TestResult(0, 0))
        getCollection(topic._2).foreach {
          file =>
            if (classify(tokenizeFile(file)) == topic._1)
              res(topic._1).positive += 1
            else
              res(topic._1).negative += 1
        }
    }

    res
  }

}
