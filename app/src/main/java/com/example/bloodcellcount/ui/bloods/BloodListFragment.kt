package com.example.bloodcellcount.ui.bloods

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bloodcellcount.R
import com.example.bloodcellcount.databinding.FragmentBloodListBinding
import com.example.bloodcellcount.dataclass.BloodCell
import com.example.bloodcellcount.dataclass.BloodPage
import com.example.bloodcellcount.repository.BloodCellRepository
import com.example.bloodcellcount.ui.genericview.BackButton
import com.example.bloodcellcount.ui.util.WithBackButtonFragment
import com.example.bloodcellcount.util.Resource
import com.example.submission1.ui.main.movie.BloodListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_blood_list.*


@AndroidEntryPoint
class BloodListFragment : WithBackButtonFragment(),BackButton {

    private lateinit var bloodListBinding: FragmentBloodListBinding
    private val bloodListViewModel: BloodListViewModel by viewModels()
    private val bloodListParameters = HashMap<String, String>()
    private var ordering: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        bloodListViewModel.bloodcellList.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Success -> {
//                    hideProgressBar()
                    resource.data?.let { response ->
                        response as BloodPage
                        bloodListAdapter.emptyBloodList()
                        bloodListAdapter.setBloodList(response.results as ArrayList<BloodCell>)
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
        setBloodListParameters(initiate = true)
        bloodListViewModel.index(bloodListParameters)
        activity?.let {
            bloodListBinding.rvListMovie.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = bloodListAdapter
            }
        }
        edt_search.setOnEditorActionListener { v, actionId, event ->
            if (actionId ==EditorInfo.IME_ACTION_DONE){
                setBloodListParameters()
                bloodListViewModel.index(bloodListParameters )
            }
            false
        }
        bloodListBinding.edtPageNumber.setText("1")
        bloodListBinding.edtPageNumber.setOnEditorActionListener{ v, actionId, event ->
            if (actionId ==EditorInfo.IME_ACTION_DONE){
                setBloodListParameters()
                bloodListViewModel.index(bloodListParameters)
            }
            false
        }

        bloodListBinding.spinnerOrdering.setItems("Descending","Ascending")
        bloodListBinding.spinnerOrdering.setOnItemSelectedListener{ view, position, id, item ->
            item as String
            when(item){
                ("Descending") -> {
                    ordering = BloodCellRepository.ORDERING_ID_DESCENDING
                    Log.d("ordering", ordering)
                }
                ("Ascending") -> {
                    ordering = BloodCellRepository.ORDERING_ID_ASCENDING
                    Log.d("ordering", ordering)
                }
            }
            setBloodListParameters()
            bloodListViewModel.index(bloodListParameters)
        }
    }

    override fun back() {
        bloodListBinding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setBloodListParameters(initiate: Boolean = false){
        if(initiate){
            bloodListParameters["search"] = ""
            bloodListParameters["limit"] = "10"
            bloodListParameters["offset"] = "0"
            bloodListParameters["ordering"] = BloodCellRepository.ORDERING_ID_DESCENDING
        }else{
            bloodListParameters["search"] = bloodListBinding.edtSearch.text.toString().trim()
            bloodListParameters["limit"] = "10"
            bloodListParameters["offset"] = ((10*bloodListBinding.edtPageNumber.text.toString().toInt())-10).toString()
            bloodListParameters["ordering"] = ordering
        }

    }




}