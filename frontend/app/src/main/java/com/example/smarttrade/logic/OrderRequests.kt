package com.example.smarttrade.logic

import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.smarttrade.BuildConfig
import com.example.smarttrade.MainActivity
import com.example.smarttrade.OrderProgress
import com.example.smarttrade.models.Orders.Order_representation
import com.example.smarttrade.models.PersonBuyer
import org.json.JSONObject

object OrderRequests {
    private const val myIP = BuildConfig.MY_IP
    private val url = "https://$myIP"

    var isOrderQueue = false
    lateinit var orderVolleyQueue: RequestQueue


    fun getOrders(){
        if(!isOrderQueue) {
            orderVolleyQueue = Volley.newRequestQueue(MainActivity.getContext())
            isOrderQueue = true
        }

        val dni = PersonBuyer.getDNI()
        val res = mutableListOf<Order_representation>()

        val stringRequest = JsonObjectRequest(
            Request.Method.GET,"$url/buyers/$dni/shipments", null,
            {response ->
                val objects = response.getJSONArray("items")
                for (i in 0 until objects.length()) {
                    val o = objects.getJSONObject(i)
                    val order = Order_representation(
                        o.getString("shippingAdress"),
                        o.getString("billingAdress"),
                        o.getString("paymentMethod"),
                        o.getString("orderID"),
                        o.getString("name"),
                        o.getString("surname"),
                        o.getString("totalPrice"),
                        o.getString("estimatedDate")
                    )
                    res.add(order)
                }
                OrderProgress.setOrders(res)
            },
            {error ->
                Log.i("ErrorGettingOrders", error.message.toString())
            })



        }

    fun addOrder(order: Order_representation){
        if(!isOrderQueue) {
            orderVolleyQueue = Volley.newRequestQueue(MainActivity.getContext())
            isOrderQueue = true
        }

        val dni = PersonBuyer.getDNI()

        val json = JSONObject()
        json.put("name", order.name)
        json.put("surname", order.surname)
        json.put("shippingAdress", order.shippingAddress)
        json.put("billingAdress", order.billingAddress)
        json.put("estimatedDate", order.estimatedDate)
        json.put("totalPrice", order.totalPrice)
        json.put("paymentMethod", order.paymentMethod)
        json.put("status", order.state.stateName)

        val jsonRequest = JsonObjectRequest(
            Request.Method.POST,"$url/buyers/$dni/shipments", json,
            {response ->
                Log.i("OrderAdded", "chingon")
            },
            {error ->
                Log.i("ErrorAñadiendoPedido", error.message.toString())
            })
        orderVolleyQueue.add(jsonRequest)

    }


    }

