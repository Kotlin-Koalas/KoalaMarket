package com.example.smarttrade.models.Orders

import com.example.smarttrade.R

class OrderShipped(order:Order_representation) : OrderState {

    override var imageResource = R.drawable.shippedorder
    override val stateName = "Pedido enviado"

    var order:Order_representation = order
    override fun nextState() {
        order.setStates(order.delivered)
        //TODO peticion a la API de pasar al siguiente estado
    }

    override fun stateAction() {
        TODO("Not yet implemented")
    }
}