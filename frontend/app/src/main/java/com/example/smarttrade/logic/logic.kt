package com.example.smarttrade.logic


import VolleyMultipartRequest
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.graphics.vector.VectorProperty
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.smarttrade.AddProduct
import com.example.smarttrade.BrowseProducts
import com.example.smarttrade.BrowseProductsFiltered
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

private const val host = "http://ec2-18-100-119-61.eu-south-2.compute.amazonaws.com:5000"


object logic {
//TODO clase para comunicarse mediante el uso de api y conseguir cosas como el LogIn o el SignUp
    var isBuyer = false

    var isBQueue = false
    var isSQueue = false
    var isPQueue = false
    lateinit var buyerVolleyQueue:RequestQueue
    lateinit var sellerVolleyQueue:RequestQueue
    lateinit var productVolleyQueue:RequestQueue


    val url = "http://192.168.56.1:8080"



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

     fun addTechnology(name:String,price:String,image:String,stock:Int,description:String,leafColor:String,PN:String,brand:String,electricConsumption:String){

         productVolleyQueue =Volley.newRequestQueue(AddProduct.getContext())
         Log.i("doublePrice value and type2",price.toString() + " " + price::class.simpleName)

        val json = JSONObject()
        json.put("productNumber", PN)
        json.put("name",name)
        json.put("price",price)
        json.put("description", description)
        json.put("ecology",leafColor)
        json.put("stock",stock)
        json.put("image",image)
        json.put("cif",PersonSeller.getCIF())
        json.put("electricConsumption",electricConsumption)
        json.put("brand",brand)

         Log.i("techJson",json.toString())

        val jsonRequest = JsonObjectRequest(
            Request.Method.POST,"$url/products/technology",json,
            {response ->
                val jsonRes:JSONObject = response
                val technology = technology_representation(name,price.toDouble(),image,stock,description,leafColor,PN,brand,electricConsumption)
                AddProduct.productAded()


            },
            {error ->
                Log.i("uploadError", error.message.toString())
                AddProduct.popUpError()
            })
         productVolleyQueue.add(jsonRequest)
    }

