package com.kaankilic.foodapp.retrofit

class ApiUtils {
    companion object {
        private const val BASE_URL = "http://kasimadalan.pe.hu/"

        fun getKisilerDao(): MovieAPI {
            return RetrofitClient.getClient(BASE_URL).create(MovieAPI::class.java)
        }
    }
}
