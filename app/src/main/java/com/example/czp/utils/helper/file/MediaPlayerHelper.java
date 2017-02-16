package com.example.czp.utils.helper.file;

import android.media.AudioManager;
import android.media.MediaPlayer;

import java.util.Timer;
import java.util.TimerTask;

public class MediaPlayerHelper implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {


    private MediaPlayer mPlayer;
    private Timer timer;

    public MediaPlayerHelper(MediaPlayer mPlayer) {
        this.mPlayer = mPlayer;
        mPlayer.setOnCompletionListener(this);
        mPlayer.setOnErrorListener(this);
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.reset();
        cancelTimer();
        mOnMediaPlayListener.onComplete();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        cancelTimer();
        mOnMediaPlayListener.onError();
        return false;
    }

    public void play(String url, OnMediaPlayListener listener) {
        try {
            this.mOnMediaPlayListener = listener;
            if (mPlayer.isPlaying()) {
                mPlayer.stop();
                mPlayer.reset();
                cancelTimer();
            }
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setDataSource(url);
            listener.onPrepare();
            mPlayer.prepareAsync();
            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    progress();
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }

    }

    public void pause() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
        }
    }

    public void resume() {
        if (!mPlayer.isPlaying()) {
            mPlayer.start();
        }
    }

    public void release() {
        if (mPlayer.isPlaying()) {
            mPlayer.stop();
        }
        mPlayer.release();
        mPlayer = null;
    }

    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void progress() {
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int progress = mPlayer.getCurrentPosition() * 100 / mPlayer.getDuration();
                mOnMediaPlayListener.onPlaying(progress);
            }
        }, 20, 1000);
    }

    private OnMediaPlayListener mOnMediaPlayListener;


    public interface OnMediaPlayListener {
        void onPrepare();

        void onPlaying(int progress);

        void onComplete();

        void onError();

    }


}
