package ninja.cyplay.com.playretail_api.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.common.collect.Lists;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;
import ninja.cyplay.com.apilibrary.models.business.Tweet;
import ninja.cyplay.com.apilibrary.models.singleton.TweetCacheManager;
import ninja.cyplay.com.playretail_api.model.events.TweetItemPressed;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.playretail_api.model.singleton.SellerContext;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.adapter.TweetAdapter;
import ninja.cyplay.com.playretail_api.ui.presenter.HomeTweetPresenter;
import ninja.cyplay.com.playretail_api.ui.view.HomeTweetView;


/**
 * Created by damien on 06/05/15.
 */
public class TweetsFragmentSocketIO extends TweetsFragment implements HomeTweetView {

    @Inject
    Bus bus;

    @Inject
    APIValue apiValue;

    @Inject
    TweetCacheManager tweetCacheManager;

    @Inject
    SellerContext sellerContext;

    @Inject
    HomeTweetPresenter homeTweetPresenter;

    @InjectView(R.id.tweet_list_view)
    RecyclerView tweetListView;

    @Optional
    @InjectView(R.id.error_view)
    View ErrorView;

    @Optional
    @InjectView(R.id.empty_view)
    View EmptyView;


    @Optional
    @InjectView(R.id.progress_bar)
    View  progressBar;

    @Optional
    @InjectView(R.id.progress_bar_reconnect)
    ProgressBar progressBarReconnect;

    @Optional
    @InjectView(R.id.error_view_button)
    View ErrorViewButton;


    private TweetAdapter adapter;
    private ArrayList<Tweet> tweets = new ArrayList<Tweet>();


    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_tweets, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<Tweet>();
        adapter = new TweetAdapter(getActivity(),tweets);
    }

    @Override
    public void onStart(){
        super.onStart();
        bus.register(this);
    }

    @Override
    public void onStop(){
        super.onStop();
        bus.unregister(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeTweetPresenter.setView(this);
        homeTweetPresenter.initialize();
        initRecycler();
        initTweetList();
    }


    @Override
    public void onResume() {
        super.onResume();
        // init the tweet list
        adapter.clear();
        //add tweets from cache
        if (tweetCacheManager.getTweetCache() != null ) {
            for (Tweet tweet : Lists.reverse(tweetCacheManager.getTweetCache()))
                adapter.add(tweet);
            adapter.notifyDataSetChanged();
        }
        //connect the socket
        homeTweetPresenter.initSocket();
    }


    @Override
    public void onPause() {
        super.onPause();
        homeTweetPresenter.disconnectSocket(new HomeTweetPresenter.DisconnectTweetSocketCallback(){
            @Override
            public void onDisconnect() {
                //do nothing
            }
        });
    }

    // -------------------------------------------------------------------------------------------
    //                                       Method(s)
    // -------------------------------------------------------------------------------------------


    public void initTweetList() {
        tweetListView.setAdapter(adapter);
        //tweetListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
    }

    @Override
    public void add_tweets(List<Tweet> new_tweets) {
        for (Tweet tweet : Lists.reverse(new_tweets))
            adapter.add(tweet);
        adapter.notifyDataSetChanged();
        tweetListView.smoothScrollToPosition(0);
    }

    public void add_tweet(Tweet new_tweet){
        adapter.add(new_tweet);
        adapter.notifyDataSetChanged();
        tweetListView.smoothScrollToPosition(0);
    }

    @Override
    public void edit_tweet(Tweet tweet){
        adapter.edit(tweet);
    }

    @Override
    public boolean delete_tweet(String tweet_uid){
        boolean if_empty_list = false;
        if_empty_list = adapter.delete(tweet_uid);
        return  if_empty_list;
    }


    public void clear_tweets(){
        adapter.clear();
    }

    @Optional
    @OnClick(R.id.error_view_button)
    void reload() {
        //clear tweet list
        showLoadingReconnect();
        //disconnect old socket if exist and force a new one
        homeTweetPresenter.disconnectSocket(new HomeTweetPresenter.DisconnectTweetSocketCallback(){
            @Override
            public void onDisconnect() {
                //connect the new socket after the deconnection of the old one
                homeTweetPresenter.initSocket();
            }
        });
    }


    // -------------------------------------------------------------------------------------------
    //                                      Bus Event(s)
    // -------------------------------------------------------------------------------------------


    @Subscribe
    public void onTweetClick(TweetItemPressed tweetItemPressed) {
        if (tweetItemPressed != null && tweetItemPressed.getTweet() != null) {
            homeTweetPresenter.markTweetAsRead(tweetItemPressed.getTweet().tweet_uid);
            adapter.set_read(tweetItemPressed.getTweet().tweet_uid, tweetItemPressed.getPosition());
        }
    }

// -------------------------------------------------------------------------------------------
//                                      View Impl
// -------------------------------------------------------------------------------------------

    @Override
    public void onError() {
        hideLoading();
        hideLoadingReconnect();
        hideEmpty();
        showErrorView();
    }

    @Override
    public void displayPopUp(String message) {
        //the fragment can't show popup, it only show the reload buuton
        onError();
    }

    public void showErrorView(){

        if (ErrorView != null)
            ErrorView.setVisibility(View.VISIBLE);
        if (ErrorViewButton != null)
            ErrorViewButton.setVisibility(View.VISIBLE);
        if (progressBarReconnect != null)
            progressBarReconnect.setVisibility(View.GONE);
    }

    public void hideErrorView(){
        if (ErrorView != null)
            ErrorView.setVisibility(View.GONE);
        if (ErrorViewButton != null)
            ErrorViewButton.setVisibility(View.GONE);
        if (progressBarReconnect != null)
            progressBarReconnect.setVisibility(View.GONE);
    }

    public void hideTweetListView(){
        if(tweetListView != null)
            tweetListView.setVisibility(View.GONE);
    }

    public void showTweetListView(){
        if(tweetListView != null)
            tweetListView.setVisibility(View.VISIBLE);
    }


    public void showLoading() {
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    public void hideLoading() {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
    }

    public void showLoadingReconnect() {
        if (ErrorViewButton != null)
            ErrorViewButton.setVisibility(View.GONE);
        if (progressBarReconnect != null)
            progressBarReconnect.setVisibility(View.VISIBLE);
    }

    public void hideLoadingReconnect() {
        if (progressBarReconnect != null)
            progressBarReconnect.setVisibility(View.GONE);
        if (ErrorViewButton != null)
            ErrorViewButton.setVisibility(View.VISIBLE);
    }


    public void showEmpty() {
        if (EmptyView != null)
            EmptyView.setVisibility(View.VISIBLE);
    }

    public void hideEmpty() {
        if (EmptyView != null)
            EmptyView.setVisibility(View.GONE);
    }

    // -------------------------------------------------------------------------------------------
    //                                      private Method(s)
    // -------------------------------------------------------------------------------------------


    private void initRecycler() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        tweetListView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        tweetListView.setLayoutManager(layoutManager);

        tweetListView.setAdapter(adapter);

        //tweetListView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new ProductItemClick()));
    }

}