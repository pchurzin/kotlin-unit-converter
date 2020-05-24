package converter

import converter.Unit.*

class Quantity(val number: Double, val unit: Unit) {

    init {
        number.validate(unit)
    }

    override fun toString(): String {
        return "$number ${number.unitWord(unit)}"
    }

    private fun Double.unitWord(unit: Unit): String {
        return if (this == 1.0) unit.singularForm else unit.pluralForm
    }

    private fun Double.validate(unit: Unit) {
        when (unit) {
            METER,
            KILOMETER,
            CENTIMETER,
            MILLIMETER,
            MILE,
            YARD,
            FOOT,
            INCH -> if (this < 0.0) throw IllegalArgumentException("Length shouldn't be negative")

            GRAM,
            KILOGRAM,
            MILLIGRAM,
            POUND,
            OUNCE -> if (this < 0.0) throw IllegalArgumentException("Weight shouldn't be negative")

            else -> return
        }

    }

}