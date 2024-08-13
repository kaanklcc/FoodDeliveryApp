package com.kaankilic.foodapp.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kaankilic.foodapp.model.SepetYemekler
import com.kaankilic.foodapp.model.Yemekler
import com.kaankilic.foodapp.retrofit.ApiUtils
import com.kaankilic.foodapp.retrofit.MovieAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodCartViewModel(application: Application) : AndroidViewModel(application) {

    private val yemekAPI: MovieAPI = ApiUtils.getKisilerDao()

    val sepetYemek = MutableLiveData<List<SepetYemekler>>()





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
                if (response.success == 1) { // "succes" yerine "success"
                    // Ekleme başarılı, sepeti tekrar getir
                    sepettekiYemekleriGetir(kullanici_adi)
                } else {
                    // Ekleme başarısız, hata mesajını loglayın
                    Log.e("FoodCartViewModel", "Yemek ekleme başarısız: ${response.message}")
                }
            } catch (e: Exception) {
                // Hata durumunda loglayın
                Log.e("FoodCartViewModel", "Yemek ekleme hatası: ${e.message}")
            }
        }
    }

    fun sil(sepet_yemek_id: Int, kullanici_adi: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = yemekAPI.sepettenYemekSil(sepet_yemek_id, kullanici_adi)
                withContext(Dispatchers.Main) {
                    if (response.success == 1) {
                        // Silme başarılı, sepeti tekrar getir
                        sepettekiYemekleriGetir(kullanici_adi)
                    } else {
                        Log.e("FoodCartViewModel", "Yemek silme başarısız: ${response.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e("FoodCartViewModel", "Yemek silme hatası: ${e.message}")
            }
        }
    }

    fun sepettekiYemekleriGetir(kullanici_adi: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = yemekAPI.sepettekiYemekleriGetir(kullanici_adi)
                withContext(Dispatchers.Main) {
                    Log.d("FoodCartViewModel", "API Response: ${response.sepet_yemekler}")
                    if (response.success == 1) {
                        sepetYemek.value = response.sepet_yemekler
                    } else {
                        Log.e("FoodCartViewModel", "Sepet getirme başarısız: ${response.success}")
                    }
                }
            } catch (e: Exception) {
                Log.e("FoodCartViewModel", "Sepet getirme hatası: ${e.message}")
            }
        }
    }

}

