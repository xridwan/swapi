package com.xridwan.swapi.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xridwan.swapi.databinding.FragmentStarShipsBinding
import com.xridwan.swapi.ui.adapter.StarAdapter
import com.xridwan.swapi.viewmodel.StarShipsViewModel

class StarShipsFragment : Fragment() {

    private var _binding: FragmentStarShipsBinding? = null
    private val binding get() = _binding!!

    private lateinit var starAdapter: StarAdapter
    private lateinit var starShipsViewModel: StarShipsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStarShipsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView()
        setupViewModel()
        getViewModel()
    }

    private fun recyclerView() {
        starAdapter = StarAdapter()
        starAdapter.notifyDataSetChanged()

        binding.rvStarShips.layoutManager = LinearLayoutManager(context)
        binding.rvStarShips.adapter = starAdapter
    }

    private fun setupViewModel() {
        starShipsViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(StarShipsViewModel::class.java)
    }

    private fun getViewModel() {
        starShipsViewModel.setStarShips()

        starShipsViewModel.getStarShips().observe(viewLifecycleOwner, { weatherItems ->
            if (weatherItems != null) {
                starAdapter.setData(weatherItems)
                onLoading(false)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun onLoading(state: Boolean) {
        if (state) {
            binding.progressStars.visibility = View.VISIBLE
        } else {
            binding.progressStars.visibility = View.GONE
        }
    }

}