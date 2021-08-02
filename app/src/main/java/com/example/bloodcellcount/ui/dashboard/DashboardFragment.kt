package com.example.bloodcellcount.ui.dashboard

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.bloodcellcount.R
import com.example.bloodcellcount.databinding.FragmentDashboardBinding
import com.example.bloodcellcount.models.User
import dagger.hilt.android.AndroidEntryPoint
import com.example.bloodcellcount.util.AuthUser
import com.example.bloodcellcount.util.Resource

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private lateinit var dashboardBinding: FragmentDashboardBinding
    private val dashboardViewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dashboardBinding = FragmentDashboardBinding.inflate(inflater,container,false)
        return dashboardBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dashboardBinding.btnImageScanned.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment2_to_bloodListFragment2)
        }

        loadUserDetail()
    }

    private fun loadUserDetail(){
        AuthUser.id?.let { dashboardViewModel.findUser(it) }
//        Log.d("userId",AuthUser.id)
        dashboardViewModel.user.observe(viewLifecycleOwner, { resource ->
            when(resource){
                is Resource.Success -> {
                    val user = resource.data as User
                    dashboardBinding.tvUserName.text = user.username
                    Glide.with(requireContext()).load(user.userPhoto?.path).into(dashboardBinding.profilePicture)
                }
            }
        })
    }


}