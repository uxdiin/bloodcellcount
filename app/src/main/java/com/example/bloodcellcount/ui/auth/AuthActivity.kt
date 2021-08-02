package com.example.bloodcellcount.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bloodcellcount.R
import com.example.bloodcellcount.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private lateinit var activityAuthBinding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAuthBinding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(activityAuthBinding.root)
    }

}