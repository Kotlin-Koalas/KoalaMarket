package com.example.smarttrade.logic

import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.smarttrade.BrowseProductsFiltered
import com.example.smarttrade.BuildConfig
import com.example.smarttrade.MainActivity
import com.example.smarttrade.mainBuyerFragments.HomeFragment
import com.example.smarttrade.models.PersonBuyer
import com.example.smarttrade.models.search_representation
import org.json.JSONArray
import org.json.JSONObject

object Searches {

    private const val myIP = BuildConfig.MY_IP
    private val url = "https://$myIP"

    private val DNI = PersonBuyer.getDNI()

    var isSQueue = false

    lateinit var searchesVolleyQueue: RequestQueue
    fun getSearches() {

        if(!isSQueue) {
            searchesVolleyQueue = Volley.newRequestQueue(MainActivity.getContext())
            isSQueue = true
        }

        val jsonRequest = StringRequest(
            Request.Method.GET, "$url/buyers/$DNI/search",
            { response ->
                val prevSearches = JSONArray(response)
                val res = mutableListOf<search_representation>()
                for (i in 0 until prevSearches.length()) {
                    val search = prevSearches.getString(i)
                    res.add(search_representation(search))
                }
                Log.i("searchesObtained", res.toString())
                HomeFragment.setInitialSearches(res)
            },
            { error ->
                Log.i("errorGettingSearches", error.message.toString())
            }
        )
        searchesVolleyQueue.add(jsonRequest)
    }

    fun getFilteredSearches() {

        if(!isSQueue) {
            searchesVolleyQueue = Volley.newRequestQueue(MainActivity.getContext())
            isSQueue = true
        }

        val jsonRequest = StringRequest(
            Request.Method.GET, "$url/buyers/$DNI/search",
            { response ->
                val prevSearches = JSONArray(response)
                val res = mutableListOf<search_representation>()
                for (i in 0 until prevSearches.length()) {
                    val search = prevSearches.getString(i)
                    res.add(search_representation(search))
                }
                Log.i("searchesObtained", res.toString())
                BrowseProductsFiltered.setInitialSearches(res)
            },
            { error ->
                Log.i("errorGettingSearches", error.message.toString())
            }
        )
        searchesVolleyQueue.add(jsonRequest)
    }

    fun addSearch(search: String) {
        val json = JSONObject()
        json.put("search", search)

        val jsonRequest = JsonObjectRequest(
            Request.Method.POST, "$url/buyers/$DNI/search", json,
            { response ->
            },
            { error ->
                Log.i("errorAddingSearch",error.message.toString())
            }
        )
        searchesVolleyQueue.add(jsonRequest)
    }
}