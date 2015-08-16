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
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import com.appex.tryproject.R;
import com.appex.tryproject.activites.InstaFeedActivity;
import com.appex.tryproject.adapters.EventAdapter;
import com.appex.tryproject.model.events.CatItem;
import com.appex.tryproject.model.events.RowItem;
import com.appex.tryproject.resources.Constants;

import java.util.ArrayList;

public class DayFragment extends Fragment {
    EventAdapter mEventAdapter;
    ExpandableListView mEventListView;
    ArrayList<CatItem> mCatList;
    String mCategories[] = Constants.categories, locations[] = Constants.locations, time[] = Constants.time, date[] = Constants.date, contact[] = Constants.contact;
    String mEventNames[][] = Constants.event_names;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_insta){
            startActivity(new Intent(getActivity(),InstaFeedActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_day, container, false);
        prepareListData();
        mEventListView = (ExpandableListView) rootView.findViewById(R.id.category_expandable_list_view);
        mEventAdapter = new EventAdapter(getActivity(), mCatList);
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
        return rootView;
    }

    private void prepareListData() {

        mCatList = new ArrayList<CatItem>();
        for (int i = 0; i < mCategories.length; i++) {
            RowItem Item1 = new RowItem(mEventNames[i][0], locations[0], time[0], time[0], contact[0],"999");
            RowItem Item2 = new RowItem(mEventNames[i][1], locations[1], time[1], time[1], contact[1],"999");
            RowItem Item3 = new RowItem(mEventNames[i][2], locations[2], time[2], time[2], contact[2],"999");
            ArrayList<RowItem> Row1 = new ArrayList<RowItem>();
            Row1.add(Item1);
            Row1.add(Item2);
            Row1.add(Item3);
            CatItem catItem = new CatItem(mCategories[i], Row1);
            mCatList.add(catItem);
        }
    }
    public void collapseAll(){
        int count = 4;
        for (int i = 0; i < count; i++) {
            mEventListView.collapseGroup(i);
        }
    }
    private void expandAll() {
        int count = mEventAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            mEventListView.expandGroup(i);
        }
    }
}



