package com.gv.bookshelf.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.gv.bookshelf.R
import com.gv.bookshelf.adapters.HomeCategoryAdapter
import com.gv.bookshelf.databinding.FragmentHomeBinding
import com.gv.bookshelf.listeners.OnCategoryItemClick
import com.gv.bookshelf.main.MainViewModel
import kotlinx.coroutines.flow.collect

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding : FragmentHomeBinding

    private lateinit var viewModel: MainViewModel

    lateinit var homeCategoryAdapter : HomeCategoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        viewModel = activity?.run {
            ViewModelProviders.of(this)[MainViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)



        binding.fabHomeFragment.setOnClickListener{
            findNavController().navigate(
                R.id.action_homeFragment_to_categoryListFragment
            )
        }

        viewModel.getBooks()


        viewModel.visibleCategoryList()

        viewModel.getCategoryCount()

        setupCategoryRecyclerView()


        lifecycleScope.launchWhenCreated {
            viewModel.insert.collect { event ->
                when(event){
                    is MainViewModel.InsertEvent.Success -> {
                        binding.pbHomeFragment.isVisible = false
                        var result = event.result
                        if(result.size==0){
                            binding.noCategory.isVisible=true
                        }else{
                            binding.noCategory.isVisible=false

                        }
                        homeCategoryAdapter.homeCategoryResponse = event.result.toMutableList()

                        //homeCategoryAdapter.differ.submitList(event.result)

                    }
                    is MainViewModel.InsertEvent.Failure->{

                    }
                    is MainViewModel.InsertEvent.Loading->{
                        binding.pbHomeFragment.isVisible = true

                    }
                    else -> Unit
                }
            }
        }


    }




    private fun setupCategoryRecyclerView() = binding.rvHomeFragment.apply{

        homeCategoryAdapter = HomeCategoryAdapter(context , object : OnCategoryItemClick {
            override fun onClick(categoryName: String) {


                val bundle = Bundle().apply {
                    putString("categoryName",categoryName)
                }
                findNavController().navigate(
                    R.id.action_homeFragment_to_bookListFragment,
                    bundle
                )
            }

        })
        adapter = homeCategoryAdapter
        homeCategoryAdapter.notifyDataSetChanged()
        layoutManager = GridLayoutManager(context,2)

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menusearchview, menu)
        var searchViewItem= menu.findItem(R.id.menu_search)
        var searchView=searchViewItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(activity,query, Toast.LENGTH_LONG).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Toast.makeText(activity,newText, Toast.LENGTH_LONG).show()
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
////        when(item.itemId){
////            R.id.menu_search ->{
////              Toast.makeText(this.activity,"Searching...",Toast.LENGTH_LONG).show()
////
////            }
////            else -> true
////        }
////        return super.onOptionsItemSelected(item)
//
//    }




}