package com.example.smarttrade

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.smarttrade.logic.logic

//import com.example.smarttrade.nonactivityclasses.LeafColor

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

        BrowseProductsFiltered.actContextBPF = this


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

        when(nameCategory.toString()){
            "toys" -> {
                logic.getAllProductsToys()

            }
            "food" ->{
                logic.getAllProductsFood()
            }
            "techology" ->{
                logic.getAllProductsTechnology()
            }
            "clothes" ->{
                logic.getAllProductsClothes()
            }
            else ->{

                try{
                    Log.i("QUE PASA", "NO SE")
                }catch (exception :Exception){
                    Log.e("Error" , exception.toString())
                }

            }

        }

        adapterP.addAllProducts(productsShown)


        searchBar = findViewById(R.id.SearchBarFiltered)

        searchButton = findViewById<ImageButton>(R.id.imageButtonSearch)
        searchButton.setOnClickListener{
            val searchItem = searchBar.text.toString().lowercase().trim()
            filterProduct(searchItem)


        }


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




    companion object{
        private lateinit var actContextBPF:BrowseProductsFiltered
        private lateinit var productsShown: MutableList<product_representation>
        private lateinit var adapterP: ProductAdapter
        fun updateSearch(text:String) {
            //actContextBPF.updateSearch(text)
        }
        fun setProductsShown(list:MutableList<product_representation>){
            productsShown = list
            adapterP.addAllProducts(productsShown)
        }
        fun getContext(): Context {
            return actContextBPF
        }
    }


}