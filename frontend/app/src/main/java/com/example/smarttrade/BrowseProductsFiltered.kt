package com.example.smarttrade

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.smarttrade.logic.logic
import com.example.smarttrade.nonactivityclasses.LeafColor
import com.example.smarttrade.nonactivityclasses.product_representation



class BrowseProductsFiltered : AppCompatActivity() {


    private lateinit var searchBar : EditText
    private lateinit var searchButton : ImageButton
    private lateinit var adapterP: ProductAdapter
    private lateinit var backButton : ImageButton
    private lateinit var categoryName : TextView
    private var productsShown: MutableList<product_representation> = mutableListOf()
    private var productsFiltered : MutableList<product_representation> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        setContentView(R.layout.category_filtered)

        val intent = intent
        val nameCategory = getCategoryName(intent)

        categoryName = findViewById(R.id.textViewNameCategory)

        categoryName.setText("Categoría\n" + nameCategory.toString())

        backButton = findViewById(R.id.imageButtonBackfilter)

        backButton.setOnClickListener{
            val IntentS = Intent(this, BrowseProducts::class.java)
            startActivity(IntentS)
         }

        val gridLayout =findViewById<GridView>(R.id.gridViewCategories)
        adapterP = ProductAdapter(mutableListOf())
        gridLayout.adapter = adapterP


        //TODO conseguir los datos que pertenezcan a la categoría que se haya clickado (productShown tmb)
        //Temporal
        for(i in 1..10){
            productsShown.add(product_representation("","","",1, LeafColor.GREEN,33))
            productsShown.add(product_representation("abc","","",1, LeafColor.GREEN,33))
        }
        adapterP.addAllProducts(productsShown)


        searchBar = findViewById(R.id.SearchBarFiltered)



        searchButton = findViewById<ImageButton>(R.id.imageButtonSearch)
        searchButton.setOnClickListener{
            val searchItem = searchBar.text.toString().toLowerCase().trim()
            filterProduct(searchItem)


        }

        //TODO falta hacer entero el método búsqueda
    }

    private fun getCategoryName(intent: Intent): String?{
        val bundle = intent.extras
        return bundle?.getString("categoryName")
    }



    private fun filterProduct(searchItem :String){
        productsFiltered.clear()
        productsFiltered.addAll(logic.filterProduct(productsShown, searchItem))
        adapterP.updateProducts(productsFiltered)
    }


}