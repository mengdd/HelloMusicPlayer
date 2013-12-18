package com.example.hellomusic;

import java.util.ArrayList;
import java.util.List;

import com.examplemeng.music.SongInfo;
import com.examplemeng.utils.AppConstants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

public class PlayActivity extends Activity {
    private List<SongInfo> songInfos = null;
    private int currentID = 0;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(AppConstants.LOG_TAG, "PlayActivity --> onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        Log.v(AppConstants.LOG_TAG, "PlayActivity --> getIntent()");

        songInfos = new ArrayList<SongInfo>();

        songInfos = (ArrayList<SongInfo>) bundle
                .getSerializable(AppConstants.SONG_LIST);

        currentID = bundle.getInt(AppConstants.SONG_ID);

        Log.v(AppConstants.LOG_TAG, "PlayActivity --> getSongInfos: "
                + songInfos.size());

        updateListView();

        processViews();

        playSong();

    }

    private void updateListView() {
        Log.v(AppConstants.LOG_TAG, "PlayActivity --> updateSongList");

        ListView myListView = (ListView) findViewById(R.id.playListView);

        ArrayAdapter<SongInfo> adapter = new ArrayAdapter<SongInfo>(this,
                android.R.layout.simple_list_item_1, songInfos);
        myListView.setAdapter(adapter);

        myListView.setOnItemClickListener(new MyItemClickHandler());
    }

    class MyItemClickHandler implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
            Log.v(AppConstants.LOG_TAG,
                    "PlayActivity --> onItemClick: position" + position
                            + "id: " + id);
            currentID = position;
            playSong();
        }

    }

    private void processViews() {
        ImageButton playButton = (ImageButton) findViewById(R.id.btn_play);
        playButton.setOnClickListener(new PlayBtnListener());

        ImageButton pauseButton = (ImageButton) findViewById(R.id.btn_pause);
        pauseButton.setOnClickListener(new PauseBtnListener());

        ImageButton stopButton = (ImageButton) findViewById(R.id.btn_stop);
        stopButton.setOnClickListener(new StopBtnListener());

        ImageButton preButton = (ImageButton) findViewById(R.id.btn_pre);
        preButton.setOnClickListener(new PreBtnListener());

        ImageButton nextButton = (ImageButton) findViewById(R.id.btn_next);
        nextButton.setOnClickListener(new NextBtnListener());

    }

    class PlayBtnListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Log.v(AppConstants.LOG_TAG, "PlayActivity --> Play Button Pressed!");

            playSong();

        }
    }

    private void playSong() {
        Log.v(AppConstants.LOG_TAG, "PlayActivity --> playSong. currentID: "
                + currentID);

        Intent intent = new Intent();
        intent.setClass(PlayActivity.this, PlayService.class);

        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.SONG_ID, currentID);
        bundle.putParcelableArrayList(AppConstants.SONG_LIST,
                (ArrayList<? extends Parcelable>) songInfos);
        bundle.putInt(AppConstants.MSG, AppConstants.PlayMsg.MSG_PLAY);

        intent.putExtras(bundle);

        startService(intent);

    }

    class PauseBtnListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            Log.v(AppConstants.LOG_TAG,
                    "PlayActivity --> Pause Button Pressed!");
            Intent intent = new Intent();
            intent.setClass(PlayActivity.this, PlayService.class);
            Bundle bundle = new Bundle();
            bundle.putInt(AppConstants.MSG, AppConstants.PlayMsg.MSG_PAUSE);
            intent.putExtras(bundle);
            startService(intent);

        }
    }

    class StopBtnListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Log.v(AppConstants.LOG_TAG, "PlayActivity --> Stop Button Pressed!");
            Intent intent = new Intent();
            intent.setClass(PlayActivity.this, PlayService.class);
            Bundle bundle = new Bundle();
            bundle.putInt(AppConstants.MSG, AppConstants.PlayMsg.MSG_STOP);
            intent.putExtras(bundle);
            startService(intent);

        }
    }

    class PreBtnListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Log.v(AppConstants.LOG_TAG, "PlayActivity --> Pre Button Pressed!");
            Intent intent = new Intent();
            intent.setClass(PlayActivity.this, PlayService.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(AppConstants.SONG_LIST,
                    (ArrayList<? extends Parcelable>) songInfos);
            bundle.putInt(AppConstants.MSG, AppConstants.PlayMsg.MSG_PRE);
            intent.putExtras(bundle);
            startService(intent);
        }

    }

    class NextBtnListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Log.v(AppConstants.LOG_TAG, "PlayActivity --> Next Button Pressed!");
            Intent intent = new Intent();
            intent.setClass(PlayActivity.this, PlayService.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(AppConstants.SONG_LIST,
                    (ArrayList<? extends Parcelable>) songInfos);
            bundle.putInt(AppConstants.MSG, AppConstants.PlayMsg.MSG_NEXT);
            intent.putExtras(bundle);
            startService(intent);

        }

    }

}
