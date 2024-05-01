package com.example.smarttrade.logic


import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.smarttrade.AddProduct
import com.example.smarttrade.BrowseProductsFiltered
import com.example.smarttrade.BuildConfig
import com.example.smarttrade.BuyerMainScreen
import com.example.smarttrade.MainActivity
import com.example.smarttrade.SellerFragment

import com.example.smarttrade.SellerMain

import com.example.smarttrade.adapters.SellerAdapter
import com.example.smarttrade.mainBuyerFragments.HomeFragment
import com.example.smarttrade.models.PersonSeller
import com.example.smarttrade.models.clothes_representation
import com.example.smarttrade.models.clothes_representation_seller
import com.example.smarttrade.models.food_representation
import com.example.smarttrade.models.food_representation_seller
import com.example.smarttrade.models.product_representation
import com.example.smarttrade.models.seller_representation
import com.example.smarttrade.models.technology_representation
import com.example.smarttrade.models.technology_representation_seller
import com.example.smarttrade.models.toy_representation
import com.example.smarttrade.models.toy_representation_seller
import com.example.smarttrade.volleyRequestClasses.VolleyMultipartRequest
import org.json.JSONArray
import org.json.JSONObject

private const val host = BuildConfig.DB_LINK
private const val myIP = BuildConfig.MY_IP
private val url = "http://$myIP:8080"


object logic {

    var isPQueue = false
    var isPSQueue = false

