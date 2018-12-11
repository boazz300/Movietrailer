package com.boazz300.movietrailer.Threads;

interface IAsyncTaskEvents {

    void createAsyncTask();
    void startAsyncTask();
    void cancelAsyncTask();

    void onPreExecute();
    void onPostExecute();
    void onProgressUpdate(Integer integer);
    void onCancel();
}
