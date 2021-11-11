package com.example.bloodcellcount.ui.scan

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bloodcellcount.R
import com.example.bloodcellcount.databinding.FragmentScanBinding


class ScanFragment : Fragment() {

    private lateinit var fragmentScanBinding: FragmentScanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentScanBinding = FragmentScanBinding.inflate(inflater)
        return fragmentScanBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentScanBinding.tvScanMenu.setOnClickListener {

        }
        fragmentScanBinding.scanUsingCapture.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(ResultFragment.SCAN_METHOD, ResultFragment.SCAN_METHOD_FROM_CAMERA)
            }
            findNavController().navigate(R.id.action_scanFragment_to_resultFragment,bundle)
        }
        fragmentScanBinding.scanUsingFolder.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(ResultFragment.SCAN_METHOD, ResultFragment.SCAN_METHOD_FROM_STORAGE)
            }
            findNavController().navigate(R.id.action_scanFragment_to_resultFragment,bundle)
        }
    }
}