package com.example.smarttrade.models.PaymentMethods

import android.content.Context
import android.widget.ImageView
import com.example.smarttrade.models.Orders.Order_representation

interface PaymentMethod {

    val customMessage:String

    val image:Int

    fun setPayImage(imageView: ImageView)

    fun getID():String

    fun showMessage(context: Context,order: Order_representation)

}