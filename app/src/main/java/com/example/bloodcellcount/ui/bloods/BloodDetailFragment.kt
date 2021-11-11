package com.example.bloodcellcount.ui.bloods

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bloodcellcount.util.Resource
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.bloodcellcount.databinding.FragmentBloodDetailBinding
import com.example.bloodcellcount.dataclass.BloodCell
import com.example.bloodcellcount.ui.genericview.BackButton
import com.example.bloodcellcount.ui.util.WithBackButtonFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BloodDetailFragment : WithBackButtonFragment(),BackButton {

    private lateinit var bloodDetailBinding: FragmentBloodDetailBinding
    private lateinit var photo:Bitmap
    private val bloodDetailViewModel:BloodDetailViewModel by viewModels()
    private var blood:BloodCell = BloodCell()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bloodDetailBinding = FragmentBloodDetailBinding.inflate(layoutInflater,container, false)
        return bloodDetailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareFragment()

    }

    private fun prepareFragment(){
        back()
        arguments?.let {
            it.getString("bloodId")?.let {
                bloodDetailViewModel.find(it)
            }
        }
        bloodDetailViewModel.blood.observe(viewLifecycleOwner, { resource->
            when(resource) {
                is Resource.Success -> {
//                    hideProgressBar()
                    resource.data?.let { blood ->
                        blood as BloodCell
                        bloodDetailBinding.tvNumOfBloodCell.text = blood.numOfBloodCell.toString()
                        bloodDetailBinding.tvImageName.text = blood.name
                        bloodDetailBinding.tvBackbone.text = blood.backbone
                        Glide.with(requireContext()).asBitmap().load(blood.photo).listener(object :RequestListener<Bitmap>{
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Bitmap>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                TODO("Not yet implemented")
                            }

                            override fun onResourceReady(
                                resource: Bitmap?,
                                model: Any?,
                                target: Target<Bitmap>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {resource?.let {
                                    bloodDetailBinding.bloodCellImageView.setImageFromBitmap(resource)
                                    blood.bboxes?.let {
                                        for(bbox in blood.bboxes!!){
                                            bloodDetailBinding.bloodCellImageView.drawRect(bbox.x1 ,bbox.y1 ,bbox.x2 ,bbox.y2,1)
                                            Log.d("bbox", bbox.toString())
                                        }
                                        bloodDetailBinding.bloodCellImageView.commitDrawing()
                                    }
                                }
                                return true
                            }
                        }).into(object : CustomTarget<Bitmap>(){
                            override fun onResourceReady(
                                resource: Bitmap,
                                transition: Transition<in Bitmap>?
                            ) {
                                TODO("Not yet implemented")
                            }

                            override fun onLoadCleared(placeholder: Drawable?) {
                                TODO("Not yet implemented")
                            }

                        })

                    }
                }
                is Resource.Error -> {
//                    hideProgressBar()
                    resource.message?.let { message ->
                        Log.e(ContentValues.TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
//                    showProgressBar()
                }
            }
        })
    }

    override fun back() {
        bloodDetailBinding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}