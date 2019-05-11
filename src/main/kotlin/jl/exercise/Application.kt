package jl.exercise

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.runtime.Micronaut
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import io.micronaut.context.event.BeanCreatedEvent
import io.micronaut.context.event.BeanCreatedEventListener
import javax.inject.Singleton


object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
            .packages("jl.exercise")
            .mainClass(Application.javaClass)
            .start()
    }

    @Singleton
    internal class ObjectMapperBeanEventListener : BeanCreatedEventListener<ObjectMapper> {

        override fun onCreated(event: BeanCreatedEvent<ObjectMapper>): ObjectMapper {
            val mapper = event.bean
            mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS)
            return mapper
        }
    }
}