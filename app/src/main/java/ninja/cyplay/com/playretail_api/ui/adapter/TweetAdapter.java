package ninja.cyplay.com.playretail_api.ui.adapter;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import ninja.cyplay.com.apilibrary.models.business.Tweet;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.viewholder.TweetViewHolder;

public class TweetAdapter extends RecyclerView.Adapter<TweetViewHolder> {

    private final Activity activity;

    private List<Tweet> tweets;

    public TweetAdapter(Activity activity,List<Tweet> tweets) {
        this.tweets = tweets;
        this.activity = activity;
    }

    @Override
    public int getItemCount() {
        return tweets != null ? tweets.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return tweets != null ? tweets.get(position).hashCode() : null;
    }

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_tweet, viewGroup, false);
        return new TweetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TweetViewHolder holder, int i) {
        Tweet tweet =  tweets.get(i);
        holder.setTweet(tweet);
        holder.setContext(activity);
        holder.tweetMessage.setText(tweet.text);
        holder.tweetTitle.setText(Html.fromHtml("<b>" + tweet.title + "</b>"));
        if (tweet.date != null) {
            //TODO should remove that  (anti pattern)
            if(Constants.TWEET_PROVIDER == Constants.TWEET_PROVIDER_SOCKETIO) {
                try {
                    Calendar date_calender_format = ninja.cyplay.com.apilibrary.utils.DateUtils.toCalendar(tweet.date);
                    holder.tweetTime.setText(android.text.format.DateUtils.getRelativeTimeSpanString(date_calender_format.getTimeInMillis()));
                } catch (ParseException e) {
                    e.printStackTrace();
                    holder.tweetTime.setText("");
                }
            }
            else{
                holder.tweetTime.setText(tweet.date);
            }
        }
        // mark as read or not read
        if (tweet.readed)
            holder.markAsRead();
        else
            holder.markAsNotRead();
    }


    // data set managment

    public void add(Tweet new_tweet){
        tweets.add(0,new_tweet);
    }

    public void set_read(String tweet_uid, int pos){
        synchronized(tweets) {
            for (Tweet tweet:tweets) {
                if(tweet.tweet_uid.equals(tweet_uid))
                    tweet.readed = true;
            }
        }
        notifyData(pos);
    }

    public void edit(Tweet edit_tweet){
        // get the position
        int tweet_pos=-1;
        int i =0;
        for (Tweet tweet:tweets) {
            if(tweet.tweet_uid.equals(edit_tweet.tweet_uid))
                tweet_pos = i;
            i++;
        }
        //edit and notify
        if (tweet_pos != -1) {
            tweets.remove(tweet_pos);
            tweets.add(tweet_pos, edit_tweet);
            notifyData(tweet_pos);
        }
    }

    public boolean delete(String tweet_uid){
        // get the position
        int tweet_pos=-1;
        int i =0;
        for (Tweet tweet:tweets) {
            if(tweet.tweet_uid.equals(tweet_uid)) {
                tweet_pos = i;
                break;
            }
            i++;
        }
        //delete and notify
        if (tweet_pos != -1)
            tweets.remove(tweet_pos);

        notifyDataSetChanged();

        if(tweets.size()>0)
            return false;
        else
            return true;
    }

    public void clear(){
        tweets.clear();
    }

    public void setTweets(List<Tweet> tweets) {
        tweets.clear();
        tweets.addAll(tweets);
        notifyDataSetChanged();
    }

    public List<Tweet> getTweets() {
        return tweets;
    }


    // custom method to update adapter data
    public void notifyData(final int pos ) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            notifyDataSetChanged();
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    notifyItemChanged(pos);
                }
            });
        }
    }
}