package converter

enum class Unit(
    val additionalForms: Set<String>,
    val singularForm: String,
    val pluralForm: String
) {
    METER(setOf("m"), "meter", "meters"),
    KILOMETER(setOf("km"), "kilometer", "kilometers"),
    CENTIMETER(setOf("cm"), "centimeter", "centimeters"),
    MILLIMETER(setOf("mm"), "millimeter", "millimeters"),
    MILE(setOf("mi"), "mile", "miles"),
    YARD(setOf("yd"), "yard", "yards"),
    FOOT(setOf("ft"), "foot", "feet"),
    INCH(setOf("in"), "inch", "inches"),

    GRAM(setOf("g"), "gram", "grams"),
    KILOGRAM(setOf("kg"), "kilogram", "kilograms"),
    MILLIGRAM(setOf("mg"), "milligram", "milligrams"),
    POUND(setOf("lb"), "pound", "pounds"),
    OUNCE(setOf("oz"), "ounce", "ounces"),

    KELVIN(setOf("k", "kelvin", "kelvins"), "Kelvin", "Kelvins"),
    CELSIUS(setOf("c", "celsius", "dc"), "degree Celsius", "degrees Celsius"),
    FAHRENHEIT(setOf("f", "fahrenheit", "df"), "degree Fahrenheit", "degrees Fahrenheit"),

    UNKNOWN(setOf("???"), "???", "???");

}

fun String.toUnit(): Unit {
    return try {
        Unit.values().single { this in listOf(it.singularForm, it.pluralForm) || this in it.additionalForms }
    } catch (e: Exception) {
        Unit.UNKNOWN
    }
}