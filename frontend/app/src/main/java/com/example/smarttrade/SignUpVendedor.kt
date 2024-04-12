package com.example.smarttrade

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smarttrade.logic.logic
import org.xmlpull.v1.XmlPullParser
import java.time.LocalDate
import java.util.Calendar
import kotlin.math.sign
import kotlin.properties.Delegates

class SignUpVendedor : AppCompatActivity() {

    private lateinit var linearLayout:LinearLayout
    private var indexBefore = 0
    private var currentTypeOfPayment = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up_vendedor)
        val scrollView = findViewById<ScrollView>(R.id.scrollView)
        scrollView.overScrollMode = View.OVER_SCROLL_ALWAYS
        val cancelButton = findViewById<Button>(R.id.buttonCancel)
        val signUpButton = findViewById<Button>(R.id.buttonSignUp)
        cancelButton.setOnClickListener{
            val IntentS = Intent(this,SignUpComprador::class.java)
            startActivity(IntentS)
        }
        actContext = this
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
            }

            val patternPassword = "^(?=.{4,})(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]+\$".toRegex()
            if( !patternPassword.containsMatchIn(firstPassword) || !patternPassword.containsMatchIn(secondPassword)){
                popUpOrNot = true
                popUpText += "- La contraseña debe contener mínimo un número, una letra mayúscula, y una letra minúscula. Además no debe contener caracteres especiales, solo letras (mayúsculas y minúsculas), números y tener una logitud mínima de 4 caracteres.\n"
            }

            val currNameField = findViewById<EditText>(R.id.editTextName).text
            val currName = currNameField.toString()
            val currSurnameField = findViewById<EditText>(R.id.editTextSurname).text
            val currSurname = currSurnameField.toString()
            val currIdField = findViewById<EditText>(R.id.editTextID).text
            val currId = currIdField.toString()
            val patternOnlyLetters = "^[a-zA-Z]*$".toRegex()
            if(!patternOnlyLetters.containsMatchIn(currName) || !patternOnlyLetters.containsMatchIn(currSurname) || !patternOnlyLetters.containsMatchIn(currId)){
                popUpOrNot = true
                popUpText += "- En los campos de ID de Usuario, Nombre y Apellido solo deben haber letras.\n"
            }

            val patternDNI = "[a-zA-Z][0-9]{8}".toRegex()
            var currDNI = findViewById<EditText>(R.id.editTextDNI).text.toString()
            if(!patternDNI.containsMatchIn(currDNI)){
                popUpOrNot = true
                popUpText += "- Inserte un NIF válido, con una letra al principio y 8 números.\n"
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
            }

            val currIBAN = findViewById<EditText>(R.id.editTextIBAN).text.toString()

            val patternIBAN = "^[0-9]{24}\$".toRegex()
            if(!patternIBAN.containsMatchIn(currIBAN)){
                popUpOrNot = true
                popUpText += "- El IBAN debe contener 24 números únicamente.\n"
            }

            if(currId.isEmpty() || currName.isEmpty() || currSurname.isEmpty() || currCorreo.isEmpty() || firstPassword.isEmpty() || secondPassword.isEmpty() || currDNI.isEmpty() || currIBAN.isEmpty()){
                popUpOrNot = true
                popUpText += "- Todos los campos deben estar rellenados.\n"
            }

            if(popUpOrNot){
                showCustomDialogBoxSeller(popUpText)
            } else {
                logic.signInSeller(currName,currSurname,firstPassword,currCorreo,currId,currDNI,currIBAN)
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

    companion object {
        private lateinit var actContext:SignUpVendedor
        fun getContext(): Context {
            return actContext
        }
        fun loadSeller(){//TODO cambiar en un futuro Addproduct
            val IntentS = Intent(actContext,AddProduct::class.java)
            actContext.startActivity(IntentS)
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


