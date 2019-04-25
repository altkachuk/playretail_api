package ninja.cyplay.com.apilibrary.models.business;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Map;

public class Tweet {

    public String id;
    public Long interval;

    public String tweet_uid;
    public String title;
    public String text;
    public String date;
    public String sender;
    public String edit_date; // date of the edit, empty elsewhere
    public String edit_username; // the last user that edit the tweet
    public ArrayList<ReadSchema> read_by;
    public boolean readed = false;


    // used with firebase
    public static Tweet TweetFromDataSnapshot(DataSnapshot snapshot) {
        Map<String, Object> s = (Map<String, Object>) snapshot.getValue();
        Tweet tweet = new Tweet();
        tweet.id = snapshot.getKey();
        tweet.tweet_uid = (String) s.get("uid");
        tweet.title = (String) s.get("Title");
        tweet.text = (String) s.get("Message");
        tweet.date = (String) s.get("Date");
        tweet.interval = (Long)s.get("Interval");
        return tweet;
    }

    // used with firebase
    public void copyValue(Tweet other) {
        this.id = other.id;
        this.title = other.title;
        this.text = other.text;
        this.interval = other.interval;
        this.date = other.date;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Tweet)) return false;
        Tweet otherTweet = (Tweet) other;
        return this.id.equals(otherTweet.id);
    }

    public class ReadSchema {
        public String user;
        public String date;
    }
}

