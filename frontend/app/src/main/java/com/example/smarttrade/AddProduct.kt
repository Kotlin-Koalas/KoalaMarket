package com.example.smarttrade

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.VectorDrawable
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toFile
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class AddProduct :AppCompatActivity() {

    private val PICK_FILE_REQUEST_CODE= 1
    var isUploadImage = -1
    var isUploadCertificate = -1
    lateinit var uploadImageButton: ImageButton
    lateinit var uploadCertificateButton : Button
    private lateinit var imageCertificate: ImageView
    private lateinit var encodedImageString :String
    private lateinit var editTextCat1 :EditText
    private lateinit var textCat1 :TextView
    private lateinit var editTextCat2 :EditText
    private lateinit var textCat2 :TextView
    private lateinit var categorSelected : String

    private var existProduct = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_product)
        val scrollViewAddP = findViewById<ScrollView>(R.id.scrollView2)
        scrollViewAddP.overScrollMode = View.OVER_SCROLL_ALWAYS

        actContext = this


        imageCertificate = findViewById<ImageView>(R.id.imageViewOkcertificate)
        uploadImageButton = findViewById(R.id.uploadImageButton)
        uploadImageButton.setOnClickListener {
            pickmedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }

        uploadCertificateButton = findViewById(R.id.buttonUploadCertificate)
        uploadCertificateButton.setOnClickListener {
            val intentU = Intent(Intent.ACTION_GET_CONTENT)
            intentU.type = "*/*"
            intentU.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intentU,PICK_FILE_REQUEST_CODE)
        }

        val acceptButton = findViewById<Button>(R.id.acceptAddButton)
        val cancelButton = findViewById<Button>(R.id.cancelAddButton)


        cancelButton.setOnClickListener{//TODO cambiar de página
            val IntentS = Intent(this, MainActivity::class.java)
            startActivity(IntentS)
        }

        val prod = (findViewById<EditText>(R.id.editTextProductNumber))
        prod.addTextChangedListener(MyTextWatcher(uploadImageButton))



        editTextCat1 = (findViewById<EditText>(R.id.editTextCategoria1))
        editTextCat2 = (findViewById<EditText>(R.id.editTextCategoria2))
        textCat1 = (findViewById<TextView>(R.id.textViewCategoria1))
        textCat2 = (findViewById<TextView>(R.id.textViewCategoria2))
        val categoriesArray = resources.getStringArray(R.array.categoriesApp)
        val categories = categoriesArray.toMutableList()
        val spinnerCategory =(findViewById<Spinner>(R.id.spinnerCategory))
        val spinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories)
        spinnerCategory.adapter = spinnerAdapter

        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCategory = spinnerAdapter.getItem(position) ?: ""
                updateCategoryText(selectedCategory)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle nothing selected case (optional)
            }
        }

        acceptButton.setOnClickListener {

            val patterOnlyLettNum = "^[a-zA-Z0-9]{12}".toRegex()

            var error = false
            var msgErrror = ""


            val prodNum = (prod.text).toString()
            if (!patterOnlyLettNum.containsMatchIn(prodNum)){
                error = true
                msgErrror += "- El número del producto tiene 12 caracteres  solamente números y letras\n"

            }

            val currName = (findViewById<EditText>(R.id.editTextNameProduct).text).toString()
            val patterName = "^[a-zA-Z0-9\\s\\.,:;\\-\\(\\)\\/áéíóúñ\\$%&#@]{1,30}$".toRegex()
            if(!patterName.containsMatchIn(currName)){
                error = true
                msgErrror += "- El nombre del producto no puede contener más de 30 carácteres ni carácteres especiales\n"
            }

            val currPrice = (findViewById<EditText>(R.id.editTextNumberDecimalPrice).text).toString()
            val patternPrice =  "^[-+]?[0-9]+([.][0-9]{1,2})?$".toRegex()
            if(!patternPrice.containsMatchIn(currPrice)){
                error = true
                msgErrror += "-El precio solo puede tener 2 decimales\n"
            }

            val currDescription = (findViewById<EditText>(R.id.editTextDescription).text).toString()
            val patterDescription =  "^[a-zA-Z0-9\\s\\.,:;\\-\\(\\)\\/áéíóúñ\\$%&#@]{1,150}$".toRegex()
            if(!patterDescription.containsMatchIn(currDescription)){
                error = true
                msgErrror += "-La descripción no puede tener más de 150 carácteres\n"
            }

            val currQuantity = (findViewById<EditText>(R.id.editTextQuantity).text).toString()
            val patternQuantity = "^(?!0)[0-9]{1,3}$".toRegex()
            if (!patternQuantity.containsMatchIn(currQuantity)){
                error = true
                msgErrror += "-Cantidad mínima 1 y máxima son 999\n"
            }



            val cat1 = (editTextCat1.text).toString()
            val cat2 = (editTextCat2.text).toString()
            when(categorSelected){
                "Tecnología" -> {
                    val patternTech = "^[a-zA-Z0-9 ]+$".toRegex()
                    if (!patternTech.containsMatchIn(cat1) || !patternTech.containsMatchIn(cat2)) {
                        error = true
                        msgErrror += "-Tecnología: Consumo y marca solo pueden tener letras y números \n"
                    }

                }
                "Juguete" -> {
                    val patternEdadRec = "^[0-9]{1,2}$".toRegex() // Números de 0 a 18
                    val patternMaterial = "^[a-zA-Z]+$".toRegex() // Letras
                    if (!patternEdadRec.containsMatchIn(cat1) || !patternMaterial.containsMatchIn(cat2)) {
                        error = true
                        msgErrror += "-Juguete: Edad Recomendada solo pueden ser números de 0 a 99, Material puede tener solo letras\n"
                    }
                }
                "Ropa" -> {
                    val patternTalla = "^[SMLX]{1,3}$|^([2][0-9]|[3][0-9]|[4][0-5])$".toRegex() //TODO habría que arreglarlo
                    val patternColor = "^[a-zA-Z]+$".toRegex() // Letras

                    if (!patternTalla.containsMatchIn(cat1) || !patternColor.containsMatchIn(cat2)) {
                        error = true
                        msgErrror += "-Ropa: Talla solo puede ser XS, S, M, L, XL, XXL o números de 30 a 50, Color solo puede tener letras\n"
                    }
                }
                "Alimentación" -> {
                    val patternMac = "^[a-zA-Z0-9 ]+$".toRegex()
                    val patternCalorias = "^[0-9]{1,5}$".toRegex() // Número de 1 a 5 cifras

                    if (!patternMac.containsMatchIn(cat1) || !patternCalorias.containsMatchIn(cat2)) {
                        error = true
                        msgErrror += "-Alimentación:Macros solo puede tener letras y números, Calorias solo puede ser un número de 1 a 5 cifras\n"
                    }
                }
                else ->{


                }
            }



            if( isUploadImage == -1  ){
                error = true
                msgErrror +="-Debes añadir una foto\n"
            }

            if(isUploadCertificate ==-1){
                error = true
                msgErrror += "Debes añadir un certificado ecológico\n"
            }


            if(prodNum.isEmpty() || currName.isEmpty() || currPrice.isEmpty() || currDescription.isEmpty() || currQuantity.isEmpty() ||cat1.isEmpty() || cat2.isEmpty()){
                error = true
                msgErrror = "-Todos los campos deben estar rellenados\n" + msgErrror
            }


            if(error){
                showCustomDialogBoxSeller(msgErrror)

            } else {

            }
        }
    }

    fun showCustomDialogBoxSeller(msgErrror: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.pop_up_alert)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val messageBox = dialog.findViewById<TextView>(R.id.textViewErrorText)
        val btnOk = dialog.findViewById<Button>(R.id.buttonOkPopUp)

        btnOk.setOnClickListener {
            dialog.dismiss()
        }
        messageBox.text = msgErrror

        dialog.show()
    }

    private fun updateCategoryText(selectedCategory: String) {
        val layoutInflater = LayoutInflater.from(this)

        when (selectedCategory) {
            "Ropa" -> {
                textCat1.setText("Talla")
                textCat2.setText("Color")
                editTextCat1.setHint("42")
                editTextCat2.setHint("Verde")
                editTextCat1.setText(null)
                editTextCat2.setText(null)
                categorSelected = selectedCategory

            }
            "Tecnología" -> {
                textCat1.setText("Consumo electrónico")
                textCat2.setText("Marca")
                editTextCat1.setHint("270 kWh")
                editTextCat2.setHint("Samsung")
                editTextCat1.setText(null)
                editTextCat2.setText(null)
                categorSelected = selectedCategory


            }
            "Juguete" -> {
                textCat1.setText("Edad Recomendada ")
                textCat2.setText("Material")
                editTextCat1.setHint("3 años")
                editTextCat2.setHint("Plástico")
                categorSelected = selectedCategory
                editTextCat1.setText(null)
                editTextCat2.setText(null)
            }
            "Alimentación" -> {
                textCat1.setText("Macros")
                textCat2.setText("Calorias")
                editTextCat1.setHint("15g proteina, 10g grasas, 15g carb")
                editTextCat2.setHint("155")
                categorSelected = selectedCategory
                editTextCat1.setText(null)
                editTextCat2.setText(null)
            }
            else ->{
                textCat1.setText("")
                textCat2.setText("")
            }

        }
    }

    fun convertImageToByteArray(imageFile: File): ByteArray {
        val fis = FileInputStream(imageFile)
        val byteArray = ByteArray(imageFile.length().toInt())
        fis.read(byteArray)
        fis.close()
        return byteArray
    }


    private val pickmedia = registerForActivityResult(PickVisualMedia()) { uri ->
        if (uri != null) {
            when (uri.scheme) {
                "file" -> {
                    val archivoImagen = uri.toFile()
                    if (archivoImagen.exists()) {
                        val arregloBytesImagen = convertImageToByteArray(archivoImagen)
                        encodedImageString = Base64.encodeToString(arregloBytesImagen, Base64.DEFAULT)
                        uploadImageButton.setImageURI(uri)
                        isUploadImage = 1
                    } else {
                        Log.e("AddProduct", "Archivo no encontrado: $archivoImagen")
                    }
                }
                "content" -> {
                    try {
                        val flujoEntrada = contentResolver.openInputStream(uri)
                        if (flujoEntrada != null) {
                            val archivoTemporal = crearArchivoTemporalImagen()
                            val flujoSalida = FileOutputStream(archivoTemporal)
                            flujoSalida.write(flujoEntrada.readBytes())
                            flujoSalida.close()
                            flujoEntrada.close()

                            val arregloBytesImagen = convertImageToByteArray(archivoTemporal)
                            encodedImageString = Base64.encodeToString(arregloBytesImagen, Base64.DEFAULT)

                            uploadImageButton.setImageURI(uri)
                            isUploadImage = 1
                        }
                    } catch (e: Exception) {
                        Log.e("AddProduct", "Error al obtener los datos de la imagen: $e")
                    }
                }
                else -> { // URL remota o esquema no compatible
                    Log.w("AddProduct", "Esquema URI no compatible: ${uri.scheme}")
                }
            }
        } else {
            Log.i("aris", "No seleccionado")
        }

    }

    fun backButtonClick(view: View) { //TODO cambiar de pagina
        val backPage = Intent(this, MainActivity::class.java)
        startActivity(backPage)
    }

    private fun crearArchivoTemporalImagen(): File {
        val nombreTemporal = "imagen_temporal_${System.currentTimeMillis()}.jpg"
        val directorioCache = applicationContext.cacheDir
        return File(directorioCache, nombreTemporal)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
            val uri = data?.data

            isUploadCertificate = 1
            imageCertificate.visibility = View.VISIBLE

            val fileName = uri?.pathSegments?.last()

        }

    }

      private inner class MyTextWatcher (private val uploadImageButton: ImageButton) : TextWatcher{

              override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                  // No se necesita
              }
              override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                  // No se necesita
              }
              override fun afterTextChanged(s: Editable?) {
                  if(s?.length == 12){


                      uploadImageButton.visibility = View.VISIBLE

                      //TODO poner o no la foto en función de si existe o no el producto

                      /* if (s existe en Lista de productos (mismo PN))
                      *         coger URL y de la foto y ponerla como visualización
                      *         no se puede seleccionar otra foto, hasta que se cambio PN
                      *
                      * else
                      *       nada porque ya está que se pueda coger y poner una foto
                      *
                      *
                      * */
                  }
                  else{
                      val defaultImage = resources.getDrawable(R.drawable.add_item)
                      val defaultBitmap = (defaultImage as VectorDrawable).toBitmap()
                      val defaultImageUri = Uri.parse("android.resource://${packageName}/${R.drawable.add_item}")
                      uploadImageButton.setImageBitmap(defaultBitmap)
                      isUploadImage = -1
                     uploadImageButton.visibility = View.INVISIBLE
                  }

              }

      }

    companion object {
        private lateinit var actContext:AddProduct
        fun getContext(): Context {
            return actContext
        }


    }
}



