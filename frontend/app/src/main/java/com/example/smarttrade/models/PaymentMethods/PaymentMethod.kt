package com.example.smarttrade.models.PaymentMethods

import android.content.Context
import android.widget.ImageView

interface PaymentMethod {

    val customMessage:String

    val image:Int

    fun setPayImage(imageView: ImageView)

    fun getID():String

    fun showMessage(context: Context)

    //TODO settear imagen que toca en funcion del metodo de pago en el fragmento de texto
}