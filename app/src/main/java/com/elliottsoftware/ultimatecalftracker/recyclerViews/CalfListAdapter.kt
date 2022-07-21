package com.elliottsoftware.ultimatecalftracker.recyclerViews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elliottsoftware.ultimatecalftracker.R
import com.elliottsoftware.ultimatecalftracker.models.Calf

class CalfListAdapter:ListAdapter<Calf,CalfViewHolder>(CalfComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalfViewHolder {
        return CalfViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CalfViewHolder, position: Int) {
        // we call bind() to bind all the data. not doing that yet

    }


}

class CalfViewHolder(calfView: View): RecyclerView.ViewHolder(calfView){

    fun bind(){
        //this is where all the method binding will go
    }

    companion object{
        fun create(parent:ViewGroup):CalfViewHolder{
            val view:View = LayoutInflater.from(parent.context)
                .inflate(R.layout.indiv_calf_view,parent,false)
            return CalfViewHolder(view)
        }
    }
}
class CalfComparator : DiffUtil.ItemCallback<Calf>() {
    override fun areItemsTheSame(oldItem: Calf, newItem: Calf): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldCalf: Calf, newCalf: Calf): Boolean {
        return oldCalf == newCalf
    }
}
