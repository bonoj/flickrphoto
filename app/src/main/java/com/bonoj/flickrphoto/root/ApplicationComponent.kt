package com.bonoj.flickrphoto.root

import com.bonoj.flickrphoto.photos.PhotosActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun inject(application: FlickrPhotoApplication)

    fun inject(photosActivity: PhotosActivity)
}