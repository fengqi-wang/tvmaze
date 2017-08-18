package com.fengqi.tvmaze.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.fengqi.tvmaze.R;
import com.fengqi.tvmaze.model.Episode;
import com.fengqi.tvmaze.model.net.Api;
import com.fengqi.tvmaze.model.net.HttpEngine;
import com.fengqi.tvmaze.view.adapter.EpisodeAdapter;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAZE";

    @BindView(R.id.items) RecyclerView mEpList;
    @BindView(R.id.progress) ProgressBar mProgressBar;

    private List<Episode> mData = new ArrayList<>();
    private EpisodeAdapter mAdapter;

    private Api mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HttpEngine manager = new HttpEngine();
        mApi = manager.getRetrofit(manager.getOkHttpClient()).create(Api.class);

        JodaTimeAndroid.init(this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEpList.setLayoutManager(new LinearLayoutManager(this));
        mEpList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mEpList.setItemAnimator(new DefaultItemAnimator());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.reload);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initLoading();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initLoading();
    }

    private void refreshEpList() {
        mAdapter = new EpisodeAdapter(this, mData);
        mAdapter.setOnItemClickListener(new EpisodeAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view , int position){
                EpDetailActivity.viewEpisode(MainActivity.this, mData.get(position).url);
            }
        });
        mEpList.setAdapter(mAdapter);
    }

    private void initLoading() {
        //Snackbar.make(mEpList, "Loading Content...", Snackbar.LENGTH_LONG)
        //        .setAction("Action", null).show();

        mProgressBar.setVisibility(View.VISIBLE);

        mApi.getEpisodes().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Episode>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Episode> eps) {
                        Log.d(TAG, "Retrieved " + eps.size() + " items");
                        mData = eps;
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d(TAG, "onError!!\n" + t.getMessage());
                        Snackbar.make(mEpList, "Error Loading Content:" + t.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        mProgressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onComplete() {
                        refreshEpList();
                        mProgressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }
}
