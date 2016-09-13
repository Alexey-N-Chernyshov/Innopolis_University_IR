/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.ast

case class QueryAbstractSyntaxTreeOr(left: QueryAbstractSyntaxTreeNode, right: QueryAbstractSyntaxTreeNode)
  extends QueryAbstractSyntaxTreeNode{

  /**
    * Accepts visitor.
    */
  override def accept(): Unit = ???

}
