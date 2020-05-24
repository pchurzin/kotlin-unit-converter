package converter

import java.util.*

fun main() {
    val input = Scanner(System.`in`)
    while (true) {
        print("Enter what you want to convert (or exit): ")
        val query = input.nextLine()
        if ("exit".equals(query, ignoreCase = true)) break
        try {
            val conversion = query.toConversion()
            val sourceQuantity = conversion.quantity
            val targetQuantity = sourceQuantity.convertTo(conversion.targetUnit)
            println("$sourceQuantity is $targetQuantity")
        } catch (e: Exception) {
            println(e.message)
        }
    }
}


