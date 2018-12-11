package com.boazz300.movietrailer.Threads;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.boazz300.movietrailer.R;

public class CounterFragment extends Fragment implements View.OnClickListener{

    public final static String FRAGMENT_TYPE = "fragment_type";
    private Button mButtonCreate;
    private Button mButtonStart;
    private Button mButtonCancel;
    private TextView mTextValue;
    private IAsyncTaskEvents callbackListener;

    public  CounterFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_threads, container, false);

        mButtonCreate = rootView.findViewById(R.id.button_AsyncCreate);
        mButtonStart = rootView.findViewById(R.id.button_AsyncStart);
        mButtonCancel = rootView.findViewById(R.id.button_AsyncCancel);
        mTextValue = rootView.findViewById(R.id.text_fullscreen_content);

        mButtonCreate.setOnClickListener(this);
        mButtonStart.setOnClickListener(this);
        mButtonCancel.setOnClickListener(this);

        Bundle bundle = this.getArguments();
        if (bundle != null){
            String fragmentText = this.getArguments().getString(FRAGMENT_TYPE).toString();
            mTextValue.setText(fragmentText);
        }
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity hostActivity = getActivity();
        if (hostActivity != null && hostActivity instanceof IAsyncTaskEvents){
            callbackListener = (IAsyncTaskEvents)hostActivity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbackListener = null;
    }

    @Override
    public void onClick(View v) {
        if (!isAdded() || callbackListener == null){
            return;
        }

        switch (v.getId()){
            case R.id.button_AsyncCreate:
                callbackListener.createAsyncTask();
                break;
            case R.id.button_AsyncStart:
                callbackListener.startAsyncTask();
                break;
            case R.id.button_AsyncCancel:
                callbackListener.cancelAsyncTask();
                break;
        }
    }

    public void updateFragmentText(String text){
        if (mTextValue != null){
            mTextValue.setText(text);
        }
    }
}
