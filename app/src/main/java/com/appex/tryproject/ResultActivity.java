package com.appex.tryproject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.appex.tryproject.resources.JSONArrayParser;
import com.appex.tryproject.resources.ResultAdapter;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class ResultActivity extends ActionBarActivity {

    private ProgressDialog progressDialog;
    public static String URL_RESULTS = "http://results.techtatva.in/";
    private static final String TAG_CAT = "Category";
    private static final String TAG_EVENT = "Event";
    private static final String TAG_RES = "Result";
    JSONArrayParser jArrayParser = new JSONArrayParser();
    ListView resultView;

    ArrayList<HashMap<String, String>> resultList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_r);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.primary_dark));
        }
        resultList = new ArrayList<HashMap<String, String>>();
        new GetResults().execute();
        resultView=(ListView)findViewById(R.id.list);
        try {
            resultView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int arg2, long arg3) {

                    TextView resView=(TextView)arg1.findViewById(R.id.resultRes);
                    if(resView.getVisibility()==View.GONE)
                        resView.setVisibility(View.VISIBLE);
                    else
                        resView.setVisibility(View.GONE);
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GetResults extends AsyncTask<String, String, JSONArray> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ResultActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected JSONArray doInBackground(String... args) {
            JSONArray jsonArray = results(URL_RESULTS);
            return jsonArray;
        }

        @Override
        protected void onPostExecute(JSONArray jArr) {
            try {

                Log.e("jArr", String.valueOf(jArr));
                if (jArr.length() == 0) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(TAG_EVENT, "Sorry");
                    map.put(TAG_CAT, "No results out yet!");
                    resultList.add(map);
                } else {
                    for (int i = 0; i < jArr.length(); i++) {

                        JSONObject jObj = jArr.getJSONObject(i);
                        String name = jObj.getString(TAG_EVENT);
                        String categ = jObj.getString(TAG_CAT);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(TAG_EVENT, name);
                        map.put(TAG_CAT, categ);
                        map.put(TAG_RES, jObj.getString(TAG_RES));
                        resultList.add(map);
                    }
                }
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                ResultAdapter adapter = new ResultAdapter(
                        getApplicationContext(), resultList);
                resultView.setAdapter(adapter);


            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(ResultActivity.this,"Error Connecting to Server",Toast.LENGTH_SHORT).show();
            }
        }

        public JSONArray results(String URL) {
            return jArrayParser.getJSONFromUrl(URL);
        }
    }
}