package com.example.smarttrade.logic

import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.smarttrade.MainActivity
import com.example.smarttrade.nonactivityclasses.PersonBuyer
import com.example.smarttrade.nonactivityclasses.product_representation
import kotlinx.coroutines.launch
import org.json.JSONObject

class logic {
//TODO clase para comunicarse mediante el uso de api y conseguir cosas como el LogIn o el SignUp


    fun filterProduct(producList: MutableList<product_representation>, searchItem:String) : MutableList<product_representation>{
        val filteredProducts : MutableList<product_representation> = mutableListOf()

        for(product in producList){
            if(product.name.toLowerCase().contains(searchItem)){
                filteredProducts.add(product)

            }
        }
        return filteredProducts
    }

    suspend fun logInBuyer(email:String, password:String){

        val json = JSONObject()
        json.put("email", email)
        json.put("password", password)

        val jsonString = json.toString()

        val queue = Volley.newRequestQueue(MainActivity.getContext())

        val StringRequest = StringRequest(
            Request.Method.POST, "http://192.168.18.141:8080/buyer/login",
            {response ->
                val jsonRes = JSONObject(response)
                val name = json.getString("name")
                val surname = json.getString("surname")
                val emailRes = json.getString("email")
                val userID = json.getString("userID")
                val passwordRes = json.getString("password")
                val shippingAddresses = json.getJSONArray("shippingAddresses")
                val DNI = json.getString("DNI")
                val factAddresses = json.getJSONArray("factAddresses")
                val bizum = json.getString("bizum")
                val paypal = json.getString("paypal")
                val creditCards = json.getJSONArray("creditCards")

                if(name != "") {
                    val buyer = PersonBuyer
                    buyer.setName(name)
                    buyer.setSurname(surname)
                    buyer.setEmail(email)
                    buyer.setUserId(userID)
                    buyer.setPassword(password)
                    buyer.addShippingAddress(shippingAddresses.toString())
                    buyer.addFacturacionAddress(factAddresses.toString())
                    buyer.setBizum(bizum)
                    buyer.setPaypal(paypal)
                    for (i in 0 until creditCards.length()) {
                        val card = creditCards.getJSONObject(i)
                        buyer.addCreditCard(card.getString("cardNumber"), card.getString("expirationDate"))
                    }
                }
            },
            { error ->
                Toast.makeText(MainActivity.getContext(), "Error: $error", Toast.LENGTH_SHORT)
                    .show()
            })
        
    }
}