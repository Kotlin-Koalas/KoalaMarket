package com.example.smarttrade

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView
import com.example.smarttrade.adapters.SellerAdapter
import com.example.smarttrade.logic.ListWishRequests
import com.example.smarttrade.logic.ShoppingCartRequests
import com.example.smarttrade.logic.logic
import com.example.smarttrade.models.PersonBuyer
import com.example.smarttrade.models.clothes_representation_cart
import com.example.smarttrade.models.clothes_representation_seller
import com.example.smarttrade.models.food_representation_cart
import com.example.smarttrade.models.food_representation_seller
import com.example.smarttrade.models.product_representation
import com.example.smarttrade.models.product_representation_cart
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
    lateinit var layoutHeart : ConstraintLayout
    var like = false
    var rated = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        productContext = this

        val migasCatProd = findViewById<TextView>(R.id.migasPanPorDefecto)

        val migasProd = findViewById<TextView>(R.id.migasPan)

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

        migasProd.text = name

        val dni = PersonBuyer.getDNI()
        adapterS.setLeafColor(product.leafColor)







        if (wishL.isNotEmpty()) {
            Log.i("Primer", wishL.get(0).PN)
        } else {
            println("La lista de deseos está vacía")
        }


        val backMainScreen = findViewById<ConstraintLayout>(R.id.backArrow)
        var rate : Double = 0.0
        val nameText = findViewById<TextView>(R.id.nameProduct)
        val priceText = findViewById<TextView>(R.id.price)
        val stockText = findViewById<TextView>(R.id.stock)
        val descriptionText = findViewById<TextView>(R.id.detailDescriptionProduct)
        val imageViewProduct = findViewById<ImageView>(R.id.imageProduct)
        val addProduct = findViewById<Button>(R.id.buttonSignUp)
        val layoutSum = findViewById<ConstraintLayout>(R.id.layoutSum)
        val layoutSubstract = findViewById<ConstraintLayout>(R.id.layoutSubstract)

        val star1 = findViewById<LottieAnimationView>(R.id.star1)
        val star2 = findViewById<LottieAnimationView>(R.id.star2)
        val star3 = findViewById<LottieAnimationView>(R.id.star3)
        val star4 = findViewById<LottieAnimationView>(R.id.star4)
        val star5 = findViewById<LottieAnimationView>(R.id.star5)
        val rateText = findViewById<TextView>(R.id.rateText)

        logic.getProduct(productNumber) { rateResult ->
            if (rateResult != null) {
                rate = rateResult
                rateText.text = roundToTwoDecimals(rate).toString() +"/5"
            }
        }

        star1.setOnClickListener{
            rateAnimation(star1,R.raw.rate_animation, true)
            rateAnimation(star2,R.raw.rate_animation, false)
            rateAnimation(star3,R.raw.rate_animation, false)
            rateAnimation(star4,R.raw.rate_animation, false)
            rateAnimation(star5,R.raw.rate_animation, false)
            if(rated){
                logic.editSatifaction(productNumber, 1.0, dni)
                rated = true
            }else{
                logic.addSatisfaction(productNumber, 1.0, dni)
                rated = true
            }
            logic.getProduct(productNumber) { updatedRate ->
                if (updatedRate != null) {
                    rate = updatedRate
                    rateText.text = roundToTwoDecimals(rate) +"/5"
                }
            }

        }

        star2.setOnClickListener {
            rateAnimation(star1,R.raw.rate_animation, true)
            rateAnimation(star2,R.raw.rate_animation, true)
            rateAnimation(star3,R.raw.rate_animation, false)
            rateAnimation(star4,R.raw.rate_animation, false)
            rateAnimation(star5,R.raw.rate_animation, false)
            if(rated){
                logic.editSatifaction(productNumber, 2.0, dni)
            }else{
                logic.addSatisfaction(productNumber, 2.0, dni)
                rated = true
            }
            logic.getProduct(productNumber) { updatedRate ->
                if (updatedRate != null) {
                    rate = updatedRate
                    rateText.text = roundToTwoDecimals(rate) +"/5"
                }
            }
        }

        star3.setOnClickListener {
            rateAnimation(star1,R.raw.rate_animation, true)
            rateAnimation(star2,R.raw.rate_animation, true)
            rateAnimation(star3,R.raw.rate_animation, true)
            rateAnimation(star4,R.raw.rate_animation, false)
            rateAnimation(star5,R.raw.rate_animation, false)
            if(rated){
                logic.editSatifaction(productNumber, 3.0, dni)
            }else{
                logic.addSatisfaction(productNumber, 3.0, dni)
                rated = true

            }
            logic.getProduct(productNumber) { updatedRate ->
                if (updatedRate != null) {
                    rate = updatedRate
                    rateText.text = roundToTwoDecimals(rate) +"/5"
                }
            }
        }

        star4.setOnClickListener {
            rateAnimation(star1,R.raw.rate_animation, true)
            rateAnimation(star2,R.raw.rate_animation, true)
            rateAnimation(star3,R.raw.rate_animation, true)
            rateAnimation(star4,R.raw.rate_animation, true)
            rateAnimation(star5,R.raw.rate_animation, false)
            if(rated){
                logic.editSatifaction(productNumber, 4.0, dni)
            }else{
                logic.addSatisfaction(productNumber, 4.0, dni)
                rated = true

            }
            logic.getProduct(productNumber) { updatedRate ->
                if (updatedRate != null) {
                    rate = updatedRate
                    rateText.text = roundToTwoDecimals(rate) +"/5"
                }
            }
        }

        star5.setOnClickListener {
            rateAnimation(star1,R.raw.rate_animation, true)
            rateAnimation(star2,R.raw.rate_animation, true)
            rateAnimation(star3,R.raw.rate_animation, true)
            rateAnimation(star4,R.raw.rate_animation, true)
            rateAnimation(star5,R.raw.rate_animation, true)
            if(rated){
                logic.editSatifaction(productNumber, 5.0, dni)
            }else{
                logic.addSatisfaction(productNumber, 5.0, dni)
                rated = true

            }
            logic.getProduct(productNumber) { updatedRate ->
                if (updatedRate != null) {
                    rate = updatedRate
                    rateText.text = roundToTwoDecimals(rate) +"/5"
                }
            }
        }


        var seller : seller_representation? = seller_representation("",image,product.leafColor,price,name,description,productNumber,category,stock.toString(),"")
        var sellerTech : technology_representation_seller? = null
        var sellerToy : toy_representation_seller? = null
        var sellerClothes : clothes_representation_seller? = null
        var sellerFood : food_representation_seller? = null
        logic.getSpecificSeller(productNumber, price, stock) { sellerResult ->
            if (sellerResult != null) {
                if (seller != null) {
                    seller.cif = sellerResult.cif
                    seller.vendorName = sellerResult.vendorName
                    Log.i("seller cif", seller.cif)
                    Log.i("seller name" , seller.vendorName)

                    when (sellerResult) {
                        is technology_representation_seller -> {
                            sellerTech = sellerResult as technology_representation_seller
                        }
                        is toy_representation_seller -> {
                            sellerToy = sellerResult as toy_representation_seller

                        }
                        is clothes_representation_seller -> {
                            sellerClothes = sellerResult as clothes_representation_seller

                        }
                        is food_representation_seller -> {
                            sellerFood = sellerResult as food_representation_seller

                        }
                        else -> {
                        }
                    }
                }

            } else {
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
                Log.i("Producto añadido", "")
                val productExisting = ShoppingCartRequests.getProductInCart(productToy.PN, productToy.cif, productToy.seller, productToy)

               // showCustomDialogBoxSuccess("Producto añadido al carrito correctamente")


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

                // showCustomDialogBoxSuccess("Producto añadido al carrito correctamente")





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

                ShoppingCartRequests.getProductInCart(productFood.PN, productFood.cif, productFood.seller, productFood)

              //  showCustomDialogBoxSuccess("Producto añadido al carrito correctamente")






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
                ShoppingCartRequests.getProductInCart(productTech.PN, productTech.cif, productTech.seller, productTech)

               // showCustomDialogBoxSuccess("Producto añadido al carrito correctamente")




            }
            else{


            }






        }

        val addWishList = findViewById<ImageView>(R.id.imageViewAddWish)
        logic.getAllSellers(productNumber)

        ListWishRequests.isOnWishList(productNumber) { isOnWishList ->
            if (isOnWishList) {
                addWishList.setBackgroundResource(R.drawable.heart_icon)
                addWishList.isClickable = false
            }
            else {
                addWishList.setBackgroundResource(R.drawable.heart)
            }
        }


        addWishList.setOnClickListener{

           like =  likeSelected(addWishList,like)
                if(like){
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
                        showCustomDialogBoxSuccess("Producto añadido a lista de deseos correctamente")




                    }

                }
        }





    }

    fun setSellersShown(list: MutableList<seller_representation>){
        sellerList = list
        adapterS.setSellerListForInstance(adapterS, sellerList)

    }

    fun likeSelected(view: View, like: Boolean): Boolean{
        if(like){
            view.setBackgroundResource(R.drawable.heart)
        }
        else{
            view.setBackgroundResource(R.drawable.heart_icon)
        }
        return !like
    }

    fun isOnWishList(PN : String) : Boolean{
        for (productInWishList in wishL){
            if (productInWishList.PN == PN){
                return true
            }
        }
        return false
    }

    fun roundToTwoDecimals(number: Double): String {
        return String.format("%.2f", number)

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

    fun rateAnimation(imageView: LottieAnimationView, animation : Int, like: Boolean) : Boolean{
        if(like){
            imageView.setAnimation(animation)
            imageView.playAnimation()
        }
        else{
            imageView.setImageResource(R.drawable.star_24dp_fill0_wght400_grad0_opsz24)
        }

        return !like
    }



    fun scaleView(view: View, startScale: Float, endScale: Float) {
        val anim = ScaleAnimation(
            1f, 1f, // Start and end values for the X axis scaling
            startScale, endScale, // Start and end values for the Y axis scaling
            Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
            Animation.RELATIVE_TO_SELF, 0.5f // Pivot point of Y scaling
        )
        anim.fillAfter = true // Needed to keep the result of the animation
        anim.duration = 500
        view.startAnimation(anim)
    }


    companion object{

        val wishL = mutableListOf<product_representation_cart>()

        private lateinit var productContext: Context
        fun setImageViewProduct(image: Bitmap?, view:ImageView){
            Log.i("Image View value", "{$view}")
            if (image != null) {
                view.setImageBitmap(image)
            }
        }

        fun getContext(): Context {
            return productContext
        }




        fun getWishList(list : MutableList<product_representation_cart>) {
            wishL.clear()
            wishL.addAll(list)


        }



    }
}