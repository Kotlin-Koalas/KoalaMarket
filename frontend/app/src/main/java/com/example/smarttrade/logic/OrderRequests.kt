package com.example.smarttrade.logic

import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.smarttrade.BuildConfig
import com.example.smarttrade.MainActivity
import com.example.smarttrade.OrderProgress
import com.example.smarttrade.models.Orders.Order_representation
import com.example.smarttrade.models.PersonBuyer
import org.json.JSONArray
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

        val stringRequest = StringRequest (
            Request.Method.GET,"$url/buyers/$dni/shipments",
            {response ->
                val orders = JSONArray(response)
                for (i in 0 until orders.length()) {
                    val o = orders.getJSONObject(i)
                    val order = Order_representation(
                        o.getString("shippingAddress"),
                        o.getString("billingAddress"),
                        o.getString("paymentMethod"),
                        o.getString("id"),
                        o.getString("name"),
                        o.getString("surname"),
                        o.getString("totalPrice"),
                        o.getString("estimatedDate"),
                        o.getString("cif")
                    )
                    res.add(order)
                }
                Log.i("QUE TIENE QUE SALIRR", res.toString())
                OrderProgress.setOrders(res)
            },
            {error ->
                Log.i("ErrorGettingOrders", error.message.toString())
            })
        orderVolleyQueue.add(stringRequest)


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
        json.put("shippingAddress", order.shippingAddress)
        json.put("billingAddress", order.billingAddress)
        json.put("estimatedDate", order.estimatedDate)
        json.put("totalPrice", order.totalPrice)
        json.put("paymentMethod", order.paymentMethod)
        json.put("status", order.state.stateName)
        json.put("cif", order.cif)

        val jsonRequest = JsonObjectRequest(
            Request.Method.POST,"$url/buyers/$dni/shipments", json,
            {response ->
                val ans = response.toString()
                Log.i("OrderAdded", ans)
            },
            {error ->
                Log.i("ErrorAñadiendoPedido", error.message.toString())
            })
        orderVolleyQueue.add(jsonRequest)

    }

    fun updateState(orderID: String, state: String){
        if(!isOrderQueue) {
            orderVolleyQueue = Volley.newRequestQueue(MainActivity.getContext())
            isOrderQueue = true
        }

        val dni = PersonBuyer.getDNI()

        val json = JSONObject()
        json.put("status", state)

        val jsonRequest = JsonObjectRequest(
            Request.Method.POST,"$url/buyers/$dni/shipments/$orderID/status", json,
            {response ->
                Log.i("OrderUpdated", "State updated successfully to: $state")
            },
            {error ->
                Log.i("ErrorActualizandoPedido", error.message.toString())
            })
        orderVolleyQueue.add(jsonRequest)
    }

}

