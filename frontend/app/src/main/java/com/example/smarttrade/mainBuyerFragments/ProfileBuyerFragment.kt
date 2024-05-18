package com.example.smarttrade.mainBuyerFragments

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.smarttrade.MainActivity
import com.example.smarttrade.OrderProgress
import com.example.smarttrade.R
import com.example.smarttrade.models.PersonBuyer

class ProfileBuyerFragment : Fragment(){

        lateinit var viewB : View

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

            contextFragment = this

            viewB =  inflater.inflate(R.layout.profile_buyer, container, false)

            val nameUser = viewB.findViewById<TextView>(R.id.textViewUNProfile)
            nameUser.text =  PersonBuyer.getUserId()

            val emailUser = viewB.findViewById<TextView>(R.id.textViewEmailProfile)
            emailUser.text = PersonBuyer.getEmail()


            val myOrders = viewB.findViewById<TextView>(R.id.textViewMyOrders)
            myOrders.setOnClickListener{
                val intent = Intent(contextFragment.requireContext(), OrderProgress::class.java)
                startActivity(intent)

            }

            val logOut = viewB.findViewById<TextView>(R.id.textViewLogOut)
            logOut.setOnClickListener{
                showExistMessage("¿Estás seguro de que deseas cerrar sesión?")
            }

            return viewB
        }

    fun showExistMessage(popUpText: String) {


        val dialog = Dialog(contextFragment.requireContext())
        dialog.setTitle("ALERTA")
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.seller_pop_up_alert_product_changes)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val messageBox = dialog.findViewById<TextView>(R.id.textSellerErrorText)
        val btnOk = dialog.findViewById<Button>(R.id.buttonAcceptChanges)
        val btnCancel = dialog.findViewById<Button>(R.id.buttonCancelChanges)

        btnOk.setOnClickListener{
            //TODO logica salir sesion


            val intent = Intent(contextFragment.requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
        btnCancel.setOnClickListener{
            dialog.dismiss()
        }

        messageBox.text = popUpText

        dialog.show()

    }


        companion object{
            lateinit var contextFragment: ProfileBuyerFragment
            lateinit var buyerCIF: String

            fun getContext(): ProfileBuyerFragment {
                return contextFragment
            }



        }



}