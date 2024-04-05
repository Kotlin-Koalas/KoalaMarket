package com.example.smarttrade

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        //ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
        //    val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        //    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        //    insets
        //}
        val signUpButton = findViewById<Button>(R.id.button2)
        signUpButton.setOnClickListener{
            //codigo para ir al signup
            val IntentS = Intent(this,SignUpComprador::class.java)
            startActivity(IntentS)
        }
        val forgotPassword = findViewById<TextView>(R.id.editTextText3)
        forgotPassword.setOnClickListener {
            //codigo para recuperar contraseña
        }
        val logInButton = findViewById<Button>(R.id.button)
        logInButton.setOnClickListener{
            //codigo para logIn
        }
    }
}