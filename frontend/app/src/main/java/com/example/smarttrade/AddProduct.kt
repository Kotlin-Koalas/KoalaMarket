package com.example.smarttrade

import android.app.Dialog
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
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ScrollView
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

    private var existProduct = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_product)
        val scrollViewAddP = findViewById<ScrollView>(R.id.scrollView2)
        scrollViewAddP.overScrollMode = View.OVER_SCROLL_ALWAYS


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

        acceptButton.setOnClickListener {
            val patterOnlyLettNum = "^[a-zA-Z0-9]{12}".toRegex()

            var error = false
            var msgErrror = ""


            val prodNum = (prod.text).toString()
            if (!patterOnlyLettNum.containsMatchIn(prodNum)){
                error = true
                msgErrror += "- El número del producto tiene 12 caracteres  solamente números y letras\n"

            } else {

                if(existProduct){//TODO Poner automáticamente foto por defecto y guardarla

                }
                else {//TODO Producto no registrado en BD habilitar opcion foto

                }

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

            if( isUploadImage == -1  ){
                error = true
                msgErrror +="-Debes añadir una foto\n"
            }

            if(isUploadCertificate ==-1){
                error = true
                msgErrror += "Debes añadir un certificado ecológico\n"
            }


            if(prodNum.isEmpty() || currName.isEmpty() || currPrice.isEmpty() || currDescription.isEmpty() || currQuantity.isEmpty()){
                msgErrror = "-Todos los campos deben estar rellenados\n" + msgErrror
            }



            if(error){
                showCustomDialogBoxSeller(msgErrror)

            } else {
                //TODO codigo para añadir producto
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
                            Log.i("LLEGO",encodedImageString )
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


            /*
            uploadImageButton.setImageURI(uri)
            val imageByteArray = convertImageToByteArray(filePath)
            encodedImageString = Base64.encodeToString(imageByteArray, Base64.DEFAULT)
            Log.i("LLEGUE",encodedImageString)

             //TODO TAKE URI y guardarla
            isUploadImage = 1


        } else {
            Log.i("aris", "No seleccionado")
        }*/
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

                      Log.i("HOLA", "HOLA")
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


}


