package jl.exercise.marshaller

import com.fasterxml.jackson.annotation.JsonInclude
import jl.exercise.model.Price
import jl.exercise.model.Product
import jl.exercise.utils.formatPrice
import javax.inject.Singleton

@Singleton
class JLProductMarshaller: ProductMarshaller {

    override fun marshall(product: Product, labelType: String?): Map<String, Any> {
        return mapOf(
            "productId" to product.productId,
            "title" to product.title,
            "colorSwatches" to product.colorSwatches.map { it ->
                mapOf(
                    "color" to it.color,
                    "rgbColor" to it.rgbColor,
                    "skuid" to it.skuId
                )
            },
            "nowPrice" to formatPrice(product.price.nowPrice, product.price.currency),
            "priceLabel" to priceLabel(product.price, labelType)
        )
    }

    internal fun priceLabel(price: Price, labelType: String?) = when (labelType) {
        "ShowWasThenNow" -> when {
            price.then2 != null -> "Was ${formatPrice(price.was, price.currency)}, then ${formatPrice(
                price.then2,
                price.currency
            )}, now ${formatPrice(price.nowPrice, price.currency)}"
            price.then1 != null -> "Was ${formatPrice(price.was, price.currency)}, then ${formatPrice(
                price.then1,
                price.currency
            )}, now ${formatPrice(price.nowPrice, price.currency)}"
            else -> "Was ${formatPrice(price.was, price.currency)}, now ${formatPrice(price.nowPrice, price.currency)}"
        }
        "ShowPercDscount" -> "${price.reducedByPercentage}% off - now ${formatPrice(price.nowPrice, price.currency)}"
        else -> "Was ${formatPrice(price.was, price.currency)}, now ${formatPrice(price.nowPrice, price.currency)}"
    }
}