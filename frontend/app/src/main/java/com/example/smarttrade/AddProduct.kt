package com.example.smarttrade

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.VectorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.core.graphics.drawable.toBitmap
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.smarttrade.logic.logic
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream

class AddProduct :Fragment() {

    private val PICK_FILE_REQUEST_CODE= 1
    var isUploadImage = -1
    var isUploadCertificate = -1
    lateinit var uploadImageButton: ImageView
    lateinit var uploadCertificateButton : Button
    private lateinit var imageCertificate: ImageView
    private lateinit var editTextCat1 :EditText
    private lateinit var textCat1 :TextView
    private lateinit var editTextCat2 :EditText
    private lateinit var textCat2 :TextView
    var buscarFoto = false
    lateinit var imageURI: Uri
    private lateinit var currview : View


    private var existProduct = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        currview =  inflater.inflate(R.layout.activity_add_product, container, false)


        //val scrollViewAddP = currview.findViewById<ScrollView>(R.id.scrollView2)
        //scrollViewAddP.overScrollMode = View.OVER_SCROLL_ALWAYS


        actContext = this


        imageCertificate = currview.findViewById<ImageView>(R.id.imageViewOkcertificate)
        uploadImageButton = currview.findViewById<ImageView>(R.id.uploadImageButton)



