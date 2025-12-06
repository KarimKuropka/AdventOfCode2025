import org.junit.jupiter.api.Test
import java.io.File

class Day5 {

    @Test
    fun findFreshIngredientsPart1() {
        val input = File("src/test/resources/2025advent5.txt").readText().split("\n\n")
        //println(input[0])
        //println(input[1])

        val ranges = input[0].split('\n')
            .map { val rangeList = it.split('-')
                .map { number -> number.toLong()}
                rangeList[0]..rangeList[1]
            }
        val ingredientIDs = input[1].split('\n').map { it.toLong() }

        var idCounter = 0
        for (id in ingredientIDs) {
            for (range in ranges)
                if (id in range) {
                    idCounter++
                    break
                }
        }
        println(idCounter)
    }

    @Test
    fun findFreshIngredientsPart2() {
        val input = File("src/test/resources/2025advent5.txt").readText().split("\n\n")

        val ranges = input[0].split('\n')
            .map {
                it.split('-')
                    .map { number -> number.toLong() }
            }.sortedBy { it[0] }.toMutableList()

        // println(ranges.joinToString("\n"))

        fun flattenRanges(inputRanges: List<List<Long>>): List<List<Long>> {
            val flattedList = emptyList<List<Long>>().toMutableList()

            // iterates over all ranges, consolidates overlapping ranges and adds them to flatted list
            // a while loop is needed to manually manipulate the iterator
            var i = 0
            Loop@ while (i < inputRanges.size) {
                var canBeMadeShorter = true
                val recentRange = listOf(inputRanges[i][0], inputRanges[i][1]).toMutableList()

                while(canBeMadeShorter) {
                    when {
                        // making the loop boundary-secure, pending range is added to flatted list
                        i+1 >= inputRanges.size -> {
                            flattedList.add(recentRange)
                            break@Loop
                        }
                        // if the upper range border is bigger than lower border and smaller than upper border of the next range
                        // recentRange's upper border is replaced with the upper border of the next range
                        recentRange[1] >= inputRanges[i+1][0] - 1 && recentRange[1] < inputRanges[i+1][1] -> {
                            recentRange[1] = inputRanges[i+1][1]
                            i++
                        }
                        // upper border of recent range is even bigger the upper border of next range, so nothing happens
                        recentRange[1] >= inputRanges[i+1][0] - 1 && recentRange[1] >= inputRanges[i+1][1] -> i++
                        // upper border is smaller than lower range and leaves at least one number uncovered
                        // while-loop is broken by change of boolean state and recent range is added to flatted list
                        recentRange[1] < inputRanges[i+1][0] - 1 -> {
                            canBeMadeShorter = false
                            flattedList.add(recentRange)
                        }
                    }
                }
                // we need to manually increment the iterator
                i++
            }
            return flattedList.toList()
        }

        // println(flattenRanges(ranges).joinToString("\n"))

        // calculates the size of the range and adding 1 to each, since both borders are included
        val flattedRangesSizes = flattenRanges(ranges).map { it[1] - it[0] + 1 }
        // adds everything up and prints it
        println(flattedRangesSizes.sumOf { it })
    }
}
