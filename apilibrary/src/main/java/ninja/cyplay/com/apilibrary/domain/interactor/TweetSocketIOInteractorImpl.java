package ninja.cyplay.com.apilibrary.domain.interactor;


import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import ninja.cyplay.com.apilibrary.domain.executor.InteractorExecutor;
import ninja.cyplay.com.apilibrary.domain.executor.MainThreadExecutor;
import ninja.cyplay.com.apilibrary.models.business.Tweet;
import ninja.cyplay.com.apilibrary.models.singleton.APIValue;
import ninja.cyplay.com.apilibrary.models.singleton.TweetCacheManager;
import ninja.cyplay.com.apilibrary.models.singleton.TweetSocket;
import ninja.cyplay.com.apilibrary.utils.Constants;

/**
 * Created by anishosni on 17/06/15.
 */
public class TweetSocketIOInteractorImpl extends AbstractInteractor implements TweetInteractor {

    @Inject
    public APIValue apiValue;

    @Inject
    TweetCacheManager tweetCacheManager;

    private TweetSocketCallback callback;

    private Context myContext;

    // socket io socket
    Socket mSocket = null;

    //evens list
    Map<String, Emitter.Listener> events;

    //data
    private String read_tweet_uid;

    public TweetSocketIOInteractorImpl(Context context, InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor) {
        super(interactorExecutor, mainThreadExecutor);
        //((MVPCleanArchitectureApplication) context.getApplicationContext()).inject(this);
        myContext = context;

        //add events to the events list
        events = new HashMap<>();
        // socket connection events
        events.put(Socket.EVENT_CONNECT, onConnect);
        events.put(Socket.EVENT_CONNECT_TIMEOUT, onConnectTimeOut);
        events.put(Socket.EVENT_RECONNECTING, onReconnecting);
        events.put(Socket.EVENT_DISCONNECT, onDisconnect);
        events.put(Socket.EVENT_CONNECT_ERROR, onConnectError);

        // business events
        events.put("authenticated", onAuthenticated);
        events.put("tweets", onInitMessages);
        events.put("empty", onEmptyMessage);
        events.put("tweet", onNewtMessage);
        events.put("edited", onEditedMessage);
        events.put("deleted", onDeletedMessage);
    }


    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------

