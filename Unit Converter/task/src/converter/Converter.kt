package converter

import converter.Unit.*

fun Quantity.convertTo(unit: Unit): Quantity {
    return Converter.convert(this, unit)
}

private object Converter {

    private data class Direction(val sourceUnit: Unit, val targetUnit: Unit)

    private val conversions = mapOf<Direction, (Double) -> Double>(
        Direction(METER, METER) to { x -> x },
        Direction(KILOMETER, METER) to { x -> x * 1000 },
        Direction(CENTIMETER, METER) to { x -> x * 0.01 },
        Direction(MILLIMETER, METER) to { x -> x * 0.001 },
        Direction(MILE, METER) to { x -> x * 1609.35 },
        Direction(YARD, METER) to { x -> x * 0.9144 },
        Direction(FOOT, METER) to { x -> x * 0.3048 },
        Direction(INCH, METER) to { x -> x * 0.0254 },
        Direction(METER, KILOMETER) to { x -> x / 1000 },
        Direction(METER, CENTIMETER) to { x -> x / 0.01 },
        Direction(METER, MILLIMETER) to { x -> x / 0.001 },
        Direction(METER, MILE) to { x -> x / 1609.35 },
        Direction(METER, YARD) to { x -> x / 0.9144 },
        Direction(METER, FOOT) to { x -> x / 0.3048 },
        Direction(METER, INCH) to { x -> x / 0.0254 },

        Direction(GRAM, GRAM) to { x -> x },
        Direction(KILOGRAM, GRAM) to { x -> x * 0.001 },
        Direction(MILLIGRAM, GRAM) to { x -> x * 1000 },
        Direction(POUND, GRAM) to { x -> x * 453.592 },
        Direction(OUNCE, GRAM) to { x -> x * 28.349 },
        Direction(GRAM, KILOGRAM) to { x -> x / 0.001 },
        Direction(GRAM, MILLIGRAM) to { x -> x / 1000 },
        Direction(GRAM, POUND) to { x -> x / 453.592 },
        Direction(GRAM, OUNCE) to { x -> x / 28.349 },

        Direction(KELVIN, KELVIN) to { x -> x },
        Direction(KELVIN, CELSIUS) to { x -> x - 273.15 },
        Direction(KELVIN, FAHRENHEIT) to { x -> x * 9 / 5 - 459.67 },
        Direction(FAHRENHEIT, KELVIN) to { x -> (x + 459.67) * 5 / 9 },
        Direction(CELSIUS, KELVIN) to { x -> x + 273.15 }

    )

    fun convert(quantity: Quantity, targetUnit: Unit): Quantity {

        fun reportImpossibleConversion(): Nothing {
            throw IllegalArgumentException("Conversion from ${quantity.unit.pluralForm} to ${targetUnit.pluralForm} is impossible")
        }

        val onePassConversion =
            conversions.filterKeys { it.sourceUnit == quantity.unit && it.targetUnit == targetUnit }.entries.firstOrNull()
        if (onePassConversion != null) {
            val convertedValue = onePassConversion.value(quantity.number)
            return Quantity(convertedValue, targetUnit)
        }

        val firstConversions = conversions.filterKeys { it.sourceUnit == quantity.unit }
        if (firstConversions.isEmpty()) reportImpossibleConversion()

        val intermediateUnits = firstConversions.keys.map { it.targetUnit }
        val secondConversions =
            conversions.filterKeys { it.targetUnit == targetUnit && it.sourceUnit in intermediateUnits }
        if (secondConversions.isEmpty()) reportImpossibleConversion()

        val firstConversionEntry = firstConversions.entries.first()
        val secondConversionEntry =
            secondConversions.entries.first { it.key.sourceUnit == firstConversionEntry.key.targetUnit }

        val convertedNumber = secondConversionEntry.value(firstConversionEntry.value(quantity.number))
        return Quantity(convertedNumber, targetUnit)
    }

}
