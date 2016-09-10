import java.util.Scanner
import java.io.File
import scala.collection.immutable.ListMap
import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer
import scala.math.{max,min}
import scala.io.Source
import scala.language.postfixOps


// collection of documents
class Collection(var directory: String = "") {
  var collection = List[File]()
  loadCollection(directory)

  // returns list of files in collection
  def loadCollection(dir: String): Unit = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      collection = d.listFiles.filter(_.isFile).toList
    }
  }
}

// tokenize file
class Tokenizer {
  var totalTokens = 0
  // returns list of tokens in a string
  def tokenize(str: String): List[String] = {
    val res = str.split("[\\s\\*\\?!.,;:=\\-\"\\(\\)]").map(token => token.toLowerCase).toList
    totalTokens += res.length
    res
  }
}

// stop words
class StopWords {
  val stopWords = Set[String]("at", "of", "to", "as", "is", "in" , "at", "it", "and", "this", "as", "are", "for", "by",
    "but", "do", "would", "will", "if", "such", "may", "an", "a", "there", "on", "or", "at", "these", "from", "so",
    "has", "should", "could", "than", "then", "was", "does", "'", "", "the", "be", "that", "can", "have", "must")
  def contains(word: String): Boolean = {
    stopWords.contains(word)
  }
}

// normalize tokens
class Normalizer {
  def normalize(word: String): String = {
    var res = word.stripSuffix("\'s")
    res = res.stripSuffix("\'re")
    res = res.stripSuffix("\'")
    res = res.stripPrefix("\'")
    res
  }
}

class AbstractStemmer {
  def stem(word: String): String = {
    word
  }
}

class Stemmer extends AbstractStemmer {
  //a very simple suffix-stripping algorithm
  override def stem(word: String): String = {
    var res = word
    if (res.endsWith("sses")) {
      res = res.dropRight(4) + "ss"
    } else if (res.endsWith("ies")) {
      res = res.dropRight(3) + "i"
    } else if (res.endsWith("ss")) {
      ; //nothing
    } else if (res.endsWith("s")) {
      res = res.dropRight(1)
    }
    if ((res.length > 6) && (res.endsWith("ement")))
      res = res.dropRight(5)
    if (res.endsWith("ing"))
      res = res.dropRight(3)
    if (res.endsWith("ed"))
      res = res.dropRight(2)
    if (res.endsWith("ly"))
      res = res.dropRight(2)
    if ((res.length > 5) && (res.endsWith("able")))
      res = res.dropRight(4)
    res
  }
}

//no any stemming
class NoStemmer extends AbstractStemmer {
  override def stem(word: String): String = {
    word
  }
}

class FileProcessor(tokenizer: Tokenizer, normalizer: Normalizer, sw: StopWords, stemmer: AbstractStemmer) {
  // loads file and outputs the list of terms
  def processFile(file: File): List[String] = {
    var res = ListBuffer[String]()
    for (line <- Source.fromFile(file).getLines()) {
      res ++= tokenizer.tokenize(line).filter(!sw.contains(_)).map(normalizer.normalize(_)).map(stemmer.stem(_))
    }
    res.toList
  }
}

class InvertedIndex(fp: FileProcessor) {

  var index = scala.collection.mutable.Map[String, Set[File]]()

  // builds index entries for file
  def buildIndex(file: File): Unit = {
    fp.processFile(file).foreach( t => if (!index.contains(t)) index += (t -> Set(file)) else index(t) += file )
  }

  // prints posting lists
  def printPostingList(): Unit = {
    index.foreach(p => println(p._1 + " => " + p._2.mkString(", ")))
  }
}

class Statistics {
  var wordFrequency = scala.collection.mutable.Map[String, Int]()

  // add words from list to frequency
  def addWordFrequency(words: List[String]): Unit = {
    words.foreach(w => if (!wordFrequency.contains(w)) wordFrequency += (w -> 1) else wordFrequency(w) += 1 )
  }

