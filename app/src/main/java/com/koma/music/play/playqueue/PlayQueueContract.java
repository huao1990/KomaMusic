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
package com.koma.music.play.playqueue;

import com.koma.music.base.BasePresenter;
import com.koma.music.base.BaseView;
import com.koma.music.data.model.Song;

import java.util.List;

/**
 * Created by koma on 4/28/17.
 */

public interface PlayQueueContract {
    interface View extends BaseView<Presenter> {
        boolean isActive();

        void hideLoadingView();

        void showPlayQueueSongs(List<Song> songs);
    }

    interface Presenter extends BasePresenter {
        void loadPlayQueue();

        void onLoadPlayQueueFinished(List<Song> songs);
    }
}