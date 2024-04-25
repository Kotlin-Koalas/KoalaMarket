package com.example.smarttrade.mainBuyerFragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smarttrade.R
import com.example.smarttrade.adapters.ProductAdapter
import com.example.smarttrade.models.product_representation


class ShoppingCartFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var view: View

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
        view = inflater.inflate(R.layout.fragment_shopping_cart, container, false)
        // Inflate the layout for this fragment

        return view
    }

    companion object {
        //private const val ARG_PARAM1 = "param1"
        //private const val ARG_PARAM2 = "param2"
        private lateinit var actContextBP: Context
        private lateinit var cartProducts: MutableList<product_representation>
        private lateinit var adapterPC: ProductAdapter
        private lateinit var instance: ShoppingCartFragment
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
        fun setProductsShown(list:MutableList<product_representation>){
            cartProducts = list
            adapterPC.updateProducts(cartProducts)
        }

    }
}