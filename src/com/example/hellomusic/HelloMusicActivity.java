package com.example.hellomusic;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.examplemeng.music.SongInfo;
import com.examplemeng.utils.AppConstants;
import com.examplemeng.utils.FileUtils;

public class HelloMusicActivity extends Activity {

    private Button myButton;
    private TextView myView;
    private ListView myListView;

    private List<SongInfo> songInfos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find Views
        myButton = (Button) findViewById(R.id.getBtn);
        myView = (TextView) findViewById(R.id.myTextView1);
        myListView = (ListView) findViewById(R.id.myListView1);

        myView.setText(AppConstants.MUSIC_PATH);

        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getSongs();

            }
        });

        myListView.setOnItemClickListener(mMessageClickedHandler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hello_music, menu);
        return true;
    }

    private void getSongs() {
        Log.v(AppConstants.LOG_TAG, "Get Songs");
        FileUtils fileUtils = new FileUtils();
        songInfos = fileUtils.getSongs(AppConstants.MUSIC_PATH);

        ArrayAdapter<SongInfo> adapter = new ArrayAdapter<SongInfo>(this,
                android.R.layout.simple_list_item_1, songInfos);
        myListView.setAdapter(adapter);

    }

    // Create a message handling object as an anonymous class.
    private OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position,
                long id) {
            Log.v(AppConstants.LOG_TAG, "Item Click: position: " + position
                    + " id: " + id);

            SongInfo songInfo = songInfos.get(position);
            Intent intent = new Intent();
            intent.setClass(HelloMusicActivity.this, PlayActivity.class);

            Bundle bundle = new Bundle();
            bundle.putInt(AppConstants.SONG_ID, songInfo.getId());
            bundle.putParcelableArrayList(AppConstants.SONG_LIST,
                    (ArrayList<? extends Parcelable>) songInfos);
            // intent.putParcelableArrayListExtra(AppConstants.SONG_LIST,
            // (ArrayList<? extends Parcelable>) songInfos);
            intent.putExtras(bundle);
            startActivity(intent);

        }
    };

}
