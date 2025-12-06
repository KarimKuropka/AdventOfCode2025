import org.junit.jupiter.api.Test
import java.io.File

class Day6 {

    @Test
    fun cephalopodMathPart1() {
        val regex = """ +""".toRegex()
        val input = File("src/test/resources/2025advent6.txt").readLines()
            .map { it.split(regex) }

        println(input.joinToString("\n"))
        val numbersLists = input.dropLast(1)
            .map { line -> line
                .map { num -> num.toInt() } }

        val result = (numbersLists[0].indices).sumOf { i ->
            print("index: $i    ")
            when (input[4][i]) {
                "+" -> {
                    val sum = (0..3).sumOf { j -> numbersLists[j][i] }.toLong()
                    println("summe: $sum")
                    sum
                }
                "*" -> {
                    var product = 1L
                    for (k in 0..3) {
                        product *= numbersLists[k][i]
                    }
                    println("produkt: $product")
                    product
                }
                else -> 0L
            }
        }
        println(result)
    }

    @Test
    fun cephalopodMathPart2() {
        val regex = """ +""".toRegex()
        val input = File("src/test/resources/2025advent6.txt").readLines()
        val operatorList = input[4].split(regex)

        val cephalopodNumbers = emptyList<List<String>>().toMutableList()
        val operationNumbers = emptyList<String>().toMutableList()

        for (i in input[0].indices) {
            val colNum = buildString {
                for (j in 0..3) {
                    append(input[j][i])
                }
            }
//            println("index: $i, colNum: $colNum")
            when {
                !regex.matches(colNum) && i == input[0].lastIndex -> {
                    operationNumbers.add(colNum.trim())
                    cephalopodNumbers += operationNumbers.toList()
                    operationNumbers.clear()
//                    println("finished")
                }
                !regex.matches(colNum) -> {
                    operationNumbers.add(colNum.trim())
//                    println("added colNum")
//                    println("operationNum:" + operationNumbers.joinToString())
//                    println("cephNum:" + cephalopodNumbers.joinToString())
                }
                else -> {
                    cephalopodNumbers += operationNumbers.toList()
                    operationNumbers.clear()
//                    println("added operationsNum to cephNum")
//                    println("operationNum:" + operationNumbers.joinToString())
//                    println("cephNum:" + cephalopodNumbers.joinToString())
                }
            }
        }

//        println(cephalopodNumbers.joinToString("\n"))

        var result = 0L
        cephalopodNumbers.forEachIndexed { i, numbersBlock ->
            when (operatorList[i]) {
                "+" -> {
                    val sum = numbersBlock.sumOf { it.toLong() }
                    result += sum
                }
                "*" -> {
                    var product = 1L
                    for (k in numbersBlock.indices) {
                        product *= numbersBlock[k].toLong()
                    }
                    result += product
                }
            }
        }
        println(result)

    }

}