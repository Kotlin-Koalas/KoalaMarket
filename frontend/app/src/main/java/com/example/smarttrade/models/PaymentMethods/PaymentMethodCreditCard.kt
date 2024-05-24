package com.example.smarttrade.models.PaymentMethods

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.smarttrade.BuyerMainScreen
import com.example.smarttrade.R
import com.example.smarttrade.logic.OrderRequests
import com.example.smarttrade.logic.ShoppingCartRequests
import com.example.smarttrade.models.CreditCard
import com.example.smarttrade.models.Orders.Order_representation
import com.example.smarttrade.models.PersonBuyer

class PaymentMethodCreditCard(creditCard:CreditCard): PaymentMethod(){

    val creditCard = creditCard
    override val image: Int = R.drawable.credit_card_icon
    override val type : String = "Credit Card"
    override fun getPaymentMessage(): String {
        return "Estas a punto de pagar con la tarjeta de crédito con el siguiente número: ${creditCard.number}, con fecha de expiración: ${creditCard.expirationDate} y cvc: ${creditCard.cvc}"
    }

    init {
        cCard = creditCard
    }
    override fun setPayImage(imageView: ImageView) {
        imageView.setImageResource(image)
    }

    override fun getID(): String {
        return cCard.number
    }

    companion object{
        lateinit var cCard:CreditCard
        fun getCreditCard():CreditCard{
            return cCard
        }
    }
}