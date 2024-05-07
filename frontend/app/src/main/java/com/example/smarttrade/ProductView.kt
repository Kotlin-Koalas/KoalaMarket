package com.example.smarttrade

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smarttrade.adapters.SellerAdapter
import com.example.smarttrade.logic.ListWishRequests
import com.example.smarttrade.logic.ShoppingCartRequests
import com.example.smarttrade.logic.logic
import com.example.smarttrade.models.PersonBuyer
import com.example.smarttrade.mediador.MediatorShoppingCart
import com.example.smarttrade.models.clothes_representation
import com.example.smarttrade.models.clothes_representation_cart
import com.example.smarttrade.models.clothes_representation_seller
import com.example.smarttrade.models.food_representation_cart
import com.example.smarttrade.models.food_representation_seller
import com.example.smarttrade.models.product_representation
import com.example.smarttrade.models.seller_representation
import com.example.smarttrade.models.technology_representation_cart
import com.example.smarttrade.models.technology_representation_seller
import com.example.smarttrade.models.toy_representation_cart
import com.example.smarttrade.models.toy_representation_seller
import com.example.smarttrade.volleyRequestClasses.ImageURLtoBitmapConverter

class ProductView : AppCompatActivity() {

    var currentStock  = 1
    lateinit var adapterS : SellerAdapter
    lateinit var sellerList : MutableList<seller_representation>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        val recyclerView = findViewById<GridView>(R.id.recycledViewSellers)
        sellerList  = mutableListOf()
        adapterS = SellerAdapter(this,sellerList)
        recyclerView.adapter = adapterS

        val product = intent.getSerializableExtra("product") as product_representation

        val name = product.name
        val price = product.price
        val description = product.description
        val image = product.image
        val stock = product.stock
        val productNumber = product.PN
        val category = product.type

        adapterS.setLeafColor(product.leafColor)





        logic.getAllSellers(productNumber)


        val backMainScreen = findViewById<ImageView>(R.id.backArrow)
        val nameText = findViewById<TextView>(R.id.nameProduct)
        val priceText = findViewById<TextView>(R.id.price)
        val stockText = findViewById<TextView>(R.id.stock)
        val descriptionText = findViewById<TextView>(R.id.detailDescriptionProduct)
        val imageViewProduct = findViewById<ImageView>(R.id.imageProduct)
        val addProduct = findViewById<Button>(R.id.buttonSignUp)
        val layoutSum = findViewById<ConstraintLayout>(R.id.layoutSum)
        val layoutSubstract = findViewById<ConstraintLayout>(R.id.layoutSubstract)

        var seller : seller_representation? = seller_representation("",image,product.leafColor,price,name,description,productNumber,category,stock.toString(),"")
        var sellerTech : technology_representation_seller? = null
        var sellerToy : toy_representation_seller? = null
        var sellerClothes : clothes_representation_seller? = null
        var sellerFood : food_representation_seller? = null
        logic.getSpecificSeller(productNumber, price, stock) { sellerResult ->
            if (sellerResult != null) {
                Log.i("Seller encontrado", "YUPI")
                if (seller != null) {
                    Log.i("seller asignado", "YUPI")
                    seller.cif = sellerResult.cif
                    seller.vendorName = sellerResult.vendorName
                    Log.i("seller cif", seller.cif)
                    Log.i("seller name" , seller.vendorName)

                    when (sellerResult) {
                        is technology_representation_seller -> {
                            sellerTech = sellerResult as technology_representation_seller
                            // Resto del código...
                            Log.i("HA ENTRADO", "MILAGRO")
                        }
                        is toy_representation_seller -> {
                            sellerToy = sellerResult as toy_representation_seller
                            // Resto del código...
                            Log.i("HA ENTRADO", "MILAGRO")

                        }
                        is clothes_representation_seller -> {
                            sellerClothes = sellerResult as clothes_representation_seller
                            // Resto del código...
                            Log.i("HA ENTRADO", "MILAGRO")

                        }
                        is food_representation_seller -> {
                            sellerFood = sellerResult as food_representation_seller
                            // Resto del código...
                            Log.i("HA ENTRADO", "MILAGRO")

                        }
                        else -> {
                            // Manejar el caso en que seller no es una instancia de ninguno de los tipos esperados...
                            Log.i("No ha entrado", "oh no")
                        }
                    }
                }

            } else {
                Log.i("Seller no encontrado", "NO")
            }
        }




