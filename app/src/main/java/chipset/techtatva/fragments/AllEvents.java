package chipset.techtatva.fragments;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

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
import chipset.techtatva.database.DBHelper;
import chipset.techtatva.model.events.Category;
import chipset.techtatva.model.events.Event;
import chipset.techtatva.resources.Constants;

/**
 * Created by saketh on 26/8/15.
 */
public class AllEvents extends android.support.v4.app.Fragment {
    private ProgressDialog mProgressDialog;
    ArrayList<Category> categories;
    ArrayList<Event> events;
    EventAdapterNew mEventAdapter;
    ExpandableListView mEventListView;
    DBHelper dbHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        dbHelper = new DBHelper(getActivity());
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View[] rootView = {inflater.inflate(R.layout.fragment_all_events, container, false)};
        mEventListView = (ExpandableListView) rootView[0].findViewById(R.id.category_expandable_list_view);
        mEventListView.setVisibility(View.GONE);
        Log.d("JSON", "Activity started");
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        categories = new ArrayList<Category>();
        events = new ArrayList<Event>();
        categories.addAll(dbHelper.getAllCategories());
        events.addAll(dbHelper.getAllEvents());
        addEventsToCategories();
        Display();
        if (Potato.potate().Utils().isInternetConnected(getActivity())) {
            Log.d("Internet", "connected");
            prepareData();
        } else {
            if(categories.isEmpty()){
                rootView[0] = inflater.inflate(R.layout.no_connection_layout,container,false);
                Button retryButton = (Button) rootView[0].findViewById(R.id.retry_button);
                retryButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Potato.potate().Utils().isInternetConnected(getActivity())){
                            rootView[0] = inflater.inflate(R.layout.fragment_all_events, container, false);
                            prepareData();
                        }
                    }
                });
            }
            Log.d("Internet", "not connected");
        }
        return rootView[0];
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.day1, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        android.support.v7.widget.SearchView search = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchItem);
        // Configure the search info and add any event listeners
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        search.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        search.setIconifiedByDefault(false);
        search.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mEventAdapter.filterData(query);
                if (!query.isEmpty()) {
                    expandAll();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mEventAdapter.filterData(query);
                if (!query.isEmpty()) {
                    expandAll();
                }
                return false;
            }
        });
        search.setOnCloseListener(new android.support.v7.widget.SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mEventAdapter.filterData("");
                return false;
            }
        });
        search.setSubmitButtonEnabled(false);
    }
    private void prepareData() {
        mProgressDialog.show();
        JsonObjectRequest eventRequest = new JsonObjectRequest(Request.Method.GET, Constants.URL_EVENTS, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                events.clear();
                Log.d("Events",response.toString());
                try {
                    JSONArray data = response.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        Event event = new Event();
                        event.setCatId(Integer.parseInt(data.getJSONObject(i).getString("categoryID")));
                        event.setDescription(data.getJSONObject(i).getString("description"));
                        event.setEvent_id(Integer.parseInt(data.getJSONObject(i).getString("eventID")));
                        event.setEvent_name(data.getJSONObject(i).getString("eventName"));
                        event.setEventMaxTeamNumber(Integer.parseInt(data.getJSONObject(i).getString("maxTeamSize")));
                        dbHelper.insertEvent(event);
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
                categories.clear();
                dbHelper.dropTables();
                Log.d("Categories",response.toString());
                try {
                    JSONArray data = response.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        Category category = new Category();
                        category.setCatName(data.getJSONObject(i).getString("categoryName"));
                        category.setDescription(data.getJSONObject(i).getString("description"));
                        category.setCatType(data.getJSONObject(i).getString("categoryType"));
                        category.setCatId(Integer.parseInt(data.getJSONObject(i).getString("categoryID")));
                        dbHelper.insertCategory(category);
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
        Volley.newRequestQueue(getActivity()).add(catRequest);
        Volley.newRequestQueue(getActivity()).add(eventRequest);
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
        mEventAdapter = new EventAdapterNew(getActivity(),categories);
        mEventListView.setAdapter(mEventAdapter);
        mEventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(),String.valueOf(i),Toast.LENGTH_SHORT).show();
            }
        });
        mProgressDialog.dismiss();
        mEventListView.setVisibility(View.VISIBLE);
    }
    private void expandAll() {
        int count = mEventAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            mEventListView.expandGroup(i);
        }
    }
}
