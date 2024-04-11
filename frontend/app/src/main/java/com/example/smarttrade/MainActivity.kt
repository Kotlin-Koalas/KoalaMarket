package com.example.smarttrade

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.smarttrade.nonactivityclasses.PersonBuyer
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.launch
import kotlin.concurrent.Volatile

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val context = this

        val client = HttpClient(CIO)

        lifecycleScope.launch {
            val response: HttpResponse = client.get("https://google.com")
            //Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show()
        }

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
            //TODO codigo para logIn y guardar las cosas en singleton y quitar los temporal
            //temporal para acceder a la ventana de catalogo
            val IntentS = Intent(this,BrowseProducts::class.java)
            startActivity(IntentS)
        }
    }

    fun showCustomDialogBox(popUpText: String) {
        val dialog = Dialog(this)
        dialog.setTitle("ERROR")
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.pop_up_alert)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val messageBox = dialog.findViewById<TextView>(R.id.textViewErrorText)
        val btnOk = dialog.findViewById<Button>(R.id.buttonOkPopUp)

        btnOk.setOnClickListener{
            dialog.dismiss()
        }
        messageBox.text = popUpText

        dialog.show()

    }

    companion object {
        private lateinit var actContext:MainActivity
        fun getContext(): Context {
            return actContext
        }
        fun popUpError(){
            actContext.showCustomDialogBox("Error, usuario no registrado, pruebe a crear una cuenta.")
        }
    }
}