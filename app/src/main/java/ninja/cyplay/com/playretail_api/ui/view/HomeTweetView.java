package ninja.cyplay.com.playretail_api.ui.view;

import java.util.List;

import ninja.cyplay.com.apilibrary.models.business.Tweet;

/**
 * Created by anishosni on 17/06/15.
 */

public interface HomeTweetView extends View {


    public void add_tweet(Tweet new_tweet);

    public void edit_tweet(Tweet edit_tweet);

    //return true if the list become empty
    public boolean delete_tweet(String tweet_uid);

    public void add_tweets(List<Tweet> new_tweet);

    public void clear_tweets();

    public void hideTweetListView();

    public void showTweetListView();

    public void hideErrorView();

    public void showLoading() ;

    public void hideLoading() ;

    public void showLoadingReconnect();

    public void hideLoadingReconnect() ;

    public void showEmpty() ;

    public void hideEmpty() ;

}
