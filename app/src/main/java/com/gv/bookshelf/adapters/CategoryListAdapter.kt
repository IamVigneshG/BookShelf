package com.gv.bookshelf.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.gv.bookshelf.R
import com.gv.bookshelf.listeners.OnItemCategoryCheckChangedListener
import com.gv.bookshelf.models.CategoryCount
import com.gv.bookshelf.models.Model

class CategoryListAdapter (var cxt: Context, private val resource: Int, val data: List<CategoryCount>, private val onCategoryCheckChangedListener: OnItemCategoryCheckChangedListener, var modelArrayList: ArrayList<Model>) : BaseAdapter()  {


    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return modelArrayList.get(position);
    }

    override fun getItemId(position: Int): Long {
        return 0
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder: ViewHolder
        var rowView:View?
        if(convertView==null){
            val inflater = LayoutInflater.from(cxt)
            rowView = inflater.inflate(resource, null)
            viewHolder= ViewHolder(rowView)
            rowView.tag=viewHolder
        }
        else{
            rowView=convertView
            viewHolder=rowView.tag as ViewHolder
        }

        viewHolder.catTitle.text=data[position].categories
        viewHolder.bookCount.text="Books Count: "+data[position].num




        viewHolder.checkBox.isChecked=modelArrayList[position].selected
        viewHolder.checkBox.setOnClickListener {it as CheckBox
            onCategoryCheckChangedListener.onClick(data[position].categories,it.isChecked)


            if (modelArrayList.get(position).selected){
                modelArrayList.get(position).selected=false
            }
            else {
                modelArrayList.get(position).selected=true
            }
        }



        return rowView!!

    }

}
private class ViewHolder(view: View?) {
    val bookCount = view?.findViewById(R.id.rvCatBookCount) as  TextView
    val catTitle = view?.findViewById(R.id.tvAllCategoryTitle) as TextView
    val checkBox=view?.findViewById(R.id.cbAllCategory) as CheckBox


}