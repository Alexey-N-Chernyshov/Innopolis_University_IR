import com.github.alexey_n_chernyshov.iu.ir.Soundex
import org.scalatest._

/**
  * Created by Yex on 10-Sep-16.
  */
class SoundexTest extends FlatSpec with Matchers {
  "A Soundex" should "map word into one letter + 3 digits form" in {
    val soundex = Soundex
    assert(soundex.soundex("Hermann") == "H655")
    assert(soundex.soundex("Herrmann") == "H655")
    assert(soundex.soundex("Herman") == "H655")
    assert(soundex.soundex("Chernyshov") == "C652")
  }

  "A Soundex" should "pad string with trailing zeroes, if length < 3" in {
    val soundex = Soundex
    assert(soundex.soundex("") == "0000")
    assert(soundex.soundex("H") == "H000")
    assert(soundex.soundex("He") == "H000")
  }
}