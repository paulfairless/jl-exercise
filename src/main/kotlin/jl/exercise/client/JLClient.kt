package jl.exercise.client

import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import jl.exercise.model.Category

/*
    Declarative client implementation is provided by the framework, generated at compile time
 */
@Client("\${jlapi.url}")
interface JLClient : JLApiOperations {

    @Get("/\${jlapi.apiversion}/categories/\${jlapi.category}/products?key=\${jlapi.key}")
    override fun fetchCategory(): Category
}