package com.example.submission1.ui.main.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bloodcellcount.databinding.ItemRowBloodBinding
import com.example.bloodcellcount.models.BloodCell

class BloodListAdapter: RecyclerView.Adapter<BloodListAdapter.MovieItemViewHolder>() {
    private val bloods = ArrayList<BloodCell>()
    inner class MovieItemViewHolder(private val binding: ItemRowBloodBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(bloodCell: BloodCell){
            binding.run {
                tvRowName.text = bloodCell.name
                tvRowBloodcount.text = bloodCell.numOfBloodCell.toString()
                Glide.with(itemView.context).
                    load(
                        bloodCell.photo
                    ).apply {

                }.into(ivRowMovie)
                itemView.setOnClickListener {
                    onBloodClickListener?.let {
                        bloodCell.id?.let { it1 -> it(it1) }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        val bloodBinding = ItemRowBloodBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieItemViewHolder(bloodBinding)
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.bind(bloods[position])
    }

    override fun getItemCount(): Int {
        return bloods.size
    }

    fun setBloodList(bloodCell:ArrayList<BloodCell>){
        this.bloods.addAll(bloodCell)
    }

    var onBloodClickListener: ((String)->Unit)? = null

    fun setOnClickListener(listener: (String)->Unit){
        onBloodClickListener = listener
    }
}