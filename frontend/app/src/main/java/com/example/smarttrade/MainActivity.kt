package com.example.smarttrade

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.Image
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.smarttrade.logic.logic
import com.example.smarttrade.nonactivityclasses.ImageURLtoBitmapConverter


class MainActivity : AppCompatActivity() {

    lateinit var bitmap: Bitmap
    lateinit var testImageView:ImageView
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
            //TODO codigo para recuperar contraseña
        }
        val logInButton = findViewById<Button>(R.id.button)
        logInButton.setOnClickListener{
            logic.logIn(findViewById<EditText>(R.id.editTextTextEmailAddress).text.toString(),firstPasswordField.text.toString())
            /*
            //temporal para acceder a la ventana de catalogo
            val IntentS = Intent(this,AddProduct::class.java)
            startActivity(IntentS)
             */
        }

        bitmap = ImageURLtoBitmapConverter.downloadImage("http://ec2-52-47-150-236.eu-west-3.compute.amazonaws.com:5000/b1g9a.jpg") as Bitmap
        testImageView = findViewById<ImageView>(R.id.imageViewTest)
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
        lateinit var testImageView:ImageView
        fun getContext(): Context {
            return actContext
        }
        fun popUpError(){
            actContext.showCustomDialogBox("Error, usuario no registrado, pruebe a crear una cuenta.")
        }

        fun loadBuyer(){
            val IntentS = Intent(actContext,BrowseProducts::class.java)
            actContext.startActivity(IntentS)
        }
        fun loadSeller(){//TODO cambiar en un futuro Addproduct
            val IntentS = Intent(actContext,AddProduct::class.java)
            actContext.startActivity(IntentS)
        }
        fun setImage(image:Bitmap?){
            testImageView.setImageBitmap(image)
        }
    }
}