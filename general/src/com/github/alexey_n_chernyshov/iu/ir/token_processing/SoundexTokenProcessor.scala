/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.token_processing


  /**
    * @param token - token itself
    * @return - returns soundex term
    */
  override def processToken(token: String): String = {
    Soundex.soundex(parentTokenProcessor.processToken(token))
  }

}

