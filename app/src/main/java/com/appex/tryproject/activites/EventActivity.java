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
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import chipset.potato.Potato;

import android.support.design.widget.TabLayout;
import com.appex.tryproject.fragments.DayFragment;


public class EventActivity extends AppCompatActivity {
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager(viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        mNavigationView=(NavigationView)findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.isChecked())
                    menuItem.setChecked(false);
                else
                    menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.result:
                        startActivity(new Intent(getApplicationContext(),ResultActivity.class));
                        return true;
                    case R.id.logout:
                        LoginManager.getInstance().logOut();
                        Toast.makeText(getApplicationContext(), "You have logged out", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.developers:
                        Toast.makeText(getApplicationContext(), "Developers", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.contact:
                        Toast.makeText(getApplicationContext(), "Contact Us", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        try {
            String name = Potato.potate().Preferences().getSharedPreferenceString(getApplicationContext(),"name");
            TextView UsernameTextView = (TextView) findViewById(R.id.username);
            UsernameTextView.setText(name);
            ImageView ProfileImageView = (ImageView)findViewById(R.id.profile_image);
            Picasso.with(getApplicationContext()).load(Potato.potate().Preferences().getSharedPreferenceString(getApplicationContext(),"pp")).resize(50,50).into(ProfileImageView);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
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
    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter mViewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addFragment(new DayFragment(),"DAY 1");
        mViewPagerAdapter.addFragment(new DayFragment(),"DAY 2");
        mViewPagerAdapter.addFragment(new DayFragment(),"DAY 3");
        mViewPagerAdapter.addFragment(new DayFragment(), "DAY 4");
        viewPager.setAdapter(mViewPagerAdapter);
    }
}
