package jl.exercise.model

data class ColorSwatch(
    val color: String = "",
    val basicColor: String = "",
    val skuId: String = ""
) {
    val rgbColor: String
        get() = Color.lookupRgbColor(basicColor)

}

// https://htmlcolorcodes.com/
enum class Color(val rgbColor: String) {
    WHITE("FFFFFF"),
    SILVER("C0C0C0"),
    GRAY("808080"),
    BLACK("000000"),
    RED("FF0000"),
    MAROON("800000"),
    YELLOW("FFFF00"),
    OLIVE("808000"),
    LIME("00FF00"),
    GREEN("008000"),
    AQUA("00FFFF"),
    TEAL("008080"),
    BLUE("008080"),
    NAVY("000080"),
    FUCHSIA("FF00FF"),
    PURPLE("800080");

    companion object {
        fun lookupRgbColor(color: String): String {
            var rgbColor = ""
            try {
                rgbColor = Color.valueOf(color.toUpperCase()).rgbColor
            } catch (e: IllegalArgumentException) {
//                Matching color not found
            }

            return rgbColor
        }
    }
}