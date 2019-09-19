package com.utils.twitter;

import winterwell.jtwitter.Twitter;

/**
 * @author sad
 */
public interface TwitterCallback {

    public void processTweet(Twitter.ITweet tweet) throws Exception;
}
