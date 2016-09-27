/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.token_processing

class StemmerTokenProcessor(component: TokenProcessor) extends TokenProcessor {

  /**
    * @param token - token itself
    * @return - returns stemmed term
    */
  override def processToken(token: String): String = {
    PorterStemmer.stem(component.processToken(token))
  }

}
