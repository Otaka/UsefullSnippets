package com.utils.twitter;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import winterwell.jtwitter.AStream;
import winterwell.jtwitter.OAuthSignpostClient;
import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterEvent;
import winterwell.jtwitter.TwitterStream;

/**
 * @author sad
 */
public class TwitterReader {

    private Twitter twitter;
    private TwitterStream stream;
    private boolean isConnected = false;
    private TwitterCallback twitterCallback;
    private Timer timer = new Timer();

    public TwitterReader(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
        OAuthSignpostClient oauthClient = new OAuthSignpostClient(consumerKey, consumerSecret, accessToken, accessTokenSecret);
        twitter = new Twitter("my-name", oauthClient);
        stream = new TwitterStream(twitter);
        stream.setAutoReconnect(false);
        stream.addListener(new AStream.IListen() {
            @Override
            public boolean processEvent(TwitterEvent event) throws Exception {
                System.out.println("SYSTEM: " + event.getType() + " | " + event.getTargetObject());
                return true;
            }

            @Override
            public boolean processSystemEvent(Object[] obj) throws Exception {
                System.out.println("PSYSTEM:" + Arrays.deepToString(obj));
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            stream.close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        stream.connect();
                    }
                }, 4000);

                return false;
            }

            @Override
            public boolean processTweet(Twitter.ITweet tweet) throws Exception {
                if (twitterCallback != null) {
                    twitterCallback.processTweet(tweet);
                }

                return false;
            }
        });
    }

    public void setTwitterCallback(TwitterCallback twitterCallback) {
        this.twitterCallback = twitterCallback;
    }

    public TwitterCallback getTwitterCallback() {
        return twitterCallback;
    }

    public void setKeywords(String... keywords) {
        setKeywords(Arrays.asList(keywords));
    }

    public void setKeywords(List<String> keywords) {
        if (stream == null) {
            throw new RuntimeException("Twitter is not initialized");
        }

        if (isConnected) {
            System.out.print("Stop twitter. ");
            stream.close();
            System.out.println("Stopped");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        }
        if (!keywords.isEmpty()) {
            stream.setTrackKeywords(keywords);
            System.out.print("Connecting to twitter. ");
            stream.connect();
            System.out.println("Connected");
            isConnected = true;
        } else {
            System.out.println("List of keywords is empty");
        }
    }

    public void close() {
        stream.close();
        if(timer!=null){
            timer.cancel();
        }

        isConnected = false;
    }
}
