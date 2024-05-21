package com.example.smarttrade

import android.os.Bundle
import android.widget.GridView
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.smarttrade.adapters.ChangeOrderStateAdapter
import com.example.smarttrade.logic.OrderRequests
import com.example.smarttrade.models.Orders.Order_representation

class ChangeOrderState : AppCompatActivity() {

    lateinit var gridView : GridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order_next_state)

        contextFragment = this

        gridView = findViewById(R.id.gridViewOrders_state)
        adapterO =  ChangeOrderStateAdapter(this, mutableListOf())
        gridView.adapter = adapterO

        orderList = mutableListOf()

        OrderRequests.getOrdersSeller()
        setOrders(orderList)


        val backButton = findViewById<ImageView>(R.id.imageViewBackArrowState)
        backButton.setOnClickListener {
            finish()
        }

    }

    companion object {

        private lateinit var contextFragment: ChangeOrderState
        private lateinit var adapterO: ChangeOrderStateAdapter
        private lateinit var orderList: MutableList<Order_representation>


        fun getContext(): ChangeOrderState {
            return contextFragment
        }


        fun setOrders(list: MutableList<Order_representation>){
            orderList = list
            adapterO.updateOrders(orderList)
        }

    }

}

