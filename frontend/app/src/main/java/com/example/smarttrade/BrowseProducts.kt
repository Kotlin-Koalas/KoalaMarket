package com.example.smarttrade

import android.app.appsearch.AppSearchManager.SearchContext
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.GridView
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.nonactivityclasses.LeafColor
import com.example.smarttrade.nonactivityclasses.category_representation
import com.example.smarttrade.nonactivityclasses.product_representation
import com.example.smarttrade.nonactivityclasses.search_representation
import kotlin.properties.Delegates

class BrowseProducts : AppCompatActivity() {

    private lateinit var searchBar:EditText
    private lateinit var adapterP: ProductAdapter
    private lateinit var adapterS: SearchAdapter
    private lateinit var adapterCat: CategoryAdapter
    private lateinit var recommendationLayout: ConstraintLayout
    private var productsShown: MutableList<product_representation> = mutableListOf()
    private var categoriesShown: MutableList<category_representation> = mutableListOf()
    private var prevSearchesShown: MutableList<search_representation> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        browseProducts = this

        categoriesShown.add(category_representation("toys",R.drawable.icon_toy))
        categoriesShown.add(category_representation("food",R.drawable.icon_food))
        categoriesShown.add(category_representation("technology",R.drawable.icon_phone))
        categoriesShown.add(category_representation("clothes",R.drawable.icon_shirt))

        //Temporal
        prevSearchesShown.add(search_representation("search de prueba"))

        //TODO conseguir los datos de la BD y meterlos a la lista prevSearchesShown

        enableEdgeToEdge()
        setContentView(R.layout.activity_browse_products)

        searchBar = findViewById<EditText>(R.id.SearchBar)

        val searchLayout = findViewById<RecyclerView>(R.id.recyclerViewSearches)
        adapterS = SearchAdapter(prevSearchesShown)

        val catLayout = findViewById<RecyclerView>(R.id.HorizontalScrollViewCat)
        adapterCat = CategoryAdapter(categoriesShown)

        val gridLayout = findViewById<GridView>(R.id.gridViewProd)
        adapterP = ProductAdapter(mutableListOf())

        searchLayout.adapter = adapterS
        searchLayout.layoutManager = LinearLayoutManager(this)
        catLayout.adapter = adapterCat
        catLayout.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        gridLayout.adapter = adapterP

        //TODO conseguir los datos de la BD y meterlos a la lista productsShown
        //Temporal solo para probar
        for(i in 1..30){
            productsShown.add(product_representation("","","",1,LeafColor.GREEN,33))
        }

        adapterP.addAllProducts(productsShown)

        val topLayout = findViewById<ConstraintLayout>(R.id.constraintLayoutTop)
        recommendationLayout = findViewById<ConstraintLayout>(R.id.recomendationsLayout)
        val recommendationRV = findViewById<RecyclerView>(R.id.recyclerViewSearches)
        topLayout.bringChildToFront(recommendationLayout)
        recommendationRV.visibility = View.INVISIBLE

        val startSearch = findViewById<ImageView>(R.id.imageViewSearchButton)
        startSearch.setOnClickListener{
            //TODO search method
        }

        val fav = findViewById<ImageView>(R.id.imageViewHeart)
        fav.setOnClickListener{
            //TODO ir a la ventana de los favs
        }

        val shoppingCart = findViewById<ImageView>(R.id.imageViewCarrito)
        shoppingCart.setOnClickListener{
            //TODO ir a la ventana del carrito
        }

        searchBar.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                recommendationRV.visibility = View.VISIBLE
                searchBar.background = AppCompatResources.getDrawable(this,R.drawable.searchbar_background_desplegado)

            } else {
                recommendationRV.visibility = View.INVISIBLE
                searchBar.background = AppCompatResources.getDrawable(this,R.drawable.rounded_button_cancel)
            }
        }
    }

    fun updateSearch(text: String){
        searchBar.setText(text)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                val rectRecommended = Rect()
                recommendationLayout.getGlobalVisibleRect(rectRecommended)
                v.getGlobalVisibleRect(outRect)
                val isWithinCustomArea = rectRecommended.contains(event.rawX.toInt(), event.rawY.toInt())
                Toast.makeText(this, isWithinCustomArea.toString(), Toast.LENGTH_SHORT).show()
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt()) && !isWithinCustomArea) {
                    v.clearFocus()
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }



    companion object{
        private lateinit var browseProducts: BrowseProducts
        fun updateSearch(text:String) {
            browseProducts.updateSearch(text)
        }
    }


}