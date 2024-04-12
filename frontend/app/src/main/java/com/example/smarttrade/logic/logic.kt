package com.example.smarttrade.logic


import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.smarttrade.MainActivity
import com.example.smarttrade.SignUpComprador
import com.example.smarttrade.SignUpVendedor
import com.example.smarttrade.nonactivityclasses.CreditCard
import com.example.smarttrade.nonactivityclasses.PersonBuyer
import com.example.smarttrade.nonactivityclasses.PersonSeller
import com.example.smarttrade.nonactivityclasses.product_representation
import com.example.smarttrade.nonactivityclasses.technology_representation
import org.json.JSONObject

object logic {
//TODO clase para comunicarse mediante el uso de api y conseguir cosas como el LogIn o el SignUp
    var isBuyer = false

    fun filterProduct(producList: MutableList<product_representation>, searchItem:String) : MutableList<product_representation>{
        val filteredProducts : MutableList<product_representation> = mutableListOf()

        for(product in producList){
            if(product.name.toLowerCase().contains(searchItem)){
                filteredProducts.add(product)

            }
        }
        return filteredProducts
    }

    fun logIn(email:String, password:String){

        val json = JSONObject()
        json.put("email", email)
        json.put("password", password)

        val jsonString = json.toString()

        val queue = Volley.newRequestQueue(MainActivity.getContext())

        val StringRequest = StringRequest(
            Request.Method.POST, "https://ec2-52-47-150-236.eu-west-3.compute.amazonaws.com:443/buyers/register",
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
                    isBuyer =true
                    val buyer = PersonBuyer
                    buyer.setName(name)
                    buyer.setSurname(surname)
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
                }
            },
            { error ->
                Toast.makeText(MainActivity.getContext(), "Error: $error", Toast.LENGTH_SHORT)
                    .show()
            })

        if(PersonBuyer.getShippingAddresses().isEmpty()){
            val StringRequest = StringRequest(
                Request.Method.POST, "http://192.168.18.141:8080/vendors/login",
                {response ->
                    val jsonRes = JSONObject(response)
                    val name = json.getString("name")
                    val surname = json.getString("surname")
                    val emailRes = json.getString("email")
                    val userID = json.getString("userID")
                    val passwordRes = json.getString("password")
                    val cif = json.getString("cif")
                    val iban = json.getString("iban")

                    if(name != ""){
                        val seller = PersonSeller
                        seller.setName(name)
                        seller.setSurname(surname)
                        seller.setEmail(emailRes)
                        seller.setUserId(userID)
                        seller.setPassword(passwordRes)
                        seller.setCIF(cif)
                        seller.setIBAN(iban)
                    }

                },
                {error ->
                    SignUpComprador.popUpError()
                })
            }
        if(PersonSeller.getEmail().isEmpty() && PersonBuyer.getEmail().isEmpty()) {
            MainActivity.popUpError()
        }else{
            if(isBuyer){
                MainActivity.loadBuyer()
            }
            else{
                MainActivity.loadSeller()
            }
        }

    }

    fun signInBuyer(name:String, surname: String, password:String, email:String, userID: String, DNI: String, shippingAddress: String, factAddress: String, bizum: String, paypal: String, card: CreditCard){

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

        val jsonString = json.toString()

        val queue = Volley.newRequestQueue(MainActivity.getContext())

        val StringRequest = StringRequest(
            Request.Method.POST, "https://ec2-52-47-150-236.eu-west-3.compute.amazonaws.com:443/buyers/register",
            {response ->
                val jsonRes = JSONObject(response)
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
            },
            { error ->
                SignUpVendedor.popUpError()
            })
            SignUpComprador.loadBuyer()
        }

    fun signInSeller(name:String, surname: String, password:String, email:String, userID: String, cif: String, iban: String){

        val json = JSONObject()

        json.put("name", name)
        json.put("cif", cif)
        json.put("surname", surname )
        json.put("userID", userID)
        json.put("email", email)
        json.put("password", password)
        json.put("iban", iban)

        val jsonString = json.toString()

        val queue = Volley.newRequestQueue(MainActivity.getContext())

        val StringRequest = StringRequest(
            Request.Method.POST, "https://ec2-52-47-150-236.eu-west-3.compute.amazonaws.com:443/buyers/register",
            {response ->
                val jsonRes = JSONObject(response)
                val buyer = PersonSeller
                buyer.setName(name)
                buyer.setSurname(surname)
                buyer.setEmail(email)
                buyer.setCIF(cif)
                buyer.setUserId(userID)
                buyer.setPassword(password)

            },
            { error ->
                Toast.makeText(MainActivity.getContext(), "Error: $error", Toast.LENGTH_SHORT)
                    .show()
            })
        SignUpVendedor.loadSeller()
    }

    suspend fun AddTechnology(PN:String){
        val json = JSONObject()
        json.put("PN", PN)

        val jsonString = json.toString()

        val queue = Volley.newRequestQueue(MainActivity.getContext())


        val StringRequest = StringRequest(
        Request.Method.POST,"http://192.168.18.141:8080/products/technology",
        {response ->
            val jsonRes = JSONObject(response)
            val name = json.getString("name")
            val price = json.getDouble("price")
            val image = json.getString("image")
            val description = json.getString("description")
            val leafColor = json.getString("ecology")
            val stock = json.getInt("stock")
            val PNres = json.getString("PN")
            val brand = json.getString("brand")
            val electricConsumption = json.getDouble("electricConsumption")

            if(PN != ""){
                val technology= technology_representation(name,price,image,stock,description,leafColor,PNres,brand,electricConsumption)
            }

        },
            {error ->
                Toast.makeText(MainActivity.getContext(), "Error: $error", Toast.LENGTH_SHORT)
                    .show()
            })



    }
}
