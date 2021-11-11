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
import com.example.bloodcellcount.dataclass.BackboneStatistic
import com.example.bloodcellcount.dataclass.BackboneStatisticPage
import com.example.bloodcellcount.dataclass.User
import com.example.bloodcellcount.dataclass.numOfScan
import dagger.hilt.android.AndroidEntryPoint
import com.example.bloodcellcount.util.AuthUser
import com.example.bloodcellcount.util.Resource
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarDataSet

import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter


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

        dashboardBinding.profilePicture.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment2_to_settingActivity)
        }

        loadUserDetail()
        loadDashboardChart()
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

    private fun loadDashboardChart(){
        dashboardViewModel.populateNumOfScanChart()
        dashboardViewModel.populateBackBoneStatisticChart()
        dashboardViewModel.numOfScan.observe(viewLifecycleOwner,{ resource ->
            when(resource){
                is Resource.Success -> {
                    resource.data?.run { this as numOfScan
                        dashboardBinding.tvNumOfScannedImage.text = this.numOfImageScanned.toString()
                    }
                }
            }
        })
        dashboardViewModel.backboneStatistic.observe(viewLifecycleOwner, { resource ->
            when(resource){
                is Resource.Success -> {
                    resource.data?.run { this as BackboneStatisticPage
                        val entries: MutableList<BarEntry> = ArrayList()
                        this.results?.let {
                            val labels = arrayListOf<String>()
                            it.forEachIndexed{index, element ->
                                entries.add(BarEntry(index.toFloat(), element.num_of_scan!!.toFloat()))
                                labels.add(element.backbone.toString())
//                                Log.d("labels",index.toString())
                            }
//                            labels.add("lol")
//                            entries.add(BarEntry(0f,3f))
                            val xAxis = dashboardBinding.chBackboneStatistic.xAxis
                            xAxis.position = (XAxis.XAxisPosition.BOTTOM)
                            xAxis.valueFormatter = IndexAxisValueFormatter(labels)
                            xAxis.labelCount = it.size

                            val set = BarDataSet(entries, "Jumlah Scan")
                            val data = BarData(set)
                            data.barWidth = 0.9f // set custom bar width

                            dashboardBinding.chBackboneStatistic.data = data
                            dashboardBinding.chBackboneStatistic.setFitBars(true)

                            dashboardBinding.chBackboneStatistic.invalidate()
                        }


                    }
                }
            }
        })
    }


}