package com.kaankilic.foodapp.model

import com.google.gson.annotations.SerializedName

data class YemeklerCevap(
    @SerializedName("yemekler")
    var yemekler : List<Yemekler>,
    @SerializedName("success")
    var succes : Int
){

}
