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
        val stateAction = view.findViewById<TextView>(R.id.textViewStateAction)
        val nextState = view.findViewById<TextView>(R.id.textViewNextStates)
        val imageState = view.findViewById<ImageView>(R.id.imagenextState)

        textNumber.text = orderList[position].orderID
        totalPrice.text = orderList[position].totalPrice
        estimatedDate.text = orderList[position].estimatedDate
        status.text = orderList[position].state.stateName
        imageState.setImageResource(orderList[position].state.imageResource)


        when (orderList[position].state.stateName) {
            "Preparando pedido" -> {
                nextState.text = "Enviar pedido"
                stateAction.text = "Cancelar pedido"

            }
            "Pedido enviado" -> {
                nextState.text = "Entregar pedido"
                stateAction.isVisible = false


            }
            "Pedido entregado" -> {
                nextState.text = "Devolver pedido"
                stateAction.isVisible = false

            }
            "Pedido devuelto" -> {
                nextState.isVisible = false
                stateAction.isVisible = false

            }
            "Pedido cancelado" -> {
                nextState.text = "AAAAAAAAA"
               // nextState.isVisible = false
                stateAction.isVisible = false


            }
            else ->{
                Log.i("No se ha encontrado el estado", "No se ha encontrado el estado")
            }
        }


        stateAction.setOnClickListener {
            orderList[position].stateAction()

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
