package com.example.smarttrade

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.concurrent.Volatile

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        actContext = this

        val signUpButton = findViewById<Button>(R.id.button2)
        signUpButton.setOnClickListener{
            val IntentS = Intent(this,SignUpComprador::class.java)
            startActivity(IntentS)
        }
        val firstPasswordField = findViewById<EditText>(R.id.editTextPassword)

        val imageP = findViewById<ImageView>(R.id.imageViewOjo1)
        imageP.setOnClickListener {
            if (firstPasswordField.transformationMethod == PasswordTransformationMethod.getInstance()) {
                firstPasswordField.transformationMethod = HideReturnsTransformationMethod.getInstance()
                imageP.setImageResource(R.drawable.eye_closed_icon)
            } else {
                firstPasswordField.transformationMethod = PasswordTransformationMethod.getInstance()
                imageP.setImageResource(R.drawable.eye_open_icon)
            }
        }
        val forgotPassword = findViewById<TextView>(R.id.editTextText3)
        forgotPassword.setOnClickListener {
            //TODO codigo para recuperar contraseña
        }
        val logInButton = findViewById<Button>(R.id.button)
        logInButton.setOnClickListener{
            //TODO codigo para logIn
            //temporal para acceder a la ventana de catalogo
            val IntentS = Intent(this,BrowseProducts::class.java)
            startActivity(IntentS)
        }
    }

    companion object {
        private lateinit var actContext:MainActivity
        fun getContext(): Context {
            return actContext
        }
    }
}