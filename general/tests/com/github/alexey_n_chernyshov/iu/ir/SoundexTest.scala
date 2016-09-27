/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir

import com.github.alexey_n_chernyshov.iu.ir.token_processing.Soundex
import org.scalatest.{FlatSpec, Matchers}

class SoundexTest extends FlatSpec with Matchers {
  behavior of "A Soundex"

  it should "map word into one letter + 3 digits form" in {
    val soundex = Soundex
    assert(soundex.soundex("Hermann") == "H655")
    assert(soundex.soundex("Herrmann") == "H655")
    assert(soundex.soundex("Herman") == "H655")
    assert(soundex.soundex("Chernyshov") == "C652")
  }

  it should "pad string with trailing zeroes, if length < 3" in {
    val soundex = Soundex
    assert(soundex.soundex("") == "0000")
    assert(soundex.soundex("H") == "H000")
    assert(soundex.soundex("He") == "H000")
  }
}