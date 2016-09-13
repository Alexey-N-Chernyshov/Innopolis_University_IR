/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.ast

import com.github.alexey_n_chernyshov.iu.ir.{SearchIndex, SearchIndexPosition}

class BooleanRetrievalVisitor(index: SearchIndex) extends AbstractSyntaxTreeVisitor {

  var result = Set[SearchIndexPosition]()

  override def visit(andNode: QueryAbstractSyntaxTreeAnd): Unit = {
    andNode.left.accept(this)
    val leftRes = result
    andNode.right.accept(this)
    result = leftRes & result
  }

  override def visit(astTerm: QueryAbstractSyntaxTreeNode): Unit = {
    throw new UnsupportedOperationException("Attempt to visit abstract node in Boolean Retrieval.")
  }

  override def visit(notNode: QueryAbstractSyntaxTreeNot): Unit = {
    notNode.value.accept(this)
    result = index.getAllPositions().filter(!result.contains(_))
  }

  override def visit(nothingNode: QueryAbstractSyntaxTreeNothing): Unit = {
    throw new UnsupportedOperationException("Attempt to visit nothing node in Boolean Retrieval.")
  }

  override def visit(orNode: QueryAbstractSyntaxTreeOr): Unit = {
    orNode.left.accept(this)
    val leftRes = result
    orNode.right.accept(this)
    result = leftRes | result
  }

  override def visit(termNode: QueryAbstractSyntaxTreeTerm): Unit = {
    result = index.getPositions(termNode.value)
  }

}
