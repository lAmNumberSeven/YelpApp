package com.example.hw5_yelp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.row_item.view.*

class RestaurantAdapter (val context: Context, val restaurants: List<YelpRestaurant>) : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_item, null))
    }

    override fun getItemCount(): Int{
        return restaurants.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant  = restaurants[position]
        holder.bind(restaurant)
    }
    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind(restaurant: YelpRestaurant){
            itemView.buisnessName.text = restaurant.name
            itemView.ratingBar.rating = restaurant.rating.toFloat()
            itemView.reviewsR.text = "${restaurant.numReviews} Reviews"
            itemView.addressName.text = restaurant.location.address
            itemView.foodName.text = restaurant.categories[0].title
            itemView.distanceR.text = restaurant.displayDistance()
            itemView.priceR.text = restaurant.price
            Glide.with(context).load(restaurant.imageUrl).apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(20))).into(itemView.foodImage)
        }

    }
}