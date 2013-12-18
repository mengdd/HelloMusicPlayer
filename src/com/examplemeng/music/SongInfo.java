package com.examplemeng.music;

import android.os.Parcel;
import android.os.Parcelable;

public class SongInfo implements Parcelable {

    int id = 0;
    String songName = null;
    String path = null;

    public SongInfo() {
        super();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public SongInfo(int id, String songName, String path) {
        super();
        this.id = id;
        this.songName = songName;
        this.path = path;
    }

    @Override
    public String toString() {
        return "SongInfo [id=" + id + ", songName=" + songName + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(songName);
        dest.writeString(path);

    }

    public static final Parcelable.Creator<SongInfo> CREATOR = new Parcelable.Creator<SongInfo>() {

        @Override
        public SongInfo createFromParcel(Parcel source) {
            SongInfo songInfo = new SongInfo();
            songInfo.setId(source.readInt());
            songInfo.setSongName(source.readString());
            songInfo.setPath(source.readString());
            return songInfo;
        }

        @Override
        public SongInfo[] newArray(int size) {
            return new SongInfo[size];
        }

    };
}
