package com.tesoem.movieapp

data class DItem(val q: String = "",
                 val s: String = "",
                 val v: List<VItem>?,
                 val i: I,
                 val rank: Int = 0,
                 val y: Int = 0,
                 val yr: String = "",
                 val id: String = "",
                 val l: String = "",
                 val vt: Int = 0)