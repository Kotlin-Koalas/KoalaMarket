package com.example.smarttrade.volleyRequestClasses

import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import com.example.smarttrade.AddProduct
import com.example.smarttrade.BuildConfig
import com.example.smarttrade.MainActivity
import com.example.smarttrade.ProductView
import com.example.smarttrade.adapters.ProductAdapter
import com.example.smarttrade.adapters.ProductAdapterSeller
import com.example.smarttrade.adapters.ProductCartAdapter

object  ImageURLtoBitmapConverter {
    val host = BuildConfig.DB_LINK
    fun downloadImage(url: String, view : View): Bitmap? {

        val urlC = "$host:5000/"+url
        val requestQueue = Volley.newRequestQueue(MainActivity.getContext())
        var bitmap: Bitmap? = null

        val imageRequest = ImageRequest(
            urlC,
            { response -> bitmap = response
            Log.i("imageBitmap", bitmap.toString())
            ProductAdapter.setImage(bitmap, view)
            },
            143,
            143,
            ImageView.ScaleType.CENTER_CROP,
            Bitmap.Config.RGB_565,
            { error -> Log.e("ImageLoadError", "Error listener: $error")
                ProductAdapter.setImage(null, view)}
        )

        requestQueue.add(imageRequest)

        return bitmap
    }

    fun downloadImageCart(url: String, view : View): Bitmap? {

        val urlC = "$host:5000/"+url
        val requestQueue = Volley.newRequestQueue(MainActivity.getContext())
        var bitmap: Bitmap? = null

        val imageRequest = ImageRequest(
            urlC,
            { response -> bitmap = response
                Log.i("imageBitmap", bitmap.toString())
                ProductCartAdapter.setImage(bitmap, view)
            },
            143,
            143,
            ImageView.ScaleType.CENTER_CROP,
            Bitmap.Config.RGB_565,
            { error -> Log.e("ImageLoadError", "Error listener: $error")
                ProductCartAdapter.setImage(null, view)}
        )

        requestQueue.add(imageRequest)

        return bitmap
    }

    fun downloadImageProduct(url: String, view : ImageView): Bitmap? {

        val urlC = "$host:5000/"+url
        val requestQueue = Volley.newRequestQueue(MainActivity.getContext())
        var bitmap: Bitmap? = null

        val imageRequest = ImageRequest(
            urlC,
            { response -> bitmap = response
                Log.i("imageBitmap", bitmap.toString())
                ProductView.setImageViewProduct(bitmap, view)
            },
            143,
            143,
            ImageView.ScaleType.CENTER_CROP,
            Bitmap.Config.RGB_565,
            { error -> Log.e("ImageLoadError", "Error listener: $error")
                ProductView.setImageViewProduct(null, view)}
        )

        requestQueue.add(imageRequest)

        return bitmap
    }

    fun downloadImageAddProduct(url: String, view:View): Bitmap? {

        var urlC = "$host:5000/"+url
        val requestQueue = Volley.newRequestQueue(MainActivity.getContext())
        var bitmap: Bitmap? = null

        val imageRequest = ImageRequest(
            urlC,
            { response -> bitmap = response
                Log.i("imageBitmap", bitmap.toString())
                AddProduct.setImage(bitmap, view)
            },
            200,
            200,
            ImageView.ScaleType.CENTER_CROP,
            Bitmap.Config.RGB_565,
            { error -> Log.e("ImageLoadError", "Error listener: $error")
                AddProduct.setImage(null, view)
        }
        )

        requestQueue.add(imageRequest)

        return bitmap
    }

    fun downloadImageProductSeller(url: String, view: View): Bitmap? {
        val urlC = "$host:5000/"+url
        val requestQueue = Volley.newRequestQueue(MainActivity.getContext())
        var bitmap: Bitmap? = null

        val imageRequest = ImageRequest(
            urlC,
            { response -> bitmap = response
                Log.i("imageBitmap", bitmap.toString())
                ProductAdapterSeller.setImage(bitmap,view)
            },

            110,
            110,
            ImageView.ScaleType.CENTER_CROP,
            Bitmap.Config.RGB_565,
            { error -> Log.e("ImageLoadError", "Error listener: $error")
                ProductAdapterSeller.setImage(null, view)
            }
        )
        requestQueue.add(imageRequest)
        return bitmap
    }
}