    public void init() {
        try {
            //id socket instance
            mSocket = TweetSocket.getINSTANCE(myContext).getsocket();
            if (!mSocket.connected()) {
                // Register events
                for (Map.Entry<String, Emitter.Listener> entry : events.entrySet()) {
                    mSocket.on(entry.getKey(), entry.getValue());
                }
                mSocket.connect();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //callback.onConnetionError();
            callback.onDisconnect();
        }
    }

    @Override
    public void initScoket(TweetInteractor.TweetSocketCallback callback) {
        this.callback = callback;
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                init();
            }
        });
    }

    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------

    private DisconnectTweetSocketCallback disconnectTweetSocketCallback;

    public void disconnect() {
        if (mSocket != null) {
            // remove listeners
            for (Map.Entry<String, Emitter.Listener> entry : events.entrySet()) {
                mSocket.off(entry.getKey());
            }
            // disconnect the socket
            mSocket.disconnect();
            if (disconnectTweetSocketCallback != null)
                disconnectTweetSocketCallback.onDisconnect();
        }
    }

    @Override
    public void disconnectSocket(DisconnectTweetSocketCallback callback) {
        this.disconnectTweetSocketCallback = callback;
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                disconnect();
            }
        });
    }

    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------

    public void readTweet() {
        if (mSocket != null) {
            mSocket.emit("read_tweet", this.read_tweet_uid);
        }
    }

    @Override
    public void readTweetEvent(String tweet_uid) {
        this.read_tweet_uid = tweet_uid;
        getInteractorExecutor().run(new Interactor() {
            @Override
            public void run() {
                readTweet();
            }
        });
    }

    // -------------------------------------------------------------------------------------------
    //
    // -------------------------------------------------------------------------------------------


    // setting up and registering with the server
    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getMainThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    // construct the token object
                    //TODO id this token from the server side
                    JSONObject token = new JSONObject();
                    try {
                        token.put("token", Constants.SOCKETIO_TOKEN);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // send the token to authenticate
                    mSocket.emit("authenticate", token);
                }
            });
        }
    };

    private Emitter.Listener onAuthenticated = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getMainThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    // send the configuration
                    String store_id = "TODO"; // (apiValue.getConfig().getShop()).getCustomerId();
                    JSONObject config_data = new JSONObject();
                    JSONArray last_received_tweets_uid = new JSONArray();
                    try {
                        config_data.put("shop_id", store_id);
                        config_data.put("user_id", tweetCacheManager.getCurrentUserName());
                        for (String uid : tweetCacheManager.getTweetCache_uid_list())
                            last_received_tweets_uid.put(uid);
                        if (last_received_tweets_uid.length() > 0)
                            config_data.put("last_received_tweets_uid", last_received_tweets_uid);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mSocket.emit("init_config", config_data);
                    callback.onConnect();
                }
            });
        }
    };

    private Emitter.Listener onConnectTimeOut = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getMainThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    //disconnect();
                    //callback.onConnetionError();
                    callback.onDisconnect();
                }
            });
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getMainThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    //disconnect();
                    //callback.onConnetionError();
                    callback.onDisconnect();
                }
            });
        }
    };

    private Emitter.Listener onReconnecting = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getMainThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    //disconnect();
                    //callback.onReconnection();
                    callback.onReconnecting();
                }
            });
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getMainThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    disconnect();
                    callback.onDisconnect();
                }
            });
        }
    };

    private Emitter.Listener onInitMessages = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getMainThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    List<Tweet> init_tweets = createTweetsObjects(args[0].toString());
                    if (init_tweets != null && init_tweets.size() > 0) {

                        // Check if tweets are read or not
                        String un = tweetCacheManager.getCurrentUserName();
                        for (Tweet tweet : init_tweets) {
                            tweet.readed = checkTweetIsRead(tweet, un);
                        }
                        //update cache
                        tweetCacheManager.addTweetsToTweetCache(init_tweets);
                    }
                    callback.onInitTweets(init_tweets);
                }
            });
        }
    };

    private Emitter.Listener onNewtMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getMainThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Tweet new_tweet = createTweetObject(args[0].toString());
                    if (new_tweet != null) {
                        callback.onNewTweet(new_tweet);
                        //update cache
                        tweetCacheManager.addTweetToTweetCache(new_tweet);
                    }
                }
            });
        }
    };


    private Emitter.Listener onEmptyMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getMainThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    callback.onEmptyTweetList();
                }
            });
        }
    };

    private Emitter.Listener onEditedMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getMainThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Tweet edit_tweet = createTweetObject(args[0].toString());
                    tweetCacheManager.onTweetEdited(edit_tweet);
                    callback.onEditedTweet(edit_tweet);
                }
            });
        }
    };

    private Emitter.Listener onDeletedMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getMainThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    String deleted_tweet_uid = (String) args[0];
                    tweetCacheManager.deleteTweetFromUid(deleted_tweet_uid);
                    callback.onDeletedTweet(deleted_tweet_uid);
                }
            });
        }
    };

    // -------------------------------------------------------------------------------------------
    //                                       private interactor  Method(s)
    // -------------------------------------------------------------------------------------------

    private Tweet createTweetObject(String tweet_json) {
        Gson gson = new Gson();
        return gson.fromJson(tweet_json, Tweet.class);
    }

    private List<Tweet> createTweetsObjects(String json_tweets) {
        Gson gson = new Gson();
        Tweet[] tweetArray = gson.fromJson(json_tweets, Tweet[].class);
        return Arrays.asList(tweetArray);
    }

    private boolean checkTweetIsRead(Tweet tweet, String un) {
        if (tweet != null && tweet.read_by != null)
            for (Tweet.ReadSchema read_by_object : tweet.read_by)
                if (un != null && un.equals(read_by_object.user))
                    return true;
        return false;
    }

}
