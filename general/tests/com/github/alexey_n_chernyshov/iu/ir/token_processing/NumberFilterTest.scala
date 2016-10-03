package com.github.alexey_n_chernyshov.iu.ir.token_processing

import org.scalatest.FlatSpec

/**
  * @author Yex
  */
class NumberFilterTest extends FlatSpec {

  behavior of "NumberFilterTest"

  it should "isOk" in {
    val parentFilter = new NoTokenFilter()
    val numberFilter = new NumberFilter(parentFilter)

    var list = List("pen", "apple", "1.32", "pineapple", "123", "1e34", "pencil")
    list = list.filter(numberFilter.isOk)
    assert(list.contains("pen"))
    assert(list.contains("apple"))
    assert(!list.contains("1.32"))
    assert(list.contains("pineapple"))
    assert(!list.contains("123"))
    assert(!list.contains("1e34"))
    assert(list.contains("pencil"))
  }

}
