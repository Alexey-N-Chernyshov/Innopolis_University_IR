package com.github.alexey_n_chernyshov.iu.ir

object Soundex {
  //Classes of equivalence of letters
  val classOfZero = Set('A', 'E', 'I', 'O', 'U', 'H', 'W', 'Y')
  val classOfOne = Set('B', 'F', 'P', 'V')
  val classOfTwo = Set('C', 'G', 'J', 'K', 'Q', 'S', 'X', 'Z')
  val classOfThree = Set('D', 'T')
  val classOfFour = Set('L')
  val classOfFive = Set('M', 'N')
  val classOfSix = Set('R')

  // recursively eliminate consecutive duplicates
  def eliminateConsecutiveDuplicates(s: String): String = {
    if (s == null) return null
    if (s.length() <= 1) return s
    if (s.head == s.tail.head)
      eliminateConsecutiveDuplicates(s.tail)
    else
      s.head + eliminateConsecutiveDuplicates(s.tail)
  }

  // map word into one letter + 3 digits form
  def soundex(s: String): String = {
    if (s == null || s.isEmpty)
      return "0000"

    var res = s.toUpperCase().substring(1) // at first we proceed rest of string (1, n)

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

    eliminateConsecutiveDuplicates(res)

    // remove zeroes, pad with trailing zeroes, get first 3 digits and add first character in uppercase
    s.toUpperCase()(0) + (res.filter(_ != '0') + "0000").substring(0, 3)
  }
}
