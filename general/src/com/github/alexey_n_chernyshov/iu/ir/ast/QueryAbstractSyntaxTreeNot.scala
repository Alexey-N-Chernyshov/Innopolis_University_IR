/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.ast

case class QueryAbstractSyntaxTreeNot(value: QueryAbstractSyntaxTreeNode) extends QueryAbstractSyntaxTreeNode {

  /**
    * Accepts visitor.
    */
  override def accept(): Unit = ???

}
