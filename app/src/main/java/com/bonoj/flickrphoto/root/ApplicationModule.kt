package com.bonoj.flickrphoto.root

import android.content.Context
import com.bonoj.flickrphoto.data.source.FlickrPhotosDataSource
import com.bonoj.flickrphoto.data.source.FlickrPhotosRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: FlickrPhotoApplication) {

    private val flickrPhotosRepository: FlickrPhotosRepository = FlickrPhotosRepository()

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return application
    }

    @Provides
    @Singleton
    fun providePhotosRepository(): FlickrPhotosDataSource {
        return flickrPhotosRepository
    }
}
