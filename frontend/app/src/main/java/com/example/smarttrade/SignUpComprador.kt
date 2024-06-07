package com.example.smarttrade

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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.smarttrade.logic.Identification
import com.example.smarttrade.models.CreditCard
import java.time.LocalDate
import java.util.Calendar
import com.example.smarttrade.utils.CheckValuesUtils

class SignUpComprador : AppCompatActivity() {

    private lateinit var linearLayout:LinearLayout
    private var indexBefore = 0
    private var currentTypeOfPayment = -1

    private var isID = false
    private var isName = false
    private var isSurname = false
    private var isDNI = false
    private var isEmail = false
    private var isPassword = false
    private var isRPassword = false
    private var isSA = false
    private var isFA = false
    private var isPayPal = false
    private var isBizum = false
    private var isNumTarj = false
    private var isExpM = false
    private var isExpY = false
    private var isCVC = false

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
        actContextBuyer = this
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

        val goBackButton = findViewById<ImageView>(R.id.backArrow)
        goBackButton.setOnClickListener{
            val IntentS = Intent(this,MainActivity::class.java)
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
            isID = false
            isName = false
            isSurname = false
            isDNI = false
            isEmail = false
            isPassword = false
            isRPassword = false
            isSA = false
            isFA = false
            isPayPal = false
            isBizum = false
            isNumTarj = false
            isExpM = false
            isExpY = false
            isCVC = false

            val firstPassword = firstPasswordField.text.toString()
            val secondPassword = secondPasswordField.text.toString()
            var notEqual = !(firstPassword.equals(secondPassword))
            if(notEqual){
                popUpOrNot = true
                popUpText += "- Las contraseñas no coinciden, por favor, asegurate de que sean iguales.\n"
                isRPassword = true
            }

            if(!CheckValuesUtils.checkIfValidPassword(firstPassword) || !CheckValuesUtils.checkIfValidPassword(secondPassword)) {
                popUpOrNot = true
                popUpText += "- La contraseña debe contener mínimo un número, una letra mayúscula, y una letra minúscula. Además no debe contener caracteres especiales, solo letras (mayúsculas y minúsculas), números y tener una logitud mínima de 4 caracteres.\n"
                isPassword = true
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
                    isExpM = true
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
                    isExpY = true
                } else  if (currentYear == yearValue && monthValue < currentMonth) {
                    popUpOrNot = true
                    popUpText += "- Inserte una fecha de cacucidad de la tarjeta a futuro.\n"
                    isExpY = true
                    isExpM = true
                }
            }

            val currNameField = findViewById<EditText>(R.id.editTextName).text
            val currName = currNameField.toString()
            val currSurnameField = findViewById<EditText>(R.id.editTextSurname).text
            val currSurname = currSurnameField.toString()
            val currIdField = findViewById<EditText>(R.id.editTextID).text
            val currId = currIdField.toString()
            val patternOnlyLetters = "^[a-zA-Z]*$".toRegex()
            if(!CheckValuesUtils.checkIfValidName(currName)) {
                popUpOrNot = true
                popUpText += "- En el campo del Nombre solo deben hacer letras\n"
                isName = true
            }
            if(!CheckValuesUtils.checkIfValidName(currSurname)) {
                popUpOrNot = true
                popUpText += "- En el campo del Apellidos solo deben haber letras.\n"
                isSurname = true
            }
            if(!CheckValuesUtils.checkIfValidName(currId)) {
                popUpOrNot = true
                popUpText += "- En el campo del ID de Usuario solo deben haber letras.\n"
                isID = true
            }

            var currDNI = findViewById<EditText>(R.id.editTextDNI).text.toString()
            if(!CheckValuesUtils.checkIfValidDNI(currDNI)) {
                popUpOrNot = true
                popUpText += "- Inserte un DNI válido, con 8 números y una letra al final.\n"
                isDNI = true
            } else{
                val numbers = currDNI.substring(0, currDNI.length - 1) // Extract numbers
                val letter = currDNI.last().uppercaseChar() // Get last character and convert to uppercase
                currDNI = "$numbers$letter" // Combine numbers and uppercase letter
            }

            val currCorreo = findViewById<EditText>(R.id.editTextEmail).text.toString()
            if(!CheckValuesUtils.checkIfValidEmail(currCorreo)) {
                popUpOrNot = true
                popUpText += "- Proporcione una dirección de correo válida siguiendo el formato standard.\n"
                isEmail = true
            }

