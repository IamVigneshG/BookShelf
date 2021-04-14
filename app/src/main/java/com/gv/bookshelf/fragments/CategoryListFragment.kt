package com.gv.bookshelf.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ListView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gv.bookshelf.R
import com.gv.bookshelf.adapters.CategoryListAdapter
import com.gv.bookshelf.databinding.FragmentCategoryListBinding
import com.gv.bookshelf.listeners.OnItemCategoryCheckChangedListener
import com.gv.bookshelf.main.MainViewModel
import com.gv.bookshelf.models.CategoryCount
import com.gv.bookshelf.models.Model
import kotlinx.coroutines.flow.collect

class CategoryListFragment : Fragment()  {

    private lateinit var binding : FragmentCategoryListBinding

    private lateinit var viewModel: MainViewModel

    lateinit var result:List<CategoryCount>

    lateinit var listAdapter : CategoryListAdapter

    var modelist:ArrayList<Model> =ArrayList()

    val TAG = "CategoryListFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        viewModel = activity?.run {
            ViewModelProviders.of(this)[MainViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_category_list, container, false)

        val activity = activity as Context

        var listView: ListView = view.findViewById(R.id.lvCategoryList)

        binding= FragmentCategoryListBinding.bind(view)




        lifecycleScope.launchWhenCreated {
            viewModel.categoryCount.collect { event ->
                when(event){
                    is MainViewModel.CategoryCountEvent.Success -> {
                        binding.progressBarAllCategoryList.isVisible = false
                        result = event.result

                        for (i in result){
                            var model = Model()
                            model.selected=i.categoryState
                            Log.d("InsideList","$i")
                            modelist.add(model)
                        }


                    }
                    is MainViewModel.CategoryCountEvent.Failure->{
                        Log.d("InsideList","faiure")
                    }
                    is MainViewModel.CategoryCountEvent.Loading->{
                        binding.progressBarAllCategoryList.isVisible = true
                    }
                    else -> Unit
                }
            }
        }


        for (i in result){
            Log.d("listAdapter","$i")
        }


        listAdapter = CategoryListAdapter(activity as Context, R.layout.item_category, result, object :
            OnItemCategoryCheckChangedListener {
            override fun onClick(categoryName: String, state: Boolean) {
                viewModel.updateCategoryState(categoryName,state)
            }
        },modelist)
        listView.adapter = listAdapter
        listAdapter.notifyDataSetChanged()

        return view
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_done ->{
                viewModel.visibleCategoryList()
                findNavController().popBackStack()
//                findNavController().navigate(
//
//                        R.id.action_categoryListFragment_to_homeFragment
//                )
            }
            else -> true
        }
        return super.onOptionsItemSelected(item)

    }



}
