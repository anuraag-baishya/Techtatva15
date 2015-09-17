package chipset.techtatva.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.eftimoff.androidplayer.Player;
import com.eftimoff.androidplayer.actions.property.PropertyAction;

import java.util.ArrayList;

import chipset.techtatva.R;
import chipset.techtatva.adapters.DayViewPagerAdapter;
import chipset.techtatva.adapters.DrawerAdapter;
import chipset.techtatva.fragments.AllEvents;
import chipset.techtatva.fragments.DayFragment;
import chipset.techtatva.fragments.FavoriteEvents;
import chipset.techtatva.model.events.DrawerItem;
import chipset.techtatva.model.instagram.InstaFeed;


public class EventActivity extends AppCompatActivity {

    ViewPager viewPager;
    DayFragment day1, day2, day3, day4;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<DrawerItem> drawerList=new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        ListView drawerListView=(ListView)findViewById(R.id.drawer_list_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        day1 = new DayFragment();
        day2 = new DayFragment();
        day3 = new DayFragment();
        day4 = new DayFragment();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_closed);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        animate(toolbar, tabLayout);
        final String[] category = getResources().getStringArray(R.array.category);
        drawerList.add(new DrawerItem(category[0],R.drawable.featured));
        drawerList.add(new DrawerItem(category[1], R.drawable.featured));
        drawerList.add(new DrawerItem(category[2], R.drawable.acumen));
        drawerList.add(new DrawerItem(category[3], R.drawable.airborne));
        drawerList.add(new DrawerItem(category[4], R.drawable.alacrity));
        drawerList.add(new DrawerItem(category[5], R.drawable.bizzmaestro));
        drawerList.add(new DrawerItem(category[6], R.drawable.cheminova));
        drawerList.add(new DrawerItem(category[7], R.drawable.constructure));
        drawerList.add(new DrawerItem(category[8], R.drawable.cryptoss));
        drawerList.add(new DrawerItem(category[9], R.drawable.electrific));
        drawerList.add(new DrawerItem(category[10], R.drawable.energia));
        drawerList.add(new DrawerItem(category[11], R.drawable.epsilon));
        drawerList.add(new DrawerItem(category[12], R.drawable.kraftwagen));
        drawerList.add(new DrawerItem(category[13], R.drawable.mechatron));
        drawerList.add(new DrawerItem(category[14], R.drawable.mechatron));
        drawerList.add(new DrawerItem(category[15], R.drawable.robotrek));
        drawerList.add(new DrawerItem(category[16], R.drawable.turing));
        drawerList.add(new DrawerItem(category[17], R.drawable.featured));

        drawerListView.setAdapter(new DrawerAdapter(EventActivity.this, drawerList));
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    protected void animate(View toolbar, View slidingTabLayout){
        final PropertyAction headerAction = PropertyAction.newPropertyAction(toolbar).interpolator(new DecelerateInterpolator()).translationY(-200).duration(250).alpha(0.4f).build();
        final PropertyAction tabAction = PropertyAction.newPropertyAction(slidingTabLayout).translationY(200).duration(250).alpha(0f).build();
        final PropertyAction bottomAction = PropertyAction.newPropertyAction(viewPager).translationY(500).duration(250).alpha(0f).build();
        Player.init().animate(headerAction).animate(tabAction).animate(bottomAction).play();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_results:
                startActivity(new Intent(getApplicationContext(), ResultActivity.class));
                break;
            case R.id.action_developers:
                Toast.makeText(getApplicationContext(), "Developers", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_contact:
                Toast.makeText(getApplicationContext(), "Contact Us", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(menuItem);
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

    private void setupViewPager(final ViewPager viewPager) {
        final DayViewPagerAdapter mDayViewPagerAdapter = new DayViewPagerAdapter(getSupportFragmentManager());
        mDayViewPagerAdapter.addFragment(day1, "DAY 1");
        mDayViewPagerAdapter.addFragment(day2, "DAY 2");
        mDayViewPagerAdapter.addFragment(day3, "DAY 3");
        mDayViewPagerAdapter.addFragment(day4, "DAY 4");
        mDayViewPagerAdapter.addFragment(new AllEvents(),"All events");
        mDayViewPagerAdapter.addFragment(new FavoriteEvents(),"Favourite events");
        viewPager.setAdapter(mDayViewPagerAdapter);
    }
}
