package com.example.hw5_yelp

import com.google.gson.annotations.SerializedName

data class YelpSearchResult(
    @SerializedName("total") val total : Int,
    @SerializedName("businesses") val restaurants: List<YelpRestaurant>
)
data class YelpRestaurant(
    val name : String,
    val rating : Double,
    val price : String,
    @SerializedName("review_count") val numReviews : Int,
    @SerializedName("distance") val distanceMeters : Double,
    @SerializedName("image_url") val imageUrl : String,
    val categories: List<YelpCategory>,
    val location: YelpLocation
){
    fun displayDistance():String{
        val miles = 0.00621371
        val distanceInMiles = "%.2f".format(distanceMeters* miles)
        return "$distanceInMiles mi"
    }
}
data class YelpCategory(
    val title: String
)

data class YelpLocation(
    @SerializedName("address1") val address: String
)