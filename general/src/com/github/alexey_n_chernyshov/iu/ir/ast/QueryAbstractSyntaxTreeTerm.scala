/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.ast

case class QueryAbstractSyntaxTreeTerm(value: String) extends QueryAbstractSyntaxTreeNode {

  /**
    * Accepts visitor.
    */
  override def accept(visitor: AbstractSyntaxTreeVisitor): Unit = {
    visitor.visit(this)
  }
}
