package com.xridwan.swapi.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xridwan.swapi.databinding.FragmentPeopleBinding
import com.xridwan.swapi.ui.adapter.PeopleAdapter
import com.xridwan.swapi.viewmodel.PeopleViewModel

class PeopleFragment : Fragment() {
    private var _binding: FragmentPeopleBinding? = null
    private val binding get() = _binding!!

    private lateinit var peopleAdapter: PeopleAdapter
    private lateinit var peopleViewModel: PeopleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPeopleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView()
        setupViewModel()
        getViewModel()
    }

    private fun recyclerView() {
        peopleAdapter = PeopleAdapter()
        peopleAdapter.notifyDataSetChanged()

        binding.rvPeople.layoutManager = LinearLayoutManager(context)
        binding.rvPeople.adapter = peopleAdapter
    }

    private fun setupViewModel() {
        peopleViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(PeopleViewModel::class.java)
    }

    private fun getViewModel() {
        peopleViewModel.setPeople()

        peopleViewModel.getPeople().observe(viewLifecycleOwner, { items ->
            if (items != null) {
                peopleAdapter.setData(items)
                onLoading(false)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun onLoading(state: Boolean) {
        if (state) binding.progressPeople.visibility = View.VISIBLE
        else binding.progressPeople.visibility = View.GONE
    }
}