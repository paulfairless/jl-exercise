package jl.exercise.model

import com.fasterxml.jackson.databind.JsonNode
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*


data class Price(
    val was: BigDecimal?,
    val then1: BigDecimal?,
    val then2: BigDecimal?,
    val now: JsonNode,
    val currency: Currency
) {
    /*
        inconsistent data format for price
        "now": { "from": "55.00", "to": "100.00" } vs "now": "55.00"
    */
    val nowPrice: BigDecimal
        get() = when {
            now.isObject -> BigDecimal(now.get("to").asText())
            else -> BigDecimal(now.asText())
        }

    val reducedBy: BigDecimal
        get() = when {
            (was != null) -> was.minus(nowPrice)
            else -> BigDecimal(0)
        }

    val reducedByPercentage: Int
        get() = when {
            (was != null) -> reducedBy.divide(was, 2, RoundingMode.HALF_UP).times(BigDecimal(100)).toInt()
            else -> 0
        }
}