package jl.exercise.model

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object ColorSpec : Spek({
    describe("model RBG value for base color") {
        it("returns rgb value for base color") {
            assertEquals("FFFFFF", Color.lookupRgbColor("white"))
            assertEquals("800080", Color.lookupRgbColor("purple"))
        }
        it("returns empty string for unknown value") {
            assertEquals("", Color.lookupRgbColor("other"))
        }
    }
})