package com.example.smarttrade.logic

import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.smarttrade.MainActivity
import com.example.smarttrade.SignUpComprador
import com.example.smarttrade.SignUpVendedor
import com.example.smarttrade.BuildConfig
import com.example.smarttrade.mainBuyerFragments.HomeFragment
import com.example.smarttrade.models.CreditCard
import com.example.smarttrade.models.PersonBuyer
import com.example.smarttrade.models.PersonSeller
import com.example.smarttrade.models.clothes_representation_cart
import com.example.smarttrade.models.food_representation_cart
import com.example.smarttrade.models.product_representation
import com.example.smarttrade.models.product_representation_cart
import com.example.smarttrade.models.technology_representation_cart
import com.example.smarttrade.models.toy_representation_cart
import org.json.JSONArray
import org.json.JSONObject

object Identification {

    private const val myIP = BuildConfig.MY_IP
    private val url = "http://$myIP:8080"

    var isBuyer = false

    var isBQueue = false
    var isSQueue = false
    var isCartQueue = false

    lateinit var buyerVolleyQueue: RequestQueue
    lateinit var sellerVolleyQueue: RequestQueue
    lateinit var cartVolleyQueue: RequestQueue
    fun logIn(email:String, password:String){
        val json = JSONObject()
        json.put("email", email)
        json.put("password", password)

        val queue = Volley.newRequestQueue(MainActivity.getContext())

        val jsonRequest = JsonObjectRequest(
            Request.Method.POST, "$url/buyers/login", json,
            {response ->
                val jsonRes = response
                Log.i("JsonTest", response.toString())
                val nameRes = jsonRes.getString("name")
                val surnameRes = jsonRes.getString("surname")
                val emailRes = jsonRes.getString("email")
                val userID = jsonRes.getString("userID")
                val passwordRes = jsonRes.getString("password")
                val DNI = jsonRes.getString("dni")
                val bizum = jsonRes.getString("bizum")
                val paypal = jsonRes.getString("paypal")
                val creditCards = jsonRes.getJSONArray("creditCards")
                val shippingAddresses = jsonRes.getJSONArray("shippingAddresses")
                val factAddresses = jsonRes.getJSONArray("billingAddresses")

                if(nameRes != "") {
                    isBuyer =true
                    val buyer = PersonBuyer
                    buyer.setName(nameRes)
                    buyer.setSurname(surnameRes)
                    buyer.setEmail(emailRes)
                    buyer.setUserId(userID)
                    buyer.setDNI(DNI)
                    buyer.setPassword(passwordRes)
                    buyer.addShippingAddress(shippingAddresses.toString())
                    buyer.addFacturacionAddress(factAddresses.toString())
                    buyer.setBizum(bizum)
                    buyer.setPaypal(paypal)
                    for (i in 0 until creditCards.length()) {
                        val card = creditCards.getJSONObject(i)
                        buyer.addCreditCard(CreditCard(card.getString("cardNumber"), card.getString("expirationDate"),card.getString("cvc")))
                    }
                    MainActivity.loadBuyer()
                } else {
                    val json2 = JSONObject()
                    json2.put("email", email)
                    json2.put("password", password)
                    val jsonRequest2 = JsonObjectRequest(
                        Request.Method.POST, "$url/vendors/login", json2,
                        {response ->
                            val jsonRes = response
                            Log.i("JsonTest2", response.toString())

                            val nameRes = jsonRes.getString("name")
                            val surname = jsonRes.getString("surname")
                            val emailRes = jsonRes.getString("email")
                            val userID = jsonRes.getString("userID")
                            val passwordRes = jsonRes.getString("password")
                            val cif = jsonRes.getString("dni")
                            val iban = jsonRes.getString("iban")

                            if(nameRes != ""){
                                val seller = PersonSeller
                                seller.setName(nameRes)
                                seller.setSurname(surname)
                                seller.setEmail(emailRes)
                                seller.setUserId(userID)
                                seller.setPassword(passwordRes)
                                seller.setCIF(cif)
                                seller.setIBAN(iban)
                                MainActivity.loadSeller()
                            } else {
                                MainActivity.popUpError()
                            }
                        },
                        {error ->
                            //Toast.makeText(MainActivity.getContext(), "Error: $error", Toast.LENGTH_SHORT)
                            //.show()
                            Log.i("CACA",error.toString())
                        })
                    queue.add(jsonRequest2)
                }
            },
            { error ->
                //Toast.makeText(MainActivity.getContext(), "Error: $error", Toast.LENGTH_SHORT)
                // .show()
                Log.i("CACA",error.toString())
            })

        queue.add(jsonRequest)

    }

    fun signInBuyer(name:String, surname: String, password:String, email:String, userID: String, DNI: String, shippingAddress: String, factAddress: String, bizum: String, paypal: String, card: CreditCard){

        if(!isBQueue) {
            buyerVolleyQueue = Volley.newRequestQueue(SignUpComprador.getContext())
            isBQueue = true
        }
        val json = JSONObject()

        json.put("name", name)
        json.put("dni", DNI)
        json.put("surname", surname )
        json.put("userID", userID)
        json.put("email", email)
        json.put("password", password)
        json.put("cvc", card.cvc)
        json.put("cardNumber", card.number)
        json.put("expirationDate", card.expirationDate)
        json.put("shippingAddress", shippingAddress)
        json.put("billingAddress", factAddress)
        json.put("bizum", bizum)
        json.put("paypal", paypal )

        Log.i("jsonBuyer",json.toString())

        val jsonRequest = JsonObjectRequest(
            Request.Method.POST,"$url/buyers/register",json,
            {response ->
                val buyer = PersonBuyer
                buyer.setName(name)
                buyer.setSurname(surname)
                buyer.setEmail(email)
                buyer.setDNI(DNI)
                buyer.setUserId(userID)
                buyer.setPassword(password)
                buyer.addShippingAddress(shippingAddress)
                buyer.addFacturacionAddress(factAddress)
                buyer.setBizum(bizum)
                buyer.setPaypal(paypal)
                buyer.addCreditCard(CreditCard(card.number, card.expirationDate,card.cvc))
                SignUpComprador.loadBuyer()
            },
            { error ->
                Log.i("CACA", error.toString())
                SignUpComprador.popUpError()
            })
        buyerVolleyQueue.add(jsonRequest)
    }

    fun signInSeller(name:String, surname: String, password:String, email:String, userID: String, cif: String, iban: String){

        if(!isSQueue) {
            sellerVolleyQueue = Volley.newRequestQueue(SignUpVendedor.getContext())
            isSQueue = true
        }
        val json = JSONObject()

        json.put("name", name)
        json.put("cif", cif)
        json.put("surname", surname )
        json.put("userID", userID)
        json.put("email", email)
        json.put("password", password)
        json.put("iban", iban)

        Log.i("JsonSignUp",json.toString())


        val jsonRequest = JsonObjectRequest(
            Request.Method.POST, "$url/vendors/register",json,
            {response ->
                val buyer = PersonSeller
                buyer.setName(name)
                buyer.setSurname(surname)
                buyer.setEmail(email)
                buyer.setCIF(cif)
                buyer.setUserId(userID)
                buyer.setPassword(password)
                SignUpVendedor.loadSeller()
            },
            { error ->
                Log.i("error de SignUp",error.message.toString())
                SignUpVendedor.popUpError()

            })
        sellerVolleyQueue.add(jsonRequest)
    }

}