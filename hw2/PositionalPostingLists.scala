import java.util.Scanner
import java.io.File
import scala.collection.JavaConversions._

object PositionalPostingLists {

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
  def tokenizeFile(file: File): List[String] = {
    new Scanner(file).map(_.trim).map(token => token.toLowerCase).toList
  }

  // builds posting lists (Term -> Map(File -> List[pos])
  def buildPostingList(file: File, m: scala.collection.mutable.Map[String, scala.collection.mutable.Map[File, Set[Int]]]): Unit = {
    var tokens = tokenizeFile(file)
    List.range(0, tokens.size) foreach { i =>
      if (!m.contains(tokens(i))) {
        m += (tokens(i) -> scala.collection.mutable.Map(file -> Set(i)))
      } else if (!m(tokens(i)).contains(file))
        m(tokens(i)) += (file -> Set(i))
      else m(tokens(i))(file) += i
    }
  }

  // prints posting lists
  def printPostingList(m: (String, scala.collection.mutable.Map[File, Set[Int]])): Unit = {
    println(m._1 + ": " + m._2)
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

  def proximityQuery(w1: String, w2: String, dist: Int, m: scala.collection.mutable.Map[String, scala.collection.mutable.Map[File, Set[Int]]]): Unit = {
    if (m.contains(w1) & m.contains(w2)) {
      val files = m(w1).keySet.intersect(m(w2).keySet)
      var res = Set[(File, (Int, Int))]()
      files.foreach(f => m(w1)(f).foreach(i => m(w2)(f).foreach(j => if (Math.abs(i - j) <= dist) {
        val t = (f, (i, j))
        res += t
      } )))
      println(res)
    }
  }

  def main(args: Array[String]): Unit = {
    var collection1 = getCollection("ex1.1")
    var postingLists1 = scala.collection.mutable.Map[String, scala.collection.mutable.Map[File, Set[Int]]]()
    collection1.foreach(buildPostingList(_, postingLists1))
    println("The inverted index for the collection \"ex 1.1\":")
    postingLists1.foreach(printPostingList)
    println()

    println("Proximity query \"to /1 be\"")
    proximityQuery("to", "be", 1, postingLists1)

  }

}