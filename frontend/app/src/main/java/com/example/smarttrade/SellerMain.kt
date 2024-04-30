package com.example.smarttrade

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.smarttrade.mainBuyerFragments.HomeFragment

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


        val addProduct = findViewById<ConstraintLayout>(R.id.constraintLayoutAddProductSeller)//TODO PONER CONSTRAINT LAYOUT
        val accountSeller = findViewById<ConstraintLayout>(R.id.constraintLayoutProfileSeller)
        val homeSeller = findViewById<ConstraintLayout>(R.id.constraintLayoutHomeSeller)

        addProduct.setOnClickListener {


        }



        accountSeller.setOnClickListener {
            //TODO ir a la ventana de la cuenta
        }


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