  // return total number of words
  def getTotalWords(): Int = {
    var total = 0
    wordFrequency.foreach(total += _._2)
    total
  }

  def getTermNumber(): Int = {
    wordFrequency.size
  }

  def printFrequency(): Unit = {
    println( ListMap(wordFrequency.toSeq.sortWith(_._2 > _._2):_*) )
  }
}

class KGramIndex(fp: FileProcessor) {
  val k: Int = 2 // k for k-gram
  var termIndex = scala.collection.mutable.Map[String, Set[File]]() // internal index for (term -> files)
  var kgramIndex = scala.collection.mutable.Map[String, Set[String]]() // index for (k-gram -> terms)

  // split word into k-grams
  def splitWord(word: String): List[String] = {
    val str = ("$" + word + "$")
    def split(str: String): List[String] = {
      if (str.length <= k) {
        List[String](str)
      } else {
        str.substring(0, k) :: split(str.substring(1))
      }
    }
    split(str)
  }

  // builds index entries for file
  def buildIndex(file: File): Unit = {
    fp.processFile(file).foreach{ term =>
      if (!termIndex.contains(term)) termIndex += (term -> Set(file)) else termIndex(term) += file
      splitWord(term).foreach(kgram => if (!kgramIndex.contains(kgram)) kgramIndex += (kgram -> Set(term)) else kgramIndex(kgram) += term)
    }
  }

  // prints posting lists
  def printPostingList(): Unit = {
    kgramIndex.foreach(p => println(p._1 + " => " + p._2.mkString(", ")))
    termIndex.foreach(p => println(p._1 + " => " + p._2.mkString(", ")))
  }

  //
  def searchJac(query: String): Unit = {
    var termCandidates = Set[String]()
    splitWord(query).foreach( kgram => termCandidates ++= kgramIndex(kgram))
    printResult(termCandidates.filter(getJaccardCoefficient(_, query) > 0.4))
  }

  def getJaccardCoefficient(a: String, b: String): Float = {
    var x = splitWord(a).toSet
    var y = splitWord(b).toSet
    (x & y).size.toFloat / (x | y).size
  }

  def searchLev(query: String): Unit = {
    var termCandidates = Set[String]()
    //splitWord(query).foreach( kgram => termCandidates ++= kgramIndex(kgram))
    termCandidates = termIndex.keys.toSet
    printResult(termCandidates.filter(editDist(_, query) < query.length / 2))
  }

  // Levenshtein distance
  def editDist[A](a: Iterable[A], b: Iterable[A]) =
    ((0 to b.size).toList /: a)((prev, x) =>
      (prev zip prev.tail zip b).scanLeft(prev.head + 1) {
        case (h, ((d, v), y)) => min(min(h + 1, v + 1), d + (if (x == y) 0 else 1))
      }) last

  def printResult(result: Set[String]): Unit = {
    result.foreach(key => println(key + " -> " + termIndex(key)))
  }

}

object Main {

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
    var collection = new Collection("cmi")
    //var collection = new Collection("collection")
    val fp = new FileProcessor(new Tokenizer(), new Normalizer(), new StopWords(), new Stemmer())
//    val fp = new FileProcessor(new Tokenizer(), new Normalizer(), new StopWords(), new NoStemmer())
    //var index = new InvertedIndex(fp)
    var index = new KGramIndex(fp)
    collection.collection.foreach(index.buildIndex(_))
    //index.printPostingList

    //val query = new Stemmer().stem("machine")
    val query = "artificial"
    index.searchJac(query)
    index.searchLev(query)

    var stat = new Statistics()
    collection.collection.foreach(file => stat.addWordFrequency(fp.processFile(file)))

    //stat.printFrequency()
    //println("Total tokens processed: " + fp.tokenizer.totalTokens)
    println("Total terms: " + stat.getTermNumber)

    println(index.kgramIndex.size)
  }

}