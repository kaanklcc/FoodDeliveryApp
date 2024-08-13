package com.kaankilic.foodapp.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kaankilic.foodapp.model.CRUDCevap
import com.kaankilic.foodapp.model.SepetYemekler
import com.kaankilic.foodapp.retrofit.ApiUtils
import com.kaankilic.foodapp.retrofit.MovieAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val yemekAPI: MovieAPI = ApiUtils.getKisilerDao()




    fun sepeteYemekEkle(
        yemek_adi: String,
        yemek_resim_adi: String,
        yemek_fiyat: Int,
        yemek_siparis_adet: Int,
        kullanici_adi: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = yemekAPI.sepeteYemekEkle(
                    yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet, kullanici_adi
                )
                withContext(Dispatchers.Main) {
                    if (response.success == 1) {
                        // Ekleme başarılı, sepeti tekrar getir
                       yemekAPI.sepettekiYemekleriGetir(kullanici_adi)
                    } else {
                        Log.e("FoodCartViewModel", "Yemek ekleme başarısız: ${response.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e("FoodCartViewModel", "Yemek ekleme hatası: ${e.message}")
            }
        }
    }

}