package com.appex.tryproject.activites;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.appex.tryproject.R;
import com.appex.tryproject.adapters.ResultAdapter;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class ResultActivity extends AppCompatActivity {

    public static String URL_RESULTS = "http://results.techtatva.in/";
    private ProgressDialog mProgressDialog;
    private static final String TAG_CAT = "Category";
    private static final String TAG_EVENT = "Event";
    private static final String TAG_RES = "Result";
    private ListView mResultView;
    ArrayList<HashMap<String, String>> resultList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_r);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        mProgressDialog = new ProgressDialog(ResultActivity.this);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.primary_dark));
        }
        resultList = new ArrayList<HashMap<String, String>>();
        loadResults();
        mResultView=(ListView)findViewById(R.id.Result_ListView);
        try {
            mResultView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int arg2, long arg3) {

                    TextView resView = (TextView) arg1.findViewById(R.id.resultRes);
                    if (resView.getVisibility() == View.GONE)
                        resView.setVisibility(View.VISIBLE);
                    else
                        resView.setVisibility(View.GONE);
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadResults() {
        mProgressDialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL_RESULTS, (String) null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jArr) {
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
                    if(mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    ResultAdapter adapter = new ResultAdapter(
                            getApplicationContext(), resultList);
                    mResultView.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ResultActivity.this,"Error Connecting to Server",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(ResultActivity.this).add(jsonArrayRequest);
    }
}