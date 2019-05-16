package jl.exercise

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import jl.exercise.marshaller.ProductMarshaller
import jl.exercise.client.JLApiOperations
import java.math.BigDecimal

@Controller("/price-reduction")
class HomeController(private val jlClient: JLApiOperations, private val productMarshaller: ProductMarshaller) {

    @Get("/")
    fun index(labelType: String?): Any {
        val products = jlClient.fetchCategory().products.filter {
            it.price.reducedBy > BigDecimal(0)
        }.sortedByDescending { it.price.reducedBy }

        return mapOf (
            "products" to products.map { it ->
                productMarshaller.marshall(it, labelType)
            }
        )
    }
}