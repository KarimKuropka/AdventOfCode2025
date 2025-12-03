import org.junit.jupiter.api.Test
import java.io.File

class Day2 {

    @Test
    fun sortOutIDsPart1() {
        val input = File("src/test/resources/2025advent2.txt").readLines()[0].split(",")
            .map { it.split("-") }

        var addedWrongIDs = 0L
        Loop@ for (pair in input) {
            val start = pair[0]
            val end = pair[1]

            // numbers of uneven length per definition can't be wrong, so no need to check the range
            if (start.length % 2 != 0 && end.length == start.length) {
                println("skipping a range <3")
                continue@Loop
            }

            for (num in start.toLong()..end.toLong()) {
                // same as above, just skip any uneven-length numbers
                if ( num.toString().length % 2 != 0) continue

                val numString = num.toString()
                val firstHalf = numString.substring(0, numString.length / 2)
                val secondHalf = numString.substring(numString.length / 2)

                if (firstHalf == secondHalf) {
                    addedWrongIDs += num
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