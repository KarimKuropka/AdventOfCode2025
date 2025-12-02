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

            recentState = when (direction) {
                'R' -> {
                    counter += amount / 100
                    if (recentState + (amount % 100) >= 100) counter++
                    (recentState + amount) % 100
                }
                else -> {
                    counter += amount / 100
                    if (recentState != 0 && (recentState - (amount % 100) <= 0)) counter++
                    (recentState - amount).mod(100)
                }
            }
        }
        println(counter)
    }
}