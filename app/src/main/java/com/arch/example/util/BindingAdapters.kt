package com.arch.example.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

object BindingAdapters {

    @BindingAdapter("loadImageFromUrl")
    @JvmStatic
    fun loadImageFromUrl(imageView: ImageView, url: String?) {
        url?.let {
            Glide.with(imageView.context).load(url)
                .apply(
                    RequestOptions().transform(CenterCrop(), RoundedCorners(15))
                )
                .thumbnail(0.3f)
                .into(imageView)
        }
    }

    @BindingAdapter("navigationClick")
    @JvmStatic
    fun navigationClick(view: View, direction: NavDirections?) {
        direction?.let {
            view.setOnClickListener {
                it.findNavController().navigate(direction)
            }
        }
    }

    @BindingAdapter("tripleVisibilityChanger")
    @JvmStatic
    fun tripleVisibilityChanger(view: View, isVisible: Boolean?) {
        isVisible?.let {
            view.visibility = if (isVisible) View.VISIBLE else View.GONE
        } ?: run {
            view.visibility = View.GONE
        }
    }

}