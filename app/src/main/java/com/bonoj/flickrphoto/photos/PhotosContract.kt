package com.bonoj.flickrphoto.photos

import com.bonoj.flickrphoto.data.model.Photo

interface PhotosContract {

    interface View {
        fun displayPhotos(photos: List<Photo>)
        fun displayNoPhotos()
        fun displayError()
    }

    interface Presenter {
        fun loadNewPhotos()
        fun loadPhotos()
        fun setSearchText(text: String)
        fun unsubscribe()
    }
}