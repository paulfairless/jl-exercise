package jl.exercise.client

import io.micronaut.context.annotation.ConfigurationProperties
import jl.exercise.client.JLApiConfiguration.Companion.PREFIX

@ConfigurationProperties(PREFIX)
class JLApiConfiguration {
    companion object {
        const val PREFIX = "jlapi"
    }

    private var url: String? = null
    private var apiversion: String? = null
    private var category: String? = null
    private var key: String? = null

    fun toMap(): MutableMap<String, Any> {
        val m = HashMap<String, Any>()
        if (apiversion != null) {
            m["apiversion"] = apiversion!!
        }
        if (category != null) {
            m["category"] = category!!
        }
        if (key != null) {
            m["key"] = key!!
        }
        if (url != null) {
            m["url"] = url!!
        }
        return m
    }
}