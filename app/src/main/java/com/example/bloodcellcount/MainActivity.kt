package com.example.bloodcellcount

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.bloodcellcount.databinding.ActivityMainBinding
import com.example.bloodcellcount.ui.scan.ResultFragmentViewModel
import com.example.bloodcellcount.ui.scan.ResultFragmentViewModelFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    var resultFragmentViewModel : ResultFragmentViewModel?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
//        if (savedInstanceState == null) {
//            supportFragmentManager.commit {
//                setReorderingAllowed(true)
//                add<DashboardFragment>(R.id.main_navhost_fragment)
//            }
//        }
        ActivityCompat.requestPermissions(
            this@MainActivity, arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            100
        )
        val bloodCellViewModelFactory = ResultFragmentViewModelFactory(application)
        resultFragmentViewModel = ViewModelProvider(this, bloodCellViewModelFactory).get(
            ResultFragmentViewModel::class.java)
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