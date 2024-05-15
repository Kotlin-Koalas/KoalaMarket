package com.example.smarttrade.models.PaymentMethods

import android.content.Context

interface PaymentMethod {

    val customMessage:String

    fun getID():String

    fun showMessage(context: Context)
}