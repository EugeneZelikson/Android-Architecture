package com.arch.example.util

inline fun <reified T> T.TAG(): String = T::class.java.simpleName