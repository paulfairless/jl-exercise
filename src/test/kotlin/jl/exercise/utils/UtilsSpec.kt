package jl.exercise.utils

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.math.BigDecimal
import java.util.*
import kotlin.test.assertEquals

object UtilsSpec : Spek({

    describe("correctly format price") {
        it("prices under £10 should show decimal places") {
            assertEquals("£9.99", formatPrice(BigDecimal(9.99), Currency.getInstance("GBP")))
        }
        it("integer prices £10 or over should not display decimals") {
            assertEquals("£10", formatPrice(BigDecimal(10.0), Currency.getInstance("GBP")))
            assertEquals("£10.99", formatPrice(BigDecimal(10.99), Currency.getInstance("GBP")))
        }
        it("displays correct currency symbol") {
            assertEquals("£10", formatPrice(BigDecimal(10.0), Currency.getInstance("GBP")))
            assertEquals("€10", formatPrice(BigDecimal(10.0), Currency.getInstance("EUR")))
        }
        it("return empty string if null value provided") {
            assertEquals("", formatPrice(null, Currency.getInstance("GBP")))
        }
    }

})