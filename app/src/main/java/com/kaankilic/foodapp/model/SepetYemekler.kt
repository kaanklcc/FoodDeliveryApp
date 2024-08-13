package com.kaankilic.foodapp.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SepetYemekler(
    var sepet_yemek_id:Int?,
    var yemek_adi:String,
    var yemek_resim_adi:String,
    var yemek_fiyat:Int,
    var yemek_siparis_adet:Int,
    var kullanici_adi:String): Serializable{

    }
