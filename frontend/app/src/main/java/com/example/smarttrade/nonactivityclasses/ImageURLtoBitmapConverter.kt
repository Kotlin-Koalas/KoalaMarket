package com.example.smarttrade.nonactivityclasses

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import com.example.smarttrade.MainActivity

object  ImageURLtoBitmapConverter {

    val requestQueue = Volley.newRequestQueue(MainActivity.getContext())
    fun downloadImage(url: String): Bitmap? {
        val requestQueue = Volley.newRequestQueue(MainActivity.getContext())
        var bitmap: Bitmap? = null

        val imageRequest = ImageRequest(
            url,
            { response -> bitmap = response },
            143,
            143,
            ImageView.ScaleType.CENTER_CROP,
            Bitmap.Config.RGB_565,
            { error -> Log.e("ImageLoadError", "Error listener: $error") }
        )

        requestQueue.add(imageRequest)

        return bitmap
    }
}