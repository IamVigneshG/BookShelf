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
import com.gv.bookshelf.listeners.OnItemClickListener
import com.gv.bookshelf.models.BookResponseItem

class  AllCategoryAdapter(private val context: Context , private val onItemClick: OnItemClickListener) : RecyclerView.Adapter<AllCategoryAdapter.AllCategoryViewHolder>() {

    inner class AllCategoryViewHolder(val binding : HomeCategoryItemBinding) : RecyclerView.ViewHolder(binding.root)


    private val diffCallback = object : DiffUtil.ItemCallback<BookResponseItem>(){
        override fun areItemsTheSame(oldItem: BookResponseItem, newItem: BookResponseItem): Boolean {
            return oldItem.categories == newItem.categories
        }

        override fun areContentsTheSame(oldItem: BookResponseItem, newItem: BookResponseItem): Boolean {
            return oldItem == newItem
        }

    }


    private val differ = AsyncListDiffer(this,diffCallback)

    var allCategoryResponse : MutableList<BookResponseItem>

    get()=differ.currentList
    set(value) {differ.submitList(value)}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCategoryAdapter.AllCategoryViewHolder {
        return AllCategoryViewHolder(
            HomeCategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
    }

    override fun onBindViewHolder(holder: AllCategoryAdapter.AllCategoryViewHolder, position: Int) {
        val bookitem  = allCategoryResponse[position]


        holder.binding.apply {
            Glide.with(context).load(bookitem.thumbnailUrl).placeholder(R.drawable.bookplaceholder).centerCrop().into(ivHomeCategory)
            tvHomeCategory.text = bookitem.title
        }


        holder.binding.itemBook.setOnClickListener {
            onItemClick.onClick(bookitem)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}