package com.example.smarttrade.models.PaymentMethods

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.smarttrade.BuyerMainScreen
import com.example.smarttrade.R
import com.example.smarttrade.logic.OrderRequests
import com.example.smarttrade.logic.ShoppingCartRequests
import com.example.smarttrade.models.Orders.Order_representation
import com.example.smarttrade.models.PersonBuyer

class PaymentMethodBizum(bizumNumber:String): PaymentMethod() {

    override val image: Int = R.drawable.phone
    override val type : String = "Bizum"

    val bizumNumber = bizumNumber

    override fun getPaymentMessage(): String {
        return "Estas a punto de pagar con Bizum, usando el siguiente número: $bizumNumber"
    }

    override fun setPayImage(imageView: ImageView) {
        imageView.setImageResource(image)
    }

    override fun getID(): String {
        return bizumNumber
    }
    
}