package com.example.smarttrade.models.Orders

import com.example.smarttrade.R

class OrderReturned(order:Order_representation) : OrderState {

    override var imageResource = R.drawable.returnedorder
    override val stateName = "Pedido devuelto"

    var order:Order_representation = order

    override fun nextState() {
        //se acabó, no hay mas estados
    }

    override fun stateAction() {
        //se acabó no hay más cosas que hacer
    }
}