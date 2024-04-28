package com.example.smarttrade.mainBuyerFrargments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.BuyerMainScreen
import com.example.smarttrade.R
import com.example.smarttrade.adapters.CategoryAdapter
import com.example.smarttrade.adapters.ProductAdapter
import com.example.smarttrade.adapters.SearchAdapter
import com.example.smarttrade.logic.logic
import com.example.smarttrade.models.category_representation
import com.example.smarttrade.models.product_representation
import com.example.smarttrade.models.search_representation

class HomeFragment : Fragment() {
    private lateinit var searchBar: EditText
    private lateinit var adapterS: SearchAdapter
    private lateinit var adapterCat: CategoryAdapter
    private lateinit var recommendationLayout: ConstraintLayout
    private lateinit var recommendationRV: RecyclerView
    private lateinit var textDescription: TextView
    private lateinit var view: View

    private var categoriesShown: MutableList<category_representation> = mutableListOf()
    private var prevSearchesShown: MutableList<search_representation> = mutableListOf()
    private var productsFiltered : MutableList<product_representation> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        */
    }

    fun updateSearch(text: String){
        searchBar.setText(text)
    }

    private fun filterProduct(searchItem :String){
        productsFiltered.clear()
        productsFiltered.addAll(logic.filterProduct(productsShown, searchItem))
        Log.i("productsFiletered", productsFiltered.toString())
        adapterP.updateProducts(productsFiltered)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_home, container, false)

        instance = this

        actContextBP = BuyerMainScreen.getContext()
        productsShown = mutableListOf()


        categoriesShown.add(category_representation("toys",R.drawable.icon_toy))
        categoriesShown.add(category_representation("food",R.drawable.icon_food))
        categoriesShown.add(category_representation("technology",R.drawable.icon_phone))
        categoriesShown.add(category_representation("clothes",R.drawable.icon_shirt))

        //Temporal
        prevSearchesShown.add(search_representation("search de prueba"))

        //TODO conseguir los datos de la BD y meterlos a la lista prevSearchesShown



        textDescription = view.findViewById<TextView>(R.id.textView)

        recommendationRV = view.findViewById<RecyclerView>(R.id.recyclerViewSearches)

        searchBar = view.findViewById<EditText>(R.id.SearchBar)

        adapterS = SearchAdapter(prevSearchesShown)

        val catLayout = view.findViewById<RecyclerView>(R.id.HorizontalScrollViewCat)
        adapterCat = CategoryAdapter(categoriesShown)

        val gridLayout = view.findViewById<GridView>(R.id.gridViewProd)
        adapterP = ProductAdapter(mutableListOf())

        recommendationRV.adapter = adapterS
        recommendationRV.layoutManager = LinearLayoutManager(actContextBP)
        catLayout.adapter = adapterCat
        catLayout.layoutManager = LinearLayoutManager(actContextBP, LinearLayoutManager.HORIZONTAL,false)
        gridLayout.adapter = adapterP

        //TODO conseguir los datos de la BD y meterlos a la lista productsShown
        logic.getAllProducts()


        val topLayout = view.findViewById<ConstraintLayout>(R.id.constraintLayoutTop)
        recommendationLayout = view.findViewById<ConstraintLayout>(R.id.recomendationsLayout)
        topLayout.bringChildToFront(recommendationLayout)
        recommendationLayout.visibility = View.INVISIBLE

        val startSearch = view.findViewById<ImageView>(R.id.imageViewSearchButton)
        startSearch.setOnClickListener{
            textDescription.text = "Resultados de la búsqueda:"
            val searchItem = searchBar.text.toString().lowercase().trim()
            filterProduct(searchItem)
        }

        val fav = view.findViewById<ImageView>(R.id.imageViewHeart)
        fav.setOnClickListener{
            //TODO ir a la ventana de los favs
        }

        searchBar.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus && prevSearchesShown.size>0){
                //TODO poner de nuevo las busquedas a la lista, añadirlas con el metodo del adaptador y meterlas a la BD
                recommendationLayout.visibility = View.VISIBLE
                searchBar.background = AppCompatResources.getDrawable(
                    actContextBP,
                    R.drawable.searchbar_background_desplegado
                )

            } else {
                recommendationLayout.visibility = View.INVISIBLE
                searchBar.background = AppCompatResources.getDrawable(actContextBP,R.drawable.rounded_button_cancel)
            }
        }
        return view
    }

    companion object {
        //private const val ARG_PARAM1 = "param1"
        //private const val ARG_PARAM2 = "param2"
        private lateinit var actContextBP: Context
        private lateinit var productsShown: MutableList<product_representation>
        private lateinit var adapterP: ProductAdapter
        private lateinit var instance: HomeFragment
        /*
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
               }
            }
         */

        fun updateSearch(text:String) {
            instance.updateSearch(text)
        }
        fun setProductsShown(list:MutableList<product_representation>){
            productsShown = list
            adapterP.updateProducts(productsShown)
        }
        fun getRecommendationRV():RecyclerView{
            return instance.recommendationRV
        }
    }

}