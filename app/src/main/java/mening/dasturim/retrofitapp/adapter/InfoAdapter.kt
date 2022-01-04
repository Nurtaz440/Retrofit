package mening.dasturim.retrofitapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mening.dasturim.retrofitapp.R
import mening.dasturim.retrofitapp.model.CommentsItems
import mening.dasturim.retrofitapp.model.DataPostItem
import mening.dasturim.retrofitapp.model.InfoItems

class InfoAdapter(val listItems:List<InfoItems>,val adapterListener: CommentAdapterListener)
    : RecyclerView.Adapter<InfoAdapter.VH>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoAdapter.VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.item_vertical,parent,false))
    }

    override fun onBindViewHolder(holder: InfoAdapter.VH, position: Int) {
        holder.textDescription.text=listItems[position].publishDate
        holder.name.text=listItems[position].text
        Glide.with(holder.itemView.context).load(listItems[position].image).into(holder.imageView)

        holder.itemView.setOnClickListener {
            adapterListener.onClick(listItems[position])
        }
    }

    override fun getItemCount(): Int {
      return listItems.count()
    }
    inner class VH(view : View): RecyclerView.ViewHolder(view){
        val textDescription:TextView=view.findViewById(R.id.tv_ver_description)
        val name:TextView=view.findViewById(R.id.tv_ver_name)
        val imageView:ImageView=view.findViewById(R.id.iv_ver)
    }
}

interface CommentAdapterListener {
    fun onClick(item: InfoItems)
}