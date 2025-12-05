import org.junit.jupiter.api.Test
import java.io.File

class Day4 {

    @Test
    fun accessiblePaperRollsPart1() {
        val input = File("src/test/resources/2025advent4.txt").readLines()

        // sets a frame of "empty spaces" around the input
        val lineLength = input[0].length
        val emptyLine = ".".repeat(lineLength + 2)
        val framedInput = listOf(emptyLine) + input.map { ".$it." } + listOf(emptyLine)

        println(framedInput.joinToString())
        /*
        fun checkAccessibility(list: List<String>, row: Int, col: Int): Boolean {
            var rollCounter = 0
            for (i in -1..1) {
                for (j in -1..1) {
                    if (list[row + i][col + j] == '@') rollCounter++
                    if (rollCounter > 4) return false
                }
            }
            return true
        }
        */

        // checks the surroundings of each point
        fun checkAccessibility(list: List<String>, row: Int, col: Int): Boolean {
            val rollCounter = (-1..1).sumOf { i ->
                (-1..1).count { j ->
                    list[row + i][col + j] == '@'
                }
            }
            // they may be max 3 paper rolls around, so counting in the paper roll which is getting checked
            // the rollCounter must be smaller than or equal 4
            return rollCounter <= 4
        }

        val accessiblePaperRolls = (1..input.size).sumOf { i ->
            (1..lineLength).count { j ->
                // performs check for paper rolls or returns false
                if (framedInput[i][j] == '@') checkAccessibility(framedInput, i, j )
                else false
            }
        }
        println(accessiblePaperRolls)
    }


    @Test
    fun accessiblePaperRollsPart2() {
        val input = File("src/test/resources/2025advent4.txt").readLines()

        // sets a frame of "empty spaces" around the input and
        // maps every char in the input to a boolean. It will
        // be used later to remove the paper rolls and get
        // a new diagramm where there are still paper rolls
        val lineLength = input[0].length
        val emptyLine = ".".repeat(lineLength + 2).toMutableList()
        var framedInput = (listOf(emptyLine) + input.map { ".$it.".toMutableList() } + listOf(emptyLine))
            .map { it.map { char -> char to false}.toMutableList() }

        // checks the surroundings of each point
        fun checkAccessibility(list: List<MutableList<Pair<Char, Boolean>>>, row: Int, col: Int): Boolean {
            val rollCounter = (-1..1).sumOf { i ->
                (-1..1).count { j ->
                    list[row + i][col + j].first == '@'
                }
            }
            // they may be max 3 paper rolls around, so counting in the paper roll which is getting checked
            // the rollCounter must be smaller than or equal 4
            return rollCounter <= 4
        }

        // set up a counter for adding up the wanted result
        var accessiblePaperRolls = 0
        var accessiblePaperRollsPerIteration = -1

        //repeat iteration until no paper rolls can be removed anymore
        while (accessiblePaperRollsPerIteration != 0) {
            // adds up all the accessible paper rolls like part 1, but...
            accessiblePaperRollsPerIteration = (1..input.size).sumOf { i ->
                (1..lineLength).count { j ->
                    // ...if the paper roll is accessible it is saved into the Boolean of the pair
                    if (framedInput[i][j].first == '@') {
                        val check = checkAccessibility(framedInput, i, j)
                        framedInput[i][j] = framedInput[i][j].first to check
                        check
                    } else {
                        false
                    }
                }
            }

            // the input list for counting all the accessible paper rolls is mutated
            // so that all the found accessible rolls are removed and the forklift can
            // approach the next rolls
            framedInput = framedInput.map { it.map { pair ->
                if (pair.second) {
                    '.' to false
                } else pair
            }.toMutableList() }
            accessiblePaperRolls += accessiblePaperRollsPerIteration
        }
        println(accessiblePaperRolls)
    }

}