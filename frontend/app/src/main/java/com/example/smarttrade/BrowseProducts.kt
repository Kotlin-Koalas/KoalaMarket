package com.example.smarttrade

import android.os.Bundle
import android.widget.GridLayout
import android.widget.GridView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smarttrade.nonactivityclasses.LeafColor
import com.example.smarttrade.nonactivityclasses.product_representation

class BrowseProducts : AppCompatActivity() {

    private lateinit var adapterP: ProductAdapter
    private var productsShown: MutableList<product_representation> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_browse_products)
        val gridLayout = findViewById<GridView>(R.id.gridViewProd)
        adapterP = ProductAdapter(mutableListOf())

        gridLayout.adapter = adapterP

        //TODO conseguir los datos de la BD y meterlos a la lista popularProducts
        //Temporal solo para probar
        for(i in 1..30){
            productsShown.add(product_representation("","","",1,LeafColor.GREEN,33))
        }
        adapterP.addAllProducts(productsShown)

    }
}