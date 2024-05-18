package com.example.smarttrade.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.smarttrade.ProductView
import com.example.smarttrade.R
import com.example.smarttrade.WishList
import com.example.smarttrade.logic.ListWishRequests
import com.example.smarttrade.logic.ShoppingCartRequests
import com.example.smarttrade.models.PersonBuyer
import com.example.smarttrade.models.product_representation
import com.example.smarttrade.models.product_representation_cart
import com.example.smarttrade.volleyRequestClasses.ImageURLtoBitmapConverter


class ProductWishAdapter(
    private val context : Context,

) : BaseAdapter() {

    override fun getCount(): Int {
        return PersonBuyer.getWishList().count()


    }

    override fun getItem(position: Int): Any {
        return PersonBuyer.getWishList().get(position)

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    fun removeProduct(position: Int){
        PersonBuyer.removeProductFromWish(position)
        views.clear()
        notifyDataSetChanged()
    }

    fun addProduct(product: product_representation_cart){
        PersonBuyer.addProductToWish(product)
        views.clear()
        notifyDataSetChanged()
    }

    fun addAllProducts(productList: MutableList<product_representation_cart>) {

    }

    fun updateProducts(){
        //views.clear()
        notifyDataSetChanged()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(parent?.context)
            .inflate(R.layout.wish_list_product, parent, false)

        who = this

        val textViewName = view.findViewById<TextView>(R.id.textViewNameWish)
        val textViewPrice = view.findViewById<TextView>(R.id.textViewPriceWish)
        val imageViewLeaf = view.findViewById<ImageView>(R.id.imageViewLeafWish)
        val imageViewHeart = view.findViewById<ImageView>(R.id.imageViewHeartWish)
        val imageViewCart = view.findViewById<ImageView>(R.id.imageViewCartWish)

        ImageURLtoBitmapConverter.downloadImageWishList(PersonBuyer.getWishList()[position].image,view)
        textViewPrice.text = PersonBuyer.getWishList()[position].price
        textViewName.text = PersonBuyer.getWishList()[position].name
        when(PersonBuyer.getWishList()[position].leafColor){
            "green" -> imageViewLeaf.setImageResource(R.drawable.hoja_verde)
            "yellow" -> imageViewLeaf.setImageResource(R.drawable.hoja_amarilla)
            "red" -> imageViewLeaf.setImageResource(R.drawable.hoja_roja)
        }

        val productRep = view.findViewById<ConstraintLayout>(R.id.layoutWish)
        productRep.setOnClickListener{
            val i = Intent(context, ProductView::class.java)
            val ProducCart =PersonBuyer.getWishList()[position]
            val product = product_representation(
                type = ProducCart.category,
                name = ProducCart.name,
                price = ProducCart.price,
                image = ProducCart.image,
                stock = ProducCart.stock,
                description = ProducCart.description,
                leafColor = ProducCart.leafColor,
                PN = ProducCart.PN
            )
            i.putExtra("product",product)
            context.startActivity(i)

        }
        val imageViewProd = view.findViewById<ImageView>(R.id.imageViewProd)

        val currentStock = PersonBuyer.getWishList()[position].stock


        imageViewHeart.setOnClickListener{

            ListWishRequests.deleteProductWish(PersonBuyer.getWishList()[position])
            removeProduct(position)

        }

        imageViewCart.setOnClickListener{
        Log.i("QUE SALe", PersonBuyer.getWishList()[position].toString())

            var productWithOneStock = PersonBuyer.getWishList()[position]

            productWithOneStock.quantity = 1

            ShoppingCartRequests.addProductToCart(productWithOneStock)
            //PersonBuyer.addProductToCart(PersonBuyer.getWishList()[position])
            ListWishRequests.deleteProductWish(PersonBuyer.getWishList()[position])
            removeProduct(position)
            WishList.productAddedCart()







        }

        views.add(view)

        return view
    }





    companion object{
        val views = mutableListOf<View>()
        lateinit var who: ProductWishAdapter

        fun setImage(image: Bitmap?,view:View){
            val imageView = view.findViewById<ImageView>(R.id.imageViewProd)
            if (image != null) {
                imageView.setImageBitmap(image)
            }
        }


        fun updateProducts(){
            who.updateProducts()
        }





    }

}