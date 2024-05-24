package com.example.smarttrade.models.Orders

import android.widget.ImageView
import android.widget.TextView
import com.example.smarttrade.logic.OrderRequests
import com.example.smarttrade.models.CreditCard
import com.example.smarttrade.models.PaymentMethods.PaymentMethod
import com.example.smarttrade.models.PaymentMethods.PaymentMethodBizum
import com.example.smarttrade.models.PaymentMethods.PaymentMethodCreditCard
import com.example.smarttrade.models.PaymentMethods.PaymentMethodPaypal
import com.example.smarttrade.models.PersonBuyer

class Order_representation (
                            val shippingAddress:String,
                            val billingAddress:String,
                            val paymentMethod: String,
                            val orderID:String,
                            val name:String,
                            val surname:String,
                            val totalPrice: String,
                            val estimatedDate: String,
                            val dni : String,
                            val cif : String,
                            var status: String){

    lateinit var imageView:ImageView
    lateinit var textView:TextView

    lateinit var shipped:OrderState
    lateinit var delivered:OrderState
    lateinit var preparing:OrderState
    lateinit var returned:OrderState
    lateinit var canceled:OrderState

    init{
        shipped = OrderShipped(this)
        delivered = OrderDelivered(this)
        preparing = OrderPreparing(this)
        returned = OrderReturned(this)
        canceled = OrderCanceled(this)
        pMethod = paymentMethod
    }

    var state:OrderState = preparing


    //Metodo para setear la imagen y el texto del estado, llamar antes que nextState().
    fun setIView(imageView: ImageView){
        this.imageView = imageView
    }

    fun setTView(textView: TextView){
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

    fun updateStateBD(){
        OrderRequests.updateState(orderID, state.stateName, dni)
    }



    companion object{
        lateinit var pMethod:String
        fun getPayMethod() : PaymentMethod{
            return when(pMethod){
                "PayPal" -> PaymentMethodPaypal(PersonBuyer.getPaypal())
                "CreditCard" -> PaymentMethodCreditCard(PaymentMethodCreditCard.getCreditCard())
                "Bizum" -> PaymentMethodBizum(PersonBuyer.getBizum())
                else -> throw IllegalArgumentException("Unknown payment method: $pMethod")
            }
        }
    }

}

