/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.token_processing

/**
  * Abstract class for token processing, implements layering processing.
  */
trait TokenProcessor {

  /**
    * Process token.
    *
    * @param token - token itself
    * @return - returns processed token
    */
  def processToken(token: String): String

}
