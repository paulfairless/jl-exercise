package jl.exercise.marshaller

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.context.ApplicationContext
import jl.exercise.model.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.math.BigDecimal
import java.util.*
import kotlin.test.assertEquals

object JLProductMarshallerSpec : Spek({
    val mapper = ObjectMapper()
    val applicationContext = ApplicationContext.run("test")
    val productMarshaller: JLProductMarshaller = applicationContext.getBean(JLProductMarshaller::class.java)


    val now: JsonNode = mapper.readTree("""{"now":"66.66"}""").get("now")
    var price =
        Price(now = now, was = BigDecimal(100), then1 = null, then2 = null, currency = Currency.getInstance("GBP"))

    val product = Product(
        productId = "id-1",
        title = "test Product",
        colorSwatches = listOf(
            ColorSwatch("white smoke", "white", "1"),
            ColorSwatch("navy", "blue", "2")
        ),
        price = price
    )

    describe("marshal product") {
        val result: Map<String, Any> = productMarshaller.marshall(product, null)
        @Suppress("UNCHECKED_CAST")
        val colorSwatches: List<Map<String, String>> = result["colorSwatches"] as List<Map<String, String>>
        it("correctly marshall's product") {
            assertEquals("id-1", result["productId"])
            assertEquals("test Product", result["title"])

            assertEquals(2, colorSwatches.size)
            assertEquals("navy", colorSwatches[1]["color"])
            assertEquals("2", colorSwatches[1]["skuid"])
            assertEquals(Color.BLUE.rgbColor, colorSwatches[1]["rgbColor"])

            assertEquals("£66.66", result["nowPrice"])
            assertEquals("Was £100, now £66.66", result["priceLabel"])
        }
    }

    describe("marshal price") {
        price = Price(
            now = mapper.readTree("""{"now":"66.66"}""").get("now"),
            was = BigDecimal(100),
            then1 = BigDecimal(80),
            then2 = BigDecimal(70),
            currency = Currency.getInstance("GBP")
        )

        it("default price format is Was x, now y") {
            assertEquals("Was £100, now £66.66", productMarshaller.priceLabel(price, null))
        }
        it("ShowPercDscount format is x% off, now y") {
            assertEquals("33% off - now £66.66", productMarshaller.priceLabel(price, "ShowPercDscount"))
        }
        it("ShowWasThenNow format is Was x, then y, now z") {
            assertEquals("Was £100, then £70, now £66.66", productMarshaller.priceLabel(price, "ShowWasThenNow"))
        }
        it("ShowWasThenNow should use then1 if then2 is null") {
            price = Price(
                now = mapper.readTree("""{"now":"66.66"}""").get("now"),
                was = BigDecimal(100),
                then1 = BigDecimal(80),
                then2 = null,
                currency = Currency.getInstance("GBP")
            )
            assertEquals("Was £100, then £80, now £66.66", productMarshaller.priceLabel(price, "ShowWasThenNow"))
        }
        it("ShowWasThenNow should display Was x, now y if no then price provided") {
            price = Price(
                now = mapper.readTree("""{"now":"66.66"}""").get("now"),
                was = BigDecimal(100),
                then1 = null,
                then2 = null,
                currency = Currency.getInstance("GBP")
            )
            assertEquals("Was £100, now £66.66", productMarshaller.priceLabel(price, "ShowWasThenNow"))
        }
    }
})