package chipset.techtatva.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;


import chipset.techtatva.R;
import chipset.techtatva.activities.InstaFeedActivity;
import chipset.techtatva.adapters.EventAdapterNew;
import chipset.techtatva.database.DBHelper;
import chipset.techtatva.model.events.Category;
import chipset.techtatva.model.events.Event;

/**
 * Created by saketh on 16/9/15.
 */
public class FavoriteEvents extends Fragment {
    DBHelper dbHelper;
    ExpandableListView mEventListView;
    ArrayList<Category> categories;
    ArrayList<Category> allCategories;
    ArrayList<Event> events;
    EventAdapterNew mEventAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        dbHelper = new DBHelper(getActivity());
        dbHelper.createFavTable();
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite_events,container,false);
        mEventListView = (ExpandableListView) rootView.findViewById(R.id.category_expandable_list_view);
        categories = new ArrayList<Category>();
        events = new ArrayList<Event>();
        events.addAll(dbHelper.getFavEvents());
        for (Event event:events){
            Log.d("favevents","Event name : "+event.getEvent_name());
        }
        allCategories = new ArrayList<Category>();
        allCategories.addAll(dbHelper.getAllCategories());
        for(Event event:events){
            for (Category category:allCategories ){
                if(category.getCatId() == event.getCatId()){
                    category.getEvents().add(event);
                }
            }
        }
        for (Category category:allCategories){
            if(category.getEvents().size()!=0){
                categories.add(category);
            }
        }
        for (Category category:categories){
            Log.d("favevents", "Category name : " + category.getCatName());
            for (Event event:category.getEvents()){
                Log.d("favevents","Event name : "+event.getEvent_name());
            }
        }
        mEventAdapter = new EventAdapterNew(getActivity(),categories);
        mEventListView.setAdapter(mEventAdapter);
        mEventListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String name = ((TextView) v.findViewById(R.id.eventName)).getText().toString();
                for (final Event e : events) {
                    if (e.getEvent_name().toLowerCase().equals(name.toLowerCase())) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle(name);
                        builder.setMessage(e.getDescription());
                        builder.setCancelable(true);
                        builder.show();
                    }
                }
                return false;
            }
        });
        return rootView;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.favorites, menu);
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
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mEventAdapter.filterData(query);
                if (!query.isEmpty()) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_insta){
            startActivity(new Intent(getActivity(),InstaFeedActivity.class));
        }
        switch (item.getItemId()){
            case R.id.action_insta:
                startActivity(new Intent(getActivity(),InstaFeedActivity.class));
                break;
            case R.id.action_deleteFavs:
                dbHelper.deleteAllFavorites();
                mEventAdapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
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
}
