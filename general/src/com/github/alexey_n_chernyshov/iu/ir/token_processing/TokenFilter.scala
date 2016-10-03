/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.token_processing

/**
  * Filter tokens.
  */
trait TokenFilter {

  def isOk(token: String): Boolean

}
