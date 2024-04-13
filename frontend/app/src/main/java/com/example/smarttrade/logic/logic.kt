package com.example.smarttrade.logic


import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.smarttrade.AddProduct
import com.example.smarttrade.MainActivity
import com.example.smarttrade.SignUpComprador
import com.example.smarttrade.SignUpVendedor
import com.example.smarttrade.nonactivityclasses.CreditCard
import com.example.smarttrade.nonactivityclasses.PersonBuyer
import com.example.smarttrade.nonactivityclasses.PersonSeller
import com.example.smarttrade.nonactivityclasses.clothes_representation
import com.example.smarttrade.nonactivityclasses.food_representation
import com.example.smarttrade.nonactivityclasses.product_representation
import com.example.smarttrade.nonactivityclasses.technology_representation
import com.example.smarttrade.nonactivityclasses.toy_representation
import org.json.JSONObject

object logic {
//TODO clase para comunicarse mediante el uso de api y conseguir cosas como el LogIn o el SignUp
    var isBuyer = false

    var isBQueue = false
    var isSQueue = false
    var isPQueue = false
    lateinit var buyerVolleyQueue:RequestQueue
    lateinit var sellerVolleyQueue:RequestQueue
    lateinit var productVolleyQueue:RequestQueue




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

        val queue = Volley.newRequestQueue(MainActivity.getContext())

