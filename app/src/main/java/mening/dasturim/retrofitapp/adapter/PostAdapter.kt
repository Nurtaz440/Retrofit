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
import mening.dasturim.retrofitapp.model.DataPostItem


class PostAdapter(val listItems : List<DataPostItem>, val adapterListener: UserAdapterListener) :
    RecyclerView.Adapter<PostAdapter.VH>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false))
    }



    override fun onBindViewHolder(holder: PostAdapter.VH, position: Int) {

        holder.text.text = listItems[position].firstName
        Glide.with(holder.itemView.context).load(listItems[position].picture)
            .into(holder.image)

        holder.itemView.setOnClickListener {
            adapterListener.onClick(listItems[position])
        }
    }

    override fun getItemCount(): Int {
        return listItems.count()
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view){
        var text:TextView = view.findViewById(R.id.tv_name)
        var image:ImageView = view.findViewById(R.id.iv_post)

    }
}

interface UserAdapterListener {
    fun onClick(item: DataPostItem)
}