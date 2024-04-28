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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smarttrade.adapters.ProductCartAdapter
import com.example.smarttrade.models.PersonBuyer
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.adapters.SellerAdapter
import com.example.smarttrade.models.seller_representation
import com.example.smarttrade.volleyRequestClasses.ImageURLtoBitmapConverter
import com.google.android.material.internal.ViewUtils.getContentView

class ProductView : AppCompatActivity() {

    var currentStock  = 0
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
        val sellerList : MutableList<seller_representation> = mutableListOf()
        //TODO: Prueba para saber si funciona
        //sellerList.add(seller_representation("Pepe","5","5.7","Talla : S"))
        //sellerList.add(seller_representation("Paco","20","1.24","Talla : M"))
        //sellerList.add(seller_representation("Kiko","15","21.30","Talla : L"))
        val adapter = SellerAdapter(sellerList)
        recyclerView.adapter = adapter




        val name = intent.getSerializableExtra("name") as String
        val price = intent.getSerializableExtra("price") as String
        val stock = intent.getSerializableExtra("stock") as Int
        val description = intent.getSerializableExtra("description") as String
        val image = intent.getSerializableExtra("image") as String
        val productNumber = intent.getSerializableExtra("product number") as String




        val backMainScreen = findViewById<ImageView>(R.id.backArrow)
        val nameText = findViewById<TextView>(R.id.nameProduct)
        val priceText = findViewById<TextView>(R.id.price)
        val stockText = findViewById<TextView>(R.id.stock)
        val descriptionText = findViewById<TextView>(R.id.detailDescriptionProduct)
        val imageViewProduct = findViewById<ImageView>(R.id.imageProduct)
        val sumStock = findViewById<ImageView>(R.id.addStock)
        val substractStock = findViewById<ImageView>(R.id.substractStock)
        val addProduct = findViewById<Button>(R.id.buttonSignUp)





        nameText.text = name
        priceText.text = price
        descriptionText.text = description

        ImageURLtoBitmapConverter.downloadImageProduct(image,imageViewProduct)


        sumStock.setOnClickListener {

            if(currentStock < stock){
                currentStock++
                stockText.text = currentStock.toString()
            }

        }

        substractStock.setOnClickListener {

            if(currentStock > 0){
                currentStock--
                stockText.text = currentStock.toString()

            }
        }

        backMainScreen.setOnClickListener {
            val i = Intent(this, BuyerMainScreen::class.java)
            startActivity(i)
        }

        addProduct.setOnClickListener {
            //val product = product_representation_cart(...)
            //PersonBuyer.addProductToCart(product)
            //showCustomDialogBoxSuccess("Producto añadido al carrito correctamente")
        }


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