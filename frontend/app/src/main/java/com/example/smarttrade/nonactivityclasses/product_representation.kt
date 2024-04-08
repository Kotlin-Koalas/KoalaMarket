package com.example.smarttrade.nonactivityclasses

import android.graphics.Bitmap
import android.media.Image

data class product_representation (
    val name: String,
    val price: String,
    val image: String,
    val stock: Int,
    val leafColor: LeafColor,
    val PN: Long
)

enum class LeafColor{
    RED, YELLOW, GREEN
}
