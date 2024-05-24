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
import com.example.smarttrade.models.Orders.Order_representation
import com.example.smarttrade.models.PersonBuyer

abstract class PaymentMethod {

    abstract val image:Int

    abstract val type: String

    abstract fun getPaymentMessage():String
    abstract fun setPayImage(imageView: ImageView)

    abstract fun getID():String

    abstract fun showCustomDialogBox(context: Context,order:Order_representation)

    open fun showMessage(context: Context, order: Order_representation){
        val cMessage = Order_representation.getPayMethod().getPaymentMessage()
        Order_representation.getPayMethod().showCustomDialogBox(context,order)
        //Aquí se cargaria la pasarela de pago correspondiente.
    }



}