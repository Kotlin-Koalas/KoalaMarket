package com.example.smarttrade

import android.os.Bundle
import android.widget.GridView
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.smarttrade.adapters.OrderAdapter
import com.example.smarttrade.logic.OrderRequests
import com.example.smarttrade.models.Orders.Order_representation
import com.example.smarttrade.models.PaymentMethods.PaymentMethodPaypal
import com.example.smarttrade.models.PersonBuyer
import com.example.smarttrade.models.product_representation_cart

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

        OrderRequests.getOrders(PersonBuyer.getDNI())

        //TODO eliminar ES EJEMPLO
        val product = product_representation_cart(cif = "cifExample", category = "categoryExample", name = "nameExample", price = "priceExample", image = "imageExample", stock = 10, description = "descriptionExample", leafColor = "leafColorExample", PN = "PNExample", quantity = 1, seller = "sellerExample")
        val paymentMethod = PaymentMethodPaypal("details1")
        for (i in 1..7) {
            val order = Order_representation(
                mutableListOf(product),
                "vashippingAddress$i",
                "billingAddress$i",
                paymentMethod,
                "orderID$i",
                "name$i",
                "surname$i",
                "dni$i",
                "totalPrice$i",
                "estimatedDate$i"
            )
            orderList.add(order)
        }
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