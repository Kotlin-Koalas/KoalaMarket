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

    val customMessage: String = "Estas a punto de pagar con la tarjeta de crédito con el siguiente número: ${creditCard.number}, con fecha de expiración: ${creditCard.expirationDate} y cvc: ${creditCard.cvc}"
    override val image: Int = R.drawable.credit_card_icon
    override val type : String = "Credit Card"
    override fun getPaymentMessage(): String {
        return customMessage
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

    override fun showCustomDialogBox(context: Context,order:Order_representation){
        val dialog = Dialog(context)
        dialog.setTitle("CONFIRMATION")
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.pop_up_warning)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val messageBox = dialog.findViewById<TextView>(R.id.textViewErrorText)
        val btnOk = dialog.findViewById<Button>(R.id.buttonOkPopUp)
        val btnCancel = dialog.findViewById<Button>(R.id.buttonCancelPopUp)

        btnOk.setOnClickListener{
            OrderRequests.addOrder(order)
            for(item in PersonBuyer.getSelectedItemsCart()){
                Log.i("Item",item.toString())
                ShoppingCartRequests.deleteProductInCart(item)
            }
            for(item in PersonBuyer.getSelectedItemsCart()){
                PersonBuyer.removeProductFromCart(item)
            }
            PersonBuyer.clearSelectedItems()
            val i = Intent(context, BuyerMainScreen::class.java)
            context.startActivity(i)
            dialog.dismiss()

            Handler(Looper.getMainLooper()).postDelayed({
                BuyerMainScreen.showCustomDialogBoxSeller("Pedido realizado con éxito")
            }, 1000)
        }

        btnCancel.setOnClickListener{
            dialog.dismiss()
        }

        messageBox.text = customMessage

        dialog.show()

    }
    companion object{
        lateinit var cCard:CreditCard
        fun getCreditCard():CreditCard{
            return cCard
        }
    }
}