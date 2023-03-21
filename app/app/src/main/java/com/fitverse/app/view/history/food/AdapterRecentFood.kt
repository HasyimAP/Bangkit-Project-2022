package com.fitverse.app.view.history.food

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fitverse.app.databinding.RecentFoodAdapterBinding
import com.fitverse.app.model.FoodModel
import com.fitverse.app.view.food.DetailFoodActivity
import java.util.*

class AdapterRecentFood  : RecyclerView.Adapter<AdapterRecentFood.StoryViewHolder>(){
    private val list = ArrayList<FoodModel>()

    private var onItemClickCallback: OnItemClickCallback? = null
//    private var recentFoodViewModel: RecentFoodViewModel

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(items: ArrayList<FoodModel>){
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    inner class StoryViewHolder(private val binding: RecentFoodAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(items: FoodModel) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(items)
            }
            binding.apply {
                Glide.with(itemView)
                        .load(items.photoUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(ivPhoto)
                tvNameFood.text = items.name

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailFoodActivity::class.java)
                    intent.putExtra("ListStoryModel", items)

                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            androidx.core.util.Pair(ivPhoto, "profile"),
                            androidx.core.util.Pair(tvNameFood, "name"),

                            )
                    itemView.context.startActivity(intent, optionsCompat.toBundle())
                }

                deleteButton.setOnClickListener {

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {

        val view = RecentFoodAdapterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return StoryViewHolder((view))
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(user: FoodModel)
    }
}