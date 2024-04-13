package com.example.smarttrade

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.logic.logic
import com.example.smarttrade.nonactivityclasses.category_representation
import com.example.smarttrade.nonactivityclasses.product_representation
import com.example.smarttrade.nonactivityclasses.search_representation
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class BrowseProducts : AppCompatActivity() {

    private lateinit var searchBar:EditText
    private lateinit var adapterP: ProductAdapter
    private lateinit var adapterS: SearchAdapter
    private lateinit var adapterCat: CategoryAdapter
    private lateinit var recommendationLayout: ConstraintLayout
    private lateinit var recommendationRV: RecyclerView
    private lateinit var textDescription: TextView

    private var categoriesShown: MutableList<category_representation> = mutableListOf()
    private var prevSearchesShown: MutableList<search_representation> = mutableListOf()
    private var productsFiltered : MutableList<product_representation> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        browseProducts = this
        productsShown = mutableListOf()

        categoriesShown.add(category_representation("toys",R.drawable.icon_toy))
        categoriesShown.add(category_representation("food",R.drawable.icon_food))
        categoriesShown.add(category_representation("technology",R.drawable.icon_phone))
        categoriesShown.add(category_representation("clothes",R.drawable.icon_shirt))

        //Temporal
        prevSearchesShown.add(search_representation("search de prueba"))

        //TODO conseguir los datos de la BD y meterlos a la lista prevSearchesShown

        enableEdgeToEdge()
        setContentView(R.layout.activity_browse_products)

        textDescription = findViewById<TextView>(R.id.textView)

        recommendationRV = findViewById<RecyclerView>(R.id.recyclerViewSearches)

        searchBar = findViewById<EditText>(R.id.SearchBar)

        adapterS = SearchAdapter(prevSearchesShown)

        val catLayout = findViewById<RecyclerView>(R.id.HorizontalScrollViewCat)
        adapterCat = CategoryAdapter(categoriesShown)

        val gridLayout = findViewById<GridView>(R.id.gridViewProd)
        adapterP = ProductAdapter(mutableListOf())

        recommendationRV.adapter = adapterS
        recommendationRV.layoutManager = LinearLayoutManager(this)
        catLayout.adapter = adapterCat
        catLayout.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        gridLayout.adapter = adapterP

        //TODO conseguir los datos de la BD y meterlos a la lista productsShown
        logic.getAllProducts()
        /*
        //Temporal solo para probar
        for(i in 1..5){
            productsShown.add(product_representation("","","",1,"yellow","yellow", "33"))
            productsShown.add(product_representation("aab","","",1,"red","red","34" ))
            productsShown.add(product_representation("aaaab","","",1,"green","green","33"))

        }
        */

        adapterP.addAllProducts(productsShown)

        val topLayout = findViewById<ConstraintLayout>(R.id.constraintLayoutTop)
        recommendationLayout = findViewById<ConstraintLayout>(R.id.recomendationsLayout)
        topLayout.bringChildToFront(recommendationLayout)
        recommendationLayout.visibility = View.INVISIBLE

        val startSearch = findViewById<ImageView>(R.id.imageViewSearchButton)
        startSearch.setOnClickListener{
            textDescription.text = "Resultados de la búsqueda:"
            val searchItem = searchBar.text.toString().toLowerCase().trim()
            filterProduct(searchItem)
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
            if(hasFocus && prevSearchesShown.size>0){
                //TODO poner de nuevo las busquedas a la lista, añadirlas con el metodo del adaptador y meterlas a la BD
                recommendationLayout.visibility = View.VISIBLE
                searchBar.background = AppCompatResources.getDrawable(
                    this,
                    R.drawable.searchbar_background_desplegado
                )

            } else {
                recommendationLayout.visibility = View.INVISIBLE
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



    companion object{
        private lateinit var browseProducts: BrowseProducts
        private lateinit var productsShown: MutableList<product_representation>
        fun updateSearch(text:String) {
            browseProducts.updateSearch(text)
        }
        fun setProductsShown(list:MutableList<product_representation>){
            productsShown = list
        }
    }


    //val logic = logic()
    private fun filterProduct(searchItem :String){
        productsFiltered.clear()
        productsFiltered.addAll(logic.filterProduct(productsShown, searchItem))
        adapterP.updateProducts(productsFiltered)
    }


}