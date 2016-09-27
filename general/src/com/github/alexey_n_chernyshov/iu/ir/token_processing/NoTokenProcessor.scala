/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.token_processing

class NoTokenProcessor extends TokenProcessor {

  /**
    * @param token - token itself
    * @return - returns term
  */
  override def processToken(token: String): String = {
    token
  }

}
