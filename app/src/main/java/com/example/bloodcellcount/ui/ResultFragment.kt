package com.example.bloodcellcount.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.bloodcellcount.BuildConfig
import com.example.bloodcellcount.MainActivity
import com.example.bloodcellcount.R
import com.example.bloodcellcount.datasource.BloodCellDataSource
import com.example.bloodcellcount.models.BloodCell
import com.example.bloodcellcount.models.BloodResponse
import com.hbisoft.pickit.PickiT
import com.hbisoft.pickit.PickiTCallbacks
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_result.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ResultFragment : Fragment(R.layout.fragment_result), PickiTCallbacks {
    private var imageData : Intent? = null
    private var imagename : MultipartBody.Part? = null
    private val args : ResultFragmentArgs by navArgs()
    var pickiT: PickiT? = null

    companion object{
        public const val REQUEST_GET_IMAGE = 2
        public const val REQUEST_CAPTURE_IMAGE = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pickiT = PickiT(requireContext(), this, requireActivity())
        (activity as MainActivity).btnBack.visibility = View.VISIBLE
        (activity as MainActivity).btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        if (args.MODE == "OPEN"){
            blood_cell_image_view.setOnClickListener {
                openPhoto()
            }
        }else{
            blood_cell_image_view.setOnClickListener {
                capturePhoto()
            }
        }
        btn_count.setOnClickListener {
            btn_count.startAnimation()
            (activity as MainActivity).bloodCellViewModel!!.count("lol",imagename!!,object : BloodCellDataSource.BloodCellDataCallBack{
                override fun onSuccess(bloodCell: BloodCell) {
//                    Log.d("avatar",bloodResponse.bloodCell!!.avatar!!)
                    bloodCell.let {
                        tv_num_of_blood_cell.setText(it.numOfBloodCell.toString()!!)
                        Glide.with(requireContext()).load(BuildConfig.BASE_MEDIA_URL+it.avatar).into(blood_cell_image_view)
                        Log.d("avatar",BuildConfig.BASE_MEDIA_URL+it.avatar)
                    }
                    btn_count.revertAnimation()
                }

                @SuppressLint("ShowToast")
                override fun onError(errorMessage: String) {
                    Toast.makeText(requireContext(),errorMessage,Toast.LENGTH_SHORT)
                    btn_count.revertAnimation()
                }

            })
        }

    }

    private fun placeHolderGone(){
        image_view_place_holder.visibility = View.GONE
        tv_blood_image_place_holder.visibility = View.GONE
    }
    private fun openPhoto(){
        val intent = Intent()
            .setType("*/*")
            .setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(
            Intent.createChooser(intent, "Select a file"),
            REQUEST_GET_IMAGE)
    }

    private fun capturePhoto(){
        val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent,
            REQUEST_CAPTURE_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_GET_IMAGE && resultCode == Activity.RESULT_OK){
            Glide.with(requireContext()).load(data?.data).into(blood_cell_image_view)
            imageData = data
            placeHolderGone()
            val pickedImg = pickiT!!.getPath(data!!.data!!,Build.VERSION.SDK_INT)
//            Log.d("picked",pickedImg!! )
        }else if(requestCode == REQUEST_CAPTURE_IMAGE && resultCode == Activity.RESULT_OK){
//            var bitmap = data?.data
            Glide.with(requireContext()).load((data?.extras?.get("data")) as Bitmap).into(blood_cell_image_view)
            imageData = data
            placeHolderGone()
        }
    }

    override fun PickiTonUriReturned() {
        TODO("Not yet implemented")
    }

    override fun PickiTonProgressUpdate(progress: Int) {
        TODO("Not yet implemented")
    }

    override fun PickiTonStartListener() {
        TODO("Not yet implemented")
    }

    override fun PickiTonCompleteListener(
        path: String?,
        wasDriveFile: Boolean,
        wasUnknownProvider: Boolean,
        wasSuccessful: Boolean,
        Reason: String?
    ) {
        val requestBody = RequestBody.create(MediaType.parse("multipart"), File(path!!))

        imagename = MultipartBody.Part.createFormData("avatar",File(path).name,requestBody)
    }


}