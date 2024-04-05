package com.example.smarttrade

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ScrollView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignUpComprador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up_comprador)
        //ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
        //    val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        //    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        //    insets
        //}
        val scrollView = findViewById<ScrollView>(R.id.scrollView)
        scrollView.overScrollMode = View.OVER_SCROLL_ALWAYS
        val spinner = findViewById<Spinner>(R.id.spinnerSPM)
        val items = arrayOf("Paypal", "Tarjeta de Crédito", "Bizum")
        val adapter = ArrayAdapter<String>(this, R.layout.spinner_item_pago, items)
        spinner.adapter = adapter
    }
}