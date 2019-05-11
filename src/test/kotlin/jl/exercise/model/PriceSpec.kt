package jl.exercise.model

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.math.BigDecimal
import java.util.*
import kotlin.test.assertEquals


object PriceSpec : Spek({
    val mapper = ObjectMapper()

    describe("marshall now price") {
        describe("object type") {
            val jsonString = "{\"now\":\"66.66\"}"
            val now: JsonNode = mapper.readTree(jsonString).get("now")

            val price = Price(now = now, was = null, then1 = null, then2 = null, currency = Currency.getInstance("GBP"))

            it("marshal now price with object type") {
                assertEquals("66.66", price.nowPrice.toString())
            }
        }

        describe("value type") {
            val jsonString = "{\"now\":{\"from\":\"29.99\", \"to\":\"50\"}}"
            val now: JsonNode = mapper.readTree(jsonString).get("now")

            val price = Price(now = now, was = null, then1 = null, then2 = null, currency = Currency.getInstance("GBP"))

            it("marshal now price with value type") {
                assertEquals("50", price.nowPrice.toString())
            }
        }
    }
    describe("calculate reductions") {
        val jsonString = "{\"now\":\"66.66\"}"
        val now: JsonNode = mapper.readTree(jsonString).get("now")

        val price =
            Price(was = BigDecimal(100), now = now, then1 = null, then2 = null, currency = Currency.getInstance("GBP"))

        it("calculate correct reducedBy") {
            assertEquals("33.34", price.reducedBy.toString())
        }

        it("calculate correct reducedByPercentage") {
            assertEquals(33, price.reducedByPercentage)
        }
    }
})