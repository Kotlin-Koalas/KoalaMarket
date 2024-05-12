package com.example.smarttrade.models.Orders

interface OrderState {
    val imageResource: Int
    val stateName:String
    public fun nextState()
    public fun stateAction()
}