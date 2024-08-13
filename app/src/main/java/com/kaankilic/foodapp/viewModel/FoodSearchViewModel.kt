package com.kaankilic.foodapp.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kaankilic.foodapp.model.Yemekler
import com.kaankilic.foodapp.retrofit.ApiUtils
import com.kaankilic.foodapp.retrofit.MovieAPI
import kotlinx.coroutines.launch

class FoodSearchViewModel(application: Application) : AndroidViewModel(application){

    val foodAll = MutableLiveData<List<Yemekler>>()

    private val yemekAPI: MovieAPI = ApiUtils.getKisilerDao()

    private fun FoodGoster(foodListesi:List<Yemekler>){
        foodAll.value=foodListesi
    }

    fun searchFood(query: String) {
        viewModelScope.launch {
            try {
                val foodList = yemekAPI.tumYemekleriGetir().yemekler
                val filteredList = foodList.filter { food ->
                    food.yemek_adi?.contains(query, ignoreCase = true) ?: false
                }
                FoodGoster(filteredList)
            } catch (e: Exception) {
                Log.e("FoodSearchViewModel", "Arama hatası: ${e.message}")
            }
        }
    }




}