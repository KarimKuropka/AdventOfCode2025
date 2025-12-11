import org.junit.jupiter.api.Test
import java.io.File
import kotlin.collections.map
import kotlin.math.abs

class Day9 {

    @Test
    fun movieTheaterPart1() {
        val input = File("src/test/resources/2025advent9.txt").readLines()
            .map { it.split(',')
                .map {num -> num.toLong()}
            }

        val area = input.maxOf { redTile ->
            input.maxOf { otherRedTile ->
                (abs(redTile[0] - otherRedTile[0]) + 1 ) * (abs(redTile[1] - otherRedTile[1]) + 1 )
            }
        }
        println(area)
    }

    @Test
    fun movieTheaterPart2() {
        val input = File("src/test/resources/2025advent9test.txt").readLines()
            .map { it.split(',')
                .map {num -> num.toInt()}
            }

        // calculate the area of a square from its corner points
        fun areaSquare(redTile: List<Int>, otherRedTile: List<Int>): Long {
            return (abs(redTile[0] - otherRedTile[0]) + 1 ).toLong() * (abs(redTile[1] - otherRedTile[1]) + 1 )
        }

        val area = input.maxOf { redTile ->
            input.maxOf { otherRedTile ->
//                println("----- new set of points -----")
                val minX = minOf(redTile[0], otherRedTile[0])
                val maxX = maxOf(redTile[0], otherRedTile[0])
                val minY = minOf(redTile[1], otherRedTile[1])
                val maxY = maxOf(redTile[1], otherRedTile[1])
                val rangeX = minX+1 until maxX
                val rangeY = minY+1 until maxY

//                println("red: $redTile")
//                println("other: $otherRedTile")

                val isSomePointInRectangle = input.any { (x, y) ->
                    x in rangeX && y in rangeY
                }
//                println(isSomePointInRectangle)

                val isPointUpperLeft = input.any { (x, y) ->
                    x == minX && y in minY + 1 until maxY
                }
                val isPointLowerLeft = input.any { (x, y) ->
                    x == maxX && y in minY + 1 until maxY
                }
                val isPointUpperRight = input.any { (x, y) ->
                    y == minY && x in minX + 1 until maxX
                }
                val isPointLowerRight = input.any { (x, y) ->
                    y == maxY && x in minX + 1 until maxX
                }

                val anyPointOnEdges = isPointUpperLeft || isPointLowerLeft || isPointUpperRight || isPointLowerRight
//                println(areaSquare(redTile, otherRedTile))

                if (!anyPointOnEdges && !isSomePointInRectangle) areaSquare(redTile, otherRedTile) else 0L
            }
        }
        println(area)
    }



    /*@Test
    fun movieTheaterPart2() {
        val input = File("src/test/resources/2025advent9.txt").readLines()
            .map { it.split(',')
                .map {num -> num.toInt()}
            }.sortedBy { it[1] }

        // calculate the area of a square from its corner points
        fun areaSquare(redTile: List<Int>, otherRedTile: List<Int>): Long {
            return (abs(redTile[0] - otherRedTile[0]) + 1 ).toLong() * (abs(redTile[1] - otherRedTile[1]) + 1 )
        }

        val lineFrames = emptyList<IntRange>().toMutableList()

        // sorts all the x values per y into a map <y, x-range>
        val redTileRangesPerRow = input
            .groupBy { it[1] }
            .mapValues { (_, coord) ->
                val listOfX = coord.map { it[0] }
                listOfX.min()..listOfX.max()
        }
        println(redTileRangesPerRow)

        val listOfXEdges = redTileRangesPerRow.map {(_, range) ->
            listOf(range.first, range.last)
        }.flatten().toSet().sorted()

        println(listOfXEdges)*/

//        val range = redTileRangesPerRow.getValue(1595)
//        print(range.first)

/*
        // fills the spaces between all red tiles row-wise
        for (row in redOrGreenTile) {
            // if the row has any red tile, then find the last tile and mark all tiles in between as true
            if (row.any { it }) {
                val indexFirstRedTile = row.indexOf(true)
                val indexLastRedTile = row.lastIndexOf(true)
                for (index in indexFirstRedTile..indexLastRedTile) row[index] = true
            }
        }
        // fills the spaces between all red tiles column-wise
        for (col in 0 until redOrGreenTile[0].size) {
            // alle Werte der Spalte holen
            val column = redOrGreenTile.map { it[col] }

            if (column.any { it }) {
                val first = column.indexOf(true)
                val last = column.lastIndexOf(true)
                for (row in first..last) {
                    redOrGreenTile[row][col] = true
                }
            }
        }


        val area = input.maxOf { redTile ->
            input.maxOf { otherRedTile ->
                val redTileX = redTile[0]
                val redTileY = redTile[1]
                val otherRedTileX = otherRedTile[0]
                val otherRedTileY = otherRedTile[1]
                val linesDroppedAtTheEnd = redOrGreenTile.size - 1 - maxOf(redTileY, otherRedTileY)
                val linesDroppedAtTheBeginning = minOf(redTileY, otherRedTileY)
                val columnsDroppedAtTheEnd = redOrGreenTile[0].size -1 - maxOf(redTileX, otherRedTileX)
                val columnsDroppedAtTheBeginning = minOf(redTileX, otherRedTileX)

                println("red: $redTile")
                println("other: $otherRedTile")

                val allRedOrGreen = redOrGreenTile
                    .dropLast(linesDroppedAtTheEnd)
                    .drop(linesDroppedAtTheBeginning)
                    .map { it ->
                        it.dropLast(columnsDroppedAtTheEnd)
                            .drop(columnsDroppedAtTheBeginning)}.all { it -> it.all { it } }

                println(allRedOrGreen)

                if (allRedOrGreen) areaSquare(redTile, otherRedTile) else 0L
            }
        }
        println(area)
    }*/

