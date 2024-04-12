package com.example.smarttrade

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.Person
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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smarttrade.logic.logic
import com.example.smarttrade.nonactivityclasses.CreditCard
import com.example.smarttrade.nonactivityclasses.PersonBuyer
import org.xmlpull.v1.XmlPullParser
import java.time.LocalDate
import java.util.Calendar
import kotlin.math.sign
import kotlin.properties.Delegates

class SignUpComprador : AppCompatActivity() {

    private lateinit var linearLayout:LinearLayout
    private var indexBefore = 0
    private var currentTypeOfPayment = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up_comprador)
        val scrollView = findViewById<ScrollView>(R.id.scrollView)
        scrollView.overScrollMode = View.OVER_SCROLL_ALWAYS
        val spinner = findViewById<Spinner>(R.id.spinnerSPM)
        val items = arrayOf<String>(
            "Paypal",
            "Tarjeta de Crédito",
            "Bizum"
        )
        actContext = this
        val adapter = ArrayAdapter<String>(this, R.layout.spinner_item_pago, items)
        spinner.adapter = adapter
        linearLayout = scrollView.getChildAt(0) as LinearLayout
        indexBefore = linearLayout.indexOfChild(findViewById<ConstraintLayout>(R.id.frameLayoutSPM))
        var viewName = spinner.selectedItem as String
        var checkFirstTime = true
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,view: View, position: Int, id: Long){
                val selectedItem = parent.getItemAtPosition(position) as String
                if(checkFirstTime) {
                    checkFirstTime = false;
                } else {
                    deleteView()
                }
                when(selectedItem) {
                    "Paypal" -> addView(R.layout.paypal_option)
                    "Tarjeta de Crédito" -> addView(R.layout.activity_calendar_view)
                    "Bizum" -> addView(R.layout.bizum_option)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //nothing
            }
        }

        val btnBeSeller = findViewById<Button>(R.id.buttonChangeSeller)
        btnBeSeller.setOnClickListener{
            val IntentS = Intent(this,SignUpVendedor::class.java)
            startActivity(IntentS)
        }

        val cancelButton = findViewById<Button>(R.id.buttonCancel)
        val signUpButton = findViewById<Button>(R.id.buttonSignUp)
        cancelButton.setOnClickListener{
            val IntentS = Intent(this,MainActivity::class.java)
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
            }

            val patternPassword = "^(?=.{4,})(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]+\$".toRegex()
            if( !patternPassword.containsMatchIn(firstPassword) || !patternPassword.containsMatchIn(secondPassword)){
                popUpOrNot = true
                popUpText += "- La contraseña debe contener mínimo un número, una letra mayúscula, y una letra minúscula. Además no debe contener caracteres especiales, solo letras (mayúsculas y minúsculas), números y tener una logitud mínima de 4 caracteres.\n"
            }

            if(currentTypeOfPayment == R.layout.activity_calendar_view) {
                val monthField = findViewById<EditText>(R.id.editTextCad)
                val yearField = findViewById<EditText>(R.id.editTextCad2)
                var monthValueString = monthField.text.toString()
                if(monthValueString.isEmpty()) monthValueString = "-1"
                val monthValue = monthValueString.toInt()
                var yearValueString = yearField.text.toString()
                if(yearValueString.isEmpty()) yearValueString = "-1"
                val yearValue = yearValueString.toInt()
                if(monthValue > 12 || monthValue < 1){
                    popUpOrNot = true
                    popUpText += "- Inserte un valor para el mes válido (1-12).\n"
                }
                val currentYearFull = LocalDate.now().year.toString()
                var currentYearString = currentYearFull.substring(currentYearFull.length - 2)
                if(currentYearString.isEmpty()) currentYearString = "-1"
                val currentYear = currentYearString.toInt()
                val calendar = Calendar.getInstance()
                val currentMonth = calendar.get(Calendar.MONTH) + 1
                if(currentYear > yearValue){
                    popUpOrNot = true
                    popUpText += "- Inserte una fecha de cacucidad de la tarjeta a futuro.\n"
                } else  if (currentYear == yearValue && monthValue < currentMonth) {
                    popUpOrNot = true
                    popUpText += "- Inserte una fecha de cacucidad de la tarjeta a futuro.\n"

                }
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

            val patternDNI = "[0-9]{8}[a-zA-Z]".toRegex()
            var currDNI = findViewById<EditText>(R.id.editTextDNI).text.toString()
            if(!patternDNI.containsMatchIn(currDNI)){
                popUpOrNot = true
                popUpText += "- Inserte un DNI válido, con 8 números y una letra al final.\n"
            } else{
                val numbers = currDNI.substring(0, currDNI.length - 1) // Extract numbers
                val letter = currDNI.last().uppercaseChar() // Get last character and convert to uppercase
                currDNI = "$numbers$letter" // Combine numbers and uppercase letter
            }

            val patternCorreo = "^[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\$".toRegex()
            val currCorreo = findViewById<EditText>(R.id.editTextEmail).text.toString()
            if(!patternCorreo.containsMatchIn(currCorreo)){
                popUpOrNot = true
                popUpText += "- Proporcione una dirección de correo válida siguiendo el formato standard.\n"
            }

            if(currentTypeOfPayment == R.layout.paypal_option) {
                val currPayPalCorreo = findViewById<EditText>(R.id.editTextEmailPayPal).text.toString()
                if(!patternCorreo.containsMatchIn(currPayPalCorreo)){
                    popUpOrNot = true
                    popUpText += "- Proporcione una dirección de correo de la cuenta de PayPal válida siguiendo el formato standard.\n"
                }
            }

            val currSA = findViewById<EditText>(R.id.editTextAddress).text.toString()
            val currFA = findViewById<EditText>(R.id.editTextFAddress).text.toString()
            if(currId.isEmpty() || currName.isEmpty() || currSurname.isEmpty() || currCorreo.isEmpty() || firstPassword.isEmpty() || secondPassword.isEmpty() || currDNI.isEmpty() || currSA.isEmpty() || currFA.isEmpty()){
                popUpOrNot = true
                popUpText += "- Todos los campos deben estar rellenados.\n"
            } else if(currentTypeOfPayment == R.layout.paypal_option){
                val currPayPalCorreo = findViewById<EditText>(R.id.editTextEmailPayPal).text.toString()
                if(currPayPalCorreo.isEmpty()){
                    popUpOrNot = true
                    popUpText += "- Todos los campos deben estar rellenados.\n"
                }
            } else if(currentTypeOfPayment == R.layout.bizum_option) {
                val currBizumNum = findViewById<EditText>(R.id.editTextBizumNumber).text.toString()
                if (currBizumNum.isEmpty()) {
                    popUpOrNot = true
                    popUpText += "- Todos los campos deben estar rellenados.\n"
                }
            } else if(currentTypeOfPayment == R.layout.activity_calendar_view) {
                val currNumTarj = findViewById<EditText>(R.id.editTextnumTarj).text.toString()
                val currExpM = findViewById<EditText>(R.id.editTextCad).text.toString()
                val currExpY = findViewById<EditText>(R.id.editTextCad2).text.toString()
                val currCVC = findViewById<EditText>(R.id.editTextCVC).text.toString()
                if (currNumTarj.isEmpty() || currExpM.isEmpty() || currExpY.isEmpty() || currCVC.isEmpty()) {
                    popUpOrNot = true
                    popUpText += "- Todos los campos deben estar rellenados.\n"
                }
            }

            if(popUpOrNot){
                showCustomDialogBox(popUpText)
            } else  {
                if(currentTypeOfPayment == R.layout.activity_calendar_view) {
                    val currNumTarj = findViewById<EditText>(R.id.editTextnumTarj).text.toString()
                    val currExpM = findViewById<EditText>(R.id.editTextCad).text.toString()
                    val currExpY = findViewById<EditText>(R.id.editTextCad2).text.toString()
                    val currCVC = findViewById<EditText>(R.id.editTextCVC).text.toString()
                    logic.signInBuyer(
                        currName,
                        currSurname,
                        firstPassword,
                        currCorreo,
                        currId,
                        currDNI,
                        currSA,
                        currFA,
                        "",
                        "",
                        CreditCard(currNumTarj,currExpM+"/"+currExpY,currCVC)
                    )
                }
                if(currentTypeOfPayment == R.layout.bizum_option) {
                    val currBizumNum = findViewById<EditText>(R.id.editTextBizumNumber).text.toString()
                    logic.signInBuyer(
                        currName,
                        currSurname,
                        firstPassword,
                        currCorreo,
                        currId,
                        currDNI,
                        currSA,
                        currFA,
                        currBizumNum,
                        "",
                        CreditCard("","","")
                    )
                }
                if(currentTypeOfPayment == R.layout.paypal_option) {
                    val currPayPalCorreo = findViewById<EditText>(R.id.editTextEmailPayPal).text.toString()
                    logic.signInBuyer(
                        currName,
                        currSurname,
                        firstPassword,
                        currCorreo,
                        currId,
                        currDNI,
                        currSA,
                        currFA,
                        "",
                        currPayPalCorreo,
                        CreditCard("","","")
                    )
                }
            }
        }

    }

    private fun showCustomDialogBox(popUpText: String) {
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

    fun addView(SelectedView: Int) {
        currentTypeOfPayment = SelectedView
        // Create the new view
        var newView = LayoutInflater.from(this).inflate(SelectedView, linearLayout, false)
        // Add the new view in between the existing views
        linearLayout.addView(newView, indexBefore + 1)
        /*
        if(SelectedView == R.layout.activity_calendar_view){
            setUpDateEdtClickListener()
        } */

    }

    fun deleteView() {
        /*
        if(currentTypeOfPayment == R.layout.activity_calendar_view){
            dateEdt.setOnClickListener(null)
        }
        */
        linearLayout.removeViewAt(indexBefore + 1)
    }

    companion object {
        private lateinit var actContext: SignUpComprador
        fun getContext(): Context {
            return actContext
        }
        fun loadBuyer(){
            val IntentS = Intent(actContext,BrowseProducts::class.java)
            actContext.startActivity(IntentS)
        }
        fun popUpError(){
            actContext.showCustomDialogBox("Error, ya registrado, pruebe a iniciar sesión.")
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
