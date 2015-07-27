package com.appex.tryproject.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.appex.tryproject.R;
import com.appex.tryproject.activites.InstaFeedActivity;
import com.appex.tryproject.adapters.EventAdapter;
import com.appex.tryproject.model.events.CatItem;
import com.appex.tryproject.model.events.RowItem;
import com.appex.tryproject.resources.Constants;

import java.util.ArrayList;

public class Day3Fragment extends Fragment {
    EventAdapter eventAdapter;
    String logTAG = "";
    ExpandableListView eventListView;
    ArrayList<CatItem> catList;
    String categories[] = Constants.categories, locations[] = Constants.locations, time[] = Constants.time, date[] = Constants.date, contact[] = Constants.contact;
    String names[][] = Constants.event_names;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== R.id.action_insta){
            startActivity(new Intent(getActivity(),InstaFeedActivity.class));
        }
        return super.onOptionsItemSelected(item);
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
                eventAdapter.filterData(query);
                if (!query.isEmpty()) {
                    expandAll();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                eventAdapter.filterData(query);
                if (!query.isEmpty()) {
                    expandAll();
                }
                return false;
            }
        });
        search.setOnCloseListener(new android.support.v7.widget.SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                eventAdapter.filterData("");
                return false;
            }
        });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_day3, container, false);
        eventListView = (ExpandableListView) rootView.findViewById(R.id.catListDay3);
        prepareListData();
        eventAdapter = new EventAdapter(getActivity(), catList);
        eventListView.setAdapter(eventAdapter);
        return rootView;
    }

    private void prepareListData() {

        catList = new ArrayList<CatItem>();
        for (int i = 0; i < categories.length; i++) {
            RowItem Item1 = new RowItem(names[i][0], locations[0], time[0], time[0], contact[0],"999");
            RowItem Item2 = new RowItem(names[i][1], locations[1], time[1], time[1], contact[1],"999");
            RowItem Item3 = new RowItem(names[i][2], locations[2], time[2], time[2], contact[2],"999");
            ArrayList<RowItem> Row1 = new ArrayList<RowItem>();
            Row1.add(Item1);
            Row1.add(Item2);
            Row1.add(Item3);
            CatItem catItem = new CatItem(categories[i], Row1);
            catList.add(catItem);
        }
    }

    private void expandAll() {
        int count = eventAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            eventListView.expandGroup(i);
        }
    }

}