package com.kaankilic.foodapp.model

import com.google.gson.annotations.SerializedName

data class SepetYemeklerCevap(
    var sepet_yemekler:List<SepetYemekler>,
    var success:Int){

    }


