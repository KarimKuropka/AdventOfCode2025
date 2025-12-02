import org.junit.jupiter.api.Test
import java.io.File



class AdventOfCode2025 {

    // ---------- Day 1 ---------- //
    @Test
    fun findEntranceCodePart1() {
        val input = File("src/test/resources/2025advent1.txt").readLines()

        fun followInstruction(start: Int, instruction: String): Int {
            val direction = instruction[0]
            val amount = instruction.substring(1).toInt()

            val newState = when (direction) {
                'R' -> (start + amount) % 100
                else -> (start - amount).mod(100)
            }
            return newState
        }

        var recentState = 50
        var counter = 0
        for (line in input) {
            recentState = followInstruction(recentState, line)
            if (recentState == 0) counter++
        }
        println(counter)
    }

    @Test
    fun findEntranceCodePart2() {
        val input = File("src/test/resources/2025advent1.txt").readLines()
        var recentState = 50
        var counter = 0
        for (line in input) {
            val direction = line[0]
            val amount = line.substring(1).toInt()
            counter += amount / 100

            recentState = when (direction) {
                'R' -> {
                    if (recentState + (amount % 100) >= 100) counter++
                    (recentState + amount) % 100
                }
                else -> {
                    if (recentState != 0 && (recentState - (amount % 100) <= 0)) counter++
                    (recentState - amount).mod(100)
                }
            }
        }
        println(counter)
    }

    // ---------- Day 2 ---------- //
    @Test
    fun sortOutIDsPart1() {
        val input = File("src/test/resources/2025advent2.txt").readLines()[0].split(",")
            .map { it.split("-") }
        println(input.joinToString())

        var addedWrongIDs = 0L
        Loop@ for (pair in input) {
            val start = pair[0]
            val end = pair[1]

            // numbers of uneven length per definition can't be wrong, so no need to check the range
            if (start.length % 2 != 0 && end.length == start.length) continue@Loop

            for (num in start.toLong()..end.toLong()) {
                // same as above, just skip any uneven-length numbers
                if ( num.toString().length % 2 != 0) continue

                val numString = num.toString()
                val firstHalf = numString.substring(0, numString.length / 2)
                val secondHalf = numString.substring(numString.length / 2)

                if (firstHalf == secondHalf) {
                    addedWrongIDs += num
                    println(num)
                }
            }
        }
        println(addedWrongIDs)
    }

    @Test
    fun sortOutIDsPart2() {
        val input = File("src/test/resources/2025advent2.txt").readLines()[0].split(",")
            .map { it.split("-") }
        println(input.joinToString())

        val regex = """(\d+)\1+""".toRegex()
        var addedWrongIDs = 0L

        Loop@ for (pair in input) {
            val start = pair[0]
            val end = pair[1]

            for (num in start.toLong()..end.toLong()) {
                if (regex.matches(num.toString())) {
                    addedWrongIDs += num
                    println(num)
                }
            }
        }
        println(addedWrongIDs)
    }
}