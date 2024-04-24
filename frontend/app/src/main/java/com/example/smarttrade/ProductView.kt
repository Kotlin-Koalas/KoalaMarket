package com.example.smarttrade

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

        val name = intent.getSerializableExtra("name") as String
        val price = intent.getSerializableExtra("price") as String
        val stock = intent.getSerializableExtra("stock") as Int
        val description = intent.getSerializableExtra("description") as String
        val image = intent.getSerializableExtra("image") as String


        val backMainScreen = findViewById<ImageView>(R.id.backArrow)
        val nameText = findViewById<TextView>(R.id.nameProduct)
        val priceText = findViewById<TextView>(R.id.price)
        val stockText = findViewById<TextView>(R.id.stock)
        val descriptionText = findViewById<TextView>(R.id.detailDescriptionProduct)
        val imageViewProduct = findViewById<ImageView>(R.id.imageProduct)
        val sumStock = findViewById<ImageView>(R.id.addStock)
        val substractStock = findViewById<ImageView>(R.id.substractStock)

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