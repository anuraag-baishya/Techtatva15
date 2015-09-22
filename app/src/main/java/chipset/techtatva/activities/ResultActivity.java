package chipset.techtatva.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import chipset.techtatva.R;
import chipset.techtatva.adapters.ResultAdapter;
import chipset.techtatva.resources.SwipeDownRefreshLayout;


public class ResultActivity extends AppCompatActivity {

    public static String URL_RESULTS = "http://results.techtatva.in/";
    private ProgressDialog mProgressDialog;
    private static final String TAG_CAT = "Category";
    private static final String TAG_EVENT = "Event";
    private static final String TAG_RES = "Result";
    private ListView mResultView;
    ArrayList<HashMap<String, String>> resultList;
    private SwipeDownRefreshLayout mSwipedownRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_r);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSwipedownRefreshLayout  =(SwipeDownRefreshLayout)findViewById(R.id.result_swipe_refresh);
        mSwipedownRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadResults();

            }
        });
        mProgressDialog = new ProgressDialog(ResultActivity.this);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        resultList = new ArrayList<>();
        loadResults();
        mResultView = (ListView) findViewById(R.id.Result_ListView);
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
                        HashMap<String, String> map = new HashMap<>();
                        map.put(TAG_EVENT, "Sorry");
                        map.put(TAG_CAT, "No results out yet!");
                        resultList.add(map);
                    } else {
                        for (int i = 0; i < jArr.length(); i++) {
                            JSONObject jObj = jArr.getJSONObject(i);
                            String name = jObj.getString(TAG_EVENT);
                            String categ = jObj.getString(TAG_CAT);
                            HashMap<String, String> map = new HashMap<>();
                            map.put(TAG_EVENT, name);
                            map.put(TAG_CAT, categ);
                            map.put(TAG_RES, jObj.getString(TAG_RES));
                            resultList.add(map);
                        }
                    }
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    ResultAdapter adapter = new ResultAdapter(
                            getApplicationContext(), resultList);
                    mResultView.setAdapter(adapter);
                    mSwipedownRefreshLayout.setRefreshing(false);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ResultActivity.this, "Error Connecting to Server", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(ResultActivity.this).add(jsonArrayRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right);
    }
}