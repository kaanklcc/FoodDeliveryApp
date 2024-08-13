package com.kaankilic.foodapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kaankilic.foodapp.databinding.RecyclerRowBinding
import com.kaankilic.foodapp.model.SepetYemekler
import com.kaankilic.foodapp.model.Yemekler
import com.kaankilic.foodapp.view.FoodDetailFragmentDirections
import com.kaankilic.foodapp.view.FoodListFragmentDirections

class FoodAdapter (var mContext : Context, var foodListesi : ArrayList<Yemekler>) : RecyclerView.Adapter<FoodAdapter.CardTasarimTutucu>(){

    inner class CardTasarimTutucu(var tasarim : RecyclerRowBinding) : RecyclerView.ViewHolder(tasarim.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucu {
       val  binding =  RecyclerRowBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return CardTasarimTutucu(binding)

    }

    override fun getItemCount(): Int {
       return foodListesi.size
    }

    override fun onBindViewHolder(holder: CardTasarimTutucu, position: Int) {
        val food = foodListesi.get(position)
        val t = holder.tasarim
        t.urunText.text = food.yemek_adi
        t.fiyatText.text= "${food.yemek_fiyat} TL"


        t.sepeteEkleButton.setOnClickListener {
            val gecis = FoodListFragmentDirections.actionFoodListFragment2ToFoodCartFragment2(food.yemek_adi,food.yemek_fiyat,1,"kaan",food.yemek_resim_adi)
            Navigation.findNavController(it).navigate(gecis)
        }


        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}"
        Glide.with(mContext).load(url).override(300,300).into(t.resimView)

        t.cardView.setOnClickListener {
            val gecis = FoodListFragmentDirections.actionFoodListFragment2ToFoodDetailFragment(yemek = food)
            Navigation.findNavController(it).navigate(gecis)
        }


    }


}