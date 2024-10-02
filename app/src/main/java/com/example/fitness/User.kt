package com.example.fitness

data class User(var user: String, var pass: String, var Remember: Boolean) {


    override fun toString(): String {
        return "$user\t$pass\t$Remember\n"
    }

}