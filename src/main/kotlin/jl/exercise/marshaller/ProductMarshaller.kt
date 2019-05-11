package jl.exercise.marshaller

import jl.exercise.model.Product

interface ProductMarshaller {
    fun marshall(product: Product, labelType: String?): Map<String, Any>
}