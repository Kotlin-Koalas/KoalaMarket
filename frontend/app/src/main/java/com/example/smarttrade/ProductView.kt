package com.example.smarttrade

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smarttrade.adapters.ProductCartAdapter
import com.example.smarttrade.models.PersonBuyer
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.adapters.SellerAdapter
import com.example.smarttrade.logic.logic
import com.example.smarttrade.models.clothes_representation
import com.example.smarttrade.models.clothes_representation_cart
import com.example.smarttrade.models.clothes_representation_seller
import com.example.smarttrade.models.food_representation
import com.example.smarttrade.models.food_representation_cart
import com.example.smarttrade.models.food_representation_seller
import com.example.smarttrade.models.product_representation
import com.example.smarttrade.models.product_representation_cart
import com.example.smarttrade.models.seller_representation
import com.example.smarttrade.models.technology_representation
import com.example.smarttrade.models.technology_representation_cart
import com.example.smarttrade.models.technology_representation_seller
import com.example.smarttrade.models.toy_representation
import com.example.smarttrade.models.toy_representation_cart
import com.example.smarttrade.models.toy_representation_seller
import com.example.smarttrade.volleyRequestClasses.ImageURLtoBitmapConverter
import com.google.android.material.internal.ViewUtils.getContentView

class ProductView : AppCompatActivity() {

    var currentStock  = 1
    lateinit var adapterS : SellerAdapter
    lateinit var sellerList : MutableList<seller_representation>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        val recyclerView = findViewById<GridView>(R.id.recycledViewSellers)
        sellerList  = mutableListOf()
        adapterS = SellerAdapter(this,sellerList)
        recyclerView.adapter = adapterS

        val product = intent.getSerializableExtra("product") as product_representation

        val name = product.name
        val price = product.price
        val description = product.description
        val image = product.image
        val stock = product.stock
        val productNumber = product.PN
        val category = product.type

        adapterS.setLeafColor(product.leafColor)





        logic.getAllSellers(productNumber)


        val backMainScreen = findViewById<ImageView>(R.id.backArrow)
        val nameText = findViewById<TextView>(R.id.nameProduct)
        val priceText = findViewById<TextView>(R.id.price)
        val stockText = findViewById<TextView>(R.id.stock)
        val descriptionText = findViewById<TextView>(R.id.detailDescriptionProduct)
        val imageViewProduct = findViewById<ImageView>(R.id.imageProduct)
        val addProduct = findViewById<Button>(R.id.buttonSignUp)
        val layoutSum = findViewById<ConstraintLayout>(R.id.layoutSum)
        val layoutSubstract = findViewById<ConstraintLayout>(R.id.layoutSubstract)


        val seller = logic.getSpecificSeller(productNumber,price,stock.toString())

        stockText.text = currentStock.toString()
        nameText.text = name
        priceText.text = price
        descriptionText.text = description

        ImageURLtoBitmapConverter.downloadImageProduct(image,imageViewProduct)


        layoutSum.setOnClickListener {

            if(currentStock < stock){
                currentStock++
                stockText.text = currentStock.toString()
            }

        }

        layoutSubstract.setOnClickListener {

            if(currentStock > 1){
                currentStock--
                stockText.text = currentStock.toString()

            }
        }

        backMainScreen.setOnClickListener {
            val i = Intent(this, BuyerMainScreen::class.java)
            startActivity(i)
        }

        addProduct.setOnClickListener {
            when(category){
                "technology" ->{
                    val sellerTech = seller as technology_representation_seller
                    val cartProduct = technology_representation_cart(
                        name,
                        price,
                        image,
                        stock,
                        description,
                        product.leafColor,
                        productNumber,
                        currentStock,
                        seller!!.name,
                        sellerTech.brand,
                        sellerTech.electricConsumption
                    )
                    PersonBuyer.addProductToCart(cartProduct)
                    showCustomDialogBoxSuccess("Producto añadido al carrito correctamente")
                }
                "toy" ->{
                    val sellerToy = seller as toy_representation_seller
                    val cartProduct = toy_representation_cart(
                        name,
                        price,
                        image,
                        stock,
                        description,
                        product.leafColor,
                        productNumber,
                        currentStock,
                        seller!!.name,
                        sellerToy.material,
                        sellerToy.age
                    )
                    PersonBuyer.addProductToCart(cartProduct)
                    showCustomDialogBoxSuccess("Producto añadido al carrito correctamente")
                }
                "clothes" ->{
                    val sellerClothes = seller as clothes_representation_seller
                    val cartProduct = clothes_representation_cart(
                        name,
                        price,
                        image,
                        stock,
                        description,
                        product.leafColor,
                        productNumber,
                        currentStock,
                        seller!!.name,
                        sellerClothes.size,
                        sellerClothes.color
                    )
                    PersonBuyer.addProductToCart(cartProduct)
                    showCustomDialogBoxSuccess("Producto añadido al carrito correctamente")
                }
                "food" ->{
                    val sellerFood = seller as food_representation_seller
                    val cartProduct = food_representation_cart(
                        name,
                        price,
                        image,
                        stock,
                        description,
                        product.leafColor,
                        productNumber,
                        currentStock,
                        seller!!.name,
                        sellerFood.calories,
                        sellerFood.macros
                    )
                    PersonBuyer.addProductToCart(cartProduct)
                    showCustomDialogBoxSuccess("Producto añadido al carrito correctamente")
                }

            }


        }




    }

    fun setSellersShown(list: MutableList<seller_representation>){
        sellerList = list
        adapterS.setSellerListForInstance(adapterS, sellerList)

    }


    fun showCustomDialogBoxSuccess(msgSuccess: String) {
        val dialog = Dialog(this)
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


    companion object{
        fun setImageViewProduct(image: Bitmap?, view:ImageView){
            Log.i("Image View value", "{$view}")
            if (image != null) {
                view.setImageBitmap(image)
            }
        }



    }
}