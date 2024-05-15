package com.example.smarttrade.mainBuyerFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
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

            viewB =  inflater.inflate(R.layout.profile_buyer, container, false)

            val nameUser = viewB.findViewById<TextView>(R.id.textViewUNProfile)
            nameUser.text =  PersonBuyer.getUserId()

            val emailUser = viewB.findViewById<TextView>(R.id.textViewEmailProfile)
            emailUser.text = PersonBuyer.getEmail()


            val myOrders = viewB.findViewById<TextView>(R.id.textViewMyOrders)
            myOrders.setOnClickListener{
                //TODO go to my orders
            }

            val logOut = viewB.findViewById<TextView>(R.id.textViewLogOut)
            logOut.setOnClickListener{
                //TODO log out
            }

            return viewB
        }
    }