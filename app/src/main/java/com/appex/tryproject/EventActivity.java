package com.appex.tryproject;

import android.content.res.Configuration;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.appex.tryproject.Resources.Constants;
import com.appex.tryproject.Resources.DrawerItem;
import com.appex.tryproject.Resources.EventAdapter;
import com.appex.tryproject.Resources.RowItem;
import com.appex.tryproject.Resources.DrawerListAdapter;

import java.util.ArrayList;
import java.util.List;


public class EventActivity extends ActionBarActivity{

    String events[]= Constants.events,locations[]=Constants.locations,time[]=Constants.time,date[]=Constants.date,contact[]=Constants.contact;
    ListView eventView, mDrawerList;
    List<RowItem> eventItems;
    ActionBarDrawerToggle mDrawerToggle;
    String msg="clicked";
    android.support.v7.app.ActionBar actionBar;
    DrawerLayout mDrawerLayout;
    ArrayList<DrawerItem> mDrawerItems = new ArrayList<DrawerItem>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.toolbar_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_event);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mDrawerItems.add(new DrawerItem("Home", "Meetup destination", R.drawable.ic_date));
        mDrawerItems.add(new DrawerItem("Preferences", "Change your preferences", R.drawable.ic_location));
        mDrawerItems.add(new DrawerItem("About", "Get to know about us", R.drawable.ic_contact));
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, R.string.drawer_opened,
                R.string.drawer_closed);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter dradapter = new DrawerListAdapter(this, mDrawerItems);
        mDrawerList.setAdapter(dradapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
            }
        });
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        eventItems = new ArrayList<RowItem>();
        for (int i = 0; i < events.length; i++) {
            RowItem item = new RowItem(events[i], locations[i], time[i], date[i], contact[i]);
            eventItems.add(item);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        eventView = (ListView) findViewById(R.id.eventList);
        EventAdapter adapter = new EventAdapter(this, eventItems);
        eventView.setAdapter(adapter);
        eventView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Item " + (position + 1) + ": " + eventItems.get(position),
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
        });
        TextView UsernameText=(TextView)findViewById(R.id.userName);
        UsernameText.setText(RegisterActivity.user_name);

    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
