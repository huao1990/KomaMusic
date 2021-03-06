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
package com.koma.music.playlist;

import com.koma.music.base.BasePresenter;
import com.koma.music.base.BaseView;
import com.koma.music.data.model.Playlist;

import java.util.List;

/**
 * Created by koma on 3/21/17.
 */

public interface PlaylistsConstract {
    interface View extends BaseView<Presenter> {
        boolean isActive();

        void hideLoadingView();

        void showPlaylist(List<Playlist> playlists);
    }

    interface Presenter extends BasePresenter {
        void loadPlaylists();

        void onLoadPlaylistsFinished(List<Playlist> playlists);
    }
}
