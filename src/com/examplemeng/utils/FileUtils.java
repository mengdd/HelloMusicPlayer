package com.examplemeng.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;
import android.util.Log;

import com.examplemeng.music.SongInfo;

public class FileUtils {
    private String SDCardRoot;

    public FileUtils() {

        SDCardRoot = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator;

        Log.v(AppConstants.LOG_TAG, "Get SDCardRoot: " + SDCardRoot);
    }

    public List<SongInfo> getSongs(String path) {
        Log.v(AppConstants.LOG_TAG, "Get Songs in" + SDCardRoot + path);

        List<SongInfo> songInfos = new ArrayList<SongInfo>();

        File file = new File(SDCardRoot + path);
        File[] files = file.listFiles();

        if (null != files) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().endsWith("mp3")
                        || files[i].getName().endsWith("wma")) {
                    SongInfo songInfo = new SongInfo();

                    songInfo.setSongName(files[i].getName());
                    songInfo.setId(i);
                    songInfo.setPath(SDCardRoot + path + File.separator);

                    songInfos.add(songInfo);
                }
            }
        }
        Log.v(AppConstants.LOG_TAG, "Get " + songInfos.size() + " songs!");

        return songInfos;

    }
}
