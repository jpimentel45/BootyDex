package com.example.bootydex.fragments.list

import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bootydex.R
import com.example.bootydex.model.BootyCall
import kotlinx.android.synthetic.main.custom_row.view.*
import kotlinx.android.synthetic.main.custom_row.view.ivBooty
import kotlinx.android.synthetic.main.custom_row.view.tvAge


class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    private var bootyList = listOf<BootyCall>()

    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun getItemCount(): Int {
        return bootyList.size
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        val currentItem = bootyList[position]
        //decode base64 string to image
        val bitMap = currentItem.image
        val drawable = BitmapDrawable(bitMap)
        val uri = Uri.parse(currentItem.image)

//        val bitmap = BitmapFactory.decodeResource(currentItem.image)
//        holder.itemView.ivBooty.background = drawable
        holder.itemView.tvAge.text = currentItem.age.toString()
        holder.itemView.tvName.text = currentItem.firstName
        holder.itemView.tvLastName.text = currentItem.lastName
        holder.itemView.tvNumber.text = currentItem.phoneNumber
//        holder.itemView.ivBooty.setImageURI(null)
        holder.itemView.ivBooty.setImageURI(uri)
        holder.itemView.bootyCard.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToDetailFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(booty: List<BootyCall>){
        this.bootyList = booty
        notifyDataSetChanged()
    }
}