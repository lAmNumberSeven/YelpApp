package com.example.hw5_yelp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.yelp.com/v3/"
private const val API_KEY = "A57g5lTT9XD2vi2J_JXxgzDNL9yA9s30u449j_4IMsiXXXyiNbjauOa0Fm3xGXLwBtEP4jPzWZIh8qX5ocNqalk3cqPKcdzvdhb_sLNC_5Hl8WW-hS9lm0rzOi-LYHYx"
private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val restaurants = mutableListOf<YelpRestaurant>()
        val adapter = RestaurantAdapter(this, restaurants)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        val yelpService = retrofit.create(YelpService::class.java)

        searchButton.setOnClickListener {
            fun View.hideKeyBoard(){
                val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE)
                        as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
            }
            searchButton.hideKeyBoard()
            var food = foodSearch.text.toString()
            var location = locationSearch.text.toString()
            if(food.isNullOrEmpty() || location.isNullOrEmpty()){
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Search term missing")
                builder.setMessage("Search term cannot be empty. Please enter a search term")

                builder.setIcon(android.R.drawable.ic_delete)
                builder.setNeutralButton("OKAY"){ _, _ ->

                }
                val dialog = builder.create()
                dialog.show()
            }else {

                yelpService.searchRestaurants("Bearer $API_KEY", food, location).enqueue(object :
                    Callback<YelpSearchResult> {
                    override fun onResponse(
                        call: Call<YelpSearchResult>,
                        response: Response<YelpSearchResult>
                    ) {
                        Log.i(TAG, "onResponse $response")
                        val body = response.body()
                        if (body == null) {
                            Log.w(TAG, "Did not receive valid response body from Yelp API")
                            return
                        }
                        restaurants.addAll(body.restaurants)
                        adapter.notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                        Log.i(TAG, "onFailure $t")
                    }
                })
            }
        }

    }

}