        uploadCertificateButton = currview.findViewById(R.id.buttonUploadCertificate)
        uploadCertificateButton.setOnClickListener {
            val intentU = Intent(Intent.ACTION_GET_CONTENT)
            intentU.type = "*/*"
            intentU.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intentU,PICK_FILE_REQUEST_CODE)
        }

        val acceptButton = currview.findViewById<Button>(R.id.acceptAddButton)
        val cancelButton = currview.findViewById<Button>(R.id.cancelAddButton)

        /*
        cancelButton.setOnClickListener{//TODO QUITAR
            val IntentS = Intent(this, MainActivity::class.java)
            startActivity(IntentS)
        }
        */
        val prod = (currview.findViewById<EditText>(R.id.editTextProductNumber))
        //prod.addTextChangedListener(MyTextWatcher(uploadImageButton))




        uploadImageButton.setOnClickListener {
            if(buscarFoto){
                pickmedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
            }
        }


        prod.addTextChangedListener {

            if(prod.length() == 12){
                //TODO comprobar si funciona
                logic.existProduct(prod.text.toString()){ productExist ->
                    if(productExist){
                        uploadImageButton.isClickable = false
                        buscarFoto = false
                    }

                    else{
                        uploadImageButton.isClickable = true
                        buscarFoto = true
                    }

                }


            }
            else{

                //uploadImageButton.setOnClickListener(null)
                //uploadImageButton.setImageURI(null)
                uploadImageButton.isClickable= false

                val defaultImage = resources.getDrawable(R.drawable.add_item)
                val defaultBitmap = (defaultImage as VectorDrawable).toBitmap()
                val defaultImageUri = Uri.parse("android.resource://${requireActivity().packageName}/${R.drawable.add_item}")
                uploadImageButton.setImageBitmap(defaultBitmap)

                isUploadImage = -1
            }

        }



        editTextCat1 = (currview.findViewById<EditText>(R.id.editTextCategoria1))
        editTextCat2 = (currview.findViewById<EditText>(R.id.editTextCategoria2))
        textCat1 = (currview.findViewById<TextView>(R.id.textViewCategoria1))
        textCat2 = (currview.findViewById<TextView>(R.id.textViewCategoria2))
        val categoriesArray = resources.getStringArray(R.array.categoriesApp)
        val categories = categoriesArray.toMutableList()

        val spinnerEcology = (currview.findViewById<Spinner>(R.id.spinnerEcology))
        val spinnerCategory =(currview.findViewById<Spinner>(R.id.spinnerCategory))
        val spinnerAdapter = ArrayAdapter<String>(actContext.requireContext(), android.R.layout.simple_spinner_dropdown_item, categories)
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

        spinnerEcology.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = spinnerEcology.selectedItem
                val selectedEcologyText = selectedItem.toString()
                when(selectedEcologyText){
                    "Verde" ->{
                        leafColor = "green"
                    }
                    "Naranja"->{
                        leafColor = "yellow"

                    }
                    "Rojo" ->{
                        leafColor = "red"
                    }
                    else ->{
                        leafColor = ""
                        Log.e("Error", "Ecologia desconocida")
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle nothing selected case (optional)
            }
        }



        acceptButton.setOnClickListener {


            val patterOnlyLettNum = "^[a-zA-Z0-9]{12}".toRegex()

            var error = false
            var msgErrror = ""


            prodNum = (prod.text).toString()
            if (!patterOnlyLettNum.containsMatchIn(prodNum)) {
                error = true
                msgErrror += "- El número del producto tiene 12 caracteres  solamente números y letras\n"

            }

            currName = (currview.findViewById<EditText>(R.id.editTextNameProduct).text).toString()
            val patterName = "^[a-zA-Z0-9\\s\\.,:;\\-\\(\\)\\/áéíóúñ\\$%&#@]{1,30}$".toRegex()
            if (!patterName.containsMatchIn(currName)) {
                error = true
                msgErrror += "- El nombre del producto no puede contener más de 30 carácteres ni carácteres especiales\n"
            }

            val currPrice =
                (currview.findViewById<EditText>(R.id.editTextNumberDecimalPrice).text).toString()
            val patternPrice = "^[-+]?[0-9]+([.][0-9]{1,2})?$".toRegex()
            if (!patternPrice.containsMatchIn(currPrice)) {
                error = true
                msgErrror += "-El precio solo puede tener 2 decimales\n"
            }

            currDescription =
                (currview.findViewById<EditText>(R.id.editTextDescription).text).toString()
            val patterDescription =
                "^[a-zA-Z0-9\\s\\.,:;\\-\\(\\)\\/áéíóúñ\\$%&#@]{1,150}$".toRegex()
            if (!patterDescription.containsMatchIn(currDescription)) {
                error = true
                msgErrror += "-La descripción no puede tener más de 150 carácteres\n"
            }

            val currQuantity = (currview.findViewById<EditText>(R.id.editTextQuantity).text).toString()
            val patternQuantity = "^(?!0)[0-9]{1,3}$".toRegex()
            if (!patternQuantity.containsMatchIn(currQuantity)) {
                error = true
                msgErrror += "-Cantidad mínima 1 y máxima son 999\n"
            }


            cat1 = (editTextCat1.text).toString()
            cat2 = (editTextCat2.text).toString()
            when (categorSelected) {
                "Tecnología" -> {
                    val patternCon = "^[-+]?[0-9]+([.][0-9]{1,2})?$".toRegex()
                    val patternTech = "^[a-zA-Z0-9 ]+$".toRegex()
                    if (!patternCon.containsMatchIn(cat1) || !patternTech.containsMatchIn(cat2)) {
                        error = true
                        msgErrror += "-Tecnología: Consumo solo números como máximo 2 decimales (12.50) y marca solo pueden tener letras y números \n"
                    }

                }

                "Juguete" -> {
                    val patternEdadRec = "^[0-9]{1,2}$".toRegex() // Números de 0 a 18
                    val patternMaterial = "^[a-zA-Z]+$".toRegex() // Letras
                    if (!patternEdadRec.containsMatchIn(cat1) || !patternMaterial.containsMatchIn(
                            cat2
                        )
                    ) {
                        error = true
                        msgErrror += "-Juguete: Edad Recomendada solo pueden ser números de 0 a 99, Material puede tener solo letras\n"
                    }
                }

                "Ropa" -> {
                    val patternTalla =
                        "^[SMLX]{1,3}$|^([2][0-9]|[3][0-9]|[4][0-5])$".toRegex() //TODO habría que arreglarlo
                    val patternColor = "^[a-zA-Z]+$".toRegex() // Letras

                    if (!patternTalla.containsMatchIn(cat1) || !patternColor.containsMatchIn(
                            cat2
                        )
                    ) {
                        error = true
                        msgErrror += "-Ropa: Talla solo puede ser XS, S, M, L, XL, XXL o números de 30 a 50, Color solo puede tener letras\n"
                    }
                }

                "Alimentación" -> {
                    val patternMac = "^[a-zA-Z0-9 ]+$".toRegex()
                    val patternCalorias = "^[0-9]{1,5}$".toRegex() // Número de 1 a 5 cifras

                    if (!patternMac.containsMatchIn(cat1) || !patternCalorias.containsMatchIn(
                            cat2
                        )
                    ) {
                        error = true
                        msgErrror += "-Alimentación:Macros solo puede tener letras y números, Calorias solo puede ser un número de 1 a 5 cifras\n"
                    }
                }

                else -> {


                }
            }



            if (isUploadImage == -1) {
                error = true
                msgErrror += "-Debes añadir una foto\n"
            }

            if (isUploadCertificate == -1) {
                error = true
                msgErrror += "-Debes añadir un certificado ecológico\n"
            }


            if (prodNum.isEmpty() || currName.isEmpty() || currPrice.isEmpty() || currDescription.isEmpty() || currQuantity.isEmpty() || cat1.isEmpty() || cat2.isEmpty()) {
                error = true
                msgErrror = "-Todos los campos deben estar rellenados\n" + msgErrror
            }


            if (error) {
                showCustomDialogBox(msgErrror)

            } else {//TODO LOGIC
                uploadImageButton.isDrawingCacheEnabled = true
                uploadImageButton.buildDrawingCache()
                val bitmap = uploadImageButton.drawingCache
                val outputStream = ByteArrayOutputStream()
                bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, outputStream)
                val imageProduct = outputStream.toByteArray()
                doublePrice = currPrice
                intStock = currQuantity.toInt()
                logic.getImage(imageProduct,"").toString()

            }
        }


        return currview
    }

    fun getFileFromUri(context: Context, uri: Uri): File {
        val inputStream: InputStream = context.contentResolver.openInputStream(uri)!!
        val file = File(context.cacheDir, "tempFile")
        file.createNewFile()

        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

        return file
    }

    fun showCustomDialogBox(msgErrror: String) {
        val dialog = Dialog(actContext.requireContext())
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

    fun showCustomDialogBoxSuccess(msgSuccess: String) {
        val dialog = Dialog(actContext.requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.pop_up_alert_success)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val messageBox = dialog.findViewById<TextView>(R.id.textViewErrorText)
        val btnOk = dialog.findViewById<Button>(R.id.buttonOkPopUp)

        btnOk.setOnClickListener {
            dialog.dismiss()
        }
        messageBox.text = msgSuccess

        dialog.show()
    }

    private fun updateCategoryText(selectedCategory: String) {
        val layoutInflater = LayoutInflater.from(actContext.requireContext())

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

    val pickmedia =registerForActivityResult(PickVisualMedia()){uri->
        if(uri!=null){
            uploadImageButton.setImageURI(uri)
            imageURI = uri
            isUploadImage = 1
        }else{

        }

    }

    fun convertImageToByteArray(imageFile: File): ByteArray {
        val fis = FileInputStream(imageFile)
        val byteArray = ByteArray(imageFile.length().toInt())
        fis.read(byteArray)
        fis.close()
        return byteArray
    }

/*
    private val pickmedia = registerForActivityResult(PickVisualMedia()) { uri ->
        if (uri != null) {
            when (uri.scheme) {
                "file" -> {

                    val ImagenUri = uri

                    if (archivoImagen.exists()) {



                        //uploadImageButton.setImageURI(uri)
                        isUploadImage = 1
                    } else {
                        Log.e("AddProduct", "Archivo no encontrado: $archivoImagen")
                    }
                }
                "content" -> {
                    try {
                        val flujoEntrada = contentResolver.openInputStream(uri)
                        if (flujoEntrada != null) {

                            runBlocking { val archivoTemporal = crearArchivoTemporalImagen().await()


                            // val x = logic.getImage(archivoTemporal)



                            val flujoSalida = FileOutputStream(archivoTemporal)
                            flujoSalida.write(flujoEntrada.readBytes())
                            flujoSalida.close()
                            flujoEntrada.close()

                            val arregloBytesImagen = convertImageToByteArray(archivoTemporal)
                            encodedImageString = Base64.encodeToString(arregloBytesImagen, Base64.DEFAULT)

                            uploadImageButton.setImage
                            isUploadImage = 1
                            }
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

    }*/

    /*
    fun backButtonClick(view: View) { //TODO QUITAR
        val backPage = Intent(this, MainActivity::class.java)
        startActivity(backPage)
    }
*/

    /*
    private suspend fun crearArchivoTemporalImagen():Deferred< File>{//TODO QUITAR
        return GlobalScope.async {
            val nombreTemporal = "imagen_temporal_${System.currentTimeMillis()}.jpg"
            val directorioCache = applicationContext.cacheDir
            return@async File(directorioCache, nombreTemporal)
        }

    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val uri = data?.data

            isUploadCertificate = 1
            imageCertificate.visibility = View.VISIBLE

            val fileName = uri?.pathSegments?.last()

        }

    }



    companion object {
        private lateinit var actContext:AddProduct
        private lateinit var encodedImageString :String
        private lateinit var categorSelected : String
        private lateinit var leafColor: String
        private lateinit var currName: String
        private var intStock = 0
        private lateinit var doublePrice:String
        private lateinit var currDescription: String
        private lateinit var prodNum: String
        private lateinit var cat1: String
        private lateinit var cat2: String
        private lateinit var currview : View
        fun getContext(): Context {
            return actContext.requireContext()
        }

        fun popUpError(){
            actContext.showCustomDialogBox("Error al intentar añadir producto")
        }


        fun productAded(){
            actContext.showCustomDialogBoxSuccess("Producto añadido correctamente")
        }
        fun setEncodedImageString(encodedImageString: String){
            this.encodedImageString = encodedImageString
            when (categorSelected) {

                "Tecnología" -> {
                    Log.i("doublePrice value and type", doublePrice.toString() + " " + doublePrice::class.simpleName)
                    Log.i("IMAGEN", Companion.encodedImageString)
                    try {
                        logic.addTechnology(
                            currName,
                            doublePrice,
                            encodedImageString,
                            intStock,
                            currDescription,
                            leafColor,
                            prodNum,
                            cat2,
                            cat1
                        )
                    } catch (exception: Exception) {
                        Log.e("ERROR", "Error adding technology: ${exception.message}")
                    }


                }

                "Juguete" -> {

                    logic.addToy(
                        currName,
                        doublePrice,
                        encodedImageString,
                        intStock,
                        currDescription,
                        leafColor,
                        prodNum,
                        cat2,
                        cat1
                    )

                }

                "Ropa" -> {
                    logic.addClothes(
                        currName,
                        doublePrice,
                        encodedImageString,
                        intStock,
                        currDescription,
                        leafColor,
                        prodNum,
                        cat2,
                        cat1
                    )

                }

                "Alimentación" -> {
                    logic.addFood(
                        currName,
                        doublePrice,
                        encodedImageString,
                        intStock,
                        currDescription,
                        leafColor,
                        prodNum,
                        cat2,
                        cat1
                    )

                }

                else -> {
                    Log.e("ERROR", "ERROR")

                }
            }

        }

        fun getCurrView(): View{
            return currview
        }

    }
}



