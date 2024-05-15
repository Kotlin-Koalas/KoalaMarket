package com.example.smarttrade

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smarttrade.models.PersonBuyer

class Shipment : AppCompatActivity() {

    var popUpErrorOrNot = false
    var popUpText = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shipment)

        val nameTextView = findViewById<TextView>(R.id.editTextName)
        val surnameTextView = findViewById<TextView>(R.id.editTextSurname)

        nameTextView.setText(PersonBuyer.getName())
        surnameTextView.setText(PersonBuyer.getSurname())

        val paymentSpinner = findViewById<Spinner>(R.id.spinnerSPM)

        val items = arrayOf<String>()

        if(!PersonBuyer.getCreditCards().isEmpty())
        {
            for(c in PersonBuyer.getCreditCards()) {
                items.plus(c.number)
            }
        }

        if(PersonBuyer.getPaypal() != "")
        {
            items.plus(PersonBuyer.getPaypal())
        }

        if(PersonBuyer.getBizum() != "")
        {
            items.plus(PersonBuyer.getBizum())
        }

        val adapter = ArrayAdapter<String>(this, R.layout.spinner_item_pago, items)
        paymentSpinner.adapter = adapter

        val backButton = findViewById<Button>(R.id.buttonCancel)

        backButton.setOnClickListener {
            //TODO: Implementar la lógica de volver a la pantalla anterior
        }

        val confirmButton = findViewById<Button>(R.id.buttonSignUp)

        confirmButton.setOnClickListener {
            if (nameTextView.text.isEmpty()) {
                popUpText += "El campo nombre no puede estar vacío"
                popUpErrorOrNot = true
            }
            if (surnameTextView.text.isEmpty()) {
                popUpText += "El campo apellido no puede estar vacío"
                popUpErrorOrNot = true
            }
            if (popUpErrorOrNot) {
                showCustomDialogBox(popUpText)
            }
        }
    }

    private fun showCustomDialogBox(popUpText: String) {
        val dialog = Dialog(this)
        dialog.setTitle("ERROR")
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.pop_up_alert)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val messageBox = dialog.findViewById<TextView>(R.id.textViewErrorText)
        val btnOk = dialog.findViewById<Button>(R.id.buttonOkPopUp)

        btnOk.setOnClickListener{
            dialog.dismiss()
        }
        messageBox.text = popUpText

        dialog.show()

    }
}