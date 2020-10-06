package com.bonoj.flickrphoto.photos

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.GridLayoutManager
import com.bonoj.flickrphoto.R
import com.bonoj.flickrphoto.data.model.Photo
import com.bonoj.flickrphoto.data.source.FlickrPhotosDataSource
import com.bonoj.flickrphoto.details.DetailsActivity
import com.bonoj.flickrphoto.root.FlickrPhotoApplication
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_photos.*
import javax.inject.Inject

class PhotosActivity : AppCompatActivity(), PhotosContract.View, PhotosAdapter.ItemClickListener {

    companion object {
        @JvmStatic
        val DETAILS_INTENT_KEY = "com.bonoj.flickrphoto.DETAILS_INTENT_KEY"
    }

    private val PHOTOS_PARCEL_KEY = "com.bonoj.flickrphoto.PHOTOS_PARCEL_KEY"

    @Inject
    lateinit var flickrPhotosDataSource: FlickrPhotosDataSource

    private lateinit var presenter: PhotosPresenter
    private lateinit var adapter: PhotosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)

        FlickrPhotoApplication.graph.inject(this)

        val layoutManager = GridLayoutManager(this, 2)
        var searchText = ""

        adapter = PhotosAdapter(this, this)
        photos_rv.setHasFixedSize(true)
        photos_rv.layoutManager = layoutManager
        photos_rv.adapter = adapter
        photos_rv.addOnScrollListener(
            EndlessScrollListener({ presenter.loadPhotos() }, layoutManager)
        )

        photos_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchText = s.toString()
            }
        })

        photos_search_button.setOnClickListener {
            hideKeyboard()
            adapter.resetPhotos()
            presenter.setSearchText(searchText)
            presenter.loadNewPhotos()
        }

        if (savedInstanceState != null) {
            adapter.refillAdapterAfterDeviceRotation(savedInstanceState.getParcelableArrayList(PHOTOS_PARCEL_KEY)!!)
        }

        presenter = PhotosPresenter(this, flickrPhotosDataSource, AndroidSchedulers.mainThread())
    }

    override fun onStop() {
        super.onStop()
        presenter.unsubscribe()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(PHOTOS_PARCEL_KEY, adapter.getPhotosParcel())
    }

    override fun displayPhotos(photos: List<Photo>) {
        adapter.setPhotos(photos)

        photos_progress_bar.visibility = View.GONE
        photos_empty_tv.visibility = View.GONE
        photos_rv.visibility = View.VISIBLE
    }

    override fun displayNoPhotos() {
        if (photos_rv.adapter?.itemCount == 0) {
            photos_empty_tv.setText(R.string.unavailable)
            photos_progress_bar.visibility = View.GONE
            photos_rv.visibility = View.GONE
            photos_empty_tv.visibility = View.VISIBLE
        }
    }

    override fun displayError() {
        if (photos_rv.adapter?.itemCount == 0) {
            photos_empty_tv.setText(R.string.error_connection)
            photos_progress_bar.visibility = View.GONE
            photos_rv.visibility = View.GONE
            photos_empty_tv.visibility = View.VISIBLE
        }
    }

    override fun onItemClick(view: View, position: Int) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DETAILS_INTENT_KEY, adapter.getPhoto(position))
        startActivity(intent)
    }

    private fun hideKeyboard() {
        val inputMethodManager = this.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            this.currentFocus?.windowToken, 0
        )
    }
}
