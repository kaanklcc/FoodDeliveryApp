package com.kaankilic.foodapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kaankilic.foodapp.R
import com.kaankilic.foodapp.adapter.FoodAdapter
import com.kaankilic.foodapp.databinding.FragmentFoodListBinding
import com.kaankilic.foodapp.model.Yemekler
import com.kaankilic.foodapp.viewModel.FoodListViewModel


class FoodListFragment : Fragment() {
    private var _binding: FragmentFoodListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FoodListViewModel
    private lateinit var popup : PopupMenu
    private lateinit var foodRecyclerAdapter: FoodAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this)[FoodListViewModel::class.java]
        viewModel.yemekleriYukle()





        foodRecyclerAdapter = FoodAdapter(requireContext(), arrayListOf())
        binding.foodRecyclerView.layoutManager =  StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        binding.foodRecyclerView.adapter=foodRecyclerAdapter

        binding.floatingActionButton.setOnClickListener { floatingButtonTiklandi(it) }
         popup = PopupMenu(requireContext(),binding.floatingActionButton)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.my_popup_menu,popup.menu)
        //popup.setOnMenuItemClickListener(this)


        observeLiveData()
    }


    fun floatingButtonTiklandi(view: View){

        popup.show()
    }


    private fun observeLiveData(){
        viewModel.yemek.observe(viewLifecycleOwner){
            foodRecyclerAdapter.foodListesi= it as ArrayList<Yemekler>
            foodRecyclerAdapter.notifyDataSetChanged()
            binding.foodRecyclerView.visibility=View.VISIBLE

        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onResume() {
        super.onResume()
        viewModel.yemekleriYukle()
    }



}