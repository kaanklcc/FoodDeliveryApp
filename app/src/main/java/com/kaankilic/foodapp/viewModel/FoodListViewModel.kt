package com.kaankilic.foodapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kaankilic.foodapp.retrofit.ApiUtils
import com.kaankilic.foodapp.retrofit.MovieAPI
import com.kaankilic.foodapp.model.Yemekler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodListViewModel(application: Application , ) : AndroidViewModel(application) {

    private val yemekAPI: MovieAPI = ApiUtils.getKisilerDao()


    val yemek = MutableLiveData<List<Yemekler>>()

    init {
        yemekleriYukle()
    }

    fun yemekleriYukle(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = yemekAPI.tumYemekleriGetir()
            withContext(Dispatchers.Main){
                yemek.value=response.yemekler


            }


        }
    }



}
