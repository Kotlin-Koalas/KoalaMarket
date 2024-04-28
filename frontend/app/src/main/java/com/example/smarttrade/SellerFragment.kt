package com.example.smarttrade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import com.example.smarttrade.adapters.ProductAdapter
import com.example.smarttrade.logic.logic

class SellerFragment : Fragment() {


    lateinit var gridViewSell: GridView
    lateinit var adapterP: ProductAdapter
    lateinit var viewS :View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {


         viewS = inflater.inflate(R.layout.seller_view, container, false)

        gridViewSell = viewS.findViewById(R.id.productsSellerLayout)

        //adapterP = ProductAdapter(mutableListOf())
        gridViewSell.adapter = adapterP
        logic.getAllProducts()//TODO cambiar coger todos los productos del vendedor






        return view

    }

}