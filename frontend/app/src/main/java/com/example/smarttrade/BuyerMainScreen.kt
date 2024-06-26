package com.example.smarttrade

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.smarttrade.adapters.CategoryAdapter
import com.example.smarttrade.adapters.ProductAdapter
import com.example.smarttrade.mainBuyerFragments.HomeFragment
import com.example.smarttrade.mainBuyerFragments.ProfileBuyerFragment
import com.example.smarttrade.mainBuyerFragments.ShoppingCartFragment
import com.example.smarttrade.mediador.MediatorShoppingCart
import com.example.smarttrade.models.PersonBuyer

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
        val account = findViewById<ConstraintLayout>(R.id.constraintLayoutPerson)
        val home = findViewById<ConstraintLayout>(R.id.constraintLayoutHome)

        shoppingCart.setOnClickListener{
            shoppingCart.setBackgroundResource(R.drawable.bottom_selected_background)
            account.setBackgroundResource(R.color.verdeOscuro)
            home.setBackgroundResource(R.color.verdeOscuro)
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<ShoppingCartFragment>(R.id.fragment_container_view)
            }
            ProductAdapter.deleteListeners()
            CategoryAdapter.deleteListeners()
            MediatorShoppingCart.setPriceToCero()
        }

        account.setOnClickListener{
            PersonBuyer.clearSelectedItems()
            account.setBackgroundResource(R.drawable.bottom_selected_background)
            shoppingCart.setBackgroundResource(R.color.verdeOscuro)
            home.setBackgroundResource(R.color.verdeOscuro)
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<ProfileBuyerFragment>(R.id.fragment_container_view)
            }
            ProductAdapter.deleteListeners()
            CategoryAdapter.deleteListeners()
        }

        home.setOnClickListener{
            PersonBuyer.clearSelectedItems()
            home.setBackgroundResource(R.drawable.bottom_selected_background)
            account.setBackgroundResource(R.color.verdeOscuro)
            shoppingCart.setBackgroundResource(R.color.verdeOscuro)
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<HomeFragment>(R.id.fragment_container_view)
            }
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

        fun showCustomDialogBoxSeller(popUpText: String) {
            val dialog = Dialog(actContextBP)
            dialog.setTitle("ÉXITO")
            Log.i("Entra dentro del dialog","Entra")
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.pop_up_alert_success)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val messageBox = dialog.findViewById<TextView>(R.id.textViewErrorText)
            val btnOk = dialog.findViewById<Button>(R.id.buttonOkPopUp)

            btnOk.setOnClickListener{
                dialog.dismiss()
            }
            messageBox.text = popUpText

            dialog.show()

        }
    }
}