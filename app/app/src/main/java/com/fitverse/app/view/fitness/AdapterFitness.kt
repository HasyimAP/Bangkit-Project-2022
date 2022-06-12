package com.fitverse.app.view.fitness

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fitverse.app.databinding.FitnessListBinding
import com.fitverse.app.model.FitnessModel
import java.util.ArrayList

class AdapterFitness  : RecyclerView.Adapter<AdapterFitness.FitnessViewHolder>()  {
    private val list = ArrayList<FitnessModel>()

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(items: ArrayList<FitnessModel>){
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    inner class FitnessViewHolder(private val binding: FitnessListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(items: FitnessModel) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(items)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(items.photoUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivPhoto)
                tvNameFitness.text = items.name

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailFitnessActivity::class.java)
                    intent.putExtra("ListFitnessModel", items)

                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            androidx.core.util.Pair(ivPhoto, "profile"),
                            androidx.core.util.Pair(tvNameFitness, "name"),

                            )
                    itemView.context.startActivity(intent, optionsCompat.toBundle())
                }
//
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FitnessViewHolder {
        val view = FitnessListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FitnessViewHolder((view))
    }

    override fun onBindViewHolder(holder: FitnessViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(user: FitnessModel)
    }
}