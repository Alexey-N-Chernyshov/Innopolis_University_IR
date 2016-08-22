import java.util.Scanner
import java.io.File
import scala.collection.JavaConversions._

object BooleanRetrieval {

  // returns list of files in collection
  def getCollection(dir: String): Set[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toSet
    } else {
      Set[File]()
    }
  }

  // returns set of tokens in a file
  def tokenizeFile(file: File): Set[String] = {
    new Scanner(file).map(_.trim).map(token => token.toLowerCase).toSet
  }

  // builds posting lists Set(Term -> List[File])
  def buildPostingList(file: File, m: scala.collection.mutable.Map[String, Set[File]]): Unit = {
    tokenizeFile(file) foreach { t => if (!m.contains(t)) m += (t -> Set(file)) else m(t) += file }
  }

  // prints posting lists
  def printPostingList(m: (String, Set[File])): Unit = {
    println(m._1 + " => " + m._2.mkString(", "))
  }

  def getNot(collection: Set[File], postingList: Set[File]): Set[File] = {
    collection &~ postingList
  }

  def getAnd(list1: Set[File], list2: Set[File]): Set[File] = {
    list1 & list2
  }

  def getOr(list1: Set[File], list2: Set[File]): Set[File] = {
    list1 | list2
  }

  def main(args: Array[String]): Unit = {
    var collection1 = getCollection("ex1.1")
    var postingLists1 = scala.collection.mutable.Map[String, Set[File]]()
    collection1.foreach(buildPostingList(_, postingLists1))
    println("The inverted index for ex 1.1:")
    postingLists1.foreach(printPostingList)
    println()

    var collection2 = getCollection("ex1.2")
    var postingLists2 = scala.collection.mutable.Map[String, Set[File]]()
    collection2.foreach(buildPostingList(_, postingLists2))
    println("The inverted index for ex 1.2:")
    postingLists2.foreach(printPostingList)
    println()

    println("ex 1.3")
    println("schizophrenia AND drug")
    println(getAnd(postingLists2("schizophrenia"), postingLists2("drug")).mkString(", "))
    println("for AND NOT(drug OR approach)")
    println(getAnd(postingLists2("for"), getNot(collection2, getOr(postingLists2("drug"), postingLists2("approach")))).mkString(", "))
  }

}