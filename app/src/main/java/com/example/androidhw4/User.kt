package com.example.androidhw4

data class User(
    private val name: String = "",
    private val phone: String = "",
    private val gender: String = "",
    private val notification_1: Boolean = true,
    private val notification_2: Boolean = true
) {

    fun getName(): String {
        return name
    }


    override fun toString(): String {
        return super.toString()
    }
}