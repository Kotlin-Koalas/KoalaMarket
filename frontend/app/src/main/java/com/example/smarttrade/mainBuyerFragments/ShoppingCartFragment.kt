package com.example.smarttrade.mainBuyerFragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.BuyerMainScreen
import com.example.smarttrade.R
import com.example.smarttrade.Shipment
import com.example.smarttrade.adapters.ProductAdapter
import com.example.smarttrade.adapters.ProductCartAdapter
import com.example.smarttrade.logic.ShoppingCartRequests
import com.example.smarttrade.models.PersonBuyer
import com.example.smarttrade.models.product_representation
import com.example.smarttrade.models.product_representation_cart


class ShoppingCartFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        */
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currView = inflater.inflate(R.layout.fragment_shopping_cart, container, false)
        // Inflate the layout for this fragment
        instance = this

        actContextBP = BuyerMainScreen.getContext()

        adapterPC =  ProductCartAdapter()

        val buttonComprar = currView.findViewById<Button>(R.id.buttonComprar)

        val productsLayout = currView.findViewById<GridView>(R.id.VerticalGridViewProductsCart)
        productsLayout.adapter = adapterPC

        ShoppingCartRequests.getShoppingCart()

        var allSelected = false
        val selectAll = currView.findViewById<ImageView>(R.id.imageViewSelectTodos)
        selectAll.setOnClickListener{
            if(!allSelected){
                ProductCartAdapter.setAllSelected()
                selectAll.setImageResource(R.drawable.cart_selected)
                allSelected = true
            } else {
                ProductCartAdapter.setAllUnselected()
                selectAll.setImageResource(R.drawable.ellipse_5)
                allSelected = false
            }
        }

        buttonComprar.setOnClickListener {
            if(!PersonBuyer.getSelectedItemsCart().isEmpty()) {
                val i = Intent(currView.context, Shipment::class.java)
                startActivity(i)
            }

        }
        return currView
    }

    companion object {
        //private const val ARG_PARAM1 = "param1"
        //private const val ARG_PARAM2 = "param2"
        private lateinit var actContextBP: Context
        private lateinit var adapterPC: ProductCartAdapter
        private lateinit var instance: ShoppingCartFragment
        private lateinit var currView: View
        /*
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShoppingCartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
        */
        fun setInitialProductsShown(){
            adapterPC.updateProducts()
            Log.i("ShoppingCartAfterGET", PersonBuyer.getShoppingCart().toString())
        }
        fun getCurrView(): View{
            return currView
        }

    }
}