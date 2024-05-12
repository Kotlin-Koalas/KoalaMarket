package com.example.smarttrade.models.Orders

import android.widget.ImageView
import android.widget.TextView
import com.example.smarttrade.models.PaymentMethods.PaymentMethod
import com.example.smarttrade.models.product_representation_cart

class Order_representation (products:MutableList<product_representation_cart>,shippingAddress:String,billingAddress:String,paymentMethod: PaymentMethod,orderID:String,name:String,surname:String){

    lateinit var imageView:ImageView
    lateinit var textView:TextView

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

    //Metodo para setear la imagen y el texto del estado, llamar antes que nextState().
    fun setImageView(imageView: ImageView){
        this.imageView = imageView
    }

    fun setTextView(textView: TextView){
        this.textView = textView
    }

    //Metodo a llamar para pasar al siguiente estado.
    fun nextState(){
        state.nextState()
    }

    //Metodo a llamar para realizar la accion del estado, como devolver el pedido.
    fun stateAction(){
        state.stateAction()
    }

    fun setStates(state:OrderState){
        this.state = state
        imageView.setImageResource(state.imageResource)
        textView.text = state.stateName
    }
}