package com.kaankilic.foodapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kaankilic.foodapp.databinding.SepetReyclerRowBinding
import com.kaankilic.foodapp.model.SepetYemekler
import com.kaankilic.foodapp.model.Yemekler

class SepetAdapter(var mContext : Context, var foodListesi : ArrayList<SepetYemekler>,private val onDeleteClicked: (Int,String)-> Unit) : RecyclerView.Adapter<SepetAdapter.CardTasarimTutucu>() {


    inner class CardTasarimTutucu(var tasarim : SepetReyclerRowBinding) : RecyclerView.ViewHolder(tasarim.root)

  init {

  }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucu {
        val  binding =  SepetReyclerRowBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return CardTasarimTutucu(binding)
    }

    override fun getItemCount(): Int {
        return foodListesi.size
    }

    override fun onBindViewHolder(holder: CardTasarimTutucu, position: Int) {
        val yemek = foodListesi.get(position)
        val t = holder.tasarim
        t.fiyatText.text= yemek.yemek_fiyat.toString()
        t.adetText.text=yemek.yemek_siparis_adet.toString()
        t.yemekAdiText.text=yemek.yemek_adi



        val fiyat = t.fiyatText.text.toString().removeSuffix("TL").toInt()
        val adet = t.adetText.text.toString().toInt()
        val toplamFiyat = fiyat * adet
        t.toplamText.text = "$toplamFiyat TL"

        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}"
        Glide.with(mContext).load(url).override(300,300).into(t.yemekView)

        t.silButton.setOnClickListener {
            val sepetYemek=foodListesi[position]
            onDeleteClicked(sepetYemek.sepet_yemek_id?:0,sepetYemek.kullanici_adi?:"")
        }


    }


}