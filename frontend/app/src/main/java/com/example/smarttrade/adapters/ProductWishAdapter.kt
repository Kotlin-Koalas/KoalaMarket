package com.example.smarttrade.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
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
import com.example.smarttrade.models.PersonBuyer
import com.example.smarttrade.models.product_representation_cart
import com.example.smarttrade.volleyRequestClasses.ImageURLtoBitmapConverter


class ProductWishAdapter(
    private val context : Context
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

    fun updateProducts() {
        views.clear()
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
            i.putExtra("product", PersonBuyer.getWishList()[position])
            context.startActivity(i)

        }

        val imageViewProd = view.findViewById<ImageView>(R.id.imageViewProd)

        val currentStock = PersonBuyer.getWishList()[position].stock //TODO añadir stock


        imageViewHeart.setOnClickListener{//TODO CHANGE
            ListWishRequests.deleteProductWish(PersonBuyer.getWishList()[position])
            PersonBuyer.removeProductFromWish(position)
            views.clear()
            notifyDataSetChanged()
        }

        imageViewCart.setOnClickListener{//TODO CHANGE

            //ShoppingCartRequests.addProductToCart(PersonBuyer.getWishList()[position])
            //ListWishRequests.deleteProductWish(PersonBuyer.getWishList()[position])
            PersonBuyer.addProductToCart(PersonBuyer.getWishList()[position])
            views.clear()
            notifyDataSetChanged()
            WishList.productAddedCart()
        }

        views.add(view)

        return view
    }




    //TODO CAMBIAR FUNCION VIEW IMAGE
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