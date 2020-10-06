package com.bonoj.flickrphoto.data.source.remote

object FlickerPhotoApiUtils {

    val BASE_URL = "https://api.flickr.com"

    val apiService: FlickrPhotoService
        get() = RetrofitClient.getClient(BASE_URL).create(FlickrPhotoService::class.java)
}