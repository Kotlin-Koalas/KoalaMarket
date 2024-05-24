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
import com.example.smarttrade.adapters.ProductCartAdapter
import com.example.smarttrade.logic.OrderRequests
import com.example.smarttrade.logic.ShoppingCartRequests
import com.example.smarttrade.models.Orders.Order_representation
import com.example.smarttrade.models.PersonBuyer

class PaymentMethodPaypal(PayPalEmail:String):PaymentMethod() {

    val PayPalEmail = PayPalEmail

    override val image: Int = R.drawable.email_icon
    override val type : String = "PayPal"

    val paypalEmail = PayPalEmail

    override fun getPaymentMessage(): String {
        return "Estas a punto de pagar con PayPal, usando la siguiente cuenta: $PayPalEmail"
    }

    override fun setPayImage(imageView: ImageView) {
        imageView.setImageResource(image)
    }

    override fun getID(): String {
        return paypalEmail
    }

    override fun showCustomDialogBox(context: Context,order:Order_representation,message:String){
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
            dialog.dismiss()
        }

        btnCancel.setOnClickListener{
            dialog.dismiss()
        }

        messageBox.text = message

        dialog.show()

    }


}