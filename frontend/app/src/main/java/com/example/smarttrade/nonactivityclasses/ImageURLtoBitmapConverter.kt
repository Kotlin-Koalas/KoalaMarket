package com.example.smarttrade.nonactivityclasses

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import com.example.smarttrade.AddProduct
import com.example.smarttrade.BrowseProducts
import com.example.smarttrade.BuildConfig
import com.example.smarttrade.MainActivity
import com.example.smarttrade.ProductAdapter

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

    fun downloadImageAddProduct(url: String): Bitmap? {
        val requestQueue = Volley.newRequestQueue(MainActivity.getContext())
        var bitmap: Bitmap? = null

        val imageRequest = ImageRequest(
            url,
            { response -> bitmap = response
                Log.i("imageBitmap", bitmap.toString())
                //AddProduct.setImage(bitmap)
            },
            143,
            143,
            ImageView.ScaleType.CENTER_CROP,
            Bitmap.Config.RGB_565,
            { error -> Log.e("ImageLoadError", "Error listener: $error")
                //AddProduct.setImage(null)
        }
        )

        requestQueue.add(imageRequest)

        return bitmap
    }
}