    lateinit var buyerVolleyQueue:RequestQueue
    lateinit var sellerVolleyQueue:RequestQueue
    lateinit var productVolleyQueue:RequestQueue
    lateinit var productSellerQueue: RequestQueue


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
                val technology = technology_representation(name,price,image,stock,description,leafColor,PN,brand,electricConsumption)
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
                val toy = toy_representation(name,price,image,stock,description,leafColor,PN,material,age)
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
                val clothes = clothes_representation(name,price,image,stock,description,leafColor,PN,size,color)
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
                val foods = food_representation(name,price,image,stock,description,leafColor,PN,calories,macros)
                AddProduct.productAded()
            },
            {error ->
                AddProduct.popUpError()
            })
        productVolleyQueue.add(jsonRequest)
    }




    //TODO preparar recepcion de distintos tipos de productos, casteando a product_representation

    fun getAllProducts() {
        if(!isPQueue) {
            productVolleyQueue = Volley.newRequestQueue(BuyerMainScreen.getContext())
            isPQueue = true
        }
        val res = mutableListOf<product_representation>()
        val stringRequest = StringRequest(
            Request.Method.GET,"$url/products",
            {response ->
                val products = JSONArray(response)
                for (i in 0 until products.length()) {
                    val p = products.getJSONObject(i)
                    res.add(product_representation(p.getString("category"),p.getString("name"),p.getString("price"),p.getString("image"),p.getString("stock").toInt(),p.getString("description"),p.getString("ecology"),p.getString("productNumber")))
                }
                HomeFragment.setProductsShown(res)
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
                    res.add(product_representation(p.getString("category"),p.getString("name"),p.getString("price"),p.getString("image"),p.getString("stock").toInt(),p.getString("description"),p.getString("ecology"),p.getString("productNumber")))
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
                    res.add(product_representation(p.getString("category"),p.getString("name"),p.getString("price"),p.getString("image"),p.getString("stock").toInt(),p.getString("description"),p.getString("ecology"),p.getString("productNumber")))
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
                    res.add(product_representation(p.getString("category"),p.getString("name"),p.getString("price"),p.getString("image"),p.getString("stock").toInt(),p.getString("description"),p.getString("ecology"),p.getString("productNumber")))
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
                    res.add(product_representation(p.getString("category"),p.getString("name"),p.getString("price"),p.getString("image"),p.getString("stock").toInt(),p.getString("description"),p.getString("ecology"),p.getString("productNumber")))
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

    fun getAllSellers(PN: String){
        if(!isPQueue) {
            productVolleyQueue = Volley.newRequestQueue(BuyerMainScreen.getContext())
            isPQueue = true
        }
        val res = mutableListOf<seller_representation>()
        val request = StringRequest(
            Request.Method.GET,"$url/products/$PN",
            {response ->
                val objects = JSONObject(response)
                val products = objects.getJSONArray("items")
                for (i in 0 until products.length()) {
                    val p = products.getJSONObject(i)
                    when(p.getString("category")){
                        "toy" -> res.add(toy_representation_seller(p.getString("cif"),p.getString("image"),p.getString("ecology"),p.getDouble("price").toString(),p.getString("name"),p.getString("description"),p.getString("productNumber"),p.getString("category"),p.getInt("stock").toString(),p.getString("vendorName"),p.getString("material"),p.getString("age")))
                        "food" -> res.add(food_representation_seller(p.getString("cif"),p.getString("image"),p.getString("ecology"),p.getDouble("price").toString(),p.getString("name"),p.getString("description"),p.getString("productNumber"),p.getString("category"),p.getInt("stock").toString(),p.getString("vendorName"),p.getInt("calories").toString(),p.getString("macros")))
                        "technology" -> res.add(technology_representation_seller(p.getString("cif"),p.getString("image"),p.getString("ecology"),p.getDouble("price").toString(),p.getString("name"),p.getString("description"),p.getString("productNumber"),p.getString("category"),p.getInt("stock").toString(),p.getString("vendorName"),p.getString("brand"),p.getString("electricConsumption")))
                        "clothes" -> res.add(clothes_representation_seller(p.getString("cif"),p.getString("image"),p.getString("ecology"),p.getDouble("price").toString(),p.getString("name"),p.getString("description"),p.getString("productNumber"),p.getString("category"),p.getInt("stock").toString(),p.getString("vendorName"),p.getString("size"),p.getString("color")))
                    }
                }
                SellerAdapter.setSellerList(res)
            },
            {error ->
                Toast.makeText(MainActivity.getContext(), "Error: $error", Toast.LENGTH_SHORT)
                    .show()
            })
        productVolleyQueue.add(request)
    }




     fun  existProduct(productNumber : String, completion: (Boolean) ->Unit){
        productVolleyQueue =Volley.newRequestQueue(AddProduct.getContext())

        val stringRequest = StringRequest(
            Request.Method.GET,"$url/products/$productNumber/exist",
            {response ->
                    try{
                        val isProdcutExist = JSONObject(response).getBoolean("exist")
                        completion(isProdcutExist)

                }catch (e:Exception){
                    Log.e("AddProductError","Error parsing response: ${e.message}"  )
                        completion(false)
                }

            },
            {error ->
                Log.e("AddProduct", "Error in network request: ${error.message}")
                completion(false) // Handle network error gracefully
            })

            productVolleyQueue.add(stringRequest)
    }



    fun deleteProduct(PN: String, CIF: String){

        productSellerQueue = Volley.newRequestQueue(SellerFragment.getContext())
        val stringRequest = StringRequest(
            Request.Method.DELETE,"$url/products/$PN/$CIF",
            {response ->
                Toast.makeText(SellerFragment.getContext(), "Producto eliminado con éxito", Toast.LENGTH_SHORT).show()
            },
            {error ->
                Toast.makeText(MainActivity.getContext(), "Error: $error", Toast.LENGTH_SHORT)
                    .show()
            })
        productSellerQueue.add(stringRequest)
    }


    fun changePriceProduct(PN: String, CIF: String, price: String, stock: Int){
        productSellerQueue = Volley.newRequestQueue(SellerFragment.getContext())
        val json = JSONObject()
        json.put("price", price)
        json.put("stock", stock)
        val stringRequest = JsonObjectRequest(
            Request.Method.PUT,"$url/products/$PN/$CIF",json,
            {response ->
                Toast.makeText(SellerFragment.getContext(), "Precio del producto actualizado con éxito", Toast.LENGTH_SHORT).show()
            },
            {error ->
                Toast.makeText(MainActivity.getContext(), "Error: $error", Toast.LENGTH_SHORT)
                    .show()
            })
        productSellerQueue.add(stringRequest)
    }





    fun getAllProductsSeller(sellerCif : String){
        if(!isPSQueue) {
            productSellerQueue = Volley.newRequestQueue(SellerMain.getContext())
            isPSQueue = true
        }
        val res = mutableListOf<product_representation>()
        val stringRequest = StringRequest(
            Request.Method.GET,"$url/$sellerCif/products",
            {response ->
                val products = JSONArray(response)
                for (i in 0 until products.length()) {
                    val p = products.getJSONObject(i)
                    res.add(product_representation(p.getString("category"),p.getString("name"),p.getString("price"),p.getString("image"),p.getString("stock").toInt(),p.getString("description"),p.getString("ecology"),p.getString("productNumber")))
                }
                SellerFragment.setProductsSeller(res)
            },
            {error ->
                Toast.makeText(MainActivity.getContext(), "Error: $error", Toast.LENGTH_SHORT)
                    .show()
                Log.i("ERROR","$error")
            })
        productSellerQueue.add(stringRequest)
    }














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





