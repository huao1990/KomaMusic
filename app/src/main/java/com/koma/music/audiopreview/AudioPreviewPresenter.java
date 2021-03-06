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
package com.koma.music.audiopreview;

import android.support.annotation.NonNull;

import com.koma.music.util.LogUtils;

/**
 * Created by koma on 4/5/17.
 */

public class AudioPreviewPresenter implements AudioPreviewContract.Presenter {
    private static final String TAG = AudioPreviewPresenter.class.getSimpleName();
    @NonNull
    private AudioPreviewContract.View mView;

    public AudioPreviewPresenter(@NonNull AudioPreviewContract.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {
        LogUtils.i(TAG, "subscribe");
    }

    @Override
    public void unSubscribe() {
        LogUtils.i(TAG, "unSubscribe");
    }
}
