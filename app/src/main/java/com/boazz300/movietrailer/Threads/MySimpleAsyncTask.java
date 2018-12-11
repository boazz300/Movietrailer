package com.boazz300.movietrailer.Threads;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

public class MySimpleAsyncTask {

    private volatile boolean mCancelled = false;
    private Thread mBackgroundThread;
    private IAsyncTaskEvents mIAsyncTaskEvents;

    public MySimpleAsyncTask(IAsyncTaskEvents iAsyncTaskEvents){
        mIAsyncTaskEvents = iAsyncTaskEvents;
    }

    protected void onPreExecute(){mIAsyncTaskEvents.onPreExecute();}

    protected void doInBackground(){
        int end = 10;
        for (int i = 0; i <= end; i++){
            if(isCancelled()){
                return;
            }
            publishProgress(i);
            SystemClock.sleep(500);
        }
    }

    protected void onPostExecute() {mIAsyncTaskEvents.onPostExecute();}

    protected void onProgressUpdate(Integer progress){
        mIAsyncTaskEvents.onProgressUpdate(progress);
    }

    public void execute(){
        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                onPreExecute();
                mBackgroundThread = new Thread("Handler_thread"){
                    @Override
                    public void run() {
                        doInBackground();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onPostExecute();
                            }
                        });
                    }
                };
                mBackgroundThread.start();
            }
        });
    }

    private void runOnUiThread(Runnable runnable){
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    protected void publishProgress(final int progress){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onProgressUpdate(progress);
            }
        });
    }

    public void cancel(){
        mCancelled = true;
        if (mBackgroundThread != null){
            mBackgroundThread.interrupt();
        }
    }

    public boolean isCancelled(){ return mCancelled;}
}
