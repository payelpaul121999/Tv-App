package com.payelpaul.androidtvapp.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.payelpaul.androidtvapp.R
import com.payelpaul.androidtvapp.model.CastResponse


class CastPresenter : Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {

        val view = LayoutInflater.from(parent!!.context)
            .inflate(R.layout.cast_image_card, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        val userData = item as CastResponse.Cast
        val imageview = viewHolder!!.view.findViewById<ImageView>(R.id.imageCast)

        val path = "https://www.themoviedb.org/t/p/w780" + userData.profile_path
        Glide.with(viewHolder.view.context)
            .load(path)
            .into(imageview)

    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
    }
}