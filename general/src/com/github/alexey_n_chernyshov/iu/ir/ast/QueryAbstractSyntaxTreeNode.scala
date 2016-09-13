/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.ast

import com.github.alexey_n_chernyshov.iu.ir.SearchIndexPosition

/**
  * AST (Abstract Syntax Tree) node for query.
  */
trait QueryAbstractSyntaxTreeNode {

  /**
    * Accepts visitor.
    */
  def accept(visitor: AbstractSyntaxTreeVisitor): Unit

}
