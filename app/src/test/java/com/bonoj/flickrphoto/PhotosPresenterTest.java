package com.bonoj.flickrphoto;

import com.bonoj.flickrphoto.data.model.Photo;
import com.bonoj.flickrphoto.data.source.FlickrPhotosDataSource;
import com.bonoj.flickrphoto.photos.PhotosContract;
import com.bonoj.flickrphoto.photos.PhotosPresenter;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PhotosPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    FlickrPhotosDataSource flickrPhotosDataSource;

    @Mock
    PhotosContract.View view;

    private PhotosPresenter presenter;

    private Photo getMockPhoto() {
        return new Photo(
                0,
                "",
                "",
                0,
                0,
                "",
                0,
                0,
                "",
                0,
                0);
    }

    @Before
    public void setUp() {
        presenter = new PhotosPresenter(
                view, flickrPhotosDataSource, Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) {
                return Schedulers.trampoline();
            }
        });
        // TODO Fix scheduler for testing
    }

    @Test
    public void shouldDeliverNewPhotosToView() {

        List<Photo> photos = Arrays.asList(
                getMockPhoto(),
                getMockPhoto(),
                getMockPhoto());
        Mockito.when(flickrPhotosDataSource.getFlickrPhotos("", 0)).thenReturn(Single.just(photos));

        presenter.loadNewPhotos();

        Mockito.verify(view).displayPhotos(photos);
    }

    @Test
    public void shouldDeliverPhotosToView() {

        List<Photo> photos = Arrays.asList(
                getMockPhoto(),
                getMockPhoto(),
                getMockPhoto());
        Mockito.when(flickrPhotosDataSource.getFlickrPhotos("", 0)).thenReturn(Single.just(photos));

        presenter.loadPhotos();

        Mockito.verify(view).displayPhotos(photos);
    }

    @Test
    public void shouldHandleNoPhotosFound() {

        List<Photo> photos = Collections.emptyList();
        Mockito.when(flickrPhotosDataSource.getFlickrPhotos("", 0)).thenReturn(Single.just(photos));

        presenter.loadPhotos();

        Mockito.verify(view).displayNoPhotos();
    }

    @Test
    public void shouldHandleError() {

        Mockito.when(flickrPhotosDataSource.getFlickrPhotos("", 0)).thenReturn(Single.<List<Photo>>error(new Throwable("error")));

        presenter.loadPhotos();

        Mockito.verify(view).displayError();
    }
}
