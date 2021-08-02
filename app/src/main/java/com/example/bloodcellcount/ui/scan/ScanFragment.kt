package com.example.bloodcellcount.ui.scan

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bloodcellcount.R
import kotlinx.android.synthetic.main.fragment_scan.*


class ScanFragment : Fragment(R.layout.fragment_scan) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scan_using_folder.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(ResultFragment.SCAN_METHOD, ResultFragment.SCAN_METHOD_FROM_STORAGE)
            }
            findNavController().navigate(R.id.action_scanFragment_to_resultFragment,bundle)
        }
    }
}