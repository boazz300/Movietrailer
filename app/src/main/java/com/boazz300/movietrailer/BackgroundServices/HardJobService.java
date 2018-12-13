package com.boazz300.movietrailer.BackgroundServices;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.boazz300.movietrailer.R;

import java.time.Instant;

class HardJobService extends Service {

    private static final String TAG = "HardJobService";

    private ServiceHandler mServiceHandler;
    private Looper mServiceLooper;
    private boolean isDestroyed=false;

    public HardJobService() {
    }

    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread(TAG, Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isDestroyed = false;
        showToast(getString(R.string.starting_hardjob_service_msg));

        Message message = mServiceHandler.obtainMessage();
        message.arg1 = startId;
        return START_STICKY;
    }

    protected void showToast(final String msg) {
        Intent intent = new Intent(BGServiceActivity.BackgroundProgressReceiver.PROGRESS_UPDATE_ACTION);
        intent.putExtra(BGServiceActivity.BackgroundProgressReceiver.SERVICE_STATUS, msg);
        sendBroadcast(intent);
    }



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        isDestroyed = true;
        super.onDestroy();
    }

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper){ super(looper);}

        @Override
        public void handleMessage(Message msg) {
            for (int i=0; i <=100 && !isDestroyed; i++){
                SystemClock.sleep(100);
                Intent intent = new Intent(BGServiceActivity.BackgroundProgressReceiver.PROGRESS_UPDATE_ACTION);
                intent.putExtra(BGServiceActivity.BackgroundProgressReceiver.PROGRESS_VALUE_KEY,i);
                sendBroadcast(intent);
            }
            showToast(getString(R.string.finishing_hardjob_service_msg,msg.arg1));
            stopSelf(msg.arg1);
        }
    }
}
