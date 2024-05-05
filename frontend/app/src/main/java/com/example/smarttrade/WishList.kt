package com.example.smarttrade

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.smarttrade.adapters.ProductWishAdapter
import com.example.smarttrade.logic.ListWishRequests

class WishList : AppCompatActivity() {



    lateinit var gridProducts : GridView
    lateinit var backButton : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.wish_list_view)

        actContext = this


        adapterP = ProductWishAdapter(actContext )
        gridProducts = findViewById(R.id.gridViewWishList)
        gridProducts.adapter = adapterP

        ListWishRequests.getWishList()

/*
        for (i in 0 until 10){
            adapterWL.addProduct(product_representation_cart(
                PN = "PN$i",
                description = "description $i",
                image = "image_url_$i",
                leafColor = "leafColor $i",
                name = "name $i",
                price = "price $i",
                quantity = i,
                seller = "seller $i",
                stock = i,
                category = "category $i"
            ))
        }
*/


        backButton = findViewById(R.id.imageBack)
        backButton.setOnClickListener {
            finish()
        }

    }


    fun showCustomDialogBoxSuccess(msgSuccess: String) {
        val dialog = Dialog(actContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.pop_up_alert_success)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val messageBox = dialog.findViewById<TextView>(R.id.textViewErrorText)
        val btnOk = dialog.findViewById<Button>(R.id.buttonOkPopUp)

        btnOk.setOnClickListener {
            dialog.dismiss()
            dialog.dismiss()
        }
        messageBox.text = msgSuccess

        dialog.show()


    }


    companion object {
        lateinit var actContext: WishList
        lateinit var adapterP : ProductWishAdapter



        fun getContext(): Context {
            return actContext
        }

        fun setProducts(){
            adapterP.updateProducts()
        }

        fun productAddedCart(){
            actContext.showCustomDialogBoxSuccess("Producto añadido al carrito")
        }



    }

}