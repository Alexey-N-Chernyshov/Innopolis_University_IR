/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.token_processing

class NoTokenFilter extends TokenFilter {

  /**
    * @param token - token itself
    * @return - returns true
    */
  override def isOk(token: String): Boolean = {
    true
  }

}
