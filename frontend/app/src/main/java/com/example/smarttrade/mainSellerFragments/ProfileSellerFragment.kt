package com.example.smarttrade.mainSellerFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.smarttrade.R
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

        viewS =  inflater.inflate(R.layout.profile_seller, container, false)

        val nameUser = viewS.findViewById<TextView>(R.id.textViewUNProfileSeller)
        nameUser.text = PersonSeller.getUserId()

        val emailUser = viewS.findViewById<TextView>(R.id.textViewEmailProfileSeller)
        emailUser.text = PersonSeller.getEmail()


        val logOut = viewS.findViewById<TextView>(R.id.textViewLogOutSeller)

        logOut.setOnClickListener{
            //TODO log out
        }

        return viewS

    }
}