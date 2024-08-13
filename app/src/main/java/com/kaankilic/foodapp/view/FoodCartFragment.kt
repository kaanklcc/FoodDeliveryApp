package com.kaankilic.foodapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaankilic.foodapp.adapter.SepetAdapter
import com.kaankilic.foodapp.databinding.FragmentFoodCartBinding
import com.kaankilic.foodapp.model.SepetYemekler
import com.kaankilic.foodapp.viewModel.FoodCartViewModel
import com.kaankilic.foodapp.viewModel.FoodListViewModel

class FoodCartFragment : Fragment() {
    private var _binding: FragmentFoodCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FoodCartViewModel
    private lateinit var listeVievModel: FoodListViewModel
    private lateinit var sepetRecyclerAdapter: SepetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodCartBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[FoodCartViewModel::class.java]
        listeVievModel = ViewModelProvider(this)[FoodListViewModel::class.java]


       arguments?.let {
            val sepetYemek = SepetYemekler(
                null,
                FoodCartFragmentArgs.fromBundle(it).yemekAdi,
                FoodCartFragmentArgs.fromBundle(it).resimUrl,
                FoodCartFragmentArgs.fromBundle(it).yemekFiyat,
                FoodCartFragmentArgs.fromBundle(it).yemekAdet,
                FoodCartFragmentArgs.fromBundle(it).kullaniciAdi

            )

            viewModel.sepeteYemekEkle(
                sepetYemek.yemek_adi,
                sepetYemek.yemek_resim_adi,
                sepetYemek.yemek_fiyat,
                sepetYemek.yemek_siparis_adet,
                sepetYemek.kullanici_adi
            )

            viewModel.sepettekiYemekleriGetir(kullanici_adi = sepetYemek.kullanici_adi)


        }






        viewModel.sepettekiYemekleriGetir("kaan")
        sepetRecyclerAdapter = SepetAdapter(requireContext(), arrayListOf()) { sepet_yemek_id, kullanici_adi ->
            sil(sepet_yemek_id, kullanici_adi)
        }
        binding.sepetRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.sepetRecyclerView.adapter = sepetRecyclerAdapter

        observeLiveData()
    }

    fun sil(sepet_yemek_id: Int, kullanici_adi: String) {
        viewModel.sil(sepet_yemek_id, kullanici_adi)
        viewModel.sepettekiYemekleriGetir(kullanici_adi)
    }

    fun observeLiveData() {
        viewModel.sepetYemek.observe(viewLifecycleOwner) { sepetList ->
            Log.d("FoodCartFragment", "Sepet List: $sepetList")
            sepetList?.let {
                sepetRecyclerAdapter.foodListesi = it as ArrayList<SepetYemekler>
                sepetRecyclerAdapter.notifyDataSetChanged()
                binding.sepetRecyclerView.visibility = View.VISIBLE

                // Toplam ücreti hesapla
                val toplamUcret = it.sumOf { yemek ->
                    yemek.yemek_fiyat * yemek.yemek_siparis_adet
                }
                binding.toplamUcret.text = "$toplamUcret TL"
            } ?: run {
                binding.sepetRecyclerView.visibility = View.GONE
                binding.toplamUcret.text = "0 TL"
            }
        }
    }

    override fun onResume() {
        super.onResume()
        listeVievModel.yemekleriYukle()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
