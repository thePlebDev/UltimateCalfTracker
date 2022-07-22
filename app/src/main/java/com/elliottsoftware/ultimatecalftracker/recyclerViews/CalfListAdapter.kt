package com.elliottsoftware.ultimatecalftracker.recyclerViews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elliottsoftware.ultimatecalftracker.R
import com.elliottsoftware.ultimatecalftracker.models.Calf
import java.text.SimpleDateFormat

class CalfListAdapter(listener:OnCalfListener):ListAdapter<Calf,CalfViewHolder>(CalfComparator()) {
        val calfListener:OnCalfListener = listener
    //methods on the Adapter get
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalfViewHolder {
        return CalfViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CalfViewHolder, position: Int) {
        // we call bind() to bind all the data. not doing that yet
        val current = getItem(position)

        holder.bind(current,calfListener)

    }


}
interface OnCalfListener{
    fun onDeleteCalfClick(position:Long)
}

class CalfViewHolder(calfView: View): RecyclerView.ViewHolder(calfView){
    private val tagNumber:TextView = calfView.findViewById(R.id.text_view_title)
    private val dateView:TextView = calfView.findViewById(R.id.text_view_date);
    private val details:TextView = calfView.findViewById(R.id.text_view_description)
    private val sex:TextView = calfView.findViewById(R.id.text_view_sex)
    private val deleteButton:Button = calfView.findViewById(R.id.delete_calf)



    fun bind(calf: Calf,listener:OnCalfListener){
        //this is where all the method binding will go
        val pattern = "MM-dd-yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern)
        tagNumber.text = calf.tagNumber
        dateView.text = simpleDateFormat.format(calf.date)
        details.text = calf.details
        sex.text = calf.sex;

        deleteButton.setOnClickListener{
                listener.onDeleteCalfClick(calf.id)
        }

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
