package com.bonoj.flickrphoto.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bonoj.flickrphoto.R
import com.bonoj.flickrphoto.data.model.Photo
import com.bonoj.flickrphoto.photos.PhotosActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    private lateinit var flickrPhoto: Photo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        supportActionBar?.setTitle(getString(R.string.app_name))

        flickrPhoto = intent.getParcelableExtra(PhotosActivity.DETAILS_INTENT_KEY)!!

        val requestOptions = RequestOptions().placeholder(R.drawable.placeholder)

        Glide.with(this)
            .load(flickrPhoto.url_s)
            .apply(requestOptions)
            .into(photo_iv)
    }
}