        val jsonRequest = JsonObjectRequest(
            Request.Method.POST, "https://ec2-52-47-150-236.eu-west-3.compute.amazonaws.com:443/buyers/register", json,
            {response ->
                val jsonRes = response
                val name = jsonRes.getString("name")
                val surname = jsonRes.getString("surname")
                val emailRes = jsonRes.getString("email")
                val userID = jsonRes.getString("userID")
                val passwordRes = jsonRes.getString("password")
                val shippingAddresses = jsonRes.getJSONArray("shippingAddresses")
                val DNI = jsonRes.getString("DNI")
                val factAddresses = jsonRes.getJSONArray("factAddresses")
                val bizum = jsonRes.getString("bizum")
                val paypal = jsonRes.getString("paypal")
                val creditCards = jsonRes.getJSONArray("creditCards")

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

        queue.add(jsonRequest)

        if(PersonBuyer.getShippingAddresses().isEmpty()){
            val jsonRequest2 = JsonObjectRequest(
                Request.Method.POST, "https://ec2-52-47-150-236.eu-west-3.compute.amazonaws.com:443/buyers/register", json,
                {response ->
                    val jsonRes = response
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
                    Toast.makeText(MainActivity.getContext(), "Error: $error", Toast.LENGTH_SHORT)
                        .show()
                })
            queue.add(jsonRequest2)
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

        if(!isBQueue) {
            buyerVolleyQueue = Volley.newRequestQueue(SignUpVendedor.getContext())
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

        val jsonRequest = JsonObjectRequest(
            Request.Method.POST,"https://ec2-52-47-150-236.eu-west-3.compute.amazonaws.com:443/buyers/register",json,
            {response ->
                val jsonRes:JSONObject = response
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
                SignUpComprador.popUpError()
            })
        buyerVolleyQueue.add(jsonRequest)
    }

    fun signInSeller(name:String, surname: String, password:String, email:String, userID: String, cif: String, iban: String){

        if(!isSQueue) {
            sellerVolleyQueue = Volley.newRequestQueue(SignUpComprador.getContext())
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

        val jsonString = json.toString()

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
                SignUpVendedor.loadSeller()
            },
            { error ->
                SignUpVendedor.popUpError()
            })
        sellerVolleyQueue.add(StringRequest)
    }

     fun addTechnology(name:String,price:Double,image:String,stock:Int,description:String,leafColor:String,PN:String,brand:String,electricConsumption:Double){

         productVolleyQueue =Volley.newRequestQueue(AddProduct.getContext())


        val json = JSONObject()
        json.put("productNumber", PN)
        json.put("name",name)
        json.put("price",price)
        json.put("description", description)
        json.put("ecology",leafColor)
        json.put("stock",stock)
        json.put("image",image)
        json.put("cif",PersonSeller.getCIF())
        json.put("electricalConsumption",electricConsumption)
        json.put("brand",brand)

         val queue = Volley.newRequestQueue(AddProduct.getContext())

        val jsonRequest = JsonObjectRequest(
            Request.Method.POST,"https://ec2-52-47-150-236.eu-west-3.compute.amazonaws.com:443/products/technology",json,
            {response ->
                val jsonRes:JSONObject = response
                val technology = technology_representation(name,price,image,stock,description,leafColor,PN,brand,electricConsumption)

            },
            {error ->
                AddProduct.popUpError()
            })
         productVolleyQueue.add(jsonRequest)
    }

    fun addToy(name:String,price:Double,image:String,stock:Int,description:String,leafColor:String,PN:String,material: String,age:String ){

        productVolleyQueue =Volley.newRequestQueue(AddProduct.getContext())

        val json = JSONObject()
        json.put("productNumber", PN)
        json.put("name",name)
        json.put("price",price)
        json.put("description", description)
        json.put("ecology",leafColor)
        json.put("stock",stock)
        json.put("image",image)
        json.put("cif",PersonSeller.getCIF())
        json.put("material",material)
        json.put("age",age)



        val jsonRequest = JsonObjectRequest(
            Request.Method.POST,"https://ec2-52-47-150-236.eu-west-3.compute.amazonaws.com:443/products/toys",json,
            {response ->
                val jsonRes:JSONObject = response
                val toy = toy_representation(name,price,image,stock,description,leafColor,PN,material,age)

            },
            {error ->
                AddProduct.popUpError()
            })
        productVolleyQueue.add(jsonRequest)
    }

    fun addClothes(name:String,price:Double,image:String,stock:Int,description:String,leafColor:String,PN:String,size:String,color:String){


        productVolleyQueue =Volley.newRequestQueue(AddProduct.getContext())
        val json = JSONObject()
        json.put("productNumber", PN)
        json.put("name",name)
        json.put("price",price)
        json.put("description", description)
        json.put("ecology",leafColor)
        json.put("stock",stock)
        json.put("image",image)
        json.put("cif",PersonSeller.getCIF())
        json.put("color",color)
        json.put("size",size)

        val jsonString = json.toString()

        val jsonRequest = JsonObjectRequest(
            Request.Method.POST,"https://ec2-52-47-150-236.eu-west-3.compute.amazonaws.com:443/products/clothes",json,
            {response ->
                val jsonRes:JSONObject = response
                val clothes = clothes_representation(name,price,image,stock,description,leafColor,PN,size,color)

            },
            {error ->
                AddProduct.popUpError()
            })
        productVolleyQueue.add(jsonRequest)
    }


    fun addFood(name:String,price:Double,image:String,stock:Int,description:String,leafColor:String,PN:String,calories:String,macros:String){


        productVolleyQueue =Volley.newRequestQueue(AddProduct.getContext())

        val json = JSONObject()
        json.put("productNumber", PN)
        json.put("name",name)
        json.put("price",price)
        json.put("description", description)
        json.put("ecology",leafColor)
        json.put("stock",stock)
        json.put("image",image)
        json.put("cif",PersonSeller.getCIF())
        json.put("calories",calories)
        json.put("macros",macros)

        val jsonString = json.toString()

        val jsonRequest = JsonObjectRequest(
            Request.Method.POST,"https://ec2-52-47-150-236.eu-west-3.compute.amazonaws.com:443/products/foods",json,
            {response ->
                val jsonRes:JSONObject = response
                val foods = food_representation(name,price,image,stock,description,leafColor,PN,calories,macros)

            },
            {error ->
                AddProduct.popUpError()
            })
        productVolleyQueue.add(jsonRequest)
    }






    fun getAllProducts(): MutableList<product_representation> {
        if(!isPQueue) {
            productVolleyQueue = Volley.newRequestQueue(SignUpVendedor.getContext())
            isPQueue = true
        }
        val res = mutableListOf<product_representation>()
        val stringRequest = StringRequest(
            Request.Method.GET,"https://ec2-52-47-150-236.eu-west-3.compute.amazonaws.com:443/products",
            {response ->
                val jsonRes = JSONObject(response)
                val products = jsonRes.getJSONArray("products")
                for (i in 0 until products.length()) {
                    val p = products.getJSONObject(i)
                    res.add(product_representation(p.getString("name"),p.getString("price"),p.getString("image"),p.getString("stock").toInt(),p.getString("description"),p.getString("ecology"),p.getString("product_number")))
                }
            },
            {error ->
                Toast.makeText(MainActivity.getContext(), "Error: $error", Toast.LENGTH_SHORT)
                    .show()
            })
        buyerVolleyQueue.add(stringRequest)
        return res
    }

}