            if(currentTypeOfPayment == R.layout.paypal_option) {
                val currPayPalCorreo = findViewById<EditText>(R.id.editTextEmailPayPal).text.toString()
                if(!CheckValuesUtils.checkIfValidEmail(currPayPalCorreo)) {
                    popUpOrNot = true
                    popUpText += "- Proporcione una dirección de correo de la cuenta de PayPal válida siguiendo el formato standard.\n"
                    isPayPal = true
                }
            }

            val currSA = findViewById<EditText>(R.id.editTextAddress).text.toString()
            val currFA = findViewById<EditText>(R.id.editTextFAddress).text.toString()
            if(currSA.isEmpty()){
                popUpOrNot = true
                popUpText += "- Debe rellenar el campo de Dirección de Envio.\n"
                isSA = true
            }
            if(currFA.isEmpty()){
                popUpOrNot = true
                popUpText += "- Debe rellenar el campo de Dirección de Facturación.\n"
                isFA = true
            }
            if(currId.isEmpty() || currName.isEmpty() || currSurname.isEmpty() || currCorreo.isEmpty() || firstPassword.isEmpty() || secondPassword.isEmpty() || currDNI.isEmpty()){
                popUpOrNot = true
                popUpText += "- Todos los campos deben estar rellenados.\n"
            } else if(currentTypeOfPayment == R.layout.paypal_option){
                val currPayPalCorreo = findViewById<EditText>(R.id.editTextEmailPayPal).text.toString()
                if(currPayPalCorreo.isEmpty()){
                    popUpOrNot = true
                    popUpText += "- Debe rellenar el campo del Correo de Paypal.\n"
                    isPayPal = true
                }
            } else if(currentTypeOfPayment == R.layout.bizum_option) {
                val currBizumNum = findViewById<EditText>(R.id.editTextBizumNumber).text.toString()
                if (currBizumNum.isEmpty()) {
                    popUpOrNot = true
                    popUpText += "- Debe rellenar el campo del número de telefono de Bizum.\n"
                    isBizum = true
                }
            } else if(currentTypeOfPayment == R.layout.activity_calendar_view) {
                val currNumTarj = findViewById<EditText>(R.id.editTextnumTarj).text.toString()
                val currExpM = findViewById<EditText>(R.id.editTextCad).text.toString()
                val currExpY = findViewById<EditText>(R.id.editTextCad2).text.toString()
                val currCVC = findViewById<EditText>(R.id.editTextCVC).text.toString()
                if (currNumTarj.isEmpty()){
                    popUpOrNot = true
                    popUpText += "- Debe rellenar el campo del Número de tarjeta.\n"
                    isNumTarj = true
                }
                if(currCVC.isEmpty()){
                    popUpOrNot = true
                    popUpText += "- Debe rellenar el campo de CVC.\n"
                    isCVC = true
                }
                if (currExpM.isEmpty() || currExpY.isEmpty()) {
                    popUpOrNot = true
                    popUpText += "- Debe rellenar el campo de Caducidad.\n"
                }
            }

