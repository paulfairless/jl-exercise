package jl.exercise.utils

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

fun formatPrice(price: BigDecimal?, currency: Currency): String {
    if (price == null) return ""

    val currencyFormatter: NumberFormat = NumberFormat.getCurrencyInstance(Locale.UK)
    currencyFormatter.currency = currency
    if (price >= BigDecimal(10)) {
        currencyFormatter.minimumFractionDigits = 0
    } else {
        currencyFormatter.minimumFractionDigits = 2
    }

    return currencyFormatter.format(price)
}