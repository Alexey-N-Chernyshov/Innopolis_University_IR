/**
  * @author Yex
  */

package com.github.alexey_n_chernyshov.iu.ir.ast

import com.github.alexey_n_chernyshov.iu.ir.token_processing.TokenProcessor

import scala.util.parsing.combinator.JavaTokenParsers

/**
  * Parse query string according the following rules and builds AST:
  *
  * query => nothing
  * nothing => or [nothing]
  * or => and [OR or]
  * and => term [AND and]
  * term => (nothing) | negation | word
  * negation => NOT term
  * word => "\d\w+"
  */
class QueryParser(tokenProcessor: TokenProcessor) extends JavaTokenParsers {

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
    tokenProcessor.processToken(token)
  }

  /** Returns AST built on the given query. */
  def buildAST(queryString: String): QueryAbstractSyntaxTreeNode = {
    parse(query, queryString.trim) match {
      case Success(matched, _) => matched
      case Failure(msg, _) => throw new IllegalArgumentException("Failure: " + msg)
      case Error(msg, _) => throw new IllegalArgumentException("ERROR: " + msg)
    }
  }

}
