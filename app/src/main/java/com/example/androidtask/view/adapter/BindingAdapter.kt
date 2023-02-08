package com.example.androidtask.view.adapter

import android.R
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target


object BindingAdapter {

    @BindingAdapter("app:personImage")
    @JvmStatic()
     fun avatar(imageView: ImageView, imageURL: String) {

        /*Glide.with(imageView.context)
            .setDefaultRequestOptions(
                RequestOptions()
                    .circleCrop()
            )
            .load(imageURL)
            .placeholder(R.drawable.ic_dialog_email)
            .into(imageView)*/
        Glide.with(imageView.context).load(imageURL).into(imageView)
    }

    @BindingAdapter("imageUrl", "error")
    @JvmStatic()
    fun loadImage(view: ImageView, url: String, error: Drawable) {
        /*Picasso.get().load(url).error(error).into(view)*/
        Glide.with(view.context).load(url).into(view)
    }

}