/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.util

import com.github.alexey_n_chernyshov.iu.ir.IndexBuilder
import com.github.alexey_n_chernyshov.iu.ir.token_processing.{NoTokenFilter, NoTokenProcessor, StrangeSymbolFilter}

object FileCounterApp extends IndexBuilder {

  blackList += "postIDs"

  def main(args: Array[String]): Unit = {
    val path = "C:\\Users\\Yex\\src\\lj" //args(0)//

    val tokenFilter = new StrangeSymbolFilter((new NoTokenFilter()))

    var res = getCollection(path)
    println("total files: " + res.length)
    res = res.filter(x => !tokenizeFile(x).filter(tokenFilter.isOk).isEmpty)
    println("files to index: " + res.length)
  }

}
