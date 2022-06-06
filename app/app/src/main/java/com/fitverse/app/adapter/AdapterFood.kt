package com.fitverse.app.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fitverse.app.databinding.ItemListBinding
import com.fitverse.app.model.ListFoodModel
import java.util.*

class AdapterFood  : RecyclerView.Adapter<AdapterFood.StoryViewHolder>()  {
    private val list = ArrayList<ListFoodModel>()

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(items: List<ListFoodModel>){
        list.clear()
        list.addAll(items)
    }

    inner class StoryViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(items: ListFoodModel) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(items)
            }
            binding.apply {
                Glide.with(itemView)
                        .load(items.photoUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(ivPhoto)
                tvNameFood.text = items.nama
                tvDesc.text = items.description
//
//                itemView.setOnClickListener {
//                    val intent = Intent(itemView.context, DetailStoryActivity::class.java)
//                    intent.putExtra("ListStoryModel", items)

//                    val optionsCompat: ActivityOptionsCompat =
//                        ActivityOptionsCompat.makeSceneTransitionAnimation(
//                            itemView.context as Activity,
//                            Pair(ivPhoto, "profile"),
//                            Pair(tvName, "name"),
//
//                        )
//                    itemView.context.startActivity(intent, optionsCompat.toBundle())
//                }
//
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = ItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return StoryViewHolder((view))
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(user: ListFoodModel)
    }
}