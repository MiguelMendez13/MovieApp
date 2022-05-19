package com.tesoem.movieapp

data class APIresponse(val q: String = "",
                       val d: List<DItem>?,
                       val v: Int = 0)