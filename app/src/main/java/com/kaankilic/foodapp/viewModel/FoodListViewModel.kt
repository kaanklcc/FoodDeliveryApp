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

    val yemekAdi = MutableLiveData<String>()
    val yemekFiyat = MutableLiveData<Int>()
    val yemekAdet = MutableLiveData<Int>()
    val kullaniciAdi = MutableLiveData<String>()
    val resimUrl = MutableLiveData<String>()


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
/*val popularMovies = MutableLiveData<List<Result>>()
    val topRatedMovies = MutableLiveData<List<Film>>()

    val movieError = MutableLiveData<Boolean>()
    val movieLoading = MutableLiveData<Boolean>()

    private val movieAPIServis = MovieAPIServis()

    private val ozelSharedPreferences = OzelSharedPreferences(getApplication())
    private val guncellemeZamani= 10*60*1000*1000*1000L

    fun refreshData(){
        val kaydedilmeZamani = ozelSharedPreferences.zamaniAL()
        if (kaydedilmeZamani != null && kaydedilmeZamani != 0L && System.nanoTime() - kaydedilmeZamani < guncellemeZamani){
            verileriRoomdanAl()
        }else{
            verileriIntarnettenAlPopular()
           // verileriIntarnettenAlTopRated()

        }
    }

    fun refreshDataFromIntarnet(){
        verileriIntarnettenAlPopular()

    }

    private fun verileriIntarnettenAlPopular() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = movieAPIServis.getDataPopular("aaaaf180f736635b6d7b6e158b4b8da0")
                if (response.isSuccessful) {
                    response.body()?.let { movieResponse ->
                        val movieList = movieResponse.results.map {
                            Film(
                                adult = it.adult,
                                backdropPath = it.backdropPath,
                                id = it.id,
                                originalLanguage = it.originalLanguage,
                                originalTitle = it.originalTitle,
                                overview = it.overview,
                                popularity = it.popularity,
                                posterPath = it.posterPath,
                                releaseDate = it.releaseDate,
                                title = it.title,
                                video = it.video,
                                voteAverage = it.voteAverage,
                                voteCount = it.voteCount
                            )
                        }

                        withContext(Dispatchers.Main) {
                            roomaKaydet(movieList)
                            movieleriGösterPopular(movieList)
                            Toast.makeText(getApplication(), "Popüler Verileri İntarnetten Aldık", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        movieLoading.value = false
                        movieError.value = true
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    movieLoading.value = false
                    movieError.value = true
                    e.printStackTrace()
                }
            }
        }
    }


   /* private fun verileriIntarnettenAlTopRated() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = movieAPIServis.getDataRated("aaaaf180f736635b6d7b6e158b4b8da0")
                if (response.isSuccessful) {
                    response.body()?.let { movieResponse ->
                        val movieList = movieResponse.results.map {
                            Film(
                                adult = it.adult,
                                backdropPath = it.backdropPath,
                                id = it.id,
                                originalLanguage = it.originalLanguage,
                                originalTitle = it.originalTitle,
                                overview = it.overview,
                                popularity = it.popularity,
                                posterPath = it.posterPath,
                                releaseDate = it.releaseDate,
                                title = it.title,
                                video = it.video,
                                voteAverage = it.voteAverage,
                                voteCount = it.voteCount
                            )
                        }

                        withContext(Dispatchers.Main) {
                            roomaKaydet(movieList)
                            movieleriGösterTopRated(movieList)
                            Toast.makeText(getApplication(), "En Yüksek Puanlı Verileri İntarnetten Aldık", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        movieLoading.value = false
                        movieError.value = true
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    movieLoading.value = false
                    movieError.value = true
                    e.printStackTrace()
                }
            }
        }
    }*/

    fun populerFimler(){
        viewModelScope.launch(Dispatchers.IO) {
           val response = movieAPIServis.getDataPopular("aaaaf180f736635b6d7b6e158b4b8da0")
            withContext(Dispatchers.Main){

            }
        }
    }




    private fun verileriRoomdanAl(){
        viewModelScope.launch(Dispatchers.IO) {
            val movieListesi = MovieDatabase(getApplication()).movieDao().getAllMovie()
            withContext(Dispatchers.Main){
                movieleriGösterPopular(movieListesi)


                Toast.makeText(getApplication(), "Verileri Roomdan Aldık", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun movieleriGösterPopular(movieListesi : List<Film>){
        popularMovies.value = movieListesi

    }

    private fun movieleriGösterTopRated(movieListesi: List<Film>) {
        topRatedMovies.value = movieListesi
    }



    private fun roomaKaydet(movieListesi: List<Film>) {
        viewModelScope.launch {
            val dao = MovieDatabase(getApplication()).movieDao()
            dao.deleteAll()
            val uuidListesi = dao.insertAll(*movieListesi.toTypedArray())
            var i = 0
            while (i < movieListesi.size) {
                movieListesi[i].uuid = uuidListesi[i].toInt()
                i = i + 1
            }
            movieleriGösterPopular(movieListesi)

        }
        ozelSharedPreferences.zamaniKaydet(System.nanoTime())
    }
}*/