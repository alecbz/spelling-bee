import java.io.File

data class Entry(val word: String) {
    val letters = word.toSet()
}

fun loadWords() = File("words").readLines().map { Entry(it.trim()) }.filter { it.word.length >= 4 }

val words = loadWords()
val panagrams = words.filter { it.letters.size == 7 }

data class Game(val central: Char, val others: List<Char>, val matching: List<String>) {
    init {
        if (others.size != 6) {
            throw Exception("Expecting 6 other characters; got $others")
        }
        if (others.toSet().size != 6) {
            throw Exception("Duplicate characters: $others")
        }
    }

    companion object {
        fun generate(): Game {
            val panagram = panagrams.random()
            val central = panagram.letters.random()

            val matching = words.filter {
                panagram.letters.containsAll(it.letters) && it.letters.contains(central)
            }
            if (!matching.contains(panagram)) {
                throw Exception("Panagram ($panagram) not found in matching words")
            }

            val allPanagrams = matching.filter { it.letters.size == 7 }
            val matchingList = allPanagrams.sortedBy {it.word} + (matching - allPanagrams).sortedBy{it.word}

            return Game(central, (panagram.letters - setOf(central)).toList(), matchingList.map { it.word })
        }
    }
}

fun main() {
    val game = Game.generate()
    println(game)
}
