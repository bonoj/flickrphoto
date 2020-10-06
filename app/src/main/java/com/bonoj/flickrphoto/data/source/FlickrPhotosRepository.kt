package com.bonoj.flickrphoto.data.source

import com.bonoj.flickrphoto.data.model.Photo
import com.bonoj.flickrphoto.data.source.remote.FlickerPhotoApiUtils
import io.reactivex.Single

class FlickrPhotosRepository : FlickrPhotosDataSource {

    // Local cache
    val photos = ArrayList<Photo>()

    // Remote
    private val apiService = FlickerPhotoApiUtils.apiService

    override fun getFlickrPhotos(text: String, page: Int): Single<List<Photo>> {
        return Single.fromCallable { requestPhotosFromApi(text, page) }
    }

    override fun resetPhotos() {
        photos.clear()
    }

    private fun requestPhotosFromApi(text: String, page: Int): List<Photo> {
        val response = apiService.setParameters(text, page).execute()
        response.body()?.photos?.photo?.forEach {
            photos.add(it)
        }
        return photos
    }
}