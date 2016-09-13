/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir

import com.github.alexey_n_chernyshov.iu.ir.ast._

import scala.util.parsing.combinator.JavaTokenParsers

/**
  * Parse query string according the following rules:
  *
  * query => nothing
  * nothing => or [nothing]
  * or => and [OR or]
  * and => term [AND and]
  * term => (nothing) | negation | word
  * negation => NOT term
  * word => "\d\w+"
  */
object QueryParser extends JavaTokenParsers  {

  /** Parses words. */
  def word: Parser[QueryAbstractSyntaxTreeNode] = """[\d\w]+""".r ^^ { token =>
    QueryAbstractSyntaxTreeTerm(processToken(token))
  }

  /** Parses NOT expression. */
  def negation: Parser[QueryAbstractSyntaxTreeNode] = "NOT" ~> term ^^ {
    QueryAbstractSyntaxTreeNot(_)
  }

  /** Parses terms, which are words or expression in braces or negations. */
  def term: Parser[QueryAbstractSyntaxTreeNode] = "(" ~> nothing <~ ")" | negation | word

  /** Parses "nothing" (two sequential words). */
  def nothing: Parser[QueryAbstractSyntaxTreeNode] = or ~ rep(multiNothing) ^^ {
    case t ~ list => list.foldLeft(t)((l, r) => QueryAbstractSyntaxTreeNothing(l, r))
  }

  /** Parses rest of "nothing". */
  def multiNothing: Parser[QueryAbstractSyntaxTreeNode] = or

  /** Parses AND. */
  def and: Parser[QueryAbstractSyntaxTreeNode] = term ~ rep(multiand) ^^ {
    case t ~ list => list.foldLeft(t)((l, r) => QueryAbstractSyntaxTreeAnd(l, r))
  }

  /** Parses rest of AND. */
  def multiand: Parser[QueryAbstractSyntaxTreeNode] = "AND" ~> term

  /** Parses OR. */
  def or: Parser[QueryAbstractSyntaxTreeNode] = and ~ rep(multiOr) ^^ {
    case t ~ list => list.foldLeft(t)((l, r) => QueryAbstractSyntaxTreeOr(l, r))
  }

  /** Parses rest of OR. */
  def multiOr: Parser[QueryAbstractSyntaxTreeNode] = "OR" ~> and

  /** Parses query and returns root of AST. */
  def query: Parser[QueryAbstractSyntaxTreeNode] = nothing

  /** Processes tokens and returns terms. */
  def processToken(token: String): String = {
    token
  }

  /** Returns AST built on the given query. */
  def buildAST(query: String): Unit = {
    parse(query, query.trim) match {
      case Success(matched, _) => println(matched)
      case Failure(msg, _) => println("Failure: " + msg)
      case Error(msg, _) => println("ERROR: " + msg)
    }
  }

}
