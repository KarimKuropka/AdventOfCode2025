import org.junit.jupiter.api.Test
import java.io.File

class Day7 {

    @Test
    fun tachyonBeamsPart1() {
        val input = File("src/test/resources/2025advent7.txt").readLines()
            .map { it.toMutableList() }

        var tachyonSplitCounter = 0
        input.forEachIndexed { i, line ->
            if (i == 0) return@forEachIndexed
            for (j in line.indices) {
                when {
                    // checks if above is the start and lets the beam emit
                    input[i-1][j] == 'S' && line[j] == '.' -> line[j] = '|'
                    // beam just continues
                    input[i-1][j] == '|' && line[j] == '.' -> line[j] = '|'
                    // beam is split
                    input[i-1][j] == '|' && line[j] == '^' -> {
                        tachyonSplitCounter++
                        line[j-1] = '|'
                        line[j+1] = '|'
                    }
                }

            }
        }
        println(tachyonSplitCounter)
    }

    @Test
    fun tachyonBeamsPart2() {
        val input = File("src/test/resources/2025advent7.txt").readLines()
            .map { it.toMutableList() }
        // val input = File("src/test/resources/2025advent7test.txt").readLines()
        //    .map { it.toMutableList() }

        val pathCount = List(input.size) {MutableList(input[0].size) {0L} }

        var tachyonSplitCounter = 0
        input.forEachIndexed { i, line ->
            if (i == 0) return@forEachIndexed
            for (j in line.indices) {
                when {
                    // checks if above is the start and lets the beam emit
                    input[i-1][j] == 'S' && line[j] == '.' -> {
                        line[j] = '|'
                        pathCount[i][j]++
                    }
                    // beam just continues
                    input[i-1][j] == '|' && (line[j] == '.' || line[j] == '|') -> {
                        line[j] = '|'
                        pathCount[i][j] += pathCount[i-1][j]
                    }
                    // beam is split
                    input[i-1][j] == '|' && line[j] == '^' -> {
                        tachyonSplitCounter++
                        line[j-1] = '|'
                        line[j+1] = '|'
                        pathCount[i][j-1] += pathCount[i-1][j]
                        pathCount[i][j+1] += pathCount[i-1][j]
                    }
                }

            }
        }
        println(tachyonSplitCounter)
        println(pathCount.joinToString("\n"))
        println(pathCount.last().sumOf { it })
    }
}