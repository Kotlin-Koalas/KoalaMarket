package com.example.smarttrade

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.commit
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.add
import com.example.smarttrade.mainBuyerFrargments.HomeFragment

class BuyerMainScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        actContextBP = this

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<HomeFragment>(R.id.fragment_container_view)
        }

        setContentView(R.layout.activity_browse_products)

        val shoppingCart = findViewById<ConstraintLayout>(R.id.constraintLayoutCart)
        shoppingCart.setOnClickListener{
            //TODO ir a la ventana del carrito
        }

        val account = findViewById<ConstraintLayout>(R.id.constraintLayoutPerson)
        account.setOnClickListener{
            //TODO ir a la ventana de la cuenta
        }

        val home = findViewById<ConstraintLayout>(R.id.constraintLayoutHome)
        home.setOnClickListener{
            //TODO ir a la ventana de inicio
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
        private lateinit var actContextBP:BuyerMainScreen
        fun getContext(): Context {
            return actContextBP
        }
    }
}