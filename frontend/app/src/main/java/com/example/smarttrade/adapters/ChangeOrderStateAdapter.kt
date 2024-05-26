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

class ChangeOrderStateAdapter(
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
            .inflate(R.layout.order_view_next_state, parent, false)

        val textNumber = view.findViewById<TextView>(R.id.textViewNumerOrder_nextState)
        val totalPrice = view.findViewById<TextView>(R.id.textTotalPrice_nextState)
        val estimatedDate = view.findViewById<TextView>(R.id.textViewEstimateDate_nextState)
        val status = view.findViewById<TextView>(R.id.textViewEstado_nextState)

        val nextState = view.findViewById<TextView>(R.id.textViewNextStates)
        val imageState = view.findViewById<ImageView>(R.id.imagenextState)

        textNumber.text = orderList[position].orderID
        totalPrice.text = orderList[position].totalPrice
        estimatedDate.text = orderList[position].estimatedDate



        when (orderList[position].status) {
            "Preparando pedido" -> {
                nextState.isVisible = true
                nextState.text = "Enviar pedido"

                status.text = OrderPreparing(orderList[position]).stateName
                imageState.setImageResource(OrderPreparing(orderList[position]).imageResource)


            }
            "Pedido enviado" -> {
                nextState.isVisible = true
                nextState.text = "Entregar pedido"

                status.text = OrderShipped(orderList[position]).stateName
                imageState.setImageResource(OrderShipped(orderList[position]).imageResource)
                notifyDataSetChanged()


            }
            "Pedido entregado" -> {
                nextState.isVisible = false
                nextState.text = "Devolver pedido"
                status.text = OrderDelivered(orderList[position]).stateName
                imageState.setImageResource(OrderDelivered(orderList[position]).imageResource)
                notifyDataSetChanged()

            }
            "Pedido devuelto" -> {
                nextState.isVisible = false

                status.text = OrderReturned(orderList[position]).stateName
                imageState.setImageResource(OrderReturned(orderList[position]).imageResource)
                notifyDataSetChanged()


            }
            "Pedido cancelado" -> {
                nextState.isVisible = false
                status.text = OrderCanceled(orderList[position]).stateName
                imageState.setImageResource(OrderCanceled(orderList[position]).imageResource)
                notifyDataSetChanged()

            }
            else ->{
                Log.i("No se ha encontrado el estado", "No se ha encontrado el estado")
            }
        }



        nextState.setOnClickListener {
            orderList[position].setIView(imageState)
            orderList[position].setTView(status)
            orderList[position].nextState()
            notifyDataSetChanged()



        }


        return view
    }


}
