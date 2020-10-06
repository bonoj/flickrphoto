package com.bonoj.flickrphoto.data.source

import com.bonoj.flickrphoto.data.model.Photo
import io.reactivex.Single

interface FlickrPhotosDataSource {

    fun getFlickrPhotos(text: String, page: Int): Single<List<Photo>>
    fun resetPhotos()
}
