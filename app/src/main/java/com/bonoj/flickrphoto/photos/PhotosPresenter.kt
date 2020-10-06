package com.bonoj.flickrphoto.photos

import com.bonoj.flickrphoto.data.model.Photo
import com.bonoj.flickrphoto.data.source.FlickrPhotosDataSource
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class PhotosPresenter(private val view: PhotosContract.View,
                      private val flickrPhotosDataSource: FlickrPhotosDataSource,
                      private val mainScheduler: Scheduler) : PhotosContract.Presenter {

    private val compositeDisposable = CompositeDisposable()
    private var page = 0
    private var text = ""

    override fun loadNewPhotos() {
        page = 1
        flickrPhotosDataSource.resetPhotos()
        val disposableSingleObserver = flickrPhotosDataSource.getFlickrPhotos(text, page)
            .subscribeOn(Schedulers.io())
            .observeOn(mainScheduler)
            .subscribeWith(object : DisposableSingleObserver<List<Photo>>() {
                override fun onSuccess(photos: List<Photo>) {

                    if (photos.isEmpty()) {
                        view.displayNoPhotos()
                    } else {
                        view.displayPhotos(photos)
                    }
                }

                override fun onError(e: Throwable) {
                    view.displayError()
                }
            })

        compositeDisposable.add(disposableSingleObserver)
    }

    override fun loadPhotos() {
        page += 1
        val disposableSingleObserver = flickrPhotosDataSource.getFlickrPhotos(text, page)
            .subscribeOn(Schedulers.io())
            .observeOn(mainScheduler)
            .subscribeWith(object : DisposableSingleObserver<List<Photo>>() {
                override fun onSuccess(photos: List<Photo>) {

                    if (photos.isEmpty()) {
                        view.displayNoPhotos()
                    } else {
                        view.displayPhotos(photos)
                    }
                }

                override fun onError(e: Throwable) {
                    view.displayError()
                }
            })

        compositeDisposable.add(disposableSingleObserver)
    }

    override fun setSearchText(text: String) {
        this.text = text
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }
}