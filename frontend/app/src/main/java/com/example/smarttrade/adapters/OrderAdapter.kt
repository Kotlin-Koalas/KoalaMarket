package com.example.smarttrade.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.smarttrade.R
import com.example.smarttrade.models.Orders.OrderCanceled
import com.example.smarttrade.models.Orders.OrderDelivered
import com.example.smarttrade.models.Orders.OrderPreparing
import com.example.smarttrade.models.Orders.OrderReturned
import com.example.smarttrade.models.Orders.OrderShipped
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
        val stateAction = view.findViewById<TextView>(R.id.textViewStateAction)

        val imageState = view.findViewById<ImageView>(R.id.imageState)

        textNumber.text = orderList[position].orderID
        totalPrice.text = orderList[position].totalPrice
        estimatedDate.text = orderList[position].estimatedDate


        when (orderList[position].status) {
            "Preparando pedido" -> {
                status.text = OrderPreparing(orderList[position]).stateName
                imageState.setImageResource(OrderPreparing(orderList[position]).imageResource)
                stateAction.text = "Cancelar pedido"

            }
            "Pedido enviado" -> {
                status.text = OrderShipped(orderList[position]).stateName
                imageState.setImageResource(OrderShipped(orderList[position]).imageResource)
                stateAction.isVisible = false


            }
            "Pedido entregado" -> {
                status.text = OrderDelivered(orderList[position]).stateName
                imageState.setImageResource(OrderDelivered(orderList[position]).imageResource)
                stateAction.text = "Devolver Pedido"


            }
            "Pedido devuelto" -> {
                stateAction.isVisible = false
                status.text = OrderReturned(orderList[position]).stateName
                imageState.setImageResource(OrderReturned(orderList[position]).imageResource)


            }
            "Pedido cancelado" -> {
                stateAction.isVisible = false
                status.text = OrderCanceled(orderList[position]).stateName
                imageState.setImageResource(OrderCanceled(orderList[position]).imageResource)


            }
            else ->{
                Log.i("No se ha encontrado el estado", "No se ha encontrado el estado")
            }
        }

        stateAction.setOnClickListener {
            orderList[position].setIView(imageState)
            orderList[position].setTView(status)
            orderList[position].stateAction()
            notifyDataSetChanged()

        }


        return view
    }


    companion object {
        var view = mutableListOf<View>()
    }

}