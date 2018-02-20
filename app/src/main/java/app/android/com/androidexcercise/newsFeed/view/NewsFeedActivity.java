package app.android.com.androidexcercise.newsFeed.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import app.android.com.androidexcercise.R;
import app.android.com.androidexcercise.base.BaseMVPActivity;
import app.android.com.androidexcercise.newsFeed.inject.DaggerNewsFeedComponent;
import app.android.com.androidexcercise.newsFeed.inject.NewsFeedModule;
import app.android.com.androidexcercise.newsFeed.model.NewsFeed;
import app.android.com.androidexcercise.newsFeed.model.Row;
import app.android.com.androidexcercise.newsFeed.presenter.NewsFeedPresenter;
import app.android.com.androidexcercise.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class NewsFeedActivity extends BaseMVPActivity<NewsFeedView, NewsFeedPresenter> implements NewsFeedView {

    @BindView(R.id.view_container)
    RecyclerView recyclerView;
    private Unbinder unbinder;
    private NewsFeedRecycleViewAdapter newsFeedRecycleViewAdapter;
    private NewsFeed newsFeed;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsfeed_main);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                reloadPage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @NonNull
    @Override
    public NewsFeedPresenter createPresenter() {
        return DaggerNewsFeedComponent.builder()
                .appComponent(getAppComponent())
                .newsFeedModule(new NewsFeedModule())
                .build().getNewsFeedPresenter();
    }

    @Override
    public void updateRowData(Row row) {
        if(newsFeed == null) {
            newsFeed = new NewsFeed();
        }
        newsFeed.getRows().add(row);
        this.newsFeed.getRows().remove(this.newsFeed.getRows().size()-1);
        if(newsFeedRecycleViewAdapter.getItemCount() == this.count) {
            newsFeedRecycleViewAdapter.setMoreDataAvailable(false);
        }
        newsFeedRecycleViewAdapter.notifyDataChanged();
    }

    @Override
    public void updateData(final String title, int count) {
        this.count = count;
        actionBarSetTitle(title);
        newsFeedRecycleViewAdapter = new NewsFeedRecycleViewAdapter(this, this.newsFeed, this.newsFeed.getRows().size(), Constants.TYPE_NEWSFEED);
        this.recyclerView.setAdapter(newsFeedRecycleViewAdapter);
        newsFeedRecycleViewAdapter.setLoadMoreListener(new NewsFeedRecycleViewAdapter.OnLoadMoreDataListener() {
           @Override
           public void onLoadMore() {

               recyclerView.post(new Runnable() {
                   @Override
                   public void run() {
                       int index = newsFeed.getRows().size() - 1;
                       loadMoreData(index);
                   }
               });
           }
       });
    }
    @Override
    public void updateData(final NewsFeed newsFeed) {
        if(newsFeed != null) {
            this.newsFeed = newsFeed;
            for(Row row : this.newsFeed.getRows()) {
                row.setType(Constants.TYPE_NEWSFEED);
            }
            actionBarSetTitle(newsFeed.getTitle());
            newsFeedRecycleViewAdapter = new NewsFeedRecycleViewAdapter(this, this.newsFeed, this.newsFeed.getRows().size(), Constants.TYPE_NEWSFEED);
            this.recyclerView.setAdapter(newsFeedRecycleViewAdapter);
            newsFeedRecycleViewAdapter.setLoadMoreListener(new NewsFeedRecycleViewAdapter.OnLoadMoreDataListener() {
                @Override
                public void onLoadMore() {

                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            int index = newsFeed.getRows().size() - 1;
                            // loadMoreData(index);
                            newsFeedRecycleViewAdapter.notifyDataSetChanged();
                        }
                    });
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().getNewsFeedData();
        initializeUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initializeUI() {
        recyclerView = findViewById(R.id.view_container);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setAutoMeasureEnabled(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void actionBarSetTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    private void loadMoreData(int index){

        //add loading progress view
        this.newsFeed.getRows().get(index).setType(Constants.TYPE_LOADING);
        newsFeedRecycleViewAdapter.notifyItemInserted(this.newsFeed.getRows().size()-1);
        getPresenter().getRowData(index);
    }

    private void reloadPage() {
        recyclerView.invalidate();
        getPresenter().getNewsFeedData();
        newsFeedRecycleViewAdapter.notifyDataSetChanged();
    }
}
