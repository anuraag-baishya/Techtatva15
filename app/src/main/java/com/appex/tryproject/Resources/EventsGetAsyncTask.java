package com.appex.tryproject.resources;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;

import static com.appex.tryproject.resources.Constants.URL_EVENTS;
import static com.appex.tryproject.resources.Constants.PREF_JSON;
public class EventsGetAsyncTask extends AsyncTask<String,String,JSONArray> {
    Functions functions=new Functions();
    Context context;
    Activity activity;
    public EventsGetAsyncTask(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONArray doInBackground(String... args) {
        JSONArray jObj = functions.events(URL_EVENTS);
        return jObj;
    }

    @Override
    protected void onPostExecute(JSONArray jObj) {
        try {
            String data = jObj.toString();
            functions.putSharedPrefrences(context, PREF_JSON, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

