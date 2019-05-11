package jl.exercise.client

import jl.exercise.model.Category

interface JLApiOperations {
    fun fetchCategory(): Category
}