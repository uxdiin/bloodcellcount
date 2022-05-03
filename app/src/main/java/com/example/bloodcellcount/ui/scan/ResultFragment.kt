package com.example.bloodcellcount.ui.scan

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bloodcellcount.BuildConfig
import com.example.bloodcellcount.MainActivity
import com.example.bloodcellcount.databinding.FragmentResultBinding
import com.example.bloodcellcount.datasource.BloodCellDataSource
import com.example.bloodcellcount.dataclass.BloodCountResponse
import com.example.bloodcellcount.repository.BloodCellRepository
import com.example.bloodcellcount.ui.util.WithBackButtonFragment
import com.hbisoft.pickit.PickiT
import com.hbisoft.pickit.PickiTCallbacks
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ResultFragment : WithBackButtonFragment(), PickiTCallbacks {
    private lateinit var fragmentResultBinding: FragmentResultBinding

    private lateinit var imageData : Intent
    private lateinit var photo : MultipartBody.Part
    private lateinit var pickiT: PickiT
    private lateinit var selectedBackbone: String
    private lateinit var photoPath: String

    private val args: ResultFragmentArgs by navArgs()

    companion object{
        const val REQUEST_GET_IMAGE = 2
        const val REQUEST_GET_IMAGE_FROM_CAMERA = 3
        const val SCAN_METHOD_FROM_CAMERA = 101
        const val SCAN_METHOD_FROM_STORAGE = 102

        const val SCAN_METHOD = "scanMode";
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentResultBinding = FragmentResultBinding.inflate(inflater,container,false)
        return fragmentResultBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (args.scanMode == SCAN_METHOD_FROM_STORAGE) {
            openPhotoFromStorage()
            Log.d("open from","storage")
        }
        else if (args.scanMode == SCAN_METHOD_FROM_CAMERA) {
            openPhotoFromCamera()
            Log.d("open from","camera")
        }
        pickiT = PickiT(requireContext(), this, requireActivity())
        fragmentResultBinding.bloodCellImageView.clipToOutline = true

        fragmentResultBinding.spinnerListBackbone.setItems(
            BloodCellRepository.BACKBONE_RESNET50,
            BloodCellRepository.BACKBONE_RESNET101,
            BloodCellRepository.BACKBONE_XCEPTION,
            BloodCellRepository.BACKBONE_INCEPTION_RESNETV2)

        fragmentResultBinding.spinnerListBackbone.setOnItemSelectedListener { view, position, id, item ->
            selectedBackbone = item as String
        }

        selectedBackbone = BloodCellRepository.BACKBONE_RESNET50

        fragmentResultBinding.bloodCellImageView.setOnClickListener {
            findNavController().popBackStack()
        }

        fragmentResultBinding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        fragmentResultBinding.btnCount.setOnClickListener {
            fragmentResultBinding.btnCount.startAnimation()
            (activity as MainActivity).resultFragmentViewModel!!.count(
                fragmentResultBinding.tvImageName.text.toString(),
                photo,
                selectedBackbone,
                object : BloodCellDataSource.BloodCellDataCallBack {
                    override fun onSuccess(bloodCountResponse: BloodCountResponse) {
                        drawImage(photoPath)
                        bloodCountResponse.let {
                            fragmentResultBinding.tvNumOfBloodCell.text = it.bloodCell?.numOfBloodCell.toString()
                            Log.d("photo", BuildConfig.BASE_MEDIA_URL + it)
                            for(bbox in it.bboxes){
                                fragmentResultBinding.bloodCellImageView.drawRect(bbox.x1 ,bbox.y1 ,bbox.x2 ,bbox.y2,1)
                                Log.d("bbox", bbox.toString())
                            }
                            fragmentResultBinding.bloodCellImageView.commitDrawing()
                        }
                        fragmentResultBinding.btnCount.revertAnimation()
                    }

                    @SuppressLint("ShowToast")
                    override fun onError(errorMessage: String) {
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                        fragmentResultBinding.btnCount.revertAnimation()
                    }

                })
        }

    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            photoPath = absolutePath
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }

                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "com.example.android.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_GET_IMAGE_FROM_CAMERA)
                }
            }
        }
    }

    private fun placeHolderGone(){
        fragmentResultBinding.imageViewPlaceHolder.visibility = View.GONE
        fragmentResultBinding.tvBloodImagePlaceHolder.visibility = View.GONE
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
        dispatchTakePictureIntent()
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
        if(requestCode == REQUEST_GET_IMAGE_FROM_CAMERA && resultCode == Activity.RESULT_OK){
            data?.let {
//                imageData = data.
                placeHolderGone()
//                pickiT.getPath(data.data, Build.VERSION.SDK_INT)
//                dispatchTakePictureIntent()
                drawImage(photoPath)
                photo = createMultiPartPhoto(photoPath)
            }
        }
    }

    private fun drawImage(path: String){
        fragmentResultBinding.bloodCellImageView.setImageFromLocalPath(path)
        fragmentResultBinding.bloodCellImageView.commitDrawing()
    }

    private fun createMultiPartPhoto(photoPath: String): MultipartBody.Part {
        val requestBody = RequestBody.create(MediaType.parse("multipart"), File(photoPath))
        return MultipartBody.Part.createFormData("photo", File(photoPath).name, requestBody)
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
            fragmentResultBinding.tvImageName.text = File(path).name

            photoPath = path;
            drawImage(photoPath)
            photo = createMultiPartPhoto(photoPath)
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