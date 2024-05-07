package com.example.smarttrade.logic

import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.smarttrade.BuildConfig
import com.example.smarttrade.MainActivity
import com.example.smarttrade.WishList
import com.example.smarttrade.models.PersonBuyer
import com.example.smarttrade.models.clothes_representation_cart
import com.example.smarttrade.models.food_representation_cart
import com.example.smarttrade.models.product_representation_cart
import com.example.smarttrade.models.technology_representation_cart
import com.example.smarttrade.models.toy_representation_cart
import org.json.JSONObject

object ListWishRequests {

    private const val myIP = BuildConfig.MY_IP
    private val url = "https://$myIP"

    var isWishQueue = false

    lateinit var wishVolleyQueue: RequestQueue


    fun getWishList(){
        if(!isWishQueue) {
            wishVolleyQueue = Volley.newRequestQueue(MainActivity.getContext())
            isWishQueue = true

        }
        val id = PersonBuyer.getDNI()


        val stringRequest = StringRequest(
            Request.Method.GET,"$url/buyers/$id/wishlist",
            {response ->
                    //PersonBuyer.clearWishList()
                    val objects = JSONObject(response)
                Log.i("LOL", "LOL")
                try{
                    val products = objects.getJSONArray("items")
                    for (i in 0 until products.length()) {
                        val p = products.getJSONObject(i)
                        when(p.getString("category")){
                            "toy" -> PersonBuyer.addProductToWish(toy_representation_cart(p.getString("cif"),p.getString("name"), p.getDouble("price").toString(), p.getString("image"), p.getInt("stock"), p.getString("description"), p.getString("ecology"), p.getString("productNumber"), p.getInt("quantity"),p.getString("cif"), p.getString("material"), p.getString("age")))
                            "food" -> PersonBuyer.addProductToWish(food_representation_cart(p.getString("cif"),p.getString("name"), p.getDouble("price").toString(), p.getString("image"), p.getInt("stock"), p.getString("description"), p.getString("ecology"), p.getString("productNumber"), p.getInt("quantity"),p.getString("cif"), p.getInt("calories").toString(), p.getString("macros")))
                            "technology" -> PersonBuyer.addProductToWish(
                                technology_representation_cart(p.getString("cif"),p.getString("name"), p.getDouble("price").toString(), p.getString("image"), p.getInt("stock"), p.getString("description"), p.getString("ecology"), p.getString("productNumber"), p.getInt("quantity"),p.getString("cif"), p.getString("brand"), p.getString("electricConsumption")))
                            "clothes" -> PersonBuyer.addProductToWish(clothes_representation_cart(p.getString("cif"),p.getString("name"), p.getDouble("price").toString(), p.getString("image"), p.getInt("stock"), p.getString("description"), p.getString("ecology"), p.getString("productNumber"), p.getInt("quantity"),p.getString("cif"), p.getString("size"), p.getString("color")))
                            else -> Log.i("UnknownCategory", "Unknown product category: ${p.getString("category")}")
                        }
                    }
                }catch (e: Exception){
                    Log.i("No Items", e.message.toString())
                }

                Log.i("ShoppingCartList", PersonBuyer.getShoppingCart().toString())
                WishList.setProducts()
            },
            {error ->
                Log.i("ErrorGettingWishList", error.message.toString())
            })
        wishVolleyQueue.add(stringRequest)


    }


    fun addProductToWish(product: product_representation_cart){
        if(!isWishQueue) {
            wishVolleyQueue = Volley.newRequestQueue(MainActivity.getContext())
            isWishQueue = true
        }

        val json = JSONObject()
        json.put("productNumber", product.PN)
        json.put("cif",product.seller)
        json.put("category", product.category)


        val id = PersonBuyer.getDNI()

        val jsonRequest = JsonObjectRequest(
            Request.Method.POST,"$url/buyers/$id/wishlist",json,
            {response ->
                val objetct = JSONObject(response.toString())
                Log.i("ProductAdded", objetct.toString())
            },
            {error ->
                Log.i("ProductAddError", error.message.toString())
            })
        wishVolleyQueue.add(jsonRequest)

    }

    fun deleteProductWish(product: product_representation_cart){
        if(!isWishQueue) {
            wishVolleyQueue = Volley.newRequestQueue(MainActivity.getContext())
            isWishQueue = true
        }

        val id = PersonBuyer.getDNI()

        val json = JSONObject()
        json.put("productNumber", product.PN)
        json.put("cif",product.seller)
        json.put("category", product.category)


        val jsonRequest = JsonObjectRequest(
            Request.Method.DELETE,"$url/buyers/$id/wishlist",json,
            {response ->
                Log.i("ProductDeleted", response.toString())
            },
            {error ->
                Log.i("ProductDeleteError", error.message.toString())
            })
        Log.i("jsonDELETEREQUE",jsonRequest.body.toString())
        wishVolleyQueue.add(jsonRequest)

    }

}