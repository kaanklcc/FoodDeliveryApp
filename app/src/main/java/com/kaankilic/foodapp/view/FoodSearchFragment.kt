package com.kaankilic.foodapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kaankilic.foodapp.adapter.SourceAdapter
import com.kaankilic.foodapp.databinding.FragmentFoodListBinding
import com.kaankilic.foodapp.databinding.FragmentFoodSearchBinding
import com.kaankilic.foodapp.viewModel.FoodSearchViewModel


class FoodSearchFragment : Fragment() {
    private var _binding: FragmentFoodSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : FoodSearchViewModel
    private lateinit var sourceRecyclerAdapter : SourceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(this).get(FoodSearchViewModel::class.java)

        sourceRecyclerAdapter = SourceAdapter(requireContext(), arrayListOf())
        binding.sourceRecyclerView.layoutManager =  StaggeredGridLayoutManager(2,
            StaggeredGridLayoutManager.VERTICAL)
        binding.sourceRecyclerView.adapter = sourceRecyclerAdapter




        binding.searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                newText?.let { viewModel.searchFood(it) }
                return true
            }
        })
        observeLiveData()

    }

    private fun observeLiveData() {
        viewModel.foodAll.observe(viewLifecycleOwner) { foodList ->
            sourceRecyclerAdapter.updateFoodList(foodList)
            binding.sourceRecyclerView.visibility = if (foodList.isEmpty()) View.GONE else View.VISIBLE
        }
    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}