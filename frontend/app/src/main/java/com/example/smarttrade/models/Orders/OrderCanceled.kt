package com.example.smarttrade.models.Orders

import com.example.smarttrade.R

class OrderCanceled(order:Order_representation) : OrderState {

    override var imageResource = R.drawable.order_canceled_image
    override val stateName = "Pedido cancelado"

    var order:Order_representation = order

    override fun nextState() {
        //no hay siguiente estado
    }

    override fun stateAction() {
        //no hay acción
    }
}