package com.xridwan.swapi.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xridwan.swapi.databinding.FragmentPlanetsBinding
import com.xridwan.swapi.ui.adapter.PlanetsAdapter
import com.xridwan.swapi.viewmodel.PlanetsViewModel

class PlanetsFragment : Fragment() {
    private var _binding: FragmentPlanetsBinding? = null
    private val binding get() = _binding!!

    private lateinit var planetsAdapter: PlanetsAdapter
    private lateinit var planetsViewModel: PlanetsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlanetsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView()
        setupViewModel()
        getViewModel()
    }

    private fun getViewModel() {
        planetsViewModel.setPlanets()

        planetsViewModel.getPlanets().observe(viewLifecycleOwner, { weatherItems ->
            if (weatherItems != null) {
                planetsAdapter.setData(weatherItems)
                onLoading(false)
            }
        })
    }

    private fun setupViewModel() {
        planetsViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(PlanetsViewModel::class.java)
    }

    private fun recyclerView() {
        planetsAdapter = PlanetsAdapter()
        planetsAdapter.notifyDataSetChanged()

        binding.rvPlanets.layoutManager = LinearLayoutManager(context)
        binding.rvPlanets.adapter = planetsAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun onLoading(state: Boolean) {
        if (state) {
            binding.progressPlanets.visibility = View.VISIBLE
        } else {
            binding.progressPlanets.visibility = View.GONE
        }
    }
}