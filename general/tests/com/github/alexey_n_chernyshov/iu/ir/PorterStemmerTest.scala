/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir

import com.github.alexey_n_chernyshov.iu.ir.util.PorterStemmer
import org.scalatest.FlatSpec

class PorterStemmerTest extends FlatSpec {

    behavior of "PorterStemmer"

    /* Examples are from the book "Introduction to Information Retrieval", exercise 2.2. */
    it should "properly remove endings from dataset" in {
      assert(PorterStemmer.stem("abandon") == "abandon")
      assert(PorterStemmer.stem("abandonment") == "abandon")
      assert(PorterStemmer.stem("absorbency") == "absorb")
      assert(PorterStemmer.stem("absorbent") == "absorb")
      assert(PorterStemmer.stem("marketing") == "market")
      assert(PorterStemmer.stem("markets") == "market")
      assert(PorterStemmer.stem("university") == "univers")
      assert(PorterStemmer.stem("universe") == "univers")
      assert(PorterStemmer.stem("volume") == "volum")
      assert(PorterStemmer.stem("volumes") == "volum")
    }
}
