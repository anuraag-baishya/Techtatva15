package chipset.techtatva.activities;


import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import chipset.potato.Potato;
import chipset.techtatva.R;
import chipset.techtatva.adapters.EventAdapterNew;
import chipset.techtatva.model.events.Category;
import chipset.techtatva.model.events.Event;
import chipset.techtatva.resources.Constants;

public class AllEvents extends AppCompatActivity {
    private ProgressDialog mProgressDialog;
    ArrayList<Category> categories;
    ArrayList<Event> events;
    EventAdapterNew mEventAdapter;
    ExpandableListView mEventListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_events);
        mEventListView = (ExpandableListView)findViewById(R.id.category_expandable_list_view);
        mEventListView.setVisibility(View.GONE);
        Log.d("JSON", "Activity started");
        mProgressDialog = new ProgressDialog(AllEvents.this);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        categories = new ArrayList<Category>();
        events = new ArrayList<Event>();
        if (Potato.potate().Utils().isInternetConnected(this)) {
            Log.d("Internet", "connected");
        } else {
            Log.d("Internet", "not connected");
        }
        prepareData();
    }

    private void prepareData() {
        mProgressDialog.show();
        JsonObjectRequest eventRequest = new JsonObjectRequest(Request.Method.GET, Constants.URL_EVENTS, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data = response.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        Event event = new Event();
                        event.setCatId(Integer.parseInt(data.getJSONObject(i).getString("cat_id")));
                        event.setDescription(data.getJSONObject(i).getString("description"));
                        event.setEvent_id(Integer.parseInt(data.getJSONObject(i).getString("event_id")));
                        event.setEvent_name(data.getJSONObject(i).getString("event_name"));
                        event.setEventMaxTeamNumber(data.getJSONObject(i).getString("event_max_team_number"));
                        events.add(event);
                    }
                    addEventsToCategories();
                    Display();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        JsonObjectRequest catRequest = new JsonObjectRequest(Request.Method.GET, Constants.URL_CATEGORIES, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data = response.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        Category category = new Category();
                        category.setCatName(data.getJSONObject(i).getString("cat_name"));
                        category.setDescription(data.getJSONObject(i).getString("description"));
                        category.setCatType(data.getJSONObject(i).getString("cat_type"));
                        category.setCatId(Integer.parseInt(data.getJSONObject(i).getString("cat_id")));
                        categories.add(category);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(AllEvents.this).add(catRequest);
        Volley.newRequestQueue(AllEvents.this).add(eventRequest);
    }

    private void addEventsToCategories() {
        for(Category category:categories){
            for (Event event:events){
                if(event.getCatId()==category.getCatId()){
                    category.getEvents().add(event);
                }
            }
        }
    }


    private void Display() {
        for (Category category:categories){
            Log.d("events","Category name : "+category.getCatName());
            for (Event event:category.getEvents()){
                Log.d("events","Event name : "+event.getEvent_name());
            }
        }
        mEventAdapter = new EventAdapterNew(this,categories);
        mEventListView.setAdapter(mEventAdapter);
        mEventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int count = mEventAdapter.getGroupCount();
                for (int c = 0; c < count; c++) {
                    mEventListView.collapseGroup(c);
                }
                mEventListView.expandGroup(i);
            }
        });
        mProgressDialog.dismiss();
        mEventListView.setVisibility(View.VISIBLE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_events, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
