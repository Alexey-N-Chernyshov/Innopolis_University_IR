/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.crawler

import java.io.PrintWriter

/** Caches the html to the directory. Each html will be cached into file named by md5 of it's url. Removes all tags,
  * multiple spaces and some symbols.
  *
  * @param directory - path to cache
  */
class HtmlCacher(directory: String) {

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

  /** Cache html.
    *
    * @param url
    * @param html
    */
  def cache(url: String, html: String): Unit = {
    val md5 = md5Hash(url)
    val text = processData(html)
    val writer = new PrintWriter(directory + "/" + md5 + ".txt")// { write("file contents"); close }
    writer.write(url)
    writer.write("\n")
    writer.write(text)
    writer.close()
  }

}
