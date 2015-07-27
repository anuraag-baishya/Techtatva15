package com.appex.tryproject.activites;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appex.tryproject.R;
import com.appex.tryproject.adapters.ViewPagerAdapter;
import com.appex.tryproject.widgets.SlidingTabLayout;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import chipset.potato.Potato;

public class EventActivity extends AppCompatActivity {

    private static String sTAG = EventActivity.class.getSimpleName();


    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private android.support.v7.app.ActionBar mActionBar;
    private SlidingTabLayout mSlidingTabLayout;
    public CharSequence Titles[] = {"Day1", "Day2", "Day3", "Day 4"};
    public int Numboftabs = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        mNavigationView=(NavigationView)findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if(menuItem.isChecked())
                    menuItem.setChecked(false);
                else
                    menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                switch (menuItem.getItemId()){
                    case R.id.result:
                        startActivity(new Intent(getApplicationContext(),ResultActivity.class));
                        return true;
                    case R.id.logout:
                        LoginManager.getInstance().logOut();
                        Toast.makeText(getApplicationContext(), "You have logged out", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.developers:
                        Toast.makeText(getApplicationContext(),"Developers",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.contact:
                        Toast.makeText(getApplicationContext(),"Contact Us",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        try {
            String name = Potato.potate().Preferences().getSharedPreferenceString(getApplicationContext(),"name");
            TextView nametv = (TextView) findViewById(R.id.username);
            nametv.setText(name);
            ImageView im = (ImageView)findViewById(R.id.profile_image);
            Picasso.with(getApplicationContext()).load(Potato.potate().Preferences().getSharedPreferenceString(getApplicationContext(),"pp")).resize(50,50).into(im);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_opened, R.string.drawer_closed);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);
        mViewPager = (ViewPager) findViewById(R.id.ViewPager);
        mViewPager.setAdapter(mViewPagerAdapter);
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.SlidingTabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.white);
            }
        });
        mSlidingTabLayout.setSelectedIndicatorColors(R.color.white);
        // Setting the ViewPager For the SlidingTabsLayout
        mSlidingTabLayout.setViewPager(mViewPager);
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