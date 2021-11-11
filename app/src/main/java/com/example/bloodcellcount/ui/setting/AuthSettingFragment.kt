package com.example.bloodcellcount.ui.setting

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bloodcellcount.R
import com.example.bloodcellcount.databinding.FragmentAuthSettingBinding
import com.example.bloodcellcount.dataclass.ErrorResponse
import com.example.bloodcellcount.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_auth_setting.*

@AndroidEntryPoint
class AuthSettingFragment : Fragment() {
    private lateinit var fragmentAuthSettingBinding: FragmentAuthSettingBinding
    private val authSettingViewModel: AuthSettingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentAuthSettingBinding= FragmentAuthSettingBinding.inflate(inflater)
        return fragmentAuthSettingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentAuthSettingBinding.edtNewPassword.setText(authSettingViewModel.getPassword())
        fragmentAuthSettingBinding.btnLogout.setOnClickListener{
            authSettingViewModel.logout()
            findNavController().navigate(R.id.action_authSettingFragment_to_authActivity)
            (activity as SettingActivity).finish()
        }
        fragmentAuthSettingBinding.btnChangePassword.setOnClickListener {
            if(fragmentAuthSettingBinding.btnChangePassword.text.equals(getString(R.string.save))){
                val newPassword = fragmentAuthSettingBinding.edtNewPassword.text.toString().trim()
                if (newPassword.isNotEmpty()){
                    authSettingViewModel.changePassword(newPassword)
                }else{
                    Toast.makeText(requireActivity(), "Password is mandatory",Toast.LENGTH_SHORT).show()
                }
            }else{
                fragmentAuthSettingBinding.edtNewPassword.isEnabled = true
                fragmentAuthSettingBinding.btnChangePassword.text = getString(R.string.save)
                fragmentAuthSettingBinding.btnChangePasswordCancel.visibility = View.VISIBLE

            }
        }

        fragmentAuthSettingBinding.btnChangePasswordCancel.setOnClickListener {
            fragmentAuthSettingBinding.edtNewPassword.isEnabled = false
            fragmentAuthSettingBinding.btnChangePassword.text = getString(R.string.change_password)
            fragmentAuthSettingBinding.edtNewPassword.setText(authSettingViewModel.getPassword())
            fragmentAuthSettingBinding.btnChangePasswordCancel.visibility = View.INVISIBLE
        }

        authSettingViewModel.changePasswordResponse.observe(viewLifecycleOwner,{ resource ->
            when(resource){
                is Resource.Success -> {
                    fragmentAuthSettingBinding.edtNewPassword.isEnabled = false
                    fragmentAuthSettingBinding.btnChangePassword.text = getString(R.string.change_password)
                    fragmentAuthSettingBinding.btnChangePasswordCancel.visibility = View.INVISIBLE
                    val newPassword = fragmentAuthSettingBinding.edtNewPassword.text.toString().trim()
                    authSettingViewModel.savePassword(newPassword)
                    Toast.makeText(requireActivity(), "Password changed", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error-> {
                    resource.data?.let { it as ErrorResponse
                        Toast.makeText(requireActivity(), it.non_field_errors, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}