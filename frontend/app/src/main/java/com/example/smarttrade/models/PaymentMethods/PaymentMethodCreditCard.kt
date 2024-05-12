package com.example.smarttrade.models.PaymentMethods

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Button
import android.widget.TextView
import com.example.smarttrade.R
import com.example.smarttrade.models.CreditCard

class PaymentMethodCreditCard(creditCard:CreditCard): PaymentMethod{
    override val customMessage: String = "Estas a punto de pagar con la tarjeta de crédito con el siguiente número: ${creditCard.number}, con fecha de expiración: ${creditCard.expirationDate} y cvc: ${creditCard.cvc}"
    override fun showMessage(context: Context) {
        showCustomDialogBox(context)
    }

    private fun showCustomDialogBox(context: Context){
        val dialog = Dialog(context)
        dialog.setTitle("CONFIRMATION")
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.pop_up_warning)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val messageBox = dialog.findViewById<TextView>(R.id.textViewErrorText)
        val btnOk = dialog.findViewById<Button>(R.id.buttonOkPopUp)
        val btnCancel = dialog.findViewById<Button>(R.id.buttonCancelPopUp)

        btnOk.setOnClickListener{
            //TODO: Implementar la lógica de crear pedido
        }

        btnCancel.setOnClickListener{
            dialog.dismiss()
        }

        messageBox.text = customMessage

        dialog.show()

    }
}