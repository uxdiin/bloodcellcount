package com.example.bloodcellcount.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bloodcellcount.BuildConfig
import com.example.bloodcellcount.MainActivity
import com.example.bloodcellcount.R
import com.example.bloodcellcount.datasource.BloodCellDataSource
import com.example.bloodcellcount.models.BloodResponse
import com.example.bloodcellcount.repository.BloodCellRepository
import com.hbisoft.pickit.PickiT
import com.hbisoft.pickit.PickiTCallbacks
import kotlinx.android.synthetic.main.fragment_result_new.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ResultFragment : Fragment(R.layout.fragment_result_new), PickiTCallbacks {
    private lateinit var imageData : Intent
    private lateinit var photo : MultipartBody.Part
    private lateinit var pickiT: PickiT
    private lateinit var selectedBackbone: String
    private lateinit var photoPath: String

    private val args: ResultFragmentArgs by navArgs()

    companion object{
        const val REQUEST_GET_IMAGE = 2
        const val SCAN_METHOD_FROM_CAMERA = 101
        const val SCAN_METHOD_FROM_STORAGE = 102

        const val SCAN_METHOD = "scanMode";
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (args.scanMode == SCAN_METHOD_FROM_STORAGE) {
            openPhotoFromStorage()
            Log.d("open from","storage")
        }
        else if (args.scanMode == SCAN_METHOD_FROM_CAMERA) {
            openPhotoFromCamera()
            Log.d("open from","camera")
        }
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pickiT = PickiT(requireContext(), this, requireActivity())
        blood_cell_image_view.clipToOutline = true

        (activity as MainActivity).btnBack.visibility = View.VISIBLE

        spinner_backbone.setItems(
            BloodCellRepository.BACKBONE_RESNET50,
            BloodCellRepository.BACKBONE_RESNET101,
            BloodCellRepository.BACKBONE_XCEPTION,
            BloodCellRepository.BACKBONE_INCEPTION_RESNETV2)

        spinner_backbone.setOnItemSelectedListener { view, position, id, item ->
            selectedBackbone = item as String
        }

        selectedBackbone = BloodCellRepository.BACKBONE_RESNET50

        blood_cell_image_view.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_scanFragment)
        }

        btn_back.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_scanFragment)
        }

        btn_count.setOnClickListener {
            btn_count.startAnimation()
            (activity as MainActivity).resultFragmentViewModel!!.count(
                "lol",
                photo,
                selectedBackbone,
                object : BloodCellDataSource.BloodCellDataCallBack {
                    override fun onSuccess(bloodResponse: BloodResponse) {
                        drawImage(photoPath)
                        bloodResponse.let {
                            tv_num_of_blood_cell.text = it.bloodCell?.numOfBloodCell.toString()
                            Log.d("photo", BuildConfig.BASE_MEDIA_URL + it)
                            for(bbox in it.bboxes){
                                blood_cell_image_view.drawRect(bbox.x1 ,bbox.y1 ,bbox.x2 ,bbox.y2,1)
                                Log.d("bbox", bbox.toString())
                            }
                            blood_cell_image_view.commitDrawing()
                        }
                        btn_count.revertAnimation()
                    }

                    @SuppressLint("ShowToast")
                    override fun onError(errorMessage: String) {
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                        btn_count.revertAnimation()
                    }

                })
        }

    }

    private fun placeHolderGone(){
        image_view_place_holder.visibility = View.GONE
        tv_blood_image_place_holder.visibility = View.GONE
    }
    private fun openPhotoFromStorage(){
        val intent = Intent()
            .setType("*/*")
            .setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(
            Intent.createChooser(intent, "Select a file"),
            REQUEST_GET_IMAGE
        )
    }

    private fun openPhotoFromCamera(){

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_GET_IMAGE && resultCode == Activity.RESULT_OK){
            data?.let {
                imageData = data
                placeHolderGone()
                pickiT.getPath(data.data, Build.VERSION.SDK_INT)
            }
        }
    }

    private fun drawImage(path: String){
        blood_cell_image_view.setImage(path)
        blood_cell_image_view.commitDrawing()
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
        path?.let {
            val requestBody = RequestBody.create(MediaType.parse("multipart"), File(path!!))
            image_name.text = File(path).name

            photoPath = path;
            drawImage(photoPath)
            photo = MultipartBody.Part.createFormData("photo", File(path).name, requestBody)
        }
    }

}
//                            Glide.with(requireContext())
//                                .load(BuildConfig.BASE_MEDIA_URL + it.avatar).into(
//                                    blood_cell_image_view
//                                )

//        blood_cell_image_view.setImage("/storage/emulated/0/Download/BloodImage_00000.jpg")
//        blood_cell_image_view.commitDrawing()
//        val requestBody = RequestBody.create(MediaType.parse("multipart"), File("val requestBody = RequestBody.create(MediaType.parse(\"multipart\"), File(path!!))"))
//        imagename = MultipartBody.Part.createFormData("avatar", File("/storage/emulated/0/Download/BloodImage_00000.jpg").name, requestBody)
//Log.d("scan_mode",args.scanMode.toString())