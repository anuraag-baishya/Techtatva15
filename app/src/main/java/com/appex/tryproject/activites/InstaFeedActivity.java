package com.appex.tryproject.activites;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.appex.tryproject.R;
import com.appex.tryproject.adapters.InstaFeedListAdapter;
import com.appex.tryproject.model.instagram.InstaFeed;
import com.appex.tryproject.network.APIClient;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class InstaFeedActivity extends AppCompatActivity{

    private static final String TAG=InstaFeedActivity.class.getSimpleName();
    private ListView mInstaFeedListView;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().setElevation(0f);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.primary_dark));
        }
        loadInstaFeed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            startActivity(new Intent(InstaFeedActivity.this,EventActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadInstaFeed() {
        setContentView(R.layout.activity_insta_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_r);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mInstaFeedListView = (ListView) findViewById(R.id.insta_feed_list_view);
        mProgressBar = (ProgressBar) findViewById(R.id.insta_feed_progress_bar);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.insta_feed_swipe_refresh);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.primary, R.color.primary_dark);

        APIClient.getInstagram().getFeed(new Callback<InstaFeed>() {
            @Override
            public void success(InstaFeed instaFeed, Response response) {
                mInstaFeedListView.setAdapter(new InstaFeedListAdapter(getApplicationContext(), instaFeed));
                mInstaFeedListView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void failure(RetrofitError error) {
                mProgressBar.setVisibility(View.GONE);
                setContentView(R.layout.no_connection_layout);
                Button retryButton = (Button) findViewById(R.id.retry_button);
                retryButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadInstaFeed();
                    }
                });
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                APIClient.getInstagram().getFeed(new Callback<InstaFeed>() {
                    @Override
                    public void success(InstaFeed instaFeed, Response response) {
                        InstaFeedListAdapter adapter = new InstaFeedListAdapter(getApplicationContext(), instaFeed);
                        adapter.notifyDataSetChanged();
                        mInstaFeedListView.setAdapter(adapter);
                        mInstaFeedListView.setVisibility(View.VISIBLE);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        mProgressBar.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setRefreshing(false);
                        setContentView(R.layout.no_connection_layout);
                        Button retryButton = (Button) findViewById(R.id.retry_button);
                        retryButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loadInstaFeed();
                            }
                        });
                    }
                });
            }
        });

        mInstaFeedListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0 && visibleItemCount > 0 && mInstaFeedListView.getChildAt(0).getTop() >= 0)
                    mSwipeRefreshLayout.setEnabled(true);
                else mSwipeRefreshLayout.setEnabled(false);
            }
        });
    }
}
