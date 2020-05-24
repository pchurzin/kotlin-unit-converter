package converter

data class Conversion(val quantity: Quantity, val targetUnit: Unit)

fun String.toConversion(): Conversion {
    val matchResult = queryPattern.matchEntire(this.toLowerCase()) ?: throw IllegalArgumentException("Parse error")
    val value = matchResult.groups["number"]!!.value.toDouble()
    val sourceUnit = matchResult.groups["su"]!!.value.toUnit()
    val targetUnit = matchResult.groups["tu"]!!.value.toUnit()
    return Conversion(Quantity(value, sourceUnit), targetUnit)
}

private val queryPattern =
    """\s*(?<number>[+-]?\d+(\.\d+)?)(?:\s+degrees?)?\s+(?<su>\w+)\s+\w+(?:\s+degrees?)?\s+(?<tu>\w+)\s*""".toRegex()