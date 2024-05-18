package com.example.smarttrade.logic

import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.smarttrade.BuildConfig
import com.example.smarttrade.MainActivity
import com.example.smarttrade.OrderProgress
import com.example.smarttrade.models.Orders.Order_representation
import com.example.smarttrade.models.clothes_representation_cart
import com.example.smarttrade.models.food_representation_cart
import com.example.smarttrade.models.product_representation_cart
import com.example.smarttrade.models.technology_representation_cart
import com.example.smarttrade.models.toy_representation_cart
import org.json.JSONObject

object OrderRequests {
    private const val myIP = BuildConfig.MY_IP
    private val url = "https://$myIP"

    var isOrderQueue = false
    lateinit var orderVolleyQueue: RequestQueue


    fun getOrders(dni :String){
        if(!isOrderQueue) {
            orderVolleyQueue = Volley.newRequestQueue(MainActivity.getContext())
            isOrderQueue = true
        }

        val stringRequest = StringRequest(
            Request.Method.GET,"$url/buyers/$dni/orders",
            {response ->
                val objects = JSONObject(response)
                val orders = objects.getJSONArray("orders")
                val orderList = mutableListOf<Order_representation>()
                for (i in 0 until orders.length()) {
                    val o = orders.getJSONObject(i)
                    val products = o.getJSONArray("products")
                    val productList = mutableListOf<product_representation_cart>()
                    for (j in 0 until products.length()) {
                        val p = products.getJSONObject(j)
                        when(p.getString("category")){
                            "toy" -> productList.add(toy_representation_cart(p.getString("cif"),p.getString("name"), p.getDouble("price").toString(), p.getString("image"), p.getInt("stock"), p.getString("description"), p.getString("ecology"), p.getString("productNumber"), p.getInt("stock"),p.getString("vendorName"), p.getString("material"), p.getString("age")))
                            "food" -> productList.add(food_representation_cart(p.getString("cif"),p.getString("name"), p.getDouble("price").toString(), p.getString("image"), p.getInt("stock"), p.getString("description"), p.getString("ecology"), p.getString("productNumber"), p.getInt("stock"),p.getString("vendorName"), p.getInt("calories").toString(), p.getString("macros")))
                            "technology" -> productList.add(technology_representation_cart(p.getString("cif"),p.getString("name"), p.getDouble("price").toString(), p.getString("image"), p.getInt("stock"), p.getString("description"), p.getString("ecology"), p.getString("productNumber"), p.getInt("stock"),p.getString("vendorName"), p.getString("brand"), p.getString("electricConsumption")))
                            "clothes" -> productList.add(clothes_representation_cart(p.getString("cif"),p.getString("name"), p.getDouble("price").toString(), p.getString("image"), p.getInt("stock"), p.getString("description"), p.getString("ecology"), p.getString("productNumber"), p.getInt("stcok"),p.getString("vendorName"), p.getString("size"), p.getString("color")))
                            else -> Log.i("UnknownCategory", "Unknown product category: ${p.getString("category")}")

                        }
                    }
                    val order = Order_representation(productList, o.getString("vashippingAddress"), o.getString("billingAddress"), Order_representation.getPayMethod(o.getString("payMethod")) ,o.getString("orderID"), o.getString("name"), o.getString("surname"), o.getString("dni"), o.getString("totalPrice"), o.getString("stimatedDate"))
                    orderList.add(order)
                }
                OrderProgress.setOrders(orderList)
            },
            {error ->
                Log.i("ErrorGettingOrders", error.message.toString())

            })

        orderVolleyQueue.add(stringRequest)
        }

    fun addOrder(order: Order_representation){
        //TODO implementar logica crear order con API
    }


    }

