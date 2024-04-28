package com.example.smarttrade

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.smarttrade.mainBuyerFrargments.HomeFragment

import androidx.fragment.app.commit
import androidx.fragment.app.add

class SellerMain: AppCompatActivity() {

    //TODO cambiar SellerFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        actContextSell = this


        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<SellerFragment>(R.id.seller_container_view)
        }

        setContentView(R.layout.seller_bottom_bar)


        val addProduct = findViewById<ImageView>(R.id.imageAddProduct)
        addProduct.setOnClickListener {
            //TODO ir a la ventana de añadir producto y cambiar foco del background

        }


        val accountSeller = findViewById<ImageView>(R.id.imageProfileSeller)
        accountSeller.setOnClickListener {
            //TODO ir a la ventana de la cuenta
        }

        val homeSeller = findViewById<ImageView>(R.id.imageHomeSeller)
        homeSeller.setOnClickListener {
            //TODO ir a la ventana de inicio
        }


    }


    companion object{
        private lateinit var actContextSell: SellerMain

        fun getContext(): Context {
            return actContextSell
        }
    }

}