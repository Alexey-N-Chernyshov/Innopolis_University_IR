/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.token_processing

class StrangeSymbolFilter(parentTokenFilter: TokenFilter) extends TokenFilter {

  /**
    *
    * @param token - token itself
    * @return true if token consists of ASCII letters
    */
  override def isOk(token: String): Boolean = {
    token.forall(c => c.isLetter && (c <= 'z')) && parentTokenFilter.isOk(token)
  }

}
