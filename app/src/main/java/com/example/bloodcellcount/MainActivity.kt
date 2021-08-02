package com.example.bloodcellcount

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.bloodcellcount.viewmodel.ResultFragmentViewModel
import com.example.bloodcellcount.viewmodel.ResultFragmentViewModelFactory

class MainActivity : AppCompatActivity() {
    var resultFragmentViewModel : ResultFragmentViewModel?=null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ActivityCompat.requestPermissions(
            this@MainActivity, arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ),
            100
        )
        val bloodCellViewModelFactory = ResultFragmentViewModelFactory(application)
        resultFragmentViewModel = ViewModelProvider(this, bloodCellViewModelFactory).get(ResultFragmentViewModel::class.java)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            100 -> if (grantResults.isNotEmpty() || grantResults[0] === PackageManager.PERMISSION_GRANTED || grantResults[1] === PackageManager.PERMISSION_GRANTED
            ) {
                /* User checks permission. */
            } else {
                Toast.makeText(this@MainActivity, "Permission is denied.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}