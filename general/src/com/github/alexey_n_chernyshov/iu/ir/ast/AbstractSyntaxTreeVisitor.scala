/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.ast

/**
  * Visitor pattern for AST traversal.
  */
trait AbstractSyntaxTreeVisitor {

  def visit(astTerm: QueryAbstractSyntaxTreeAnd): Unit

  def visit(astTerm: QueryAbstractSyntaxTreeNode): Unit

  def visit(astTerm: QueryAbstractSyntaxTreeNot): Unit

  def visit(astTerm: QueryAbstractSyntaxTreeNothing): Unit

  def visit(astTerm: QueryAbstractSyntaxTreeOr): Unit

  def visit(astTerm: QueryAbstractSyntaxTreeTerm): Unit

}
