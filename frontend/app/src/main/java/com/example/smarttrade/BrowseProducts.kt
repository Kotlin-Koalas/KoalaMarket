package com.example.smarttrade

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.commit
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.add
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.adapters.CategoryAdapter
import com.example.smarttrade.adapters.ProductAdapter
import com.example.smarttrade.adapters.SearchAdapter
import com.example.smarttrade.logic.logic
import com.example.smarttrade.mainBuyerFrargments.HomeFragment
import com.example.smarttrade.models.category_representation
import com.example.smarttrade.models.product_representation
import com.example.smarttrade.models.search_representation

class BrowseProducts : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        actContextBP = this

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<HomeFragment>(R.id.fragment_container_view)
        }

        //TODO conseguir los datos de la BD y meterlos a la lista prevSearchesShown
        setContentView(R.layout.activity_browse_products)

        val shoppingCart = findViewById<ImageView>(R.id.constraintLayoutCart)
        shoppingCart.setOnClickListener{
            //TODO ir a la ventana del carrito
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                val rectRecommended = Rect()
                HomeFragment.getRecommendationRV().getGlobalVisibleRect(rectRecommended)
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
        private lateinit var actContextBP:BrowseProducts
        fun getContext(): Context {
            return actContextBP
        }
    }
}