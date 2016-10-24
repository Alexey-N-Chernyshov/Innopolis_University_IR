/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.crawler

import java.io.PrintWriter
import java.util.logging.Logger

/** Caches the html to the directory. Each html will be cached into file named by md5 of it's url. Removes all tags,
  * multiple spaces and some symbols.
  *
  * @param directory - path to cache
  */
class HtmlCacher(directory: String) {

  /** Logger */
  val log = Logger.getLogger(this.getClass.getCanonicalName)

  /**
    * Get rid of tags and spaces.
    * @param data
    * @return
    */
  def processData(data: String): String = {
    val res = data.replaceAll("""(?:<style.*?>(?s:.)*?</style>|<script.*?>(?s:.)*?</script>|<(?:!|/?[a-‌​zA-Z]+).*?/?>)|&#160""", "")
    res.replaceAll("""\s+""", " ")
  }

  /** Computes md5 hash of string
    *
    * @param inStr input string to be hashed
    * @return md5 hash
    */
  def md5Hash(inStr: String): String = {
    java.security.MessageDigest.getInstance("MD5").digest(inStr.getBytes()).map(0xFF & _).map {
      "%02x".format(_)
    }.foldLeft("") {
      _ + _
    }
  }

  /** Make unique path and filename by md5 of url. It has 2-level structure, each level is directory named by character
    * of md5.
    */
  def makeCachePath(url: String): String = {
    var res = md5Hash(url)
    var path = directory + "/"
    for (i <- 1 to 2) {
      path = path + res.substring(0, 1) + "/"
      res = res.substring(1)
    }
    new java.io.File(path).mkdirs
    res = path + res + ".txt"
    if (new java.io.File(res).exists())
      log.warning("File \"" + res + "\" already exists. It'll be rewritten.")
    res
  }

  /** Cache html.
    *
    * @param url
    * @param html
    */
  def cache(url: String, html: String): Unit = {
    val text = processData(html)
    val writer = new PrintWriter(makeCachePath(url))
    writer.write(url)
    writer.write("\n")
    writer.write(text)
    writer.close()
  }

}
