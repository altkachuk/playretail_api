package ninja.cyplay.com.playretail_api.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;
import ninja.cyplay.com.apilibrary.models.business.Tweet;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.apilibrary.utils.LogUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.adapter.TweetAdapter;


/**
 * Created by damien on 30/04/15.
 */
public class HomeTweetsFragmentFirebase extends HomeTweetsFragment {

    @Inject
    APIValue apiValue;

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
    ProgressBar progressBar;

    private TweetAdapter adapter;
    private ChildEventListener listener = null;
    private ArrayList<Tweet> tweets = new ArrayList<Tweet>();

    private Firebase ref;
    private Query queryRef;
    private int displayLastXDays = 7; // one week old max

    private int nb_tweets =0; // to store total number of the tweet

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
        if (apiValue != null && apiValue.getPRConfig() != null && apiValue.getPRConfig().getPRShopConfig() != null) {
            String store_id = apiValue.getPRConfig().getPRShopConfig().store_id;
            ref = new Firebase(Constants.FIREBASE_URL + Constants.CLIENT_ID + "/" + store_id + "/LiveTweet");
            queryRef = ref.orderByChild("Interval").limitToLast(5); // get the # most recent tweets
        }
        tweets = new ArrayList<Tweet>();
        adapter = new TweetAdapter(getActivity(),tweets);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //showLoading();
        showEmpty();
        initRecycler();
        initTweetList();
    }

    @Override
    public void onResume() {
        super.onResume();
        initFirebase();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (listener != null)
            ref.removeEventListener(listener);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    void initTweetList() {

       // adapter = new TweetAdapter(getActivity(), tweets);
        tweetListView.setAdapter(adapter);
       // tweetListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
    }

    void initFirebase() {
        // Create a handler to handle the result of the authentication
        Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                addListener();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // Authenticated failed with error firebaseError
                Log.e(LogUtils.generateTag(this), "Firebase auth failed");
                //hideLoading();
                hideEmpty();

                onError();
            }
        };
        if (ref != null && apiValue != null)
            ref.authWithCustomToken(apiValue.getFirebaseToken(), authResultHandler);
    }

    private void addListener() {
        if (listener == null) {
            listener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String str) {
                    Tweet tweet = Tweet.TweetFromDataSnapshot(dataSnapshot);
                    // get tweet(s) only until # day(s) (using timestamp difference)
                    if (
                            tweets.contains(tweet) == false &&
                                    tweet.interval != null &&
                                    ((System.currentTimeMillis() / 1000L) - tweet.interval) < (displayLastXDays * 24 * 60 * 60)
                            )
                    {
                        nb_tweets +=1;

                        //TODO optimise this call
                        //hide loading when adding the first tweet
                        //hideLoading();
                        hideEmpty();

                        adapter.add(tweet);
                        tweetListView.smoothScrollToPosition(adapter.getItemCount() - 1);
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String str) {
                    Tweet tweet = Tweet.TweetFromDataSnapshot(dataSnapshot);
                    if (tweets.contains(tweet) == false)
                        tweets.add(tweet);
                    else
                        tweets.get(tweets.indexOf(tweet)).copyValue(tweet);
                    adapter.notifyDataSetChanged();
                    tweetListView.smoothScrollToPosition(adapter.getItemCount() - 1);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Tweet tweet = Tweet.TweetFromDataSnapshot(dataSnapshot);
                    if (tweets.contains(tweet))
                        tweets.remove(tweet);
                    adapter.notifyDataSetChanged();
                    tweetListView.smoothScrollToPosition(adapter.getItemCount() - 1);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    onError();
                }
            };
        }
        queryRef.addChildEventListener(listener);

        //TODO find the right moment to show this msg
        //check if there is no tweet
        /*
        if(nb_tweets==0){
            showEmpty();
        }
        */
    }

    @Optional
    @OnClick(R.id.error_view_button)
    void reload(){
        ErrorView.setVisibility(View.GONE);
        initTweetList();
        initFirebase();
    }

    @Override
    public void onError() {
        if (ErrorView != null)
            ErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayPopUp(String message) {
        //the fragment can't show popup, it only show the reload buuton
        onError();
    }


    public void showLoading() {
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    public void hideLoading() {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
    }

    public void showEmpty() {
        if (EmptyView != null)
            EmptyView.setVisibility(View.VISIBLE);
    }

    public void hideEmpty() {
        if (EmptyView != null)
            EmptyView.setVisibility(View.GONE);
    }

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

