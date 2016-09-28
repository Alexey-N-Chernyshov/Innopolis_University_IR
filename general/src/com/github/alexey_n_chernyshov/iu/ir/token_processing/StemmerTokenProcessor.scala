/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.token_processing

class StemmerTokenProcessor(parentTokenProcessor: TokenProcessor) extends TokenProcessor {

  /**
    * @param token - token itself
    * @return - returns stemmed term
    */
  override def processToken(token: String): String = {
    PorterStemmer.stem(parentTokenProcessor.processToken(token))
  }

}
