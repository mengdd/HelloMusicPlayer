package com.examplemeng.utils;

public interface AppConstants {
    public static final String LOG_TAG = "HelloMusic";
    public static final String MUSIC_PATH = "MyMusic";

    public static final String SONG_LIST = "SongsList";
    public static final String SONG_INFO = "SongInfo";
    public static final String SONG_ID = "SongID";

    public static final String MSG = "Message";

    public class PlayMsg {
        public static final int MSG_PLAY = 1;
        public static final int MSG_PAUSE = 2;
        public static final int MSG_STOP = 3;

        public static final int MSG_PRE = 4;
        public static final int MSG_NEXT = 5;

    }

}
