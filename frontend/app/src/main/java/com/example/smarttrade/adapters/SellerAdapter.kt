package com.example.smarttrade.adapters

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.smarttrade.R
import com.example.smarttrade.logic.ShoppingCartRequests
import com.example.smarttrade.models.clothes_representation_cart
import com.example.smarttrade.models.clothes_representation_seller
import com.example.smarttrade.models.food_representation_cart
import com.example.smarttrade.models.food_representation_seller
import com.example.smarttrade.models.seller_representation
import com.example.smarttrade.models.technology_representation_cart
import com.example.smarttrade.models.technology_representation_seller
import com.example.smarttrade.models.toy_representation_cart
import com.example.smarttrade.models.toy_representation_seller

class SellerAdapter(

    private val context : Context,

    private val sellerList : MutableList<seller_representation>

) : BaseAdapter()
 {

    init {
        who = this
        leafColor = "green"
    }
     override fun getCount(): Int {
         return sellerList.count()
     }

     fun setSellerListForInstance(adapter: SellerAdapter, sellerList: MutableList<seller_representation>){

         adapter.sellerList.clear()
         adapter.sellerList.addAll(sellerList)
         adapter.notifyDataSetChanged()

     }

     override fun getItem(position: Int): Any {
         return sellerList.get(position)
     }

     override fun getItemId(position: Int): Long {
         return position.toLong()
     }

     override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
         val view = convertView ?: LayoutInflater.from(parent?.context)
             .inflate(R.layout.seller_representation, parent, false)

         who = this

         val textNameSeller = view.findViewById<TextView>(R.id.sellerName)
         val textPrice = view.findViewById<TextView>(R.id.price)
         val stockText = view.findViewById<TextView>(R.id.stockProduct)
         val cart = view.findViewById<ImageView>(R.id.carritoImageView)
         val specificAttirbutes = view.findViewById<TextView>(R.id.specificAttribute)

         textNameSeller.text = sellerList[position].vendorName
         textPrice.text = sellerList[position].price
         stockText.text = "Stock " + sellerList[position].stock

         val category = sellerList[position].category
         when(category){
             "toy" -> {
                 val toySeller = sellerList[position] as toy_representation_seller
                    specificAttirbutes.text = "Material " + toySeller.material

             }
             "food" ->{
                 val foodSeller = sellerList[position] as food_representation_seller
                 specificAttirbutes.text = foodSeller.calories + " cal"

             }
             "technology" -> {
                 val techSeller = sellerList[position] as technology_representation_seller
                 specificAttirbutes.text = "Marca: " + techSeller.brand
             }
             "clothes" ->{
                 val clothSeller = sellerList[position] as clothes_representation_seller
                 specificAttirbutes.text = "Talla " + clothSeller.size

             }


         }



         cart.setOnClickListener {
             when(category){
                 "toy" ->{
                     val toy = sellerList[position] as toy_representation_seller
                     val productToy = toy_representation_cart(toy.cif,toy.name, toy.price, toy.image, toy.stock.toInt(), toy.description, leafColor, toy.productNumber, 1, toy.vendorName, toy.material, toy.age)
                     ShoppingCartRequests.getProductInCart(productToy.PN, productToy.cif, productToy.seller, productToy)

                 }
                    "food" ->{
                        val food = sellerList[position] as food_representation_seller
                        val productFood = food_representation_cart(food.cif,food.name, food.price, food.image, food.stock.toInt(), food.description, leafColor, food.productNumber, 1, food.vendorName, food.calories, food.macros)
                        ShoppingCartRequests.getProductInCart(productFood.PN, productFood.cif, productFood.seller, productFood)

                    }
                    "technology" ->{
                        val tech = sellerList[position] as technology_representation_seller
                        val productTech = technology_representation_cart(tech.cif,tech.name, tech.price, tech.image, tech.stock.toInt(), tech.description, leafColor, tech.productNumber, 1, tech.vendorName, tech.brand, tech.electricConsumption)
                        ShoppingCartRequests.getProductInCart(productTech.PN, productTech.cif, productTech.seller, productTech)

                    }
                    "clothes" ->{
                        val clothes = sellerList[position] as clothes_representation_seller
                        val productClothes = clothes_representation_cart(clothes.cif,clothes.name, clothes.price, clothes.image, clothes.stock.toInt(), clothes.description, leafColor, clothes.productNumber, 1, clothes.vendorName, clothes.size, clothes.color)
                        ShoppingCartRequests.getProductInCart(productClothes.PN, productClothes.cif, productClothes.seller, productClothes)

                    }



             }



         }

         return view
     }

     fun showCustomDialogBoxSuccess(msgSuccess: String) {
         val dialog = Dialog(context)
         dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
         dialog.setCancelable(false)
         dialog.setContentView(R.layout.pop_up_alert_success)
         dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

         val messageBox = dialog.findViewById<TextView>(R.id.textViewErrorText)
         val btnOk = dialog.findViewById<Button>(R.id.buttonOkPopUp)

         btnOk.setOnClickListener {
             dialog.dismiss()
         }
         messageBox.text = msgSuccess

         dialog.show()
     }
     fun setLeafColor(color: String){
         leafColor = color
     }



     companion object{
         private lateinit var who: SellerAdapter
         private lateinit var leafColor: String
         fun setSellerList(sellerList: MutableList<seller_representation>){
             who.sellerList.clear()
             who.sellerList.addAll(sellerList)
             who.notifyDataSetChanged()
         }







     }


 }