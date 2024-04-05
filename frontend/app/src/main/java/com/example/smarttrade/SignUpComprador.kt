package com.example.smarttrade

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.xmlpull.v1.XmlPullParser
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
        val cancelButton = findViewById<Button>(R.id.buttonCancel)
        val signUpButton = findViewById<Button>(R.id.buttonSignUp)
        cancelButton.setOnClickListener{
            val IntentS = Intent(this,MainActivity::class.java)
            startActivity(IntentS)
        }
        signUpButton.setOnClickListener{
            //TODO code to sign up
        }
        
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
