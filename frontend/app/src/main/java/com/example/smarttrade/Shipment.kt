package com.example.smarttrade

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.smarttrade.models.Orders.Order_representation
import com.example.smarttrade.models.PaymentMethods.PaymentMethod
import com.example.smarttrade.models.PaymentMethods.PaymentMethodBizum
import com.example.smarttrade.models.PaymentMethods.PaymentMethodCreditCard
import com.example.smarttrade.models.PaymentMethods.PaymentMethodPaypal
import com.example.smarttrade.models.PersonBuyer
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.random.Random

class Shipment : AppCompatActivity() {

    lateinit var selectedPaymentMethod:PaymentMethod
    lateinit var selectedShippingAddress:String
    lateinit var selectedBillingAddress:String
    lateinit var createdOrder: Order_representation

    var popUpErrorOrNot = false
    var popUpText = ""
    var paymentMethods = mutableListOf<PaymentMethod>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shipment)

        val nameTextView = findViewById<TextView>(R.id.editTextName)
        val surnameTextView = findViewById<TextView>(R.id.editTextSurname)

        nameTextView.setText(PersonBuyer.getName())
        surnameTextView.setText(PersonBuyer.getSurname())

        val paymentSpinner = findViewById<Spinner>(R.id.spinnerSPM)

        var itemsPayments = arrayOf<String>()

        if(!PersonBuyer.getCreditCards().isEmpty())
        {
            for(c in PersonBuyer.getCreditCards()) {
                if(c.number != "") {
                    itemsPayments = itemsPayments.plus(c.number)
                    paymentMethods.add(PaymentMethodCreditCard(c))
                }
            }
        }

        if(PersonBuyer.getPaypal() != "")
        {
            itemsPayments = itemsPayments.plus(PersonBuyer.getPaypal())
            paymentMethods.add(PaymentMethodPaypal(PersonBuyer.getPaypal()))
        }

        if(PersonBuyer.getBizum() != "")
        {
            itemsPayments = itemsPayments.plus(PersonBuyer.getBizum())
            paymentMethods.add(PaymentMethodBizum(PersonBuyer.getBizum()))
        }

        val adapterPM = ArrayAdapter<String>(this, R.layout.spinner_item_pago, itemsPayments)
        paymentSpinner.adapter = adapterPM

        paymentSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                val selectedItem = parent.getItemAtPosition(position) as String
                for(p in paymentMethods){
                    if(p.getID() == selectedItem)
                    {
                        selectedPaymentMethod = p
                        selectedPaymentMethod.setPayImage(findViewById(R.id.imageViewSPM1))
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //nothing
            }
        }

        val shippingAddressSpinner = findViewById<Spinner>(R.id.spinnerEntrega)

        var itemsShippingAddress = arrayOf<String>()

        for(a in PersonBuyer.getShippingAddresses()) {
            itemsShippingAddress = itemsShippingAddress.plus(a)
        }

        val adapterSA = ArrayAdapter<String>(this, R.layout.spinner_item_pago, itemsShippingAddress)
        shippingAddressSpinner.adapter = adapterSA

        shippingAddressSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                val selectedItem = parent.getItemAtPosition(position) as String
                selectedShippingAddress = selectedItem
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //nothing
            }
        }

        val billingAddressSpinner = findViewById<Spinner>(R.id.spinnerFacturacion)

        var itemsBillingAddress = arrayOf<String>()

        for(a in PersonBuyer.getFacturationAddresses()) {
            itemsBillingAddress = itemsBillingAddress.plus(a)
        }

        val adapterBA = ArrayAdapter<String>(this, R.layout.spinner_item_pago, itemsBillingAddress)
        billingAddressSpinner.adapter = adapterBA

        billingAddressSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                val selectedItem = parent.getItemAtPosition(position) as String
                selectedBillingAddress = selectedItem
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //nothing
            }
        }

        val backButton = findViewById<Button>(R.id.buttonCancel)

        backButton.setOnClickListener {
            PersonBuyer.clearSelectedItems()
            val IntentS = Intent(this,BuyerMainScreen::class.java)
            this.startActivity(IntentS)
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
            else
            {
                createdOrder = Order_representation(
                    //PersonBuyer.getSelectedItemsCart(),
                    selectedShippingAddress,
                    selectedBillingAddress,
                    selectedPaymentMethod.type,
                    "order"+ Random.nextInt(10000, 99999),
                    nameTextView.text.toString(),
                    surnameTextView.text.toString(),
                    PersonBuyer.getTotalPrice().toString(),
                    generateRandomFutureDate().toString()
                    )
                Log.i("OrderCreated", createdOrder.toString())
                selectedPaymentMethod.showMessage(this, createdOrder)
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
    fun generateRandomFutureDate(): LocalDate {
        val today = LocalDate.now()
        val randomDaysToAdd = Random.nextInt(3, 9) // upper bound is exclusive
        return today.plus(randomDaysToAdd.toLong(), ChronoUnit.DAYS)
    }
}
