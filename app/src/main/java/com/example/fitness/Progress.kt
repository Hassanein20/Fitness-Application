package com.example.fitness

import java.util.Date

data class Progress(
    var date: Date,
    var dcalories: Double,
    var rcalories: Double,
    var tprotein: Double,
    var tcarbs: Double,
    var tfats: Double
){
    override fun toString(): String {
        return "$date\t$dcalories\t$rcalories\t$tprotein\t$tcarbs\t$tfats\n"
    }
}
