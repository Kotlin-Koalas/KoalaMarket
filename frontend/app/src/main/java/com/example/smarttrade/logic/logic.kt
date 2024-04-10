package com.example.smarttrade.logic

import com.example.smarttrade.nonactivityclasses.PersonBuyer
import com.example.smarttrade.nonactivityclasses.PersonSeller
import com.example.smarttrade.nonactivityclasses.product_representation


class logic {
//TODO clase para comunicarse mediante el uso de api y conseguir cosas como el LogIn o el SignUp


    fun filterProduct(producList: MutableList<product_representation>, searchItem:String) : MutableList<product_representation>{
        val filteredProducts : MutableList<product_representation> = mutableListOf()

        for(product in producList){
            if(product.name.toLowerCase().contains(searchItem)){
                filteredProducts.add(product)

            }
        }
        return filteredProducts
    }
}