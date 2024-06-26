package com.example.smarttrade

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.smarttrade.logic.Identification

class SignUpVendedor : AppCompatActivity() {

    private lateinit var linearLayout:LinearLayout
    private var indexBefore = 0
    private var currentTypeOfPayment = -1

    private var isId = false
    private var isName = false
    private var isSurname = false
    private var isCIF = false
    private var isEmail = false
    private var isPassword = false
    private var isRPassword = false
    private var isIBAN = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up_vendedor)
        actContextVendor = this
        val scrollView = findViewById<ScrollView>(R.id.scrollView)
        scrollView.overScrollMode = View.OVER_SCROLL_ALWAYS

        val goBackButton = findViewById<ImageView>(R.id.backArrow)
        goBackButton.setOnClickListener{
            val IntentS = Intent(this,MainActivity::class.java)
            startActivity(IntentS)
        }

        val cancelButton = findViewById<Button>(R.id.buttonCancel)
        val signUpButton = findViewById<Button>(R.id.buttonSignUp)
        cancelButton.setOnClickListener{
            val IntentS = Intent(this,SignUpComprador::class.java)
            startActivity(IntentS)
        }

        val firstPasswordField = findViewById<EditText>(R.id.editTextPassword)
        val secondPasswordField = findViewById<EditText>(R.id.editTextRPassword)

        val imageP = findViewById<ImageView>(R.id.imageViewOjo1)
        val imageRP = findViewById<ImageView>(R.id.imageViewOjo2)
        imageP.setOnClickListener {
            if (firstPasswordField.transformationMethod == PasswordTransformationMethod.getInstance()) {
                firstPasswordField.transformationMethod = HideReturnsTransformationMethod.getInstance()
                imageP.setImageResource(R.drawable.eye_closed_icon)
            } else {
                firstPasswordField.transformationMethod = PasswordTransformationMethod.getInstance()
                imageP.setImageResource(R.drawable.eye_open_icon)
            }
        }
        imageRP.setOnClickListener {
            if (secondPasswordField.transformationMethod == PasswordTransformationMethod.getInstance()) {
                secondPasswordField.transformationMethod = HideReturnsTransformationMethod.getInstance()
                imageRP.setImageResource(R.drawable.eye_closed_icon)
            } else {
                secondPasswordField.transformationMethod = PasswordTransformationMethod.getInstance()
                imageRP.setImageResource(R.drawable.eye_open_icon)
            }
        }

        signUpButton.setOnClickListener{
            var popUpOrNot = false
            var popUpText = ""


            val firstPassword = firstPasswordField.text.toString()
            val secondPassword = secondPasswordField.text.toString()
            var notEqual = !(firstPassword.equals(secondPassword))
            if(notEqual){
                popUpOrNot = true
                popUpText += "- Las contraseñas no coinciden, por favor, asegurate de que sean iguales.\n"
                isRPassword = true
            }

            val patternPassword = "^(?=.{4,})(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]+\$".toRegex()
            if( !patternPassword.containsMatchIn(firstPassword) || !patternPassword.containsMatchIn(secondPassword)){
                popUpOrNot = true
                popUpText += "- La contraseña debe contener mínimo un número, una letra mayúscula, y una letra minúscula. Además no debe contener caracteres especiales, solo letras (mayúsculas y minúsculas), números y tener una logitud mínima de 4 caracteres.\n"
                isPassword = true
            }

            val currNameField = findViewById<EditText>(R.id.editTextName).text
            val currName = currNameField.toString()
            val currSurnameField = findViewById<EditText>(R.id.editTextSurname).text
            val currSurname = currSurnameField.toString()
            val currIdField = findViewById<EditText>(R.id.editTextID).text
            val currId = currIdField.toString()
            val patternOnlyLetters = "^[a-zA-Z]*$".toRegex()
            if(!patternOnlyLetters.containsMatchIn(currName)){
                popUpOrNot = true
                popUpText += "- En los campos de ID de Usuario, Nombre y Apellido solo deben haber letras.\n"
                isName = true
            }
            if(!patternOnlyLetters.containsMatchIn(currSurname)){
                popUpOrNot = true
                popUpText += "- En el campo de Apellido solo deben haber letras.\n"
                isSurname = true
            }
            if(!patternOnlyLetters.containsMatchIn(currId)){
                popUpOrNot = true
                popUpText += "- En el campo de ID de Usuario solo deben haber letras.\n"
                isId = true
            }
            val patternDNI = "[a-zA-Z][0-9]{8}".toRegex()
            var currDNI = findViewById<EditText>(R.id.editTextDNI).text.toString()
            if(!patternDNI.containsMatchIn(currDNI)){
                popUpOrNot = true
                popUpText += "- Inserte un NIF válido, con una letra al principio y 8 números.\n"
                isCIF = true
            } else {
                val numbers = currDNI.substring(1, currDNI.length) // Extract numbers
                val letter = currDNI.first().uppercaseChar()
                currDNI = "$letter$numbers" // Combine numbers and uppercase letter
            }

            val patternCorreo = "^[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\$".toRegex()
            val currCorreo = findViewById<EditText>(R.id.editTextEmail).text.toString()
            if(!patternCorreo.containsMatchIn(currCorreo)){
                popUpOrNot = true
                popUpText += "- Proporcione una dirección de correo válida siguiendo el formato standard.\n"
                isEmail = true
            }

            val currIBAN = findViewById<EditText>(R.id.editTextIBAN).text.toString()

            val patternIBAN = "^[0-9]{24}\$".toRegex()
            if(!patternIBAN.containsMatchIn(currIBAN)){
                popUpOrNot = true
                popUpText += "- El IBAN debe contener 24 números exclusivamente.\n"
                isIBAN = true
            }

            if(currId.isEmpty() || currName.isEmpty() || currSurname.isEmpty() || currCorreo.isEmpty() || firstPassword.isEmpty() || secondPassword.isEmpty() || currDNI.isEmpty() || currIBAN.isEmpty()){
                popUpOrNot = true
                popUpText += "- Todos los campos deben estar rellenados.\n"
            }

            if(popUpOrNot){
                showCustomDialogBoxSeller(popUpText)
                checkErrors()
            } else {
                Identification.signInSeller(currName,currSurname,firstPassword,currCorreo,currId,currDNI,currIBAN)
            }

        }

    }

    private fun showCustomDialogBoxSeller(popUpText: String) {
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

    private fun checkErrors(){
        if(isId || findViewById<EditText>(R.id.editTextID).text.isEmpty()){
            findViewById<ConstraintLayout>(R.id.frameLayoutID).setBackgroundResource(R.drawable.red_border)
        } else {
            findViewById<ConstraintLayout>(R.id.frameLayoutID).setBackgroundResource(R.drawable.basic_grey_shape)
        }
        if(isName || findViewById<EditText>(R.id.editTextName).text.isEmpty()){
            findViewById<ConstraintLayout>(R.id.frameLayoutName).setBackgroundResource(R.drawable.red_border)
        } else {
            findViewById<ConstraintLayout>(R.id.frameLayoutName).setBackgroundResource(R.drawable.basic_grey_shape)
        }
        if(isSurname || findViewById<EditText>(R.id.editTextSurname).text.isEmpty()){
            findViewById<ConstraintLayout>(R.id.frameLayoutSurname).setBackgroundResource(R.drawable.red_border)
        } else {
            findViewById<ConstraintLayout>(R.id.frameLayoutSurname).setBackgroundResource(R.drawable.basic_grey_shape)
        }
        if(isCIF || findViewById<EditText>(R.id.editTextDNI).text.isEmpty()){
            findViewById<ConstraintLayout>(R.id.frameLayoutDNI).setBackgroundResource(R.drawable.red_border)
        } else {
            findViewById<ConstraintLayout>(R.id.frameLayoutDNI).setBackgroundResource(R.drawable.basic_grey_shape)
        }
        if(isEmail || findViewById<EditText>(R.id.editTextEmail).text.isEmpty()){
            findViewById<ConstraintLayout>(R.id.frameLayoutEmail).setBackgroundResource(R.drawable.red_border)
        } else {
            findViewById<ConstraintLayout>(R.id.frameLayoutEmail).setBackgroundResource(R.drawable.basic_grey_shape)
        }
        if(isPassword || findViewById<EditText>(R.id.editTextPassword).text.isEmpty()){
            findViewById<ConstraintLayout>(R.id.frameLayoutPassword).setBackgroundResource(R.drawable.red_border)
        } else {
            findViewById<ConstraintLayout>(R.id.frameLayoutPassword).setBackgroundResource(R.drawable.basic_grey_shape)
        }
        if(isRPassword || findViewById<EditText>(R.id.editTextRPassword).text.isEmpty()){
            findViewById<ConstraintLayout>(R.id.frameLayoutRPassword).setBackgroundResource(R.drawable.red_border)
        } else {
            findViewById<ConstraintLayout>(R.id.frameLayoutRPassword).setBackgroundResource(R.drawable.basic_grey_shape)
        }
        if(isIBAN || findViewById<EditText>(R.id.editTextIBAN).text.isEmpty()){
            findViewById<ConstraintLayout>(R.id.frameLayoutSPM).setBackgroundResource(R.drawable.red_border)
        } else {
            findViewById<ConstraintLayout>(R.id.frameLayoutSPM).setBackgroundResource(R.drawable.basic_grey_shape)
        }
    }

    companion object {
        private lateinit var actContextVendor:SignUpVendedor
        fun getContext(): Context {
            return actContextVendor
        }
        fun loadSeller(){
            val IntentS = Intent(actContextVendor,SellerMain::class.java)
            actContextVendor.startActivity(IntentS)
        }
        fun popUpError(){
            actContextVendor.showCustomDialogBoxSeller("Error, ya registrado, pruebe a iniciar sesión.")
        }
    }






    /*
       //android:focusableInTouchMode="false" EN EL XML
    lateinit var dateEdt: EditText
    private fun setUpDateEdtClickListener() {
        // on below line we are initializing our variables.
        dateEdt = findViewById(R.id.editTextCad)
        // on below line we are adding
        // click listener for our edit text.
        dateEdt.setOnClickListener {
            // on below line we are getting
            // the instance of our calendar.
            val c = Calendar.getInstance()

            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // on below line we are creating a
            // variable for date picker dialog.
            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our edit text.
                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    dateEdt.setText(dat)
                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            // at last we are calling show
            // to display our date picker dialog.
            datePickerDialog.show()
        }
    } */
}


