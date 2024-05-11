package com.example.smarttrade.models.Orders

import android.graphics.Bitmap


interface OrderState {
    val imageResource: Int
    val stateName:String
    public fun nextState()
}