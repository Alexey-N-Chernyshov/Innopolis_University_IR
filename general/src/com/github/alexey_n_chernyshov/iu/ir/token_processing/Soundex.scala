/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.token_processing

/** Indexes words by sounds, as pronounced in English. */
object Soundex {

  //Classes of equivalence of letters
  val classOfZero = Set('A', 'E', 'I', 'O', 'U', 'H', 'W', 'Y')
  val classOfOne = Set('B', 'F', 'P', 'V')
  val classOfTwo = Set('C', 'G', 'J', 'K', 'Q', 'S', 'X', 'Z')
  val classOfThree = Set('D', 'T')
  val classOfFour = Set('L')
  val classOfFive = Set('M', 'N')
  val classOfSix = Set('R')

  /** Returns string with eliminated consecutive duplicates. */
  def eliminateConsecutiveDuplicates(s: String): String = {
    if (s == null) return null
    if (s.length() <= 1) return s
    if (s.head == s.tail.head)
      eliminateConsecutiveDuplicates(s.tail)
    else
      s.head + eliminateConsecutiveDuplicates(s.tail)
  }

  /**
    * Maps word into "one letter + 3 digits" form.
    *
    * @param s string to be indexed
    * @return index for input string
    */
  def soundex(s: String): String = {
    if (s == null || s.isEmpty)
      return "0000"

    // retain the first letter
    var res = s.toUpperCase().substring(1)

    // replace s(1, n) letters with corresponding digits
    res = res.map(c =>
      if (classOfZero(c)) '0' else
      if (classOfOne(c)) '1' else
      if (classOfTwo(c)) '2' else
      if (classOfThree(c)) '3' else
      if (classOfFour(c)) '4' else
      if (classOfFive(c)) '5' else
      if (classOfSix(c)) '6' else c
    )
    res = eliminateConsecutiveDuplicates(res)

    // remove zeroes, pad with trailing zeroes, get first 3 digits and retain first character in uppercase
    s.toUpperCase()(0) + (res.filter(_ != '0') + "0000").substring(0, 3)
  }

}
