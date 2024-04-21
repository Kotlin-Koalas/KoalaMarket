package com.example.smarttrade

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.adapters.ProductAdapter
import com.example.smarttrade.adapters.SearchAdapterFiltered
import com.example.smarttrade.logic.logic

//import com.example.smarttrade.models.LeafColor

import com.example.smarttrade.models.product_representation
import com.example.smarttrade.models.search_representation


class BrowseProductsFiltered : AppCompatActivity() {


    private lateinit var searchBar : EditText
    private lateinit var searchButton : ImageButton
    private lateinit var adapterS: SearchAdapterFiltered
    private lateinit var backButton : ImageButton
    private lateinit var categoryName : TextView
    private lateinit var recommendationLayout: ConstraintLayout
    private lateinit var recommendationRV: RecyclerView
    //private var productsShown: MutableList<product_representation> = mutableListOf()
    private var productsFiltered : MutableList<product_representation> = mutableListOf()
    private var prevSearchesShown: MutableList<search_representation> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        actContextBPF = this


        enableEdgeToEdge()
        setContentView(R.layout.category_filtered)

        val intent = intent
        val nameCategory = getCategoryName(intent)

        categoryName = findViewById(R.id.textViewNameCategory)

        categoryName.setText("Categoría\n" + nameCategory.toString())

        backButton = findViewById(R.id.imageButtonBackfilter)

        recommendationRV = findViewById<RecyclerView>(R.id.recyclerViewSearches)

        //Temporal
        prevSearchesShown.add(search_representation("search de prueba"))
        prevSearchesShown.add(search_representation("search de prueba 2"))

        adapterS = SearchAdapterFiltered(prevSearchesShown)

        recommendationRV.adapter = adapterS
        recommendationRV.layoutManager = LinearLayoutManager(this)

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
            "technology" ->{

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

        //adapterP.addAllProducts(productsShown)


        searchBar = findViewById(R.id.SearchBarFiltered)

        searchButton = findViewById<ImageButton>(R.id.imageButtonSearch)
        searchButton.setOnClickListener{
            val searchItem = searchBar.text.toString().lowercase().trim()
            filterProduct(searchItem)
        }

        val topLayout = findViewById<ConstraintLayout>(R.id.main)
        recommendationLayout = findViewById<ConstraintLayout>(R.id.recomendationsLayout)
        topLayout.bringChildToFront(recommendationLayout)
        recommendationLayout.visibility = View.INVISIBLE

        searchBar.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus && prevSearchesShown.size > 0) {
                //TODO poner de nuevo las busquedas a la lista, añadirlas con el metodo del adaptador y meterlas a la BD
                recommendationLayout.visibility = View.VISIBLE
                searchBar.background = AppCompatResources.getDrawable(
                    this,
                    R.drawable.searchbar_background_desplegado
                )

            } else {
                recommendationLayout.visibility = View.INVISIBLE
                searchBar.background =
                    AppCompatResources.getDrawable(this, R.drawable.rounded_button_cancel)
            }


        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                val rectRecommended = Rect()
                recommendationRV.getGlobalVisibleRect(rectRecommended)
                v.getGlobalVisibleRect(outRect)
                val isWithinCustomArea = rectRecommended.contains(event.rawX.toInt(), event.rawY.toInt())
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt()) && !isWithinCustomArea) {
                    v.clearFocus()
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }


    fun updateSearch(text: String){
        searchBar.setText(text)
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
            actContextBPF.updateSearch(text)
        }
        fun setProductsShown(list:MutableList<product_representation>){
            productsShown = list
            adapterP.updateProducts(productsShown)
        }
        fun getContext(): Context {
            return actContextBPF
        }
    }


}