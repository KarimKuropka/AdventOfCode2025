import org.junit.jupiter.api.Test
import java.io.File
import kotlin.math.pow
import kotlin.math.sqrt


class Day8 {

    data class Euclidean (val a: List<Double>, val b: List<Double>, val distance: Double)
    @Test
    fun playgroundPart1() {

        val input = File("src/test/resources/2025advent8.txt").readLines()
            .map { it.split(',').map { num -> num.toDouble() } }

        // calculates euclidean distance
        fun euclideanDistance(pointA: List<Double>, pointB: List<Double>): Double {
            return sqrt(pointA.zip(pointB).fold(0.0) { acc, (a, b) ->
                acc + (a - b).pow(2)
            })
        }

        // fills a list with 1-Element-Sets, consisting of each single point in the input
        val circuits = emptyList<MutableSet<List<Double>>>().toMutableList()
        for (point in input) {
            circuits += mutableSetOf(point)
        }

        // finds out the distances of all points to each other and adds them to the list
        var shortestConnections = emptyList<Euclidean>().toMutableList()
        for ((index, pointA) in input.withIndex()) {
            // distance with itself is always 0, so this case must be skipped
            val inputExceptRecent = input.filterIndexed { i, _ ->
                i > index
            }
            for (pointB in inputExceptRecent) {
                val distance = euclideanDistance(pointA, pointB)
                shortestConnections += Euclidean(pointA, pointB, distance)
            }
        }
        // sorts the connections by distance and takes the first 1000
        shortestConnections = shortestConnections.sortedBy { it.distance }.take(1000).toMutableList()
        println(shortestConnections.joinToString("\n"))
        println()

        // merging the sets in order of smallest distance
        for ((point, nearest, distance) in shortestConnections) {
            val indexOfPointSet = circuits.indexOf(circuits.find { it.contains(point) })
            val indexOfNearestSet = circuits.indexOf(circuits.find { it.contains(nearest) })

            println("---------- pointA: $point, pointB: $nearest, distance: $distance ------------")
            // points are in different sets, sets are merged into the one set, removes the other
            if (indexOfPointSet != indexOfNearestSet) {
                        circuits[indexOfPointSet] += circuits[indexOfNearestSet]
                        circuits -= circuits[indexOfNearestSet]
                        // println("two sets merged")
                        // println("All sets are: ${circuits.joinToString("\nnext Set: ")}\nEND OF SETS")
            // unnecessary, only for printlogging
            } else {
                println("both points in the same set")
            }
        }

        println("final List of Sets:")
        println(circuits.joinToString("\n"))

        // sorts the list of circuits by circuit.size
        val sortedCircuits = circuits.sortedByDescending { it.size }
        // takes the biggest three sets and multiplies their size
        val result = (0..2).fold(1L) { acc, index ->
            acc * sortedCircuits[index].size
        }
        println(result)
    }

    @Test
    fun playgroundPart2() {
        val input = File("src/test/resources/2025advent8.txt").readLines()
            .map { it.split(',').map { num -> num.toDouble() } }

        // calculates euclidean distance
        fun euclideanDistance(pointA: List<Double>, pointB: List<Double>): Double {
            return sqrt(pointA.zip(pointB).fold(0.0) { acc, (a, b) ->
                acc + (a - b).pow(2)
            })
        }

        // as part 1
        val circuits = emptyList<MutableSet<List<Double>>>().toMutableList()
        for (point in input) {
            circuits += mutableSetOf(point)
        }

        // as part 1
        var shortestConnections = emptyList<Euclidean>().toMutableList()
        for ((index, pointA) in input.withIndex()) {
            val inputExceptRecent = input.filterIndexed { i, _ ->
                i > index
            }
            for (pointB in inputExceptRecent) {
                val distance = euclideanDistance(pointA, pointB)
                shortestConnections += Euclidean(pointA, pointB, distance)
            }
        }

        // sorts the connections by distance and this time takes all of them
        shortestConnections = shortestConnections.sortedBy { it.distance }.toMutableList()
        println(shortestConnections.joinToString("\n"))
        println()

        // tracks the product of x-coordinates for the last two points
        var lastElementsXProduct = 0
        for ((point, nearest, distance) in shortestConnections) {
            val indexOfPointSet = circuits.indexOf(circuits.find { it.contains(point) })
            val indexOfNearestSet = circuits.indexOf(circuits.find { it.contains(nearest) })

            println("---------- pointA: $point, pointB: $nearest, distance: $distance ------------")
            if (indexOfPointSet != indexOfNearestSet) {
                circuits[indexOfPointSet] += circuits[indexOfNearestSet]
                circuits -= circuits[indexOfNearestSet]
            }

            println("number of circuits: ${circuits.size}")

            // updates the above product tracker
            lastElementsXProduct = (point[0] * nearest[0]).toInt()

            // as soon as circuit size reaches 1 (means all junctions are merged to one circuit)
            // it stops the loop.
            if (circuits.size == 1) break
        }

        println(lastElementsXProduct)
    }
}

