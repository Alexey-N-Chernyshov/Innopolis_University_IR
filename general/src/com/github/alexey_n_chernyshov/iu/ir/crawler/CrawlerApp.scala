package com.github.alexey_n_chernyshov.iu.ir.crawler

/**
  * @author Yex
  */
object CrawlerApp {

  def main(args: Array[String]): Unit = {
    val crawler = new Crawler()
    crawler.crawl()
  }
}
