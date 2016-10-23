/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.crawler

import scala.io.Source

class Crawler {

  /** List of urls to be cached. */
  val urls = List(
    "https://ru.wikipedia.org/wiki/%D0%9F%D0%B5%D0%BD%D1%8C%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%B8%D0%B9_%D1%81%D0%B5%D0%BB%D1%8C%D1%81%D0%BE%D0%B2%D0%B5%D1%82_(%D0%A7%D1%83%D0%BB%D1%8B%D0%BC%D1%81%D0%BA%D0%B8%D0%B9_%D1%80%D0%B0%D0%B9%D0%BE%D0%BD)",
    "https://ru.wikipedia.org/wiki/%D0%A0%D1%8E%D0%B4%D0%B8%D0%B3%D0%B5%D1%80_%D0%B8%D0%B7_%D0%91%D0%B5%D1%85%D0%B5%D0%BB%D0%B0%D1%80%D0%BD%D0%B0",
    "https://ru.wikipedia.org/wiki/%D0%9F%D0%BE%D0%B4%D0%BB%D0%B5%D1%81%D0%BD%D1%8B%D0%B9,_%D0%92%D1%8F%D1%87%D0%B5%D1%81%D0%BB%D0%B0%D0%B2_%D0%A1%D0%B5%D1%80%D0%B3%D0%B5%D0%B5%D0%B2%D0%B8%D1%87",
    "https://ru.wikipedia.org/wiki/%D0%93%D1%80%D0%BE%D0%BD%D0%B8%D0%BD%D0%B3%D0%B5%D0%BD",
    "https://en.wikipedia.org/wiki/Ciocia",
    "https://en.wikipedia.org/wiki/Battle_of_Sphacteria",
    "https://en.wikipedia.org/wiki/Patoka_Township,_Pike_County,_Indiana",
    "https://en.wikipedia.org/wiki/Bart_Vandepoel",
    "https://en.wikipedia.org/wiki/Arumecla",
    "https://en.wikipedia.org/wiki/C4H6O"
  )

  val cacher = new HtmlCacher("data/cache")

  /** Crawl urls and cache them.
    *
    */
  def crawl(): Unit = {
    urls.foreach(url => cacher.cache(url, Source.fromURL(url).mkString))
  }

}
