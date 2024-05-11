package com.example.smarttrade.models.Orders

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import com.example.smarttrade.MainActivity
import com.example.smarttrade.R

class OrderPreparing(order:Order_representation) : OrderState{

    override var imageResource = R.drawable.preparingorder
    override val stateName = "Preparando pedido"

    var order:Order_representation = order
    override fun nextState() {
        order.setState(order.shipped)
    }
}