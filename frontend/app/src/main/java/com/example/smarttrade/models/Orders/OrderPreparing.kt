package com.example.smarttrade.models.Orders

import com.example.smarttrade.R
import com.example.smarttrade.logic.OrderRequests

class OrderPreparing(order:Order_representation) : OrderState{

    override var imageResource = R.drawable.preparingorder
    override val stateName = "Preparando pedido"

    var order:Order_representation = order
    override fun nextState() {
        order.setStates(order.shipped)
        order.updateStateBD()
    }

    override fun stateAction() {
        order.setStates(order.canceled)
        order.updateStateBD()
    }
}