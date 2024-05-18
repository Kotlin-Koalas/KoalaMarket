package com.example.smarttrade.models.PaymentMethods

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.smarttrade.R
import com.example.smarttrade.logic.OrderRequests
import com.example.smarttrade.models.Orders.Order_representation

class PaymentMethodPaypal(PayPalEmail:String):PaymentMethod {

    override val customMessage: String = "Estas a punto de pagar con PayPal, usando la siguiente cuenta: $PayPalEmail"
    override val image: Int = R.drawable.email_icon

    val paypalEmail = PayPalEmail

    override fun showMessage(context: Context,order: Order_representation) {
        showCustomDialogBox(context,order)
    }

    override fun setPayImage(imageView: ImageView) {
        imageView.setImageResource(image)
    }

    override fun getID(): String {
        return paypalEmail
    }

    private fun showCustomDialogBox(context: Context,order:Order_representation){
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
        }

        btnCancel.setOnClickListener{
            dialog.dismiss()
        }

        messageBox.text = customMessage

        dialog.show()

    }
}