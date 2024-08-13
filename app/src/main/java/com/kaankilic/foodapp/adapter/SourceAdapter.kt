package com.kaankilic.foodapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kaankilic.foodapp.databinding.RecyclerRowBinding
import com.kaankilic.foodapp.model.Yemekler
import com.kaankilic.foodapp.view.FoodSearchFragmentDirections

class SourceAdapter(var mContext: Context, private val foodList: ArrayList<Yemekler>) : RecyclerView.Adapter<SourceAdapter.SourceViewHolder>() {

    class SourceViewHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SourceViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {
        val food = foodList[position]
        with(holder.binding) {
            urunText.text = food.yemek_adi ?: ""
            fiyatText.text = "${food.yemek_fiyat} TL" ?: ""
            val url = "http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}"
            Glide.with(mContext).load(url).override(300, 300).into(resimView)
            cardView.setOnClickListener {
                val action = FoodSearchFragmentDirections.actionFoodSearchFragment2ToFoodDetailFragment(yemek = food)
                Navigation.findNavController(it).navigate(action)
            }
        }

    }

    fun updateFoodList(newFoodList: List<Yemekler>) {
        foodList.clear()
        foodList.addAll(newFoodList)
        notifyDataSetChanged()
    }
}


