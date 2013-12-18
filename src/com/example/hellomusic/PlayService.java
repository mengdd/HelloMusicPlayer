package com.example.hellomusic;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.examplemeng.music.SongInfo;
import com.examplemeng.utils.AppConstants;

public class PlayService extends Service {
    private MediaPlayer mediaPlayer = null;

    private boolean isPlaying = false;
    private boolean isPaused = false;
    private boolean isReleased = false;

    private List<SongInfo> songInfos = null;
    private int currentID = 0;
    private int newSongID = 0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.v(AppConstants.LOG_TAG, "PlayService --> onStartCommand()");

        int MSG = 0;

        Bundle bundle = intent.getExtras();

        songInfos = new ArrayList<SongInfo>();

        songInfos = (ArrayList<SongInfo>) bundle
                .getSerializable(AppConstants.SONG_LIST);

        newSongID = bundle.getInt(AppConstants.SONG_ID);

        MSG = bundle.getInt(AppConstants.MSG);


        if (AppConstants.PlayMsg.MSG_PLAY == MSG) {
            Log.v(AppConstants.LOG_TAG,
                    "PlayService --> onStartCommand() --> MSG_PLAY");

            if (isPaused) {
                mediaPlayer.start();
                isPlaying = true;
                isPaused = false;

            } else {
                play(songInfos.get(newSongID));

            }

        } else if (AppConstants.PlayMsg.MSG_PAUSE == MSG) {
            Log.v(AppConstants.LOG_TAG,
                    "PlayService --> onStartCommand() --> MSG_PAUSE");
            pause();

        } else if (AppConstants.PlayMsg.MSG_STOP == MSG) {
            Log.v(AppConstants.LOG_TAG,
                    "PlayService --> onStartCommand() --> MSG_STOP");
            stop();

        } else if (AppConstants.PlayMsg.MSG_PRE == MSG) {
            Log.v(AppConstants.LOG_TAG,
                    "PlayService --> onStartCommand() --> MSG_PRE");
            stop();

            if (currentID == 0) {
                currentID = songInfos.size() - 1;
            } else {
                --currentID;

            }
            currentID = (currentID + 1) % songInfos.size();

            beginNewSong(songInfos.get(currentID));

        } else if (AppConstants.PlayMsg.MSG_NEXT == MSG) {
            Log.v(AppConstants.LOG_TAG,
                    "PlayService --> onStartCommand() --> MSG_NEXT");
            stop();

            currentID = (currentID + 1) % songInfos.size();

            beginNewSong(songInfos.get(currentID));

        }

        mediaPlayer
                .setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        currentID = (currentID + 1) % songInfos.size();

                        beginNewSong(songInfos.get(currentID));
                    }
                });

        return super.onStartCommand(intent, flags, startId);
    }

    private void play(SongInfo songInfo) {
        Log.v(AppConstants.LOG_TAG, "PlayService --> play the "
                + (newSongID + 1) + "th song");


        if (isPlaying && newSongID != currentID) {
            stop();
            currentID = newSongID;
            beginNewSong(songInfo);
        }

        if (!isPlaying) {
            currentID = newSongID;
            beginNewSong(songInfo);

        }

    }

    private void beginNewSong(SongInfo songInfo) {
        Log.v(AppConstants.LOG_TAG, "Begin New Song: id: " + songInfo.getId()
                + ", " + songInfo.getSongName());
        String path = null;

        path = songInfo.getPath() + songInfo.getSongName();

        try {

            mediaPlayer = MediaPlayer.create(this, Uri.parse("file://" + path));
            mediaPlayer.setLooping(false);
            mediaPlayer.start();
        }
        catch (Exception e) {
            System.out.println("Error in �������� block");
        }

        isPlaying = true;
        isReleased = false;

    }


    private void pause() {
        Log.v(AppConstants.LOG_TAG, "PlayService --> pause");
        if (null != mediaPlayer && !isReleased) {

            if (isPaused) {
                mediaPlayer.start();
                isPlaying = true;
                isPaused = false;
            } else {
                mediaPlayer.pause();
                isPlaying = false;
                isPaused = true;

            }
            isPlaying = !isPlaying;

        }
    }


    private void stop() {
        Log.v(AppConstants.LOG_TAG, "PlayService --> stop");
        if (null != mediaPlayer) {
            if (!isReleased) {

                mediaPlayer.stop();
                mediaPlayer.release();
                isReleased = true;
                isPlaying = false;
                isPaused = false;

            }
        }
    }

}