    fun addToy(name:String,price:String,image:String,stock:Int,description:String,leafColor:String,PN:String,material: String,age:String ){

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
            Request.Method.POST,"$url/products/toys",json,
            {response ->
                val jsonRes:JSONObject = response
                val toy = toy_representation(name,price.toDouble(),image,stock,description,leafColor,PN,material,age)
                AddProduct.productAded()

            },
            {error ->
                AddProduct.popUpError()
            })
        productVolleyQueue.add(jsonRequest)
    }

    fun addClothes(name:String,price:String,image:String,stock:Int,description:String,leafColor:String,PN:String,size:String,color:String){


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
            Request.Method.POST,"$url/products/clothes",json,
            {response ->
                val jsonRes:JSONObject = response
                val clothes = clothes_representation(name,price.toDouble(),image,stock,description,leafColor,PN,size,color)
                AddProduct.productAded()

            },
            {error ->
                AddProduct.popUpError()
            })
        productVolleyQueue.add(jsonRequest)
    }


    fun addFood(name:String,price:String,image:String,stock:Int,description:String,leafColor:String,PN:String,calories:String,macros:String){

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
            Request.Method.POST,"$url/products/foods",json,
            {response ->
                val jsonRes:JSONObject = response
                val foods = food_representation(name,price.toDouble(),image,stock,description,leafColor,PN,calories,macros)
                AddProduct.productAded()
            },
            {error ->
                AddProduct.popUpError()
            })
        productVolleyQueue.add(jsonRequest)
    }






    fun getAllProducts() {
        if(!isPQueue) {
            productVolleyQueue = Volley.newRequestQueue(BrowseProducts.getContext())
            isPQueue = true
        }
        val res = mutableListOf<product_representation>()
        val stringRequest = StringRequest(
            Request.Method.GET,"$url/products",
            {response ->
                val products = JSONArray(response)
                for (i in 0 until products.length()) {
                    val p = products.getJSONObject(i)
                    res.add(product_representation(p.getString("name"),p.getString("price"),p.getString("image"),p.getString("stock").toInt(),p.getString("description"),p.getString("ecology"),p.getString("productNumber")))
                }
                BrowseProducts.setProductsShown(res)
            },
            {error ->
                Toast.makeText(MainActivity.getContext(), "Error: $error", Toast.LENGTH_SHORT)
                    .show()
            })
        productVolleyQueue.add(stringRequest)
    }


    fun getAllProductsTechnology() {
        if(!isPQueue) {
            productVolleyQueue = Volley.newRequestQueue(BrowseProductsFiltered.getContext())
            isPQueue = true
        }
        val res = mutableListOf<product_representation>()
        val stringRequest = StringRequest(
            Request.Method.GET,"$url/products/technology",
            {response ->
                val products = JSONArray(response)
                for (i in 0 until products.length()) {
                    val p = products.getJSONObject(i)
                    res.add(product_representation(p.getString("name"),p.getString("price"),p.getString("image"),p.getString("stock").toInt(),p.getString("description"),p.getString("ecology"),p.getString("productNumber")))
                }
                BrowseProductsFiltered.setProductsShown(res)
            },
            {error ->
                Toast.makeText(MainActivity.getContext(), "Error: $error", Toast.LENGTH_SHORT)
                    .show()
            })
        productVolleyQueue.add(stringRequest)
    }

    fun getAllProductsFood() {
        if(!isPQueue) {
            productVolleyQueue = Volley.newRequestQueue(BrowseProductsFiltered.getContext())
            isPQueue = true
        }
        val res = mutableListOf<product_representation>()
        val stringRequest = StringRequest(
            Request.Method.GET,"$url/products/foods",
            {response ->
                val products = JSONArray(response)
                for (i in 0 until products.length()) {
                    val p = products.getJSONObject(i)
                    res.add(product_representation(p.getString("name"),p.getString("price"),p.getString("image"),p.getString("stock").toInt(),p.getString("description"),p.getString("ecology"),p.getString("productNumber")))
                }
                BrowseProductsFiltered.setProductsShown(res)
            },
            {error ->
                Toast.makeText(MainActivity.getContext(), "Error: $error", Toast.LENGTH_SHORT)
                    .show()
            })
        productVolleyQueue.add(stringRequest)
    }

    fun getAllProductsToys() {
        if(!isPQueue) {
            productVolleyQueue = Volley.newRequestQueue(BrowseProductsFiltered.getContext())
            isPQueue = true
        }
        val res = mutableListOf<product_representation>()
        val stringRequest = StringRequest(
            Request.Method.GET,"$url/products/toys",
            {response ->
                val products = JSONArray(response)
                for (i in 0 until products.length()) {
                    val p = products.getJSONObject(i)
                    res.add(product_representation(p.getString("name"),p.getString("price"),p.getString("image"),p.getString("stock").toInt(),p.getString("description"),p.getString("ecology"),p.getString("productNumber")))
                }
                BrowseProductsFiltered.setProductsShown(res)
            },
            {error ->
                Toast.makeText(MainActivity.getContext(), "Error: $error", Toast.LENGTH_SHORT)
                    .show()
            })
        productVolleyQueue.add(stringRequest)
    }


    fun getAllProductsClothes() {
        if(!isPQueue) {
            productVolleyQueue = Volley.newRequestQueue(BrowseProductsFiltered.getContext())
            isPQueue = true
        }
        val res = mutableListOf<product_representation>()
        val stringRequest = StringRequest(
            Request.Method.GET,"$url/products/clothes",
            {response ->
                val products = JSONArray(response)
                for (i in 0 until products.length()) {
                    val p = products.getJSONObject(i)
                    res.add(product_representation(p.getString("name"),p.getString("price"),p.getString("image"),p.getString("stock").toInt(),p.getString("description"),p.getString("ecology"),p.getString("productNumber")))
                }
                BrowseProductsFiltered.setProductsShown(res)
            },
            {error ->
                Toast.makeText(MainActivity.getContext(), "Error: $error", Toast.LENGTH_SHORT)
                    .show()
            })
        productVolleyQueue.add(stringRequest)
    }


    fun getImage(imageData: ByteArray,imageName: String){
        val url = "http://ec2-51-92-5-87.eu-south-2.compute.amazonaws.com:5000"
        val request = VolleyMultipartRequest(
            Request.Method.POST,
            url,
            { response ->
                val responseString = String(response.data, Charsets.UTF_8)
                val jsonResponse = JSONObject(responseString)
                AddProduct.setEncodedImageString(jsonResponse.getString("filename"))
            },
            { error ->
                Log.i("getImageError", error.toString())
            }
        )

        // add image data
        val imageData = // get your image data here
                    request.addByteData("file", imageName, imageData, "image/jpg")

        // add request to Volley queue
                val queue = Volley.newRequestQueue(AddProduct.getContext())
                queue.add(request)
    }


    /*
    *
    * fun  existProduct(productNumber : String) :  Boolean{
        productVolleyQueue =Volley.newRequestQueue(AddProduct.getContext())

        val stringRequest = StringRequest(
            Request.Method.GET,"$url/products/$productNumber/exist",
            {response ->
                    try{
                        val isProdcutExist = JSONObject(response).getBoolean("exist")
                        AddProduct.exists(boolean)

                }catch (e:Exception){
                    Log.e("AddProductError","Error parsing response: ${e.message}"  )
                }

            },
            {error ->
                Log.e("AddProduct", "Error in network request: ${error.message}")
                completion(false) // Handle network error gracefully
            })

            productVolleyQueue.add(stringRequest)
    }
    *
    *
    * */






/*
     suspend fun getImage(imageFIle : File) :String{

         val filePath = "/path/to/your/file.jpg"

         var stringPath = imageFIle.toString()
         stringPath = "https://cope-cdnmed.cope.es/resources/jpg/4/0/1601032304304.jpg"
         Log.i("stringPath",stringPath)


         var x  = "file=@$stringPath"

         // Curl command
         val curlCommand = "curl"
         //val curlArgs = listOf("-F", "@$stringPath.", "http://ec2-52-47-150-236.eu-west-3.compute.amazonaws.com:5000")
         val curlArgs = listOf("http://ec2-52-47-150-236.eu-west-3.compute.amazonaws.com:5000", "-F", x)

         val coroutineScope = CoroutineScope(Dispatchers.IO)

         // Create ProcessBuilder
         val processBuilder = ProcessBuilder(curlCommand, *curlArgs.toTypedArray())
         processBuilder.redirectErrorStream(true)
         val deferredResult   = coroutineScope.async  {
             // Start process
             val process = processBuilder.start()

             val response = StringBuilder()
             // Read output
             val reader = BufferedReader(InputStreamReader(process.inputStream))
             var line: String?
             while (reader.readLine().also { line = it } != null) {
                 response.append(line).append('\n')
                 // Wait for process to finish
                 val exitCode = process.waitFor()
                 println("Process exited with code $exitCode")


                 val res = response.toString()
                 val x = res.split("\n")
                 val y = x[x.size - 2]
                 val resultado :String =  y.substring(13, y.length - 2)


                 Log.i("response", resultado)

                 return@async resultado
             }
         }

            Log.i("resultado",deferredResult.await().toString())
            return deferredResult.await().toString()
     }

 */





}
