package com.fitverse.app.view.fitness.fitnessScan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fitverse.app.databinding.FitnessResultAdapterBinding
import com.fitverse.app.model.FitnessModel
import java.util.ArrayList

class AdapterScanFitnessResult  : RecyclerView.Adapter<AdapterScanFitnessResult.StoryViewHolder>()  {
    private val list = ArrayList<FitnessModel>()

    fun setList(items: ArrayList<FitnessModel>){
        list.clear()
        list.add(items[0])
        notifyDataSetChanged()
    }

    inner class StoryViewHolder(private val binding: FitnessResultAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(items: FitnessModel) {
            binding.apply {
                Glide.with(itemView)
                    .load(items.photoUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(photoFitness)
                resultname.text = items.name
                description.text = items.description.replace("\\n","\n")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = FitnessResultAdapterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return StoryViewHolder((view))
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}