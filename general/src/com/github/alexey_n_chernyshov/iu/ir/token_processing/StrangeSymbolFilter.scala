/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.token_processing

import scala.util.Try

class StrangeSymbolFilter(parentTokenFilter: TokenFilter) extends TokenFilter {

  /**
    *
    * @param token - token itself
    * @return true if token doesn't contain symbols
    */
  override def isOk(token: String): Boolean = {
    token.forall(_.isLetter) && parentTokenFilter.isOk(token)
  }

}
