package com.bonoj.flickrphoto.data.source.remote

import com.bonoj.flickrphoto.data.model.FlickrResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrPhotoService {

    @GET("/services/rest/?method=flickr.photos.search&api_key=675894853ae8ec6c242fa4c077bcf4a0&extras=url_s&format=json&nojsoncallback=1")
    fun setParameters(@Query("text") text: String,
                      @Query("page") page: Int): Call<FlickrResponse>
}