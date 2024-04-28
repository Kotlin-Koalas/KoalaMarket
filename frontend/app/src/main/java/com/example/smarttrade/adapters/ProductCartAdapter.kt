package com.example.smarttrade.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.material3.MediumTopAppBar
import com.example.smarttrade.R
import com.example.smarttrade.mediador.Mediador
import com.example.smarttrade.volleyRequestClasses.ImageURLtoBitmapConverter
import com.example.smarttrade.models.product_representation_cart


class ProductCartAdapter(
    private val cartProducts: MutableList<product_representation_cart>
) : BaseAdapter() {

    val who = this

    override fun getCount(): Int {
        return cartProducts.count()
    }

    override fun getItem(position: Int): Any {
        return cartProducts.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun removeProduct(position: Int){
        cartProducts.removeAt(position)
        views.clear()
        notifyDataSetChanged()
    }

    fun addProduct(product: product_representation_cart){
        cartProducts.add(product)
        views.clear()
        notifyDataSetChanged()
    }

    fun updateProducts(updateProductList: MutableList<product_representation_cart>) {
        views.clear()
        cartProducts.clear()
        cartProducts.addAll(updateProductList)
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(parent?.context)
            .inflate(R.layout.product_representation_cart, parent, false)

        // Find UI elements in the inflated view
        val textViewPrice = view.findViewById<TextView>(R.id.textViewPrice)
        val textViewName = view.findViewById<TextView>(R.id.textViewTitulo)
        ImageURLtoBitmapConverter.downloadImageCart(cartProducts[position].image,view)

        textViewPrice.text = cartProducts[position].price
        textViewName.text = cartProducts[position].name

        val selectedImageView = view.findViewById<ImageView>(R.id.imageViewSelected)
        selectedImageView.tag = R.drawable.ellipse_5

        val selected = view.findViewById<ImageView>(R.id.imageViewSelected)
        selected.setOnClickListener {
            if (selectedImageView.tag == R.drawable.ellipse_5) {
                val bitmap = layoutToImage(R.layout.cart_selected, view.context)
                selectedImageView.setImageBitmap(bitmap)
                selectedImageView.tag = R.layout.cart_selected
                Mediador.notifyItemSelected(cartProducts[position])
            } else {
                selectedImageView.setImageResource(R.drawable.ellipse_5)
                selectedImageView.tag = R.drawable.ellipse_5
                Mediador.notifyItemUnselected(cartProducts[position])
            }
        }

        var currentStock = cartProducts[position].quantity
        val stock = cartProducts[position].stock

        val addQuantity = view.findViewById<ImageView>(R.id.addStock)
        val stockText = view.findViewById<TextView>(R.id.stock)

        addQuantity.setOnClickListener {
            if(currentStock < stock){
                currentStock++
                stockText.text = currentStock.toString()
                cartProducts[position].quantity = currentStock
            }
            Mediador.notifyItemQuantityIncreased(cartProducts[position],view)
        }

        val substractQuantity = view.findViewById<ImageView>(R.id.substractStock)
        substractQuantity.setOnClickListener {
            if(currentStock > 0){
                currentStock--
                stockText.text = currentStock.toString()
                cartProducts[position].quantity = currentStock
                Mediador.notifyItemQuantityDecreased(cartProducts[position],view)
            } else{
                removeProduct(position)
                Mediador.notifyItemDeleted(cartProducts[position],view)
            }
        }
        views.add(view)
        return view
    }



    companion object{
        val views = mutableListOf<View>()
        fun setImage(image: Bitmap?,view:View){
            val imageView = view.findViewById<ImageView>(R.id.imageViewCat)
            if (image != null) {
                imageView.setImageBitmap(image)
            }
        }
        fun setAllSelected(){
            for (v in views){
                val selectedImageView = v.findViewById<ImageView>(R.id.imageViewSelected)
                val bitmap = layoutToImage(R.layout.cart_selected, v.context)
                selectedImageView.setImageBitmap(bitmap)
                selectedImageView.tag = R.layout.cart_selected
            }
            Mediador.notifyAllItemsSelected()
        }
        fun setAllUnselected(){
            for (v in views){
                val selectedImageView = v.findViewById<ImageView>(R.id.imageViewSelected)
                selectedImageView.setImageResource(R.drawable.ellipse_5)
                selectedImageView.tag = R.drawable.ellipse_5
            }
            Mediador.notifyAllItemsUnselected()
        }
        private fun layoutToImage(layoutId: Int, context: Context): Bitmap {
            val layout = LayoutInflater.from(context).inflate(layoutId, null)
            layout.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
            layout.layout(0, 0, layout.measuredWidth, layout.measuredHeight)
            val bitmap = Bitmap.createBitmap(layout.measuredWidth, layout.measuredHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            layout.draw(canvas)
            return bitmap
        }
    }

}