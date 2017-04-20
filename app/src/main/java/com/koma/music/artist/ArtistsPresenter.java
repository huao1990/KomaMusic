package com.koma.music.artist;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import com.koma.music.MusicApplication;
import com.koma.music.data.local.MusicRepository;
import com.koma.music.data.model.Artist;
import com.koma.music.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by koma on 3/21/17.
 */

public class ArtistsPresenter implements ArtistsConstract.Presenter {
    private static final String TAG = ArtistsPresenter.class.getSimpleName();
    @NonNull
    private ArtistsConstract.View mView;

    private CompositeSubscription mSubscriptions;

    private MusicRepository mRepository;

    private ArtistsPresenter(ArtistsConstract.View view, MusicRepository repository) {
        mView = view;
        mView.setPresenter(this);

        mSubscriptions = new CompositeSubscription();

        mRepository = repository;
    }

    public static ArtistsPresenter newInstance(ArtistsConstract.View view, MusicRepository repository) {
        return new ArtistsPresenter(view, repository);
    }


    @Override
    public void subscribe() {
        LogUtils.i(TAG, "subscribe");

        loadArtists();
    }

    @Override
    public void unSubscribe() {
        LogUtils.i(TAG, "unSubscribe");

        if (mSubscriptions != null) {
            mSubscriptions.clear();
        }
    }

    @Override
    public void loadArtists() {
        LogUtils.i(TAG, "loadArtists");

        mSubscriptions.clear();

        Subscription subscription = mRepository.getAllArtists().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Artist>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        LogUtils.e(TAG, "loadArtists onError : " + throwable.toString());
                    }

                    @Override
                    public void onNext(List<Artist> artists) {
                        onLoadArtistsFinished(artists);
                    }
                });

        mSubscriptions.add(subscription);
    }

    @Override
    public void onLoadArtistsFinished(List<Artist> artists) {
        LogUtils.i(TAG, "onLoadArtistsFinished");

        if (mView.isActive()) {
            mView.hideLoadingView();

            if (artists.size() == 0) {
                mView.showEmptyView();
            } else {
                mView.showArtists(artists);
            }
        }
    }

    public static List<Artist> getAllArtists() {
        List<Artist> artistList = new ArrayList<>();
        Cursor cursor = makeArtistCursor(MusicApplication.getContext());
        // Gather the data
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Copy the artist id
                final long id = cursor.getLong(0);

                // Copy the artist name
                final String artistName = cursor.getString(1);

                // Copy the number of albums
                final int albumCount = cursor.getInt(2);

                // Copy the number of songs
                final int songCount = cursor.getInt(3);

                // as per designer's request, don't show unknown artist
                if (MediaStore.UNKNOWN_STRING.equals(artistName)) {
                    continue;
                }

                // Create a new artist
                final Artist artist = new Artist(id, artistName, songCount, albumCount);
                artistList.add(artist);
            } while (cursor.moveToNext());
        }
        // Close the cursor
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        return artistList;
    }

    /**
     * Creates the {@link Cursor} used to run the query.
     *
     * @param context The {@link Context} to use.
     * @return The {@link Cursor} used to run the artist query.
     */
    private static final Cursor makeArtistCursor(final Context context) {
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                new String[]{
                        /* 0 */
                        MediaStore.Audio.Artists._ID,
                        /* 1 */
                        MediaStore.Audio.Artists.ARTIST,
                        /* 2 */
                        MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
                        /* 3 */
                        MediaStore.Audio.Artists.NUMBER_OF_TRACKS
                }, null, null, MediaStore.Audio.Artists.DEFAULT_SORT_ORDER);

        return cursor;
    }
}
