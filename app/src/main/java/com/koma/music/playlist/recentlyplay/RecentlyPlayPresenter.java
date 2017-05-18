/*
 * Copyright (C) 2017 Koma MJ
 *
 * Licensed under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.koma.music.playlist.recentlyplay;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.koma.music.MusicApplication;
import com.koma.music.data.local.MusicRepository;
import com.koma.music.data.local.db.RecentlyPlay;
import com.koma.music.data.local.db.SongPlayCount;
import com.koma.music.data.local.db.SortedCursor;
import com.koma.music.data.model.Song;
import com.koma.music.song.SongsPresenter;
import com.koma.music.util.LogUtils;

import java.util.List;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by koma on 4/20/17.
 */

public class RecentlyPlayPresenter implements RecentlyPlayContract.Presenter {
    private static final String TAG = RecentlyPlayPresenter.class.getSimpleName();

    @NonNull
    private RecentlyPlayContract.View mView;

    private CompositeSubscription mSubscriptions;

    private MusicRepository mRepository;

    public RecentlyPlayPresenter(@NonNull RecentlyPlayContract.View view, MusicRepository repository) {
        mSubscriptions = new CompositeSubscription();

        mView = view;
        mView.setPresenter(this);

        mRepository = repository;
    }

    @Override
    public void subscribe() {
        LogUtils.i(TAG, "subscribe");
    }

    @Override
    public void unSubscribe() {
        LogUtils.i(TAG, "unSubscribe");

        if (mSubscriptions != null) {
            mSubscriptions.clear();
        }
    }

    @Override
    public void loadRecentlyPlayedSongs() {

    }

    @Override
    public void onLoadPlayedSongsFinished(List<Song> songs) {

    }

    public static List<Song> getRecentlyPlaySongs() {
        return SongsPresenter.getSongsForCursor(makeRecentPlayCursor(), false);
    }

    /**
     * 获取最近播放歌曲的cursor
     *
     * @return
     */
    public static SortedCursor makeRecentPlayCursor() {

        Cursor songs = RecentlyPlay.getInstance(MusicApplication.getContext()).queryRecentIds(null);

        try {
            return SongsPresenter.makeSortedCursor(MusicApplication.getContext(), songs,
                    songs.getColumnIndex(SongPlayCount.SongPlayCountColumns.ID));
        } finally {
            if (songs != null) {
                songs.close();
            }
        }
    }
}
