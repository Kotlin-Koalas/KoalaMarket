package com.example.smarttrade.mainSellerFragments

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.smarttrade.R
import com.example.smarttrade.adapters.ProductAdapterSeller
import com.example.smarttrade.logic.logic
import com.example.smarttrade.models.PersonSeller
import com.example.smarttrade.models.product_representation

class SellerFragment : Fragment() {


    lateinit var gridViewSell: GridView
    lateinit var viewS :View
    lateinit var sellerCIF :String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

         viewS = inflater.inflate(R.layout.seller_view, container, false)

        sellerCIF = PersonSeller.getCIF()

        Log.i("Seller : ", sellerCIF)


        contextFragment = this

        gridViewSell= viewS.findViewById(R.id.productsSellerGridView)
        adapterP = ProductAdapterSeller(mutableListOf())

        //adapterP = ProductAdapter(mutableListOf())
 
        gridViewSell.adapter = adapterP

        productSown = mutableListOf()





        logic.getAllProductsSeller(sellerCIF)

        return viewS

    }


    fun showAlertDeleteProductBox(popUpText: String, PN:String) {

        sellerCIF = PersonSeller.getCIF()

        val dialog = Dialog(contextFragment.requireContext())
        dialog.setTitle("ALERTA")
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.seller_pop_up_alert_product_changes)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val messageBox = dialog.findViewById<TextView>(R.id.textSellerErrorText)
        val btnOk = dialog.findViewById<Button>(R.id.buttonAcceptChanges)
        val btnCancel = dialog.findViewById<Button>(R.id.buttonCancelChanges)

        btnOk.setOnClickListener{
            logic.deleteProduct(PN, sellerCIF)
            adapterP.notifyDataSetChanged()
            dialog.dismiss()
            activity?.recreate()

        }
        btnCancel.setOnClickListener{
            dialog.dismiss()
        }

        messageBox.text = popUpText

        dialog.show()

    }

    fun showAlertChangePriceProductBox(popUpText: String, PN:String, price:String, stock: String) {

        sellerCIF = PersonSeller.getCIF()
        val stockInt = stock.toInt()

        val dialog = Dialog(contextFragment.requireContext())
        dialog.setTitle("ALERTA")
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.seller_pop_up_alert_product_changes)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val messageBox = dialog.findViewById<TextView>(R.id.textSellerErrorText)
        val btnOk = dialog.findViewById<Button>(R.id.buttonAcceptChanges)
        val btnCancel = dialog.findViewById<Button>(R.id.buttonCancelChanges)

        btnOk.setOnClickListener {
            logic.changePriceProduct(PN,sellerCIF , price, stockInt)
            adapterP.notifyDataSetChanged()
            dialog.dismiss()
            activity?.recreate()
        }
        btnCancel.setOnClickListener {
            dialog.dismiss()

        }
        messageBox.text = popUpText

        dialog.show()

    }

    companion object{
       private  lateinit var adapterP : ProductAdapterSeller
       private lateinit var productSown: MutableList<product_representation>
        private lateinit var contextFragment: SellerFragment
        private lateinit var sellerCIF :String


       fun setProductsSeller(list:MutableList<product_representation>){
           productSown = list
           adapterP.updateProducts(productSown)
       }


      fun getContext(): Context {
          return contextFragment.requireContext()
      }

    }

}