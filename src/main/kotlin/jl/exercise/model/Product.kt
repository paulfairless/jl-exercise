package jl.exercise.model

data class Product(
    val productId: String = "",
    val title: String = "",
    val colorSwatches: List<ColorSwatch>,
    val price: Price
) {
}