package com.example.smarttrade.models.PaymentMethods

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Button
import android.widget.TextView
import com.example.smarttrade.R

class PaymentMethodBizum(bizumNumber:String): PaymentMethod{

    override val customMessage: String = "Estas a punto de pagar con Bizum, usando el siguiente número: $bizumNumber"

    val bizumNumber = bizumNumber

    override fun showMessage(context: Context) {
        showCustomDialogBox(context)
    }

    override fun getID(): String {
        return bizumNumber
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