/*
   Porter stemmer in Scala. The original paper is in

       Porter, 1980, An algorithm for suffix stripping, Program, Vol. 14,
       no. 3, pp 130-137,

   See also http://www.tartarus.org/~martin/PorterStemmer

   A few methods were borrowed from the existing Java port from the above page.
*/

package com.github.alexey_n_chernyshov.iu.ir.token_processing

/** Removes commoner morphological endings from words in English. */
object PorterStemmer {

  // word to be stemmed.
  var result = ""

  // Just recode the existing stuff, then go through and refactor with some intelligence.
  def cons(i: Int): Boolean = {
    var ch = result(i)

    // magic!
    var vowels = "aeiou"

    // multi return. yuck
    if (vowels.contains(ch))
      return false

    if (ch == 'y') {
      if (i == 0) {
        return true
      }
      else {
        // loop it!
        return !cons(i - 1)
      }
    }

    return true
  }

  // Add via letter or entire word
  def add(ch: Char) = {
    result += ch
  }

  def add(word: String) = {
    result = word
  }

  /* m() measures the number of consonant sequences between 0 and j. if c is
      a consonant sequence and v a vowel sequence, and <..> indicates arbitrary
      presence,

         <c><v>       gives 0
         <c>vc<v>     gives 1
         <c>vcvc<v>   gives 2
         <c>vcvcvc<v> gives 3
         ....
   *
   * I think this can be recoded far more neatly.
   */
  def calcM(s:String): Int = {
    val l = s.length
    var count = 0
    var currentConst = false

    for (c <- 0 to l - 1) {
      if (cons(c)) {
        if (!currentConst && c != 0) {
          count += 1
        }
        currentConst = true
      } else {
        currentConst = false
      }
    }

    return count
  }

  // removing the suffix 's', does a vowel exist?'
  def vowelInStem(s: String): Boolean = {
    for (i <- 0 to result.length - 1 - s.length) {
      if (!cons(i)) {
        return true
      }
    }
    return false
  }

  /* doublec(j) is true <=> j,(j-1) contain a double consonant. */
  def doublec(): Boolean = {
    val l = result.length - 1

    if (l < 1)
      return false

    if (result(l) != result(l - 1))
      return false

    return cons(l)
  }

  /* cvc(i) is true <=> i-2,i-1,i has the form consonant - vowel - consonant
     and also if the second c is not w,x or y. this is used when trying to
     restore an e at the end of a short word. e.g.

        cav(e), lov(e), hop(e), crim(e), but
        snow, box, tray.
  */
  def cvc(s: String): Boolean = {
    val i = result.length - 1 - s.length
    if (i < 2 || !cons(i) || cons(i - 1) || !cons(i - 2))
      return false

    val ch = result(i)
    if ("wxy".contains(ch))
      return false

    return true
  }


  // returns true if it did the change.
  def replacer(orig: String, replace: String, checker: Int => Boolean): Boolean = {
    val l = result.length
    val origLength = orig.length
    var res = false

    if (result.endsWith(orig)) {
      val n = result.substring(0, l - origLength)

      val m = calcM(n)
      if ( checker( m ) ) {
        result = n + replace
      }

      res = true
    }

    return res
  }

  // process the list of tuples to find which prefix matches the case.
  // checker is the conditional checker for m.
  def processSubList(l: List[(String, String)], checker: Int=>Boolean): Boolean = {
    l.foreach { v =>
      if (replacer(v._1, v._2, checker))
        return true
    }
    return false
  }

  def step1() {
    var l = result.length
    var m = calcM(result)

    // step 1a
    var vals = List(("sses", "ss"), ("ies","i"), ("ss","ss"), ("s", ""))
    processSubList(vals, _ >= 0 )

    // step 1b
    if (!(replacer("eed", "ee", _>0))) {
      if ((vowelInStem("ed") && replacer("ed", "", _>=0)) || (vowelInStem("ing") && replacer("ing", "", _>=0))) {
        vals = List(("at", "ate"), ("bl","ble"), ("iz","ize"))

        if (!processSubList(vals, _>=0)) {
          // if this isn't done, then it gets more confusing.

          m = calcM(result)
          val last = result(result.length - 1)
          if (doublec() && !"lsz".contains(last)) {
            result = result.substring(0, result.length - 1)
          } else if ( m == 1 && cvc("") ) {
            result = result + "e"
          }
        }
      }
    }

    // step 1c
    ( vowelInStem("y") && replacer("y", "i", _>=0))
  }


  def step2() = {
    val vals = List(("ational", "ate"), ("tional","tion"), ("enci","ence"), ("anci","ance"), ("izer","ize"),
      ("bli","ble"), ("alli", "al"), ("entli","ent"), ("eli","e"), ("ousli","ous"), ("ization","ize"), ("ation","ate"),
      ("ator","ate"), ("alism","al"), ("iveness","ive"), ("fulness","ful"), ("ousness", "ous"), ("aliti", "al"),
      ("iviti","ive"),("biliti", "ble"),("logi", "log"))
    processSubList(vals, _>0)
  }

  def step3() = {
    val vals = List( ("icate", "ic"), ("ative",""), ("alize","al"), ("iciti","ic"), ("ical","ic"), ("ful",""),
      ("ness",""))
    processSubList(vals, _>0)
  }

  def step4() = {
    // first part.
    val vals = List(("al",""), ("ance",""), ("ence",""), ("er",""), ("ic",""), ("able",""), ("ible",""), ("ant",""),
      ("ement",""), ("ment",""), ("ent",""))
    var res = processSubList( vals, _>1 )

    // special part.
    if (!res) {
      if (result.length > 4) {
        if (result(result.length - 4) == 's' || result(result.length - 4) == 't') {
          res = replacer("ion", "", _>1)
        }
      }
    }

    // third part.
    if (!res) {
      val vals = List(("ou",""), ("ism",""), ("ate",""), ("iti",""), ("ous",""), ("ive",""), ("ize",""))
      res = processSubList(vals, _>1)
    }
  }

  def step5a() = {
    var res = false
    res = replacer("e", "", _>1)

    if (!cvc("e")) {
      res = replacer("e", "", _==1)
    }
  }

  def step5b() = {
    val m = calcM( result )
    if ( m > 1 && doublec() && result.endsWith("l") ) {
      result = result.substring(0, result.length - 1)
    }
  }

  /** Returns word after stemming. */
  def stem(word: String): String = {
    add(word)
    if (result.length > 2) {
      step1()
      step2()
      step3()
      step4()
      step5a()
      step5b()
    }
    result
  }

}
