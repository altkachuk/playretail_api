package ninja.cyplay.com.apilibrary.domain.interactor;

import java.util.List;

import ninja.cyplay.com.apilibrary.models.business.Tweet;


/**
 * Created by anishosni on 17/06/15.
 */
public interface TweetInteractor {

    void initScoket(final TweetSocketCallback callback);
    void disconnectSocket(final DisconnectTweetSocketCallback callback);
    void readTweetEvent(String tweet_uid);

    interface TweetSocketCallback {

        void onConnect();
        void onDisconnect();
        void onConnetionError();

        void onReconnecting();

        void onInitTweets(List<Tweet> tweets);
        void onEmptyTweetList();
        void onNewTweet(Tweet tweet);
        void onEditedTweet(Tweet tweet);
        void onDeletedTweet(String tweet_uid);

    }

    interface DisconnectTweetSocketCallback {
        void onDisconnect();
    }
}

