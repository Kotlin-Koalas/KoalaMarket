package com.example.smarttrade

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.smarttrade.logic.Identification


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val context = this

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
            val IntentS = Intent(this,WishList::class.java)
            startActivity(IntentS)

        }
        val logInButton = findViewById<Button>(R.id.button)
        logInButton.setOnClickListener{
            Identification.logIn(findViewById<EditText>(R.id.editTextTextEmailAddress).text.toString(),firstPasswordField.text.toString())
            /*
            //temporal para acceder a la ventana de catalogo
            val IntentS = Intent(this,AddProduct::class.java)
            startActivity(IntentS)
             */
        }
    }





    fun showCustomDialogBox(popUpText: String) {
        val dialog = Dialog(this)
        dialog.setTitle("ERROR")
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.pop_up_alert_login)
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

        fun loadBuyer(){
            val IntentS = Intent(actContext,BuyerMainScreen::class.java)
            actContext.startActivity(IntentS)
        }
        fun loadSeller(){
            val IntentS = Intent(actContext,SellerMain::class.java)
            actContext.startActivity(IntentS)
        }

    }
}