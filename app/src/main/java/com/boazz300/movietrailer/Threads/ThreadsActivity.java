package com.boazz300.movietrailer.Threads;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.boazz300.movietrailer.R;

public class ThreadsActivity extends AppCompatActivity implements IAsyncTaskEvents{

    private static FragmentManager mFragmentManager;
    private CounterFragment mThreadsFragments;
    private MySimpleAsyncTask mSimpleAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);

        mFragmentManager = getSupportFragmentManager();

        if (mThreadsFragments == null){
            mThreadsFragments = new CounterFragment();
            Bundle data = new Bundle();
            data.putString(CounterFragment.FRAGMENT_TYPE,getString(R.string.handler_exe_activity));
            mThreadsFragments.setArguments(data);
            mFragmentManager.beginTransaction().replace(R.id.fragment, mThreadsFragments).commit();
        }
    }

    @Override
    public void createAsyncTask() {
        Toast.makeText(this, getString(R.string.msg_thread_oncreate), Toast.LENGTH_SHORT).show();
        mSimpleAsyncTask = new MySimpleAsyncTask(this);
    }

    @Override
    public void startAsyncTask() {
        if ((mSimpleAsyncTask == null) || (mSimpleAsyncTask.isCancelled())){
            Toast.makeText(this, R.string.msg_should_create_task, Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, getString(R.string.msg_thread_onstart), Toast.LENGTH_SHORT).show();
            mSimpleAsyncTask.execute();
        }
    }

    @Override
    public void cancelAsyncTask() {
        if (mSimpleAsyncTask == null) {
            Toast.makeText(this, R.string.msg_should_create_task, Toast.LENGTH_SHORT).show();
        } else {
            mSimpleAsyncTask.cancel();
        }
    }

    @Override
    public void onPreExecute() {
        Toast.makeText(this, getString(R.string.msg_preexecute), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostExecute() {
        Toast.makeText(this, getString(R.string.msg_postexecute), Toast.LENGTH_SHORT).show();
        mThreadsFragments.updateFragmentText(getString(R.string.done));
        mSimpleAsyncTask = null;
    }

    @Override
    public void onProgressUpdate(Integer integer) {
        mThreadsFragments.updateFragmentText(String.valueOf(integer));
    }

    @Override
    public void onCancel() {
        Toast.makeText(this, getString(R.string.msg_thread_oncancel), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        if (mSimpleAsyncTask != null){
            mSimpleAsyncTask.cancel();
            mSimpleAsyncTask =null;
        }
        super.onDestroy();
    }
}
