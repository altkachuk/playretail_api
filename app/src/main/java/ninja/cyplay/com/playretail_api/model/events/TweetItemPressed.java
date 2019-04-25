package ninja.cyplay.com.playretail_api.model.events;

import ninja.cyplay.com.apilibrary.models.business.Tweet;

/**
 * Created by anishosni on 14/01/2016.
 */

public class TweetItemPressed {

    private Tweet tweet;
    private int position;

    public TweetItemPressed(Tweet tweet, int position) {
        this.tweet = tweet;
        this.position = position;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}