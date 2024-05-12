package com.example.smarttrade.models.Orders

import com.example.smarttrade.R

class OrderPreparing(order:Order_representation) : OrderState{

    override var imageResource = R.drawable.preparingorder
    override val stateName = "Preparando pedido"

    var order:Order_representation = order
    override fun nextState() {
        order.setStates(order.shipped)
    }

    override fun stateAction() {
        TODO("Not yet implemented")
    }
}