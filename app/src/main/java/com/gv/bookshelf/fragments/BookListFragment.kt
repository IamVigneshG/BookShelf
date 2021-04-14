package com.gv.bookshelf.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.gv.bookshelf.R
import com.gv.bookshelf.adapters.AllCategoryAdapter
import com.gv.bookshelf.databinding.FragmentBookListBinding
import com.gv.bookshelf.listeners.OnItemClickListener
import com.gv.bookshelf.main.MainViewModel
import com.gv.bookshelf.models.BookResponseItem
import kotlinx.coroutines.flow.collect

class  BookListFragment : Fragment(R.layout.fragment_book_list) {

    private lateinit var binding: FragmentBookListBinding

    private lateinit var viewModel: MainViewModel

    private lateinit var allCategoryAdapter: AllCategoryAdapter

    private val args: BookListFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel = activity?.run {
            ViewModelProviders.of(this)[MainViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBookListBinding.bind(view)
        var categoryName = args.categoryName
        viewModel.getBookByCategory(categoryName)
        setupCategoryRecyclerView()
        lifecycleScope.launchWhenCreated {
            viewModel.booklisting.collect { event ->
                when (event) {
                    is MainViewModel.BookListingEvent.Success -> {
                        binding.paginationAllBookList.isVisible = false
                        var result = event.result
                        allCategoryAdapter.allCategoryResponse = event.result.toMutableList()
                    }
                    is MainViewModel.BookListingEvent.Failure -> {

                    }
                    is MainViewModel.BookListingEvent.Loading -> {
                        binding.paginationAllBookList.isVisible = true

                    }
                    else -> Unit
                }
            }
        }
    }


    private fun setupCategoryRecyclerView() = binding.rvAllBookList.apply {

        allCategoryAdapter = AllCategoryAdapter(context, object : OnItemClickListener {
            override fun onClick(bookResponseItem: BookResponseItem) {


                val bundle = Bundle().apply {
                    putSerializable("bookresponseitem", bookResponseItem)
                }
                findNavController().navigate(
                    R.id.action_bookListFragment_to_bookFragment,
                    bundle
                )
            }
        })
        adapter = allCategoryAdapter
        allCategoryAdapter.notifyDataSetChanged()
        layoutManager = GridLayoutManager(context, 2)

    }
}
