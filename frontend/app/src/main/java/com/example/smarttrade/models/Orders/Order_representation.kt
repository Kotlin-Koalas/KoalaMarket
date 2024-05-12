package com.example.smarttrade.models.Orders

class Order_representation () {

    lateinit var shipped:OrderState
    lateinit var delivered:OrderState
    lateinit var preparing:OrderState
    lateinit var returned:OrderState

    var state:OrderState = preparing

    init{
        shipped = OrderShipped(this)
        delivered = OrderDelivered(this)
        preparing = OrderPreparing(this)
        returned = OrderReturned(this)
    }

    fun setStates(state:OrderState){
        this.state = state
        //TODO: set the image of the new state and state name
    }

}