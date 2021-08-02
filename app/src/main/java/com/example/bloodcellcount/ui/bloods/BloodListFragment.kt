package com.example.submission1.ui.main.movie

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bloodcellcount.R
import com.example.bloodcellcount.databinding.FragmentBloodListBinding
import com.example.bloodcellcount.models.BloodCell
import com.example.bloodcellcount.models.BloodPage
import com.example.bloodcellcount.ui.bloods.BloodListViewModel
import com.example.bloodcellcount.ui.genericview.BackButton
import com.example.bloodcellcount.ui.util.WithBackButtonFragment
import com.example.bloodcellcount.util.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BloodListFragment : WithBackButtonFragment(),BackButton {

    private lateinit var bloodListBinding: FragmentBloodListBinding
    private val bloodListViewModel: BloodListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bloodListBinding = FragmentBloodListBinding.inflate(inflater, container, false)
        return bloodListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back()

        val bloodListAdapter = BloodListAdapter()
        bloodListAdapter.setOnClickListener {
            val bundle = Bundle().apply {
                putString("bloodId", it)
            }
            findNavController().navigate(
                R.id.action_bloodListFragment2_to_bloodDetailFragment,
                bundle

            )
        }
        bloodListViewModel.bloodcellList.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Success -> {
//                    hideProgressBar()
                    resource.data?.let { response ->
                        Log.d("result", "result")
                        val bloodPage = response.body() as BloodPage
                        bloodListAdapter.setBloodList(bloodPage.results as ArrayList<BloodCell>)
                        bloodListAdapter.notifyDataSetChanged()

                    }
                }
                is Resource.Error -> {
//                    hideProgressBar()
                    resource.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
//                    showProgressBar()
                }
            }
        })
        bloodListViewModel.index()
        activity?.let {
            bloodListBinding.rvListMovie.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = bloodListAdapter
            }
        }
    }

    override fun back() {
        bloodListBinding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }




}