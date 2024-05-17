package com.example.smarttrade.mainSellerFragments

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
import com.example.smarttrade.R
import com.example.smarttrade.mainBuyerFragments.ProfileBuyerFragment
import com.example.smarttrade.models.PersonSeller

class ProfileSellerFragment : Fragment() {

    lateinit var viewS :View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        contextFragment = this

        viewS =  inflater.inflate(R.layout.profile_seller, container, false)

        val nameUser = viewS.findViewById<TextView>(R.id.textViewUNProfileSeller)
        nameUser.text = PersonSeller.getUserId()

        val emailUser = viewS.findViewById<TextView>(R.id.textViewEmailProfileSeller)
        emailUser.text = PersonSeller.getEmail()


        val nextState = viewS.findViewById<TextView>(R.id.textViewNextState)
        nextState.setOnClickListener{
            //TODO go to next state
        }

        val logOut = viewS.findViewById<TextView>(R.id.textViewLogOutSeller)
        logOut.setOnClickListener{
            showExistMessage("¿Estás seguro de que deseas cerrar sesión?")
        }

        return viewS

    }

    fun showExistMessage(popUpText: String) {
        val dialog = Dialog(contextFragment.requireContext())
        dialog.setTitle("ALERTA")
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.seller_pop_up_alert_product_changes)
        dialog.setContentView(R.layout.seller_pop_up_alert_product_changes)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val messageBox = dialog.findViewById<TextView>(R.id.textSellerErrorText)
        val btnOk = dialog.findViewById<Button>(R.id.buttonAcceptChanges)
        val btnCancel = dialog.findViewById<Button>(R.id.buttonCancelChanges)

        btnOk.setOnClickListener{
            //TODO logica salir sesion


            val intent = Intent(ProfileBuyerFragment.contextFragment.requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
        btnCancel.setOnClickListener{
            dialog.dismiss()
        }

        messageBox.text = popUpText

        dialog.show()
    }


    companion object {
        lateinit var contextFragment: ProfileSellerFragment
        lateinit var sellerBuyer: String

        fun getContext(): ProfileSellerFragment{
            return contextFragment
        }

    }

}


