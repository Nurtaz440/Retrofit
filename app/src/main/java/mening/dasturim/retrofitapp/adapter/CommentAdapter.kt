package mening.dasturim.retrofitapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mening.dasturim.retrofitapp.R
import mening.dasturim.retrofitapp.model.CommentsItems

class CommentAdapter(val listItems:List<CommentsItems>)
    : RecyclerView.Adapter<CommentAdapter.VH>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.item_comment,parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.textDescription.text=listItems[position].publishDate
        holder.commenText.text=listItems[position].message
        holder.name.text=listItems[position].owner.firstName
        Glide.with(holder.itemView.context).load(listItems[position].owner.picture).into(holder.imageView)

    }

    override fun getItemCount(): Int {
        return listItems.count()
    }
    inner class VH(view : View): RecyclerView.ViewHolder(view){
        val textDescription: TextView =view.findViewById(R.id.tv_date)
        val commenText: TextView =view.findViewById(R.id.tv_comment)
        val name: TextView =view.findViewById(R.id.tv_name)
        val imageView: ImageView =view.findViewById(R.id.shapeableImageView)
    }
}
