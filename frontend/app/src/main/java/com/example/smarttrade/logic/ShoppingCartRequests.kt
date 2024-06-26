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
import com.example.smarttrade.mediador.MediatorShoppingCart
import com.example.smarttrade.models.PersonBuyer
import com.example.smarttrade.models.clothes_representation_cart
import com.example.smarttrade.models.food_representation_cart
import com.example.smarttrade.models.product_representation_cart
import com.example.smarttrade.models.technology_representation_cart
import com.example.smarttrade.models.toy_representation_cart
import org.json.JSONObject

object ShoppingCartRequests {

    private const val myIP = BuildConfig.MY_IP
    private val url = "https://$myIP"

    var isCartQueue = false

    lateinit var cartVolleyQueue: RequestQueue

    fun getShoppingCart(){
        if(!isCartQueue) {
            cartVolleyQueue = Volley.newRequestQueue(MainActivity.getContext())
            isCartQueue = true
        }

        val id = PersonBuyer.getDNI()

        val stringRequest = StringRequest(
            Request.Method.GET,"$url/buyers/$id/cart",
            {response ->
                PersonBuyer.clearShoppingCart()
                val objects = JSONObject(response)
                val products = objects.getJSONArray("items")
                for (i in 0 until products.length()) {
                    val p = products.getJSONObject(i)
                    when(p.getString("category")){
                        "toy" -> PersonBuyer.addProductToCart(toy_representation_cart(p.getString("cif"),p.getString("name"), p.getDouble("price").toString(), p.getString("image"), p.getInt("stock"), p.getString("description"), p.getString("ecology"), p.getString("productNumber"), p.getInt("quantity"),p.getString("vendorName"), p.getString("material"), p.getString("age")))
                        "food" -> PersonBuyer.addProductToCart(food_representation_cart(p.getString("cif"),p.getString("name"), p.getDouble("price").toString(), p.getString("image"), p.getInt("stock"), p.getString("description"), p.getString("ecology"), p.getString("productNumber"), p.getInt("quantity"),p.getString("vendorName"), p.getInt("calories").toString(), p.getString("macros")))
                        "technology" -> PersonBuyer.addProductToCart(technology_representation_cart(p.getString("cif"),p.getString("name"), p.getDouble("price").toString(), p.getString("image"), p.getInt("stock"), p.getString("description"), p.getString("ecology"), p.getString("productNumber"), p.getInt("quantity"),p.getString("vendorName"), p.getString("brand"), p.getString("electricConsumption")))
                        "clothes" -> PersonBuyer.addProductToCart(clothes_representation_cart(p.getString("cif"),p.getString("name"), p.getDouble("price").toString(), p.getString("image"), p.getInt("stock"), p.getString("description"), p.getString("ecology"), p.getString("productNumber"), p.getInt("quantity"),p.getString("vendorName"), p.getString("size"), p.getString("color")))
                    }
                }
                Log.i("ShoppingCartList", PersonBuyer.getShoppingCart().toString())
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
        json.put("cif",product.cif)
        json.put("category", product.category)
        json.put("quantity", product.quantity)

        val id = PersonBuyer.getDNI()

        val jsonRequest = JsonObjectRequest(
            Request.Method.POST,"$url/buyers/$id/cart",json,
            {response ->
                Log.i("ProductAdded", response.toString())
            },
            {error ->
                Log.i("ErrorAñadiendo", error.message.toString())
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
        json.put("cif",product.cif)
        json.put("category", product.category)
        json.put("quantity", product.quantity)

        Log.i("jsonBuyer",json.toString())

        val id = PersonBuyer.getDNI()

        val jsonRequest = JsonObjectRequest(
            Request.Method.PUT,"$url/buyers/$id/cart",json,
            null,
            {error ->
                Log.i("ErrorEditando", error.message.toString())
            })
        Log.i("jsonEDITREQUE",jsonRequest.body.toString())
        cartVolleyQueue.add(jsonRequest)
    }
    fun deleteProductInCart(product: product_representation_cart){
        if(!isCartQueue) {
            cartVolleyQueue = Volley.newRequestQueue(MainActivity.getContext())
            isCartQueue = true
        }

        val json = JSONObject()
        json.put("productNumber", product.PN)
        json.put("cif",product.cif)
        json.put("category", product.category)
        json.put("quantity", 0)
        Log.i("jsonDelete",json.toString())

        val id = PersonBuyer.getDNI()

        val jsonRequest = JsonObjectRequest(
            Request.Method.PUT,"$url/buyers/$id/cart",json,
            {response ->
                Log.i("ProductAdded", response.toString())
            },
            {error ->
                Log.i("ErrorBorrando", error.message.toString())
            })
        Log.i("jsonDELETEREQUE",jsonRequest.body.toString())
        cartVolleyQueue.add(jsonRequest)
    }

    fun checkIfExistsProductInCart(product: product_representation_cart): Boolean{

        if(!isCartQueue) {
            cartVolleyQueue = Volley.newRequestQueue(MainActivity.getContext())
            isCartQueue = true
        }
       var res : Boolean = false
        val request = StringRequest(
            Request.Method.GET,"$url/buyers/${PersonBuyer.getDNI()}/cart/${product.PN}/${product.seller}",
            {response ->
                Log.i("ProductExists", response)
                if(response == "true"){
                    res = true
                }
            },
            {error ->
                Log.i("ErrorCheckingProduct", error.message.toString())
            })
        return res


    }
    fun getProductInCart(PN: String, sellerCIF : String, seller : String, productStatic: product_representation_cart){
        if(!isCartQueue) {
            cartVolleyQueue = Volley.newRequestQueue(MainActivity.getContext())
            isCartQueue = true
        }

        var product = product_representation_cart("","","","","",0,"","","",0,"")
        val id = PersonBuyer.getDNI()

        val stringRequest = StringRequest(
            Request.Method.GET,"$url/buyers/$id/cart",
            {response ->
                val objects = JSONObject(response)
                val products = objects.getJSONArray("items")
                for (i in 0 until products.length()) {
                    val p = products.getJSONObject(i)
                    if(p.getString("productNumber") == PN  && p.getString("cif") == sellerCIF){

                        Log.i("ProductFound", p.toString())
                        product = product_representation_cart(p.getString("cif"),p.getString("category"), p.getString("name"), p.getString("price"), p.getString("image"),p.getInt("stock"), p.getString("description"), p.getString("ecology"), p.getString("productNumber"), p.getInt("quantity"), seller)
                    }
                }
                MediatorShoppingCart.notifyItemAdded(product, productStatic)

            },
            {error ->
                Log.i("ErrorGettingShoppingCart", error.message.toString())
            })

        cartVolleyQueue.add(stringRequest)





    }

    fun getProductInCartWishList(PN: String, sellerCIF : String, seller : String, productStatic: product_representation_cart){
        if(!isCartQueue) {
            cartVolleyQueue = Volley.newRequestQueue(MainActivity.getContext())
            isCartQueue = true
        }

        var product = product_representation_cart("","","","","",0,"","","",0,"")
        val id = PersonBuyer.getDNI()

        val stringRequest = StringRequest(
            Request.Method.GET,"$url/buyers/$id/cart",
            {response ->
                val objects = JSONObject(response)
                val products = objects.getJSONArray("items")
                for (i in 0 until products.length()) {
                    val p = products.getJSONObject(i)
                    if(p.getString("productNumber") == PN  && p.getString("cif") == sellerCIF){

                        Log.i("ProductFound", p.toString())
                        product = product_representation_cart(p.getString("cif"),p.getString("category"), p.getString("name"), p.getString("price"), p.getString("image"),p.getInt("stock"), p.getString("description"), p.getString("ecology"), p.getString("productNumber"), p.getInt("quantity"), seller)
                    }
                }
                MediatorShoppingCart.notifyItemAddedWishList(product, productStatic)

            },
            {error ->
                Log.i("ErrorGettingShoppingCart", error.message.toString())
            })

        cartVolleyQueue.add(stringRequest)





    }

}