/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.positional_index

import com.github.alexey_n_chernyshov.iu.ir.token_processing.{NoTokenFilter, NoTokenProcessor, NumberFilter, TokenProcessor}
import org.scalatest.FlatSpec

class PositionalSearchIndexBuilderTest extends FlatSpec {

  behavior of "PositionalIndexBuilderTest"

  it should "buildIndex" in {
    val tokenProcessor: TokenProcessor = new NoTokenProcessor()
    val tokenFilter = new NumberFilter(new NoTokenFilter())
    val pb = new PositionalSearchIndexBuilder(tokenProcessor, tokenFilter)
    val index = pb.buildIndex("data/ex1")

    index.getPositions("top").toList(0) match {
      case pos: FileWithPosition => assert(pos.position == 3)
      case _ => assert(false)
    }
  }

}
