/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.naive_bayes

object NaiveBayesApp {

  def main(args: Array[String]): Unit = {
    println("Naive Bayes application.")
    val nb = new NaiveBayes

    println("Training...")
    // (tag, path)
    val trainingTopics = scala.collection.mutable.Set[(String, String)]()
    trainingTopics += ("spam" -> "data/Emails/spam-train")
    trainingTopics += ("nonspam" -> "data/Emails/nonspam-train")
    nb.train(trainingTopics)
    println("Training completed")

    println("Testing...")
    // (tag, path)
    val testTopics = scala.collection.mutable.Set[(String, String)]()
    testTopics += ("spam" -> "data/Emails/spam-test")
    testTopics += ("nonspam" -> "data/Emails/nonspam-test")

    for ((tag, res) <- nb.test(testTopics)) {
      println(tag)
      println("total: " + (res.positive + res.negative))
      println("positive: " + res.positive + " (" + (res.positive.toDouble / (res.positive + res.negative) * 100).toInt + "%)")
      println("negative: " + res.negative + " (" + (res.negative.toDouble / (res.positive + res.negative) * 100).toInt + "%)")
    }
  }

}