            if(popUpOrNot){
                showCustomDialogBox(popUpText)
                checkErrors()
            } else  {
                if(currentTypeOfPayment == R.layout.activity_calendar_view) {
                    val currNumTarj = findViewById<EditText>(R.id.editTextnumTarj).text.toString()
                    val currExpM = findViewById<EditText>(R.id.editTextCad).text.toString()
                    val currExpY = findViewById<EditText>(R.id.editTextCad2).text.toString()
                    val currCVC = findViewById<EditText>(R.id.editTextCVC).text.toString()
                    Identification.signInBuyer(
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
                    Identification.signInBuyer(
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
                    Identification.signInBuyer(
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

    private fun checkErrors(){
        if(isID || findViewById<EditText>(R.id.editTextID).text.isEmpty()){
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
        if(isDNI){
            findViewById<ConstraintLayout>(R.id.frameLayoutDNI).setBackgroundResource(R.drawable.red_border)
        } else {
            findViewById<ConstraintLayout>(R.id.frameLayoutDNI).setBackgroundResource(R.drawable.basic_grey_shape)
        }
        if(isEmail){
            findViewById<ConstraintLayout>(R.id.frameLayoutEmail).setBackgroundResource(R.drawable.red_border)
        } else {
            findViewById<ConstraintLayout>(R.id.frameLayoutEmail).setBackgroundResource(R.drawable.basic_grey_shape)
        }
        if(isPassword){
            findViewById<ConstraintLayout>(R.id.frameLayoutPassword).setBackgroundResource(R.drawable.red_border)
        } else {
            findViewById<ConstraintLayout>(R.id.frameLayoutPassword).setBackgroundResource(R.drawable.basic_grey_shape)
        }
        if(isRPassword || findViewById<EditText>(R.id.editTextRPassword).text.isEmpty()){
            findViewById<ConstraintLayout>(R.id.frameLayoutRPassword).setBackgroundResource(R.drawable.red_border)
        } else {
            findViewById<ConstraintLayout>(R.id.frameLayoutRPassword).setBackgroundResource(R.drawable.basic_grey_shape)
        }
        if(isSA){
            findViewById<ConstraintLayout>(R.id.frameLayoutAddress).setBackgroundResource(R.drawable.red_border)
        } else {
            findViewById<ConstraintLayout>(R.id.frameLayoutAddress).setBackgroundResource(R.drawable.basic_grey_shape)
        }
        if(isFA){
            findViewById<ConstraintLayout>(R.id.frameLayoutFAddress).setBackgroundResource(R.drawable.red_border)
        } else {
            findViewById<ConstraintLayout>(R.id.frameLayoutFAddress).setBackgroundResource(R.drawable.basic_grey_shape)
        }
        if(currentTypeOfPayment == R.layout.paypal_option) {
            if (isPayPal) {
                findViewById<ConstraintLayout>(R.id.frameLayoutEmailPaypal).setBackgroundResource(R.drawable.red_border)
            } else {
                findViewById<ConstraintLayout>(R.id.frameLayoutEmailPaypal).setBackgroundResource(R.drawable.basic_grey_shape)
            }
        }
        if(currentTypeOfPayment == R.layout.bizum_option) {
            if (isBizum || findViewById<EditText>(R.id.editTextBizumNumber).text.isEmpty()) {
                findViewById<ConstraintLayout>(R.id.frameLayoutBizumNumber).setBackgroundResource(R.drawable.red_border)
            } else {
                findViewById<ConstraintLayout>(R.id.frameLayoutBizumNumber).setBackgroundResource(R.drawable.basic_grey_shape)
            }
        }
        if(currentTypeOfPayment == R.layout.activity_calendar_view) {
            if (isNumTarj || findViewById<EditText>(R.id.editTextnumTarj).text.isEmpty()) {
                findViewById<ConstraintLayout>(R.id.frameLayoutNumTarj).setBackgroundResource(R.drawable.red_border)
            } else {
                findViewById<ConstraintLayout>(R.id.frameLayoutNumTarj).setBackgroundResource(R.drawable.basic_grey_shape)
            }
            if (isExpM) {
                findViewById<ConstraintLayout>(R.id.frameLayoutExpirationDate).setBackgroundResource(
                    R.drawable.red_border
                )
            } else {
                findViewById<ConstraintLayout>(R.id.frameLayoutExpirationDate).setBackgroundResource(
                    R.drawable.basic_grey_shape
                )
            }
            if (isExpY) {
                findViewById<ConstraintLayout>(R.id.frameLayoutExpirationDate).setBackgroundResource(
                    R.drawable.red_border
                )
            } else {
                findViewById<ConstraintLayout>(R.id.frameLayoutExpirationDate).setBackgroundResource(
                    R.drawable.basic_grey_shape
                )
            }
            if (isCVC || findViewById<EditText>(R.id.editTextCVC).text.isEmpty()) {
                findViewById<ConstraintLayout>(R.id.frameLayoutCVC).setBackgroundResource(R.drawable.red_border)
            } else {
                findViewById<ConstraintLayout>(R.id.frameLayoutCVC).setBackgroundResource(R.drawable.basic_grey_shape)
            }
        }

    }

    companion object {
        private lateinit var actContextBuyer: SignUpComprador
        fun getContext(): Context {
            return actContextBuyer
        }
        fun setContext(context: SignUpComprador){
            actContextBuyer = context
        }
        fun loadBuyer(){
            val IntentS = Intent(actContextBuyer,BuyerMainScreen::class.java)
            actContextBuyer.startActivity(IntentS)
        }
        fun popUpError(){
            actContextBuyer.showCustomDialogBox("Error, ya registrado, pruebe a iniciar sesión.")
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
