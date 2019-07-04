// IMediaAidlInterface.aidl
package com.se.music;

// Declare any non-default types here with import statements

interface IMediaAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    boolean isPlaying();
    boolean isTrackLocal();
    void stop();
    void pause();
    void play();
    void nextPlay();
    void previous();
    void openFile(String path);
    void open(in Map infos, in long [] list, int position);
    void setQueuePosition(int index);
    void setRepeatMode(int repeatmode);
    void enqueue(in long [] list,in Map infos, int action);
    int secondPosition();
    int getQueueSize();
    int getQueuePosition();
    int removeTrack(long id);
    int getRepeatMode();
    long duration();
    long getAlbumId();
    long [] getQueue();
    long getAudioId();
    long position();
    long seek(long pos);
    String getArtistName();
    String getTrackName();
    String getAlbumName();
    String getAlbumPath();
    String getAlbumPic();
    String[] getAlbumPathtAll();
    String getPath();
    Map getPlaylistInfo();
}
