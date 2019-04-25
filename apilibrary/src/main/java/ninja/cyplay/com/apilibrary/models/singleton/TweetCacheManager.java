package ninja.cyplay.com.apilibrary.models.singleton;

import java.util.ArrayList;
import java.util.List;

import ninja.cyplay.com.apilibrary.models.business.Tweet;

/**
 * Created by damien on 09/03/16.
 */
public class TweetCacheManager {

    private String currentUserName;

    // Tweet cache
    private List<Tweet> tweetCache = new ArrayList<>();

    // -------------------------------------------------------------------------------------------
    //                                      Get/Set
    // -------------------------------------------------------------------------------------------

    public String getCurrentUserName() {
        return currentUserName;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    public List<Tweet> getTweetCache() {
        return tweetCache;
    }

    public void setTweetCache(List<Tweet> tweetCache) {
        this.tweetCache = tweetCache;
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    public void clearCache() {
        tweetCache.clear();
        currentUserName = null;
    }

    //Tweet feature functions

    public List<String> getTweetCache_uid_list (){
        List<String> uid_list = new ArrayList<>();
        if (tweetCache != null) {
            for (Tweet tweet : tweetCache)
                uid_list.add(tweet.tweet_uid);
        }
        return uid_list;
    }
    public int getNBNotReadTweetFromCache() {
        int nb = 0;
        if (tweetCache != null) {
            for (Tweet tweet : tweetCache)
                if(!tweet.readed)
                    nb ++;
        }
        return nb;
    }

    public void addTweetsToTweetCache(List<Tweet> Newtweets) {
        if (tweetCache == null) {
            tweetCache = new ArrayList<>();
        }
        this.tweetCache.addAll(0,Newtweets);
    }

    public void addTweetToTweetCache(Tweet newTweet) {
        if (tweetCache == null) {
            tweetCache = new ArrayList<>();
        }
        this.tweetCache.add(0,newTweet);
    }

    public void deleteTweetFromUid(String tweetUid) {
        if (tweetUid != null && tweetCache != null)
            for (int i = 0; i < tweetCache.size(); i++)
                if (tweetUid.equals(tweetCache.get(i).id)) {
                    tweetCache.remove(i);
                    break;
                }
    }

    public void onTweetEdited(Tweet editedTweet) {
        if (editedTweet != null && tweetCache != null)
            for (int i = 0; i < tweetCache.size(); i++)
                if (editedTweet.id != null) {
                    if (editedTweet.id.equals(tweetCache.get(i).id)) {
                        tweetCache.set(i, editedTweet);
                        break;
                    }
                }
    }

}
