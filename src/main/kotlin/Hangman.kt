val words = listOf(
    "Abroad",
    "Casual",
    "Dinner",
    "Easily",
    "Fellow",
    "Gather",
    "Happen",
    "Ignore",
    "Jacket",
    "Kitchen",
    "Ladder",
    "Machine",
    "Narrow",
    "Object",
    "Packet",
    "Quaint",
    "Rabbit",
    "Saddle",
    "Tablet",
    "Umbrella",
    "Vacuum",
    "Waffle",
    "Xylophone",
    "Yacht",
    "Zombie",
)
val guesses = mutableListOf<Char>()
const val maxGuesses = 6
var wrongGuesses = 0

fun main(args: Array<String>) {
    setupGame()
}

fun setupGame() {
    val word = words.random()
    println(word)
    printZeroMistakes()

    while (wrongGuesses < maxGuesses) {
        print("Please enter a letter: ")
        val input = readln()
        if (input.isBlank()) continue

        val letter = input.first()


        if (letter in guesses) {
            println("You already guessed that letter")
            continue
        }

        if (letter in word) {
            guesses.add(letter)
            printWord(word, guesses)
            if (word.all { guesses.contains(it) }) {
                println("You won!")
                break
            }
        } else {
            guesses.add(letter)
            printWord(word, guesses)
            wrongGuesses++

            when (wrongGuesses) {
                1 -> print1Mistakes()
                2 -> print2Mistakes()
                3 -> print3Mistakes()
                4 -> print4Mistakes()
                5 -> print5Mistakes()
                6 -> {
                    print6Mistakes()
                    break
                }
            }
        }
    }
}

fun printWord(word: String, guesses: MutableList<Char>) {
    print("Word: ")
    for (letter in word) {
        if (guesses.contains(letter)) {
            print(letter)
        } else {
            print("_")
        }
    }
    println()
}

fun printZeroMistakes() {
    println("  +---+")
    println("  |   |")
    println("      |")
    println("      |")
    println("      |")
    println("      |")
    println("=========")
}

fun print1Mistakes() {
    println("  +---+")
    println("  |   |")
    println("  o   |")
    println("      |")
    println("      |")
    println("      |")
    println("=========")
}

fun print2Mistakes() {
    println("  +----+")
    println("  |    |")
    println("  o    |")
    println("  |    |")
    println("  |    |")
    println("       |")
    println("=========")
}


fun print3Mistakes() {
    println("  +----+")
    println("  |    |")
    println("  o    |")
    println(" /|    |")
    println("  |    |")
    println("       |")
    println("=========")
}

fun print4Mistakes() {
    println("  +----+")
    println("  |    |")
    println("  o    |")
    println(" /|    |")
    println("  |    |")
    println(" /     |")
    println("=========")
}

fun print5Mistakes() {
    println("  +---+")
    println("  |     |")
    println("  o     |")
    println(" /|\\    |")
    println("  |     |")
    println(" /      |")
    println("=========")
}

fun print6Mistakes() {
    println("  +-------+")
    println("  |       |")
    println("  o       |")
    println(" /|\\      |")
    println("  |       |")
    println(" / \\      |")
    println("=========")

    if (wrongGuesses == maxGuesses) {
        println("You lost!")
    }
}