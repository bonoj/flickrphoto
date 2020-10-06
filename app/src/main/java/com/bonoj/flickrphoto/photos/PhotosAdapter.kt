package com.bonoj.flickrphoto.photos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bonoj.flickrphoto.R
import com.bonoj.flickrphoto.data.model.Photo
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.list_item_photo.view.*

class PhotosAdapter(private val context: Context,
                    private val clickListener: ItemClickListener) : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    private var photos: ArrayList<Photo> = ArrayList()

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.list_item_photo, parent, false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val id = photos[position].id
        val imageUrl = photos[position].url_s

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.placeholder)
            .centerCrop()

        Glide.with(context)
            .load(imageUrl)
            .apply(requestOptions)
            .into(holder.iv)

        holder.itemView.tag = id
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var iv: ImageView = itemView.list_item_iv

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            clickListener.onItemClick(view, adapterPosition)
        }
    }

    fun setPhotos(photos: List<Photo>) {

        for (photo in photos) {
            if (photo !in this.photos) {
                this.photos.add(photo)
            }
        }
        notifyDataSetChanged()
    }

    fun getPhotosParcel() : ArrayList<Photo> {
        return photos
    }

    fun getPhoto(position: Int): Photo {
        return photos[position]
    }

    fun refillAdapterAfterDeviceRotation(preexistingPhotos: List<Photo>) {
        photos.addAll(preexistingPhotos)
    }

    fun resetPhotos() {
        photos.clear()
        notifyDataSetChanged()
    }
}