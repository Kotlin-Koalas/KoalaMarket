package com.example.smarttrade.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.smarttrade.R
import com.example.smarttrade.models.Orders.Order_representation


class OrderAdapter(
    private val context : Context,
    private val orderList : MutableList<Order_representation>
) : BaseAdapter() {

    override fun getCount(): Int {
        return orderList.count()
    }

    override fun getItem(position: Int): Any {
        return orderList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun addOrder(order: Order_representation) {
        orderList.add(order)
        notifyDataSetChanged()
    }

    fun addAllOrders(orderList: MutableList<Order_representation>) {
        for (order in orderList) {
            this.orderList.add(order)
        }
        notifyDataSetChanged()
    }

    fun removeOrder(position: Int) {
        orderList.removeAt(position)
        notifyDataSetChanged()
    }

    fun notifyAddedOrder(order: Order_representation) {
        notifyDataSetChanged()
    }

    fun updateOrders(updateOrderList: MutableList<Order_representation>) {
        orderList.clear()
        orderList.addAll(updateOrderList)
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(parent?.context)
            .inflate(R.layout.order_view, parent, false)

        val textNumber = view.findViewById<TextView>(R.id.textViewNumerOrder)
        val totalPrice = view.findViewById<TextView>(R.id.textTotalPrice)
        val estimatedDate = view.findViewById<TextView>(R.id.textViewEstimateDate)
        val status = view.findViewById<TextView>(R.id.textViewEstado)

        val imageState = view.findViewById<ImageView>(R.id.imageState)

        val dni = orderList[position].dni
        textNumber.text = orderList[position].orderID
        totalPrice.text = orderList[position].totalPrice
        estimatedDate.text = orderList[position].estimatedDate
        status.text = orderList[position].state.stateName


        when (orderList[position].state.stateName) {
            "Preparando" -> {
                imageState.setImageResource(R.drawable.order_preparing)
            }
            "En camino" -> {
                imageState.setImageResource(R.drawable.order_shipped)
            }
            "Entregado" -> {
                imageState.setImageResource(R.drawable.order_delivery)
            }
            "Devolución" -> {
                imageState.setImageResource(R.drawable.order_returned)
            }
            else -> {
                imageState.setImageResource(R.drawable.addition)
            }
        }

        return view
    }


    companion object {
        var view = mutableListOf<View>()
    }

}