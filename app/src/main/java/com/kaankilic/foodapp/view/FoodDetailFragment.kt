package com.kaankilic.foodapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.kaankilic.foodapp.databinding.FragmentFoodDetailBinding
import com.kaankilic.foodapp.viewModel.FoodDetailViewModel


class FoodDetailFragment : Fragment() {
    private var _binding: FragmentFoodDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FoodDetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[FoodDetailViewModel::class.java]


        val bundle: FoodDetailFragmentArgs by navArgs()
        val yemek = bundle.yemek


        binding.fiyatiText.text = "${yemek.yemek_fiyat} TL"
        binding.yemekisimText.text = yemek.yemek_adi
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}"
        Glide.with(this).load(url).override(300, 300).into(binding.yemekview)

       binding.backView.setOnClickListener {
            val action = FoodDetailFragmentDirections.actionFoodDetailFragmentToFoodListFragment2()
            Navigation.findNavController(view).navigate(action)
        }

        binding.arttir.setOnClickListener { buttonArttir(it) }
        binding.azalt.setOnClickListener { buttonAzalt(it) }
        binding.buttonSepeteEkle.setOnClickListener {
            val action = FoodDetailFragmentDirections.actionFoodDetailFragmentToFoodCartFragment2(
                yemek.yemek_adi,
                yemek.yemek_fiyat,
                binding.adetText.text.toString().toInt(),
                "kaan",
                yemek.yemek_resim_adi
            )
            Navigation.findNavController(view).navigate(action)
        }

        updateTotalPrice()
    }

    fun buttonArttir(view: View) {
        val currentAdet = binding.adetText.text.toString().toInt()
        binding.adetText.text = (currentAdet + 1).toString()
        updateTotalPrice()

    }

    fun buttonAzalt(view: View) {
        val currentAdet = binding.adetText.text.toString().toInt()
        if (currentAdet > 0) {
            binding.adetText.text = (currentAdet - 1).toString()
            updateTotalPrice()
        }

    }

    private fun updateTotalPrice() {
        val fiyat = binding.fiyatiText.text.toString().removeSuffix(" TL").toInt()
        val adet = binding.adetText.text.toString().toInt()
        val toplamFiyat = fiyat * adet
        binding.toplamfiyatText.text = "$toplamFiyat TL"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
