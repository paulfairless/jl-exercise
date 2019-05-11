package jl.exercise.client

import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.context.annotation.Primary
import io.micronaut.context.annotation.Requires
import io.micronaut.context.env.Environment
import io.micronaut.retry.annotation.Fallback
import jl.exercise.model.*
import java.math.BigDecimal
import java.util.*
import javax.inject.Singleton

@Requires(env = [Environment.TEST])
@Fallback
@Singleton
@Primary
class JLClientStub : JLApiOperations {
    override fun fetchCategory(): Category {
        val mapper = ObjectMapper()

        return Category(
            products = listOf(
                Product(
                    productId = "id-1",
                    title = "Reduced Product 1",
                    colorSwatches = listOf(
                        ColorSwatch("white smoke", "white", "1"),
                        ColorSwatch("navy", "blue", "2")
                    ),
                    price = Price(
                        now = mapper.readTree("""{"now":"66.66"}""").get("now"),
                        was = BigDecimal(100),
                        currency = Currency.getInstance("GBP"),
                        then1 = null,
                        then2 = null
                    )
                ),
                Product(
                    productId = "id-2",
                    title = "Non Reduced Product 1",
                    colorSwatches = listOf(ColorSwatch("white smoke", "white", "1")),
                    price = Price(
                        now = mapper.readTree("""{"now":"100"}""").get("now"),
                        was = BigDecimal(100),
                        currency = Currency.getInstance("GBP"),
                        then1 = null,
                        then2 = null
                    )
                ),
                Product(
                    productId = "id-3",
                    title = "Reduced Product Least Discounted",
                    colorSwatches = listOf(ColorSwatch("maroon", "red", "1")),
                    price = Price(
                        now = mapper.readTree("""{"now":"99.99"}""").get("now"),
                        was = BigDecimal(100),
                        currency = Currency.getInstance("GBP"),
                        then1 = null,
                        then2 = null
                    )
                ),
                Product(
                    productId = "id-4",
                    title = "Reduced Product Most Discounted",
                    colorSwatches = listOf(ColorSwatch("jungle", "green", "1")),
                    price = Price(
                        now = mapper.readTree("""{"now":"20"}""").get("now"),
                        was = BigDecimal(100),
                        currency = Currency.getInstance("GBP"),
                        then1 = null,
                        then2 = BigDecimal(50)
                    )
                ),
                Product(
                    productId = "id-5",
                    title = "Reduced Product 1",
                    colorSwatches = listOf(ColorSwatch("midnight", "black", "1")),
                    price = Price(
                        now = mapper.readTree("""{"now":"50"}""").get("now"),
                        was = BigDecimal(100),
                        currency = Currency.getInstance("GBP"),
                        then1 = null,
                        then2 = null
                    )
                )
            )
        )
    }
}