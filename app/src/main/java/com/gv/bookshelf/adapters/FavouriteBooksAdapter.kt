package com.gv.bookshelf.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gv.bookshelf.R
import com.gv.bookshelf.databinding.ItemBookBinding
import com.gv.bookshelf.listeners.OnItemClickListener
import com.gv.bookshelf.models.BookResponseItem

class FavouriteBooksAdapter(private val context: Context, private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<FavouriteBooksAdapter.FavouriteBooksViewHolder>() {

    inner class FavouriteBooksViewHolder(val binding : ItemBookBinding) : RecyclerView.ViewHolder(binding.root)


    private val diffCallback = object : DiffUtil.ItemCallback<BookResponseItem>(){
        override fun areItemsTheSame(oldItem: BookResponseItem, newItem: BookResponseItem): Boolean {
            return oldItem.isbn == newItem.isbn
        }

        override fun areContentsTheSame(oldItem: BookResponseItem, newItem: BookResponseItem): Boolean {
            return oldItem == newItem
        }

    }


    val differ = AsyncListDiffer(this,diffCallback)

    var favouriteBooksResponse : MutableList<BookResponseItem>

        get()=differ.currentList
        set(value) {differ.submitList(value)}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteBooksAdapter.FavouriteBooksViewHolder {
        return FavouriteBooksViewHolder(
            ItemBookBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: FavouriteBooksAdapter.FavouriteBooksViewHolder, position: Int) {
        val favouriteBook  = favouriteBooksResponse[position]


        holder.binding.apply {

            var category = ""
            var author = ""
            var date = ""
            for (i in favouriteBook.categories){
                category = "$i $category "
            }
            for (i in favouriteBook.authors){
                author = "$i $author "
            }

            tvTitle.text = favouriteBook.title
            tvAuthor.text = author
            tvPageCount.text = favouriteBook.pageCount.toString()
            tvPublishedAt.text = favouriteBook.publishedDate?.`$date`?.subSequence(0,10) ?: "-"
            Glide.with(context).load(favouriteBook.thumbnailUrl).placeholder(R.drawable.bookplaceholder).into(ivBookImage)
        }

        holder.itemView.setOnClickListener {
//            Toast.makeText(context,"${favouriteBook.categories}", Toast.LENGTH_SHORT).show()
            onItemClickListener.onClick(favouriteBook)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }



}