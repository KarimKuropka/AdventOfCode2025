import org.junit.jupiter.api.Test
import java.io.File

class Day3 {

    @Test
    fun findHighestJoltagesPart1() {
        val input = File("src/test/resources/2025advent3.txt").readLines()
        var addedJoltages = 0

        for (line in input) {
            val biggestFirstDigit = line.dropLast(1).maxOf { it.digitToInt() }
            val biggestNumIndex = line.indexOf(biggestFirstDigit.toString())
            val biggestSecondDigit = line.substring(biggestNumIndex + 1).maxOf { it.digitToInt() }

            println(">>>> line end: $biggestFirstDigit$biggestSecondDigit")
            addedJoltages += ("$biggestFirstDigit$biggestSecondDigit").toInt()
        }
        println(addedJoltages)
    }

    @Test
    fun findHighestJoltagesPart2() {
        val input = File("src/test/resources/2025advent3.txt").readLines()
        var addedJoltages = 0L

        for (line in input) {
            var changedLine = line
            var lineJoltage = ""
            var currentBiggestDigitIndex = -1

            // needs to drop the last i digits, so the number always will be long enough
            for (i in 11 downTo 0) {
                // starts the next biggest digit search at index+1 of the biggest already found digit
                changedLine = changedLine.substring(currentBiggestDigitIndex + 1)
                val biggestDigit = changedLine.dropLast(i).maxOf { it.digitToInt() }
                // gets the new index to further shorten the substring
                currentBiggestDigitIndex = changedLine.indexOf(biggestDigit.toString())

                // concats the newly found digit to the line joltage string
                lineJoltage += biggestDigit
            }
            println(lineJoltage)
            // adds the line joltage string to the total
            addedJoltages += lineJoltage.toLong()
        }
        println(addedJoltages)
    }
}







// finding biggest number manually

/*
var biggestFirstDigit = '0'
var biggestNumIndex = 0
var biggestSecondDigit = '0'

for (digit in line.dropLast(1)) {
    println("Digit: $digit")
    println("DigitToInt: ${digit.digitToInt()}")
    println("BiggestDigit: $biggestFirstDigit")
    if (digit.digitToInt() > biggestFirstDigit.digitToInt()) {
        biggestFirstDigit = digit
        biggestNumIndex = line.indexOf(digit)
        if (biggestFirstDigit == '9') {
            println("first break!")
            break
        }
    }
}

for (digit in line.substring(biggestNumIndex + 1)) {
    if (digit.digitToInt() > biggestSecondDigit.digitToInt()) biggestSecondDigit = digit
    if (biggestSecondDigit == '9') {
        println("second break!")
        break
    }
} // result: 17263
 */
