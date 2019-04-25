package ninja.cyplay.com.playretail_api.ui.viewholder;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;
import ninja.cyplay.com.apilibrary.MVPCleanArchitectureApplication;
import ninja.cyplay.com.apilibrary.models.business.Tweet;
import ninja.cyplay.com.playretail_api.model.events.TweetItemPressed;
import ninja.cyplay.com.playretail_api.R;

/**
 * Created by damien on 30/04/15.
 */
public class TweetViewHolder extends RecyclerView.ViewHolder {

    @Inject
    Bus bus;

    @Optional
    @InjectView(R.id.tweet_content_layout)
    public View tweetCell;

    @Optional
    @InjectView(R.id.tweet_icon)
    public ImageView tweetImageView;

    @Optional
    @InjectView(R.id.tweet_message_time)
    public TextView tweetTime;

    @Optional
    @InjectView(R.id.tweet_message_title)
    public TextView tweetTitle;

    @Optional
    @InjectView(R.id.tweet_message_text)
    public TextView tweetMessage;

    @Optional
    @InjectView(R.id.tweet_notif_view)
    public View tweetNotifIcon;

    private Tweet tweet;

    private Activity activity;


    public TweetViewHolder(View view) {
        super(view);
        ButterKnife.inject(this, view);

    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }

    public Tweet getTweet() {
        return this.tweet ;
    }

    public void setContext(Activity activity) {
        if (activity != null) {
            this.activity = activity;
            ((MVPCleanArchitectureApplication)activity.getApplication()).inject(this);
        }
    }

    public void markAsNotRead(){
        //tweetMessage.setTypeface(tweetMessage.getTypeface(), Typeface.BOLD);
        //tweetTitle.setTypeface(tweetTitle.getTypeface(), Typeface.BOLD);
        //tweetTime.setTypeface(tweetTime.getTypeface(), Typeface.BOLD);
        tweetNotifIcon.setVisibility(View.VISIBLE);
    }

    public void markAsRead(){
        //tweetMessage.setTypeface(tweetMessage.getTypeface(), Typeface.NORMAL);
        //tweetTitle.setTypeface(tweetTitle.getTypeface(), Typeface.NORMAL);
        //tweetTime.setTypeface(tweetTime.getTypeface(), Typeface.NORMAL);
        tweetNotifIcon.setVisibility(View.INVISIBLE);
    }

    @Optional
    @OnClick(R.id.tweet_card_view)
    public void onTweetClick() {
        if (! this.tweet.readed) {
            tweet.readed = true;
            markAsRead();
            bus.post(new TweetItemPressed(tweet, this.getAdapterPosition()));
        }

    }

}
