package com.example.smarttrade.models.Orders

import com.example.smarttrade.R

class OrderDelivered(order:Order_representation) : OrderState {

    override var imageResource = R.drawable.deliveredorder
    override val stateName = "Pedido entregado"

    var order:Order_representation = order

    override fun nextState() {
        order.setStates(order.returned)
        order.updateStateBD()
        order.status = "Pedido devuelto"
    }

    override fun stateAction() {
        order.setStates(order.returned)
        order.updateStateBD()
        order.status = "Pedido devuelto"
    }
}