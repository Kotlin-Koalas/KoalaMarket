package com.example.smarttrade

import android.os.Bundle
import android.widget.GridView
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.smarttrade.adapters.OrderAdapter
import com.example.smarttrade.logic.OrderRequests
import com.example.smarttrade.models.Orders.Order_representation

class OrderProgress :AppCompatActivity() {


    lateinit var gridView : GridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order_progress)

        contextFragment = this

        gridView = findViewById(R.id.gridViewOrders)
        adapterO = OrderAdapter(this, mutableListOf())
        gridView.adapter = adapterO

        orderList = mutableListOf()

        OrderRequests.getOrders()
        setOrders(orderList)

        val backButton = findViewById<ImageView>(R.id.imageViewBackArrow)
        backButton.setOnClickListener{
            finish()
        }

    }


    companion object {

        private lateinit var contextFragment: OrderProgress
        private lateinit var adapterO: OrderAdapter
        private lateinit var orderList: MutableList<Order_representation>


        fun getContext(): OrderProgress {
            return contextFragment
        }


        fun setOrders(list: MutableList<Order_representation>){
            orderList = list
            adapterO.updateOrders(orderList)
        }

    }

}