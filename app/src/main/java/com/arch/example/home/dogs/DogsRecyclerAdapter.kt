package com.arch.example.home.dogs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.arch.example.R
import com.arch.example.base.BaseAdapter
import com.arch.example.base.BaseViewHolder
import com.arch.example.databinding.AdapterItemDogBinding


class DogsRecyclerAdapter() :
    BaseAdapter<AdapterItemDogBinding, String, DogsRecyclerAdapter.DogsViewHolder>() {

    override fun getBinding(inflater: LayoutInflater, parent: ViewGroup): AdapterItemDogBinding {
        return DataBindingUtil.inflate(inflater, R.layout.adapter_item_dog, parent, false)
    }

    override fun getViewHolder(binding: AdapterItemDogBinding): DogsViewHolder {
        return DogsViewHolder(binding)
    }


    inner class DogsViewHolder(binding: AdapterItemDogBinding) :
        BaseViewHolder<AdapterItemDogBinding, String>(binding) {

        override fun bind(data: String, position: Int) {
            binding.imageUrl = data
        }
    }

}