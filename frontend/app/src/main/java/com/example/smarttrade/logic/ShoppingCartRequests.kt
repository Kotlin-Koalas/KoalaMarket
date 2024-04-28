package com.example.smarttrade.logic

import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.smarttrade.BuildConfig
import com.example.smarttrade.MainActivity
import com.example.smarttrade.mainBuyerFragments.ShoppingCartFragment
import com.example.smarttrade.models.PersonBuyer
import com.example.smarttrade.models.clothes_representation_cart
import com.example.smarttrade.models.food_representation_cart
import com.example.smarttrade.models.product_representation_cart
import com.example.smarttrade.models.technology_representation_cart
import com.example.smarttrade.models.toy_representation_cart
import org.json.JSONArray
import org.json.JSONObject

object ShoppingCartRequests {

    private const val myIP = BuildConfig.MY_IP
    private val url = "http://$myIP:8080"

    var isCartQueue = false

    lateinit var cartVolleyQueue: RequestQueue

    fun getShoppingCart(){
        if(!isCartQueue) {
            cartVolleyQueue = Volley.newRequestQueue(MainActivity.getContext())
            isCartQueue = true
        }

        val id = PersonBuyer.getDNI()

        val stringRequest = StringRequest(
            Request.Method.GET,"${url}/buyers/$id/cart",
            {response ->
                val objects = JSONObject(response)
                val products = objects.getJSONArray("items")
                for (i in 0 until products.length()) {
                    val p = products.getJSONObject(i)
                    when(p.getString("category")){
                        "toy" -> PersonBuyer.addProductToCart(toy_representation_cart(p.getString("name"), p.getDouble("price").toString(), p.getString("image"), p.getInt("stock"), p.getString("description"), p.getString("ecology"), p.getString("productNumber"), p.getInt("quantity"),p.getString("cif"), p.getString("material"), p.getString("age")))
                        "food" -> PersonBuyer.addProductToCart(food_representation_cart(p.getString("name"), p.getDouble("price").toString(), p.getString("image"), p.getInt("stock"), p.getString("description"), p.getString("ecology"), p.getString("productNumber"), p.getInt("quantity"),p.getString("cif"), p.getInt("calories").toString(), p.getString("macros")))
                        "technology" -> PersonBuyer.addProductToCart(technology_representation_cart(p.getString("name"), p.getDouble("price").toString(), p.getString("image"), p.getInt("stock"), p.getString("description"), p.getString("ecology"), p.getString("productNumber"), p.getInt("quantity"),p.getString("cif"), p.getString("brand"), p.getString("electricConsumption")))
                        "clothes" -> PersonBuyer.addProductToCart(clothes_representation_cart(p.getString("name"), p.getDouble("price").toString(), p.getString("image"), p.getInt("stock"), p.getString("description"), p.getString("ecology"), p.getString("productNumber"), p.getInt("quantity"),p.getString("cif"), p.getString("size"), p.getString("color")))
                    }
                }
                ShoppingCartFragment.setInitialProductsShown()
            },
            {error ->
                Log.i("ErrorGettingShoppingCart", error.message.toString())
            })
        cartVolleyQueue.add(stringRequest)
    }
    fun addProductToCart(product: product_representation_cart){
        if(!isCartQueue) {
            cartVolleyQueue = Volley.newRequestQueue(MainActivity.getContext())
            isCartQueue = true
        }

        val json = JSONObject()
        json.put("productNumber", product.PN)
        json.put("cif",product.seller)
        json.put("category", product.category)
        json.put("quantity", product.quantity)

        val id = PersonBuyer.getDNI()

        val jsonRequest = JsonObjectRequest(
            Request.Method.POST,"${url}/buyers/$id/cart",json,
            {response ->
                //TODO handle response
                Log.i("ProductAdded", response.toString())
            },
            {error ->
                Log.i("ErrorAddingProduct", error.message.toString())
            })
        cartVolleyQueue.add(jsonRequest)
    }
    fun editProductInCart(product: product_representation_cart){
        if(!isCartQueue) {
            cartVolleyQueue = Volley.newRequestQueue(MainActivity.getContext())
            isCartQueue = true
        }

        val json = JSONObject()
        json.put("productNumber", product.PN)
        json.put("cif",product.seller)
        json.put("category", product.category)
        json.put("quantity", product.quantity)

        val id = PersonBuyer.getDNI()

        val jsonRequest = JsonObjectRequest(
            Request.Method.POST,"${url}/buyers/$id/cart",json,
            {response ->
                //TODO handle response
                Log.i("ProductAdded", response.toString())
            },
            {error ->
                Log.i("ErrorAddingProduct", error.message.toString())
            })
        cartVolleyQueue.add(jsonRequest)
    }
    fun deleteProductInCart(product: product_representation_cart){
        if(!isCartQueue) {
            cartVolleyQueue = Volley.newRequestQueue(MainActivity.getContext())
            isCartQueue = true
        }

        val json = JSONObject()
        json.put("productNumber", product.PN)
        json.put("cif",product.seller)
        json.put("category", product.category)

        val id = PersonBuyer.getDNI()

        val jsonRequest = JsonObjectRequest(
            Request.Method.POST,"${url}/buyers/$id/cart",json,
            {response ->
                //TODO handle response
                Log.i("ProductAdded", response.toString())
            },
            {error ->
                Log.i("ErrorAddingProduct", error.message.toString())
            })
        cartVolleyQueue.add(jsonRequest)
    }
}