        stockText.text = currentStock.toString()
        nameText.text = name
        priceText.text = price
        descriptionText.text = description

        ImageURLtoBitmapConverter.downloadImageProduct(image,imageViewProduct)


        layoutSum.setOnClickListener {

            if(currentStock < stock){
                currentStock++
                stockText.text = currentStock.toString()
            }

        }

        layoutSubstract.setOnClickListener {

            if(currentStock > 1){
                currentStock--
                stockText.text = currentStock.toString()

            }
        }

        backMainScreen.setOnClickListener {
            val i = Intent(this, BuyerMainScreen::class.java)
            startActivity(i)
        }

        addProduct.setOnClickListener {
            if(sellerToy != null){
                val productToy = toy_representation_cart(
                    sellerToy!!.cif,
                    sellerToy!!.name,
                    sellerToy!!.price,
                    sellerToy!!.image,
                    sellerToy!!.stock.toInt(),
                    sellerToy!!.description,
                    product.leafColor,
                    sellerToy!!.productNumber,
                    currentStock,
                    sellerToy!!.vendorName,
                    sellerToy!!.material,
                    sellerToy!!.age
                )
                Log.i("Product Toy", productToy.toString())
                Log.i("Producto añadido", "Patricio mi dios")
                val productExisting = ShoppingCartRequests.getProductInCart(productToy.PN, productToy.cif, productToy.seller, productToy)

                showCustomDialogBoxSuccess("Producto añadido al carrito correctamente")


            }
            else if (sellerClothes != null){
                val productClothes = clothes_representation_cart(
                    sellerClothes!!.cif,
                    sellerClothes!!.name,
                    sellerClothes!!.price,
                    sellerClothes!!.image,
                    sellerClothes!!.stock.toInt(),
                    sellerClothes!!.description,
                    product.leafColor,
                    sellerClothes!!.productNumber,
                    currentStock,
                    sellerClothes!!.vendorName,
                    sellerClothes!!.size,
                    sellerClothes!!.color
                )
                Log.i("Product Clothes", productClothes.toString())
                Log.i("Producto añadido", "Patricio mi dios")
                ShoppingCartRequests.getProductInCart(product.PN, productClothes.cif, productClothes.seller,productClothes )

                showCustomDialogBoxSuccess("Producto añadido al carrito correctamente")





            }

            else if(sellerFood != null){
                val productFood = food_representation_cart(
                    sellerFood!!.cif,
                    sellerFood!!.name,
                    sellerFood!!.price,
                    sellerFood!!.image,
                    sellerFood!!.stock.toInt(),
                    sellerFood!!.description,
                    product.leafColor,
                    sellerFood!!.productNumber,
                    currentStock,
                    sellerFood!!.vendorName,
                    sellerFood!!.calories,
                    sellerFood!!.macros
                )
                Log.i("Product Food", productFood.toString())

                Log.i("Producto añadido", "Patricio mi dios")
                ShoppingCartRequests.getProductInCart(productFood.PN, productFood.cif, productFood.seller, productFood)

                showCustomDialogBoxSuccess("Producto añadido al carrito correctamente")






            }
            else if(sellerTech != null){
                val productTech = technology_representation_cart(
                    sellerTech!!.cif,
                    sellerTech!!.name,
                    sellerTech!!.price,
                    sellerTech!!.image,
                    currentStock,
                    sellerTech!!.description,
                    product.leafColor,
                    sellerTech!!.productNumber,
                    currentStock,
                    sellerTech!!.vendorName,
                    sellerTech!!.brand,
                    sellerTech!!.electricConsumption
                )
                Log.i("Product Tech", productTech.toString())
                Log.i("Producto añadido", "Patricio mi dios")
                ShoppingCartRequests.getProductInCart(productTech.PN, productTech.cif, productTech.seller, productTech)

                showCustomDialogBoxSuccess("Producto añadido al carrito correctamente")




            }
            else{

                Log.i("Locura colectiva", "No se ha podido añadir")

            }






        }

        val addWishList = findViewById<ImageView>(R.id.imageViewAddWish)
        addWishList.setOnClickListener(){
            if(sellerToy != null){
                val productToy = toy_representation_cart(
                    sellerToy!!.cif,
                    sellerToy!!.name,
                    sellerToy!!.price,
                    sellerToy!!.image,
                    sellerToy!!.stock.toInt(),
                    sellerToy!!.description,
                    product.leafColor,
                    sellerToy!!.productNumber,
                    currentStock,
                    sellerToy!!.cif,
                    sellerToy!!.material,
                    sellerToy!!.age
                )
                Log.i("Product Toy", productToy.toString())
                PersonBuyer.addProductToWish(productToy)
                ListWishRequests.addProductToWish(productToy)
                Log.i("Producto añadido", "Patricio mi dios")
                showCustomDialogBoxSuccess("Producto añadido a lista de deseos correctamente")


            }
            else if (sellerClothes != null){
                val productClothes = clothes_representation_cart(
                    sellerClothes!!.cif,
                    sellerClothes!!.name,
                    sellerClothes!!.price,
                    sellerClothes!!.image,
                    sellerClothes!!.stock.toInt(),
                    sellerClothes!!.description,
                    product.leafColor,
                    sellerClothes!!.productNumber,
                    currentStock,
                    sellerClothes!!.cif,
                    sellerClothes!!.size,
                    sellerClothes!!.color
                )
                Log.i("Product Clothes", productClothes.toString())
                PersonBuyer.addProductToWish(productClothes)
                ListWishRequests.addProductToWish(productClothes)
                Log.i("Producto añadido", "Patricio mi dios")
                showCustomDialogBoxSuccess("Producto añadido a lista de deseos correctamente")





            }

            else if(sellerFood != null){
                val productFood = food_representation_cart(
                    sellerFood!!.cif,
                    sellerFood!!.name,
                    sellerFood!!.price,
                    sellerFood!!.image,
                    sellerFood!!.stock.toInt(),
                    sellerFood!!.description,
                    product.leafColor,
                    sellerFood!!.productNumber,
                    currentStock,
                    sellerFood!!.cif,
                    sellerFood!!.calories,
                    sellerFood!!.macros
                )
                Log.i("Product Food", productFood.toString())
                PersonBuyer.addProductToWish(productFood)
                ListWishRequests.addProductToWish(productFood)
                Log.i("Producto añadido", "Patricio mi dios")
                showCustomDialogBoxSuccess("Producto añadido a lista de deseos correctamente")





            }
            else if(sellerTech != null){
                val productTech = technology_representation_cart(
                    sellerTech!!.cif,
                    sellerTech!!.name,
                    sellerTech!!.price,
                    sellerTech!!.image,
                    currentStock,
                    sellerTech!!.description,
                    product.leafColor,
                    sellerTech!!.productNumber,
                    currentStock,
                    sellerTech!!.cif,
                    sellerTech!!.brand,
                    sellerTech!!.electricConsumption
                )
                Log.i("Product Tech", productTech.toString())
               PersonBuyer.addProductToWish(productTech)
                ListWishRequests.addProductToWish(productTech)
                Log.i("Producto añadido", "Patricio mi dios")
                showCustomDialogBoxSuccess("Producto añadido a lista de deseos correctamente")




            }
            else{

                Log.i("Locura colectiva", "No se ha podido añadir")

            }
        }





    }

    fun setSellersShown(list: MutableList<seller_representation>){
        sellerList = list
        adapterS.setSellerListForInstance(adapterS, sellerList)

    }


    fun showCustomDialogBoxSuccess(msgSuccess: String) {
        val dialog = Dialog(this)
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


    companion object{
        fun setImageViewProduct(image: Bitmap?, view:ImageView){
            Log.i("Image View value", "{$view}")
            if (image != null) {
                view.setImageBitmap(image)
            }
        }



    }
}