package com.fitverse.app.view.food.foodScan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fitverse.app.databinding.FoodResultAdapterBinding
import com.fitverse.app.model.FoodModel
import java.util.ArrayList

class AdapterScanFoodResult  : RecyclerView.Adapter<AdapterScanFoodResult.StoryViewHolder>()  {
    private val list = ArrayList<FoodModel>()

    fun setList(items: ArrayList<FoodModel>){
        list.clear()
        list.add(items[0])
        notifyDataSetChanged()
    }

    inner class StoryViewHolder(private val binding: FoodResultAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(items: FoodModel) {
            binding.apply {
                Glide.with(itemView)
                    .load(items.photoUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(photoFood)
                resultname.text = items.name
                description.text = items.description.replace("\\n","\n")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = FoodResultAdapterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return StoryViewHolder((view))
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}