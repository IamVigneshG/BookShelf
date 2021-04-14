package com.gv.bookshelf.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.gv.bookshelf.R
import com.gv.bookshelf.databinding.FragmentBookBinding
import com.gv.bookshelf.main.MainViewModel

class BookFragment: Fragment(R.layout.fragment_book) {

    private lateinit var binding : FragmentBookBinding

    private lateinit var viewModel: MainViewModel

    private val args : BookFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel = activity?.run {
            ViewModelProviders.of(this)[MainViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBookBinding.bind(view)


        val book = args.bookresponseitem

        if (book.favourite){
            binding.ivFavBookFragment.setImageResource(R.drawable.star)
        }else{
            binding.ivFavBookFragment.setImageResource(R.drawable.staroutline)
        }

        var author = ""
        for (i in book.authors){
            author = "$i $author "
        }


        Glide.with(requireContext()).load(book.thumbnailUrl).placeholder(R.drawable.bookplaceholder).into(binding.ivFavImage)
        binding.tvTitleBooks.text = book.title
        binding.tvAuthorBooks.text = author
        binding.tvPageCount.text = book.pageCount.toString()
        binding.tvPublishDate.text = book.publishedDate?.`$date`?.subSequence(0,10)
        binding.tvCategory.text = book.categories
        if (book.pageCount==0){
            binding.longDescription.text = "No Long Description Available"
        }else{
            binding.longDescription.text = book.longDescription
        }


        binding.ivFavBookFragment.setOnClickListener{
            book.favourite=!book.favourite
            if(book.favourite==true) {
                binding.ivFavBookFragment.setImageResource(R.drawable.star)
            }
            else{
                binding.ivFavBookFragment.setImageResource(R.drawable.staroutline)
            }
            viewModel.saveFavouriteBooks(book,book.favourite)
        }

    }
}