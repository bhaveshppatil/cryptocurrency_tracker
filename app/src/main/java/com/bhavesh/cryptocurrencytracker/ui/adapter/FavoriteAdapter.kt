package com.bhavesh.cryptocurrencytracker.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bhavesh.cryptocurrencytracker.R
import com.bhavesh.cryptocurrencytracker.databinding.FavoriteItemLayoutBinding
import com.bhavesh.cryptocurrencytracker.remote.Currency
import com.bhavesh.cryptocurrencytracker.ui.clicklistener.OnItemClick
import com.bhavesh.cryptocurrencytracker.ui.viewholder.FavoriteViewHolder

class FavoriteAdapter(
    var dataModelList: MutableList<Currency>,
    val onItemClick: OnItemClick
) : RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemLayoutBinding: FavoriteItemLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.favorite_item_layout,
            parent,
            false
        )
        return FavoriteViewHolder(itemLayoutBinding, onItemClick)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val dataModel = dataModelList[position]
        holder.onBindData(dataModel)
    }

    override fun getItemCount(): Int {
        return dataModelList.size
    }
}