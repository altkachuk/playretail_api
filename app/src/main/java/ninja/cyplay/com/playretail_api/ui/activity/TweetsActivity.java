package ninja.cyplay.com.playretail_api.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseActivity;
import ninja.cyplay.com.playretail_api.ui.fragment.TweetsFragment;
import ninja.cyplay.com.playretail_api.ui.fragment.TweetsFragmentFirebase;
import ninja.cyplay.com.playretail_api.ui.fragment.TweetsFragmentSocketIO;

/**
 * Created by anishosni on 18/06/15.
 */
public class TweetsActivity extends BaseActivity {

    // Fragments
    private TweetsFragment tweetsFragment;

    // Fragments keys (retain)
    private final static String TWEETS_FRAGMENT = "TWEET_FRAGMENT";

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_tweets, null);
        setContentView(view);

        updateDesign();
        if (savedInstanceState != null && savedInstanceState.containsKey(TWEETS_FRAGMENT))
            tweetsFragment = (TweetsFragment) getSupportFragmentManager().getFragment(savedInstanceState, TWEETS_FRAGMENT);
        else
            createFragments();
        bindFragmentToView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (tweetsFragment != null && getSupportFragmentManager().findFragmentById(R.id.container) == tweetsFragment)
            getSupportFragmentManager().putFragment(outState, TWEETS_FRAGMENT, tweetsFragment);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Override(s)
    // -------------------------------------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contact_manager, menu);
        return true;
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void updateDesign() {
        // show back button
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // update title
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getResources().getString(R.string.home_tweet_list));
    }

    private void createFragments() {
        if (Constants.TWEET_PROVIDER == Constants.TWEET_PROVIDER_FIREBASE)
            tweetsFragment= new TweetsFragmentFirebase();
        else if (Constants.TWEET_PROVIDER == Constants.TWEET_PROVIDER_SOCKETIO)
            tweetsFragment= new TweetsFragmentSocketIO();
    }

    private void bindFragmentToView() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, tweetsFragment);
        transaction.commit();
    }

}
