package com.boazz300.movietrailer.Threads;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.boazz300.movietrailer.R;

public class AsyncTaskActivity extends AppCompatActivity implements IAsyncTaskEvents{

    private static FragmentManager mFragmentManager;
    private CounterFragment mThreadsFragments;
    private CounterAsyncTask mAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        mFragmentManager = getSupportFragmentManager();//Get Fragment Manager
        if (mThreadsFragments == null) {
            mThreadsFragments = new CounterFragment();//Get Fragment Instance
            Bundle data = new Bundle();//Use bundle to pass data
            data.putString(CounterFragment.FRAGMENT_TYPE, getString(R.string.async_task_activity));//put string, int, etc in bundle with a key value
            mThreadsFragments.setArguments(data);//Finally set argument bundle to fragment
            mFragmentManager.beginTransaction().replace(R.id.fragment, mThreadsFragments).commit();//now replace the argument fragment
        }
    }

    @Override
    public void createAsyncTask() {
        Toast.makeText(this, getString(R.string.msg_oncreate), Toast.LENGTH_SHORT).show();
        mAsyncTask = new CounterAsyncTask(this);
    }

    public void startAsyncTask() {
        if ((mAsyncTask == null) || (mAsyncTask.isCancelled())) {
            Toast.makeText(this, R.string.msg_should_create_task, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.msg_onstart), Toast.LENGTH_SHORT).show();
            mAsyncTask.execute(10);
        }
    }

    public void cancelAsyncTask() {
        if (mAsyncTask == null) {
            Toast.makeText(this, R.string.msg_should_create_task, Toast.LENGTH_SHORT).show();
        } else {
            mAsyncTask.cancel(true);
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
        mAsyncTask = null;
    }

    @Override
    public void onProgressUpdate(Integer integer) {
        mThreadsFragments.updateFragmentText(String.valueOf(integer));
    }

    @Override
    public void onCancel() {
        Toast.makeText(this, getString(R.string.msg_oncancel), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        if (mAsyncTask != null) {
            mAsyncTask.cancel(false);
            mAsyncTask = null;
        }
        super.onDestroy();
    }
}
