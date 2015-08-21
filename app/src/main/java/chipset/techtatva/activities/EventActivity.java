package chipset.techtatva.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import chipset.techtatva.R;
import chipset.techtatva.adapters.DayViewPagerAdapter;
import chipset.techtatva.fragments.DayFragment;


public class EventActivity extends AppCompatActivity {

    ViewPager viewPager;
    DayFragment day1, day2, day3, day4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        day1 = new DayFragment();
        day2 = new DayFragment();
        day3 = new DayFragment();
        day4 = new DayFragment();
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
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
            case R.id.action_allcats:
                startActivity(new Intent(getApplicationContext(),AllEvents.class));
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void setupViewPager(final ViewPager viewPager) {
        final DayViewPagerAdapter mDayViewPagerAdapter = new DayViewPagerAdapter(getSupportFragmentManager());
        mDayViewPagerAdapter.addFragment(day1, "DAY 1");
        mDayViewPagerAdapter.addFragment(day2, "DAY 2");
        mDayViewPagerAdapter.addFragment(day3, "DAY 3");
        mDayViewPagerAdapter.addFragment(day4, "DAY 4");
        viewPager.setAdapter(mDayViewPagerAdapter);
    }
}
