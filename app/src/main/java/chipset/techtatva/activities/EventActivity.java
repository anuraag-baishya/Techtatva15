package chipset.techtatva.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.eftimoff.androidplayer.Player;
import com.eftimoff.androidplayer.actions.property.PropertyAction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import chipset.potato.Potato;
import chipset.techtatva.R;
import chipset.techtatva.adapters.DayViewPagerAdapter;
import chipset.techtatva.adapters.DrawerAdapter;
import chipset.techtatva.database.DBHelper;
import chipset.techtatva.fragments.DayFragment;
import chipset.techtatva.model.events.Category;
import chipset.techtatva.model.events.DrawerItem;
import chipset.techtatva.resources.Constants;


public class EventActivity extends AppCompatActivity {

    ViewPager viewPager;
    DayFragment day1, day2, day3, day4;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private DBHelper dbHelper;
    ArrayList<DrawerItem> drawerList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        ListView drawerListView=(ListView)findViewById(R.id.drawer_list_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHelper= new DBHelper(this);
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
        Potato.potate().Preferences().putSharedPreference(EventActivity.this,"cat",category[0]);
        setupDrawer();
        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name= ((TextView)view.findViewById(R.id.category_name_text_view)).getText().toString();
                Potato.potate().Preferences().putSharedPreference(EventActivity.this,"cat",name);
                try {
                    day1.DataChange();
                    day2.DataChange();
                    day3.DataChange();
                    day4.DataChange();
                }catch (Exception e){

                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        drawerListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String name= ((TextView)view.findViewById(R.id.category_name_text_view)).getText().toString();
                for(Category cat : dbHelper.getAllCategories() ){
                    if(cat.getCatName().toLowerCase().equals(name.toLowerCase())){
                        AlertDialog.Builder builder = new AlertDialog.Builder(EventActivity.this);
                        builder.setTitle(name);
                        builder.setCancelable(true);
                        builder.setMessage(cat.getDescription());
                        builder.setIcon(R.drawable.ic_action_about);
                        builder.show();
                    }
                }
                return false;
            }
        });
        drawerListView.setAdapter(new DrawerAdapter(EventActivity.this, drawerList));
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
    private void setupDrawer(){
       ArrayList<Category> categories = dbHelper.getAllCategories();
        drawerList.add(new DrawerItem("All events",R.drawable.featured));
        for (Category category: categories)
            prepareDrawer(category.getCatName());
    }
    private void prepareDrawer(String categoryName){
        switch (categoryName){
            case "Acumen":
                drawerList.add(new DrawerItem(categoryName,R.drawable.acumen));
                break;
            case "Airborne":
                drawerList.add(new DrawerItem(categoryName, R.drawable.airborne));
                break;
            case "Alacrity":
                drawerList.add(new DrawerItem(categoryName, R.drawable.alacrity));
                break;
            case "Bizzmaestro":
                drawerList.add(new DrawerItem(categoryName, R.drawable.bizzmaestro));
                break;
            case "Cheminova":
                drawerList.add(new DrawerItem(categoryName, R.drawable.cheminova));
                break;
            case "Constructure":
                drawerList.add(new DrawerItem(categoryName, R.drawable.constructure));
                break;
            case "Cryptoss":
                drawerList.add(new DrawerItem(categoryName, R.drawable.cryptoss));
                break;
            case "Electrific":
                drawerList.add(new DrawerItem(categoryName, R.drawable.electrific));
                break;
            case "Energia":
                drawerList.add(new DrawerItem(categoryName, R.drawable.energia));
                break;
            case "Epsilon":
                drawerList.add(new DrawerItem(categoryName, R.drawable.epsilon));
                break;
            case "Kraftwagen":
                drawerList.add(new DrawerItem(categoryName, R.drawable.kraftwagen));
                break;
            case "Mechatron":
                drawerList.add(new DrawerItem(categoryName, R.drawable.mechatron));
                break;
            case "Mechanize":
                drawerList.add(new DrawerItem(categoryName, R.drawable.mechatron));
                break;
            case "Robotrek":
                drawerList.add(new DrawerItem(categoryName, R.drawable.robotrek));
                break;
            case "Turing":
                drawerList.add(new DrawerItem(categoryName, R.drawable.turing));
                break;
            default:
                drawerList.add(new DrawerItem(categoryName, R.drawable.featured));
                break;
        }
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
            case R.id.action_favourites:
                startActivity(new Intent(getApplicationContext(),FavouritesActivity.class));
                break;
            case R.id.action_about:
                startActivity(new Intent(getApplicationContext(),AboutUsActivity.class));
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
        viewPager.setAdapter(mDayViewPagerAdapter);
    }
}
