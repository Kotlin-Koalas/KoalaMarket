package com.example.smarttrade.mainSellerFragments

import android.util.Log

class ProductValidator {

    fun validateProductNumber(prodNum : String) : Boolean{
        val patternPN = "^[a-zA-Z0-9]{12}".toRegex()
        return patternPN.matches(prodNum)

    }

    fun validatePrice(produPrice : String) : Boolean{
        val patternPrice = "^[-+]?[0-9]+([.][0-9]{1,2})?$".toRegex()
        return patternPrice.matches(produPrice)
    }

    fun validateProducName(producName: String): Boolean {
        val patterName = "^[a-zA-Z0-9\\s\\.,:;\\-\\(\\)\\/áéíóúñ\\$%&#@]{1,30}$".toRegex()
        return patterName.matches(producName)
    }



    fun validateQuantity(prodQuantity : String) :Boolean{
        val patternQuantity = "^(?!0)[0-9]{1,3}$".toRegex()
        return patternQuantity.matches(prodQuantity)
    }

    fun validateDescrption(prodDescription : String) : Boolean{
       val patternDescription= "^[a-zA-Z0-9\\s\\.,:;\\-\\(\\)\\/áéíóúñ\\$%&#@]{1,150}$".toRegex()
        return patternDescription.matches(prodDescription)
    }

   fun validateCategories(category: String,  prodCat1: String, prodCat2: String) : Boolean{

       when(category){
           "Tecnología" ->{
               val patternCon = "^[-+]?[0-9]+([.][0-9]{1,2})?$".toRegex()
               val patternTech = "^[a-zA-Z0-9 ]+$".toRegex()
               return patternCon.matches(prodCat1) && patternTech.matches(prodCat2)
           }
           "Juguete" ->{
               val patternEdadRec = "^[0-9]{1,2}$".toRegex()
               val patternMaterial = "^[a-zA-Z]+$".toRegex()
                return patternEdadRec.matches(prodCat1) && patternMaterial.matches(prodCat2)
           }
           "Ropa" ->{
               val patternTalla = "^[SMLX]{1,3}$|^([2][0-9]|[3][0-9]|[4][0-5])$".toRegex()
               val patternColor = "^[a-zA-Z]+$".toRegex()
                return patternTalla.matches(prodCat1) && patternColor.matches(prodCat2)

           }
           "Alimentación" ->{
               val patternMac = "^[a-zA-Z0-9 ]+$".toRegex()
               val patternCalorias = "^[0-9]{1,5}$".toRegex()
                return patternMac.matches(prodCat1) && patternCalorias.matches(prodCat2)

           }
           else ->{
               Log.e("Error", "Category not found")
                return false
           }

       }


   }

    fun validateNotEmptyFields(fields : List<String>) :Boolean{
        return fields.all { it.isNotEmpty() }
    }
}

