package com.example.acer.bakingrecipes.Detail;


import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acer.bakingrecipes.Detail.ItemDetailActivity;
import com.example.acer.bakingrecipes.R;
import com.example.acer.bakingrecipes.UI.ItemListActivity;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    private SimpleExoPlayerView exoPlayerView;
    private TextView textView;

    private static final String READY_PLAYER = "playwhenready";
    private static final String WINDOW_INDEX = "windowindex";
    private static final String URI = "uri";
    private static final String VIDEO_STATE = "videoposition";
    private SimpleExoPlayer simpleExoPlayer;
    private long current_state;
    private String videolink;
    private String fulldesc;
    private String thumburl;
    private boolean PlayWhenReady = true;
    private Uri mVideoUri;
    private int WindowIndex;

    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey("link")) {
            videolink = getArguments().getString("link");
            fulldesc = getArguments().getString("fullDesc");
            thumburl = getArguments().getString("thumburl");


        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);
        textView = rootView.findViewById(R.id.exo_tv);
        textView.setText(fulldesc);
        exoPlayerView = rootView.findViewById(R.id.exo_player);
        if (savedInstanceState != null) {

            PlayWhenReady = savedInstanceState.getBoolean(READY_PLAYER);
            current_state = savedInstanceState.getLong(VIDEO_STATE);
            WindowIndex = savedInstanceState.getInt(WINDOW_INDEX);
            mVideoUri = Uri.parse((savedInstanceState.getString(URI)));
            if (videolink.isEmpty())
                mVideoUri = Uri.parse(thumburl);


        } else {
            if (getArguments() != null) {


                exoPlayerView.setVisibility(View.VISIBLE);

                if (videolink.isEmpty() || videolink.equals("")) {

                    if (thumburl.isEmpty()) {

                    } else {
                        mVideoUri = Uri.parse(thumburl);

                    }

                } else {

                    mVideoUri = Uri.parse(videolink);
                }


            }
        }

        return rootView;
    }

    private void initializeplayer(Uri mVideoUri) {
        if (simpleExoPlayer == null) {
            exoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.mipmap.novideo));


            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());


            exoPlayerView.setPlayer(simpleExoPlayer);


            String userAgent = Util.getUserAgent(getActivity(), getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(mVideoUri,
                    new DefaultDataSourceFactory(getActivity(), userAgent),
                    new DefaultExtractorsFactory(),
                    null,
                    null);

            if (current_state != C.TIME_UNSET) {
                simpleExoPlayer.seekTo(current_state);
            }

            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(PlayWhenReady);
        }

    }

    private void releaseplayer() {
        if (simpleExoPlayer != null) {
            updateStartPosition();
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {


            initializeplayer(mVideoUri);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || simpleExoPlayer == null) {
            initializeplayer(mVideoUri);
        }
        if (simpleExoPlayer != null) {
            simpleExoPlayer.setPlayWhenReady(PlayWhenReady);
            simpleExoPlayer.seekTo(current_state);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (simpleExoPlayer != null) {

            if (Util.SDK_INT <= 23) {
                releaseplayer();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (simpleExoPlayer != null) {

            if (Util.SDK_INT > 23) {
                releaseplayer();
            }
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (simpleExoPlayer != null) {
            updateStartPosition();
            outState.putString(URI, videolink);
            outState.putLong(VIDEO_STATE, current_state);
            outState.putBoolean(READY_PLAYER, PlayWhenReady);
            outState.putInt(WINDOW_INDEX, WindowIndex);


        }
    }

    private void updateStartPosition() {
        if (simpleExoPlayer != null) {
            PlayWhenReady = simpleExoPlayer.getPlayWhenReady();
            WindowIndex = simpleExoPlayer.getCurrentWindowIndex();
            current_state = simpleExoPlayer.getCurrentPosition();


        }


    }


}
