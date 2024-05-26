package com.example.smarttrade


import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.smarttrade.adapters.ProductAdapterSeller
import com.example.smarttrade.mainSellerFragments.AddProduct
import com.example.smarttrade.mainSellerFragments.ProfileSellerFragment
import com.example.smarttrade.mainSellerFragments.SellerFragment

class SellerMain: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()



        actContextSell = this

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<SellerFragment>(R.id.seller_container_view)
        }

        setContentView(R.layout.seller_bottom_bar)


        val addProduct = findViewById<ConstraintLayout>(R.id.constraintLayoutAddProductSeller)
        val accountSeller = findViewById<ConstraintLayout>(R.id.constraintLayoutProfileSeller)
        val homeSeller = findViewById<ConstraintLayout>(R.id.constraintLayoutHomeSeller)






        addProduct.setOnClickListener {
            addProduct.setBackgroundResource(R.drawable.bottom_selected_background)
            accountSeller.setBackgroundResource(R.color.verdeOscuro)
            homeSeller.setBackgroundResource(R.color.verdeOscuro)
            val fragment = AddProduct()
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                //replace(R.id.seller_container_view, fragment)
                add<AddProduct>(R.id.seller_container_view)
            }

            ProductAdapterSeller.deleteListeners()
        }





            accountSeller.setOnClickListener {
                accountSeller.setBackgroundResource(R.drawable.bottom_selected_background)
                addProduct.setBackgroundResource(R.color.verdeOscuro)
                homeSeller.setBackgroundResource(R.color.verdeOscuro)
                val fragment = ProfileSellerFragment()
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    //replace(R.id.seller_container_view, fragment)
                    add<ProfileSellerFragment>(R.id.seller_container_view)
                }
                ProductAdapterSeller.deleteListeners()
            }



            homeSeller.setOnClickListener {
                homeSeller.setBackgroundResource(R.drawable.bottom_selected_background)
                accountSeller.setBackgroundResource(R.color.verdeOscuro)
                addProduct.setBackgroundResource(R.color.verdeOscuro)
                val fragment = SellerFragment()
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    //replace(R.id.seller_container_view, fragment)
                    add<SellerFragment>(R.id.seller_container_view)
                }
            }
    }


    companion object{
        private lateinit var actContextSell: SellerMain

        fun getContext(): Context {
            return actContextSell
        }
    }

}