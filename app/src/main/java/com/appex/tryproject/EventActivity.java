package com.appex.tryproject;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appex.tryproject.resources.DrawerItem;
import com.appex.tryproject.resources.DrawerListAdapter;
import com.appex.tryproject.resources.SlidingTabLayout;
import com.appex.tryproject.resources.ViewPagerAdapter;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import chipset.potato.Potato;

public class EventActivity extends AppCompatActivity {

    private static String TAG = EventActivity.class.getSimpleName();

    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    ViewPager pager;
    ViewPagerAdapter VPadapter;
    String msg = "clicked";
    android.support.v7.app.ActionBar actionBar;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Day1", "Day2", "Day3"};
    int Numboftabs = 3;
    boolean doubleBackToExitPressedOnce = false;
    ArrayList<DrawerItem> mNavItems = new ArrayList<DrawerItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        try {
            String name = Potato.potate().Preferences().getSharedPreferenceString(getApplicationContext(),"name");
            TextView nametv = (TextView) findViewById(R.id.userName);
            nametv.setText(name);
            ImageView im = (ImageView)findViewById(R.id.avatar);
            Picasso.with(getApplicationContext()).load(Potato.potate().Preferences().getSharedPreferenceString(getApplicationContext(),"pp")).resize(50,50).into(im);
        }catch (Exception e){
        }
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        VPadapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(VPadapter);

        mNavItems.add(new DrawerItem("Results", "Check Results", R.drawable.ic_contact));
        mNavItems.add(new DrawerItem("About", "Get to know about us", R.drawable.ic_location));
        mNavItems.add(new DrawerItem("Log Out","Log out of facebook",R.drawable.ic_action_about));

        // Populate the Navigtion Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.material_blue_grey_950);
            }
        });
        tabs.setSelectedIndicatorColors(R.color.white);
        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                if (position == 0) {
                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                    startActivity(intent);
                }
                if(position==2){
                    LoginManager.getInstance().logOut();
                    Toast.makeText(getApplicationContext(), "You have logged out", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_opened, R.string.drawer_closed);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

}