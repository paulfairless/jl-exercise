package jl.exercise

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.context.ApplicationContext
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer
import io.restassured.RestAssured.given
import io.restassured.builder.RequestSpecBuilder
import io.restassured.specification.RequestSpecification
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.requestParameters
import org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document
import org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class HomeControllerSpec : Spek({
    val mapper = ObjectMapper()
    val restDocumentation = ManualRestDocumentation()
    val embeddedServer: EmbeddedServer = ApplicationContext.run(EmbeddedServer::class.java)
    val client: HttpClient = HttpClient.create(embeddedServer.url)

    val spec: RequestSpecification = RequestSpecBuilder()
        .addFilter(
            documentationConfiguration(restDocumentation)
                .operationPreprocessors()
                .withRequestDefaults()
                .withResponseDefaults()
        )
        .build()

    describe("/price-reduction responds with reduced products") {
        restDocumentation.beforeTest(javaClass, "ListProducts")

        val response: String =
            given(spec).port(embeddedServer.port)
                .accept("application/json")
                .filter(
                    document(
                        "all",
                        requestParameters(
                            parameterWithName("labelType").optional().description("Formatting of price label")
                        ),
                        responseFields(
                            fieldWithPath("[].productId").type(JsonFieldType.STRING).description("ProductId"),
                            fieldWithPath("[].title").type(JsonFieldType.STRING).description("Product title"),
                            fieldWithPath("[].colorSwatches").type(JsonFieldType.ARRAY).description("Colour swatches"),
                            fieldWithPath("[].colorSwatches[].color").type(JsonFieldType.STRING).description("color"),
                            fieldWithPath("[].colorSwatches[].rgbColor").type(JsonFieldType.STRING).description("RGB representation of color"),
                            fieldWithPath("[].colorSwatches[].skuid").type(JsonFieldType.STRING).description("sku"),
                            fieldWithPath("[].nowPrice").type(JsonFieldType.STRING).description("Formatted now price"),
                            fieldWithPath("[].priceLabel").type(JsonFieldType.STRING).description("Formatted price label")
                        )
                    )
                )
                .get("/price-reduction?labelType=ShowWasNow")
                .then()
                .assertThat().statusCode(200)
                .extract().response().asString()

        val results: List<JsonNode> = mapper.readValue(response, object : TypeReference<List<JsonNode>>() {})

        it("filters out products that are not reduced") {
            assertEquals(4, results.size)
            assertFalse {
                results.any {
                    it.get("productId").textValue() == "id-2"
                }
            }
        }
        it("sorts by biggest discount") {
            assertEquals("id-4", results.first().get("productId").textValue())
            assertEquals("id-3", results.last().get("productId").textValue())
        }

        restDocumentation.afterTest()
    }

    describe("/price-reduction accepts labelType") {
        var rsp: String
        var results: List<JsonNode>

        it("displays correct ShowWasNow label") {
            rsp = client.toBlocking().retrieve("/price-reduction?labelType=ShowWasNow")
            results = mapper.readValue(rsp, object : TypeReference<List<JsonNode>>() {})
            assertEquals("Was £100, now £20", results.first().get("priceLabel").textValue())
        }

        it("displays correct ShowWasThenNow label") {
            rsp = client.toBlocking().retrieve("/price-reduction?labelType=ShowWasThenNow")
            results = mapper.readValue(rsp, object : TypeReference<List<JsonNode>>() {})
            assertEquals("Was £100, then £50, now £20", results.first().get("priceLabel").textValue())
        }

        it("displays correct ShowPercDscount label") {
            rsp = client.toBlocking().retrieve("/price-reduction?labelType=ShowPercDscount")
            results = mapper.readValue(rsp, object : TypeReference<List<JsonNode>>() {})
            assertEquals("80% off - now £20", results.first().get("priceLabel").textValue())
        }
    }

    afterGroup {
        client.close()
        embeddedServer.close()
    }
})