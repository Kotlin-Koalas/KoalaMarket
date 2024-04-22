package com.example.smarttrade.logic


import com.example.smarttrade.volleyRequestClasses.VolleyMultipartRequest
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.smarttrade.AddProduct
import com.example.smarttrade.BrowseProducts
import com.example.smarttrade.BrowseProductsFiltered
import com.example.smarttrade.BuildConfig
import com.example.smarttrade.MainActivity
import com.example.smarttrade.SignUpComprador
import com.example.smarttrade.SignUpVendedor
import com.example.smarttrade.models.CreditCard
import com.example.smarttrade.models.PersonBuyer
import com.example.smarttrade.models.PersonSeller
import com.example.smarttrade.models.clothes_representation
import com.example.smarttrade.models.food_representation
import com.example.smarttrade.models.product_representation
import com.example.smarttrade.models.technology_representation
import com.example.smarttrade.models.toy_representation
import org.json.JSONArray
import org.json.JSONObject

private const val host = BuildConfig.DB_LINK
private const val myIP = BuildConfig.MY_IP
private val url = "http://$myIP:8080"


object logic {
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
        val url = "$host:5000"
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