    // Sadly this approach dies from running out of memory, works fine on the example though :D
    @Test
    fun movieTheaterPart2OutOfMemory() {
        val input = File("src/test/resources/2025advent9test.txt").readLines()
            .map { it.split(',')
                .map {num -> num.toInt()}
            }

        // calculate the area of a square from its corner points
        fun areaSquare(redTile: List<Int>, otherRedTile: List<Int>): Long {
            return (abs(redTile[0] - otherRedTile[0]) + 1 ).toLong() * (abs(redTile[1] - otherRedTile[1]) + 1 )
        }

        fun printDiagramm(redOrGreenTile: List<MutableList<Boolean>>) {
            redOrGreenTile.forEach { it ->
                it.forEach { bool ->
                    if (bool) print("#") else print(".")
                }
                println()
            }
        }

        // we need those to determine the size of a board of the floor
        val maxX = input.maxOf { coord -> coord[0] }
        val maxY = input.maxOf { coord -> coord[1] }

        // sets up a board of the floor (true means it's red or green)
        val redOrGreenTile = List(maxY+1) { MutableList(maxX+1) { false } }

        // enters all red tiles to the board
        for (coord in input) {
            redOrGreenTile[coord[1]][coord[0]] = true
        }

        // fills the spaces between all red tiles row-wise
        for (row in redOrGreenTile) {
            // if the row has any red tile, then find the last tile and mark all tiles in between as true
            if (row.any { it }) {
                val indexFirstRedTile = row.indexOf(true)
                val indexLastRedTile = row.lastIndexOf(true)
                for (index in indexFirstRedTile..indexLastRedTile) row[index] = true
            }
        }

        printDiagramm(redOrGreenTile)

        // fills the spaces between all red tiles column-wise
        for (col in 0 until redOrGreenTile[0].size) {
            // alle Werte der Spalte holen
            val column = redOrGreenTile.map { it[col] }

            if (column.any { it }) {
                val first = column.indexOf(true)
                val last = column.lastIndexOf(true)
                for (row in first..last) {
                    redOrGreenTile[row][col] = true
                }
            }
        }



        val area = input.maxOf { redTile ->
            input.maxOf { otherRedTile ->
                val redTileX = redTile[0]
                val redTileY = redTile[1]
                val otherRedTileX = otherRedTile[0]
                val otherRedTileY = otherRedTile[1]
                val linesDroppedAtTheEnd = redOrGreenTile.size - 1 - maxOf(redTileY, otherRedTileY)
                val linesDroppedAtTheBeginning = minOf(redTileY, otherRedTileY)
                val columnsDroppedAtTheEnd = redOrGreenTile[0].size -1 - maxOf(redTileX, otherRedTileX)
                val columnsDroppedAtTheBeginning = minOf(redTileX, otherRedTileX)

                // lots of printlogging
                println("red: $redTile")
                println("other: $otherRedTile")
                printDiagramm(redOrGreenTile)
                println("dropLast:")
                printDiagramm(redOrGreenTile.dropLast(linesDroppedAtTheEnd))
                println("dropFirst:")
                printDiagramm(redOrGreenTile.dropLast(linesDroppedAtTheEnd).drop(linesDroppedAtTheBeginning))
                println("dropLastCols:")
                printDiagramm(redOrGreenTile.dropLast(linesDroppedAtTheEnd).drop(linesDroppedAtTheBeginning)
                    .map { it ->
                        it.dropLast(columnsDroppedAtTheEnd).toMutableList()})
                println("dropFirstCols:")
                printDiagramm(redOrGreenTile
                    .dropLast(linesDroppedAtTheEnd)
                    .drop(linesDroppedAtTheBeginning)
                    .map { it ->
                        it.dropLast(columnsDroppedAtTheEnd)
                            .drop(columnsDroppedAtTheBeginning).toMutableList()})

                val allRedOrGreen = redOrGreenTile
                    .dropLast(linesDroppedAtTheEnd)
                    .drop(linesDroppedAtTheBeginning)
                    .map { it ->
                        it.dropLast(columnsDroppedAtTheEnd)
                            .drop(columnsDroppedAtTheBeginning)}.all { it -> it.all { it } }

                println(allRedOrGreen)

                if (allRedOrGreen) areaSquare(redTile, otherRedTile) else 0L
            }
        }
        println(area)
    }


}
