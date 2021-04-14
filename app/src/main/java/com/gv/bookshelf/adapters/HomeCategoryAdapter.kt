package com.gv.bookshelf.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gv.bookshelf.R
import com.gv.bookshelf.databinding.HomeCategoryItemBinding
import com.gv.bookshelf.listeners.OnCategoryItemClick

class HomeCategoryAdapter (private val context: Context, private val onItemClickListener: OnCategoryItemClick) : RecyclerView.Adapter<HomeCategoryAdapter.HomeCategoryViewHolder>() {

    inner class HomeCategoryViewHolder(val binding : HomeCategoryItemBinding) : RecyclerView.ViewHolder(binding.root)


    private val diffCallback = object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }


    private val differ = AsyncListDiffer(this,diffCallback)
    var homeCategoryResponse : MutableList<String>

        get()=differ.currentList
        set(value) {differ.submitList(value)}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCategoryAdapter.HomeCategoryViewHolder {
        return HomeCategoryViewHolder(
            HomeCategoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: HomeCategoryAdapter.HomeCategoryViewHolder, position: Int) {
        val bookResponseItem  = homeCategoryResponse[position]


        holder.binding.apply {

            Glide.with(context).load("").placeholder(R.drawable.bookplaceholder).centerCrop().into(ivHomeCategory)

            tvHomeCategory.text = bookResponseItem.toString()
        }
        holder.itemView.setOnClickListener {
//                    Toast.makeText(context,"Clicked",Toast.LENGTH_SHORT).show()
            onItemClickListener.onClick(bookResponseItem)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}