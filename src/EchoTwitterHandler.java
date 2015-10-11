package echoTweets;

import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class EchoTwitterHandler {

    public static void main(String[] args) throws IOException, TwitterException {

        APIKeys Keys = new APIKeys();
        //Your Twitter App's Consumer Key
        String consumerKey = Keys.getconsumerKey();

        //Your Twitter App's Consumer Secret
        String consumerSecret = Keys.getconsumerSecret();

        //Your Twitter Access Token
        String accessToken = Keys.getaccessToken();

        //Your Twitter Access Token Secret
        String accessTokenSecret = Keys.getaccessTokenSecret();

        //Instantiate a re-usable and thread-safe factory
        TwitterFactory twitterFactory = new TwitterFactory();

        //Instantiate a new Twitter instance
        Twitter twitter = twitterFactory.getInstance();

        //setup OAuth Consumer Credentials
        twitter.setOAuthConsumer(consumerKey, consumerSecret);

        //setup OAuth Access Token
        twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));

        //Instantiate and initialize a new twitter status update
        StatusUpdate statusUpdate = new StatusUpdate(
                //your tweet or status message
                "sent a tweet from Java again");

        //tweet or update status
        Status status = twitter.updateStatus(statusUpdate);

        //response from twitter server
        System.out.println("status.toString() = " + status.toString());
        System.out.println("status.getInReplyToScreenName() = " + status.getInReplyToScreenName());
        System.out.println("status.getSource() = " + status.getSource());
        System.out.println("status.getText() = " + status.getText());
        System.out.println("status.getContributors() = " + Arrays.toString(status.getContributors()));
        System.out.println("status.getCreatedAt() = " + status.getCreatedAt());
        System.out.println("status.getCurrentUserRetweetId() = " + status.getCurrentUserRetweetId());
        System.out.println("status.getGeoLocation() = " + status.getGeoLocation());
        System.out.println("status.getId() = " + status.getId());
        System.out.println("status.getInReplyToStatusId() = " + status.getInReplyToStatusId());
        System.out.println("status.getInReplyToUserId() = " + status.getInReplyToUserId());
        System.out.println("status.getPlace() = " + status.getPlace());
        System.out.println("status.getRetweetCount() = " + status.getRetweetCount());
        System.out.println("status.getRetweetedStatus() = " + status.getRetweetedStatus());
        System.out.println("status.getUser() = " + status.getUser());
        System.out.println("status.getAccessLevel() = " + status.getAccessLevel());
        System.out.println("status.getHashtagEntities() = " + Arrays.toString(status.getHashtagEntities()));
        System.out.println("status.getMediaEntities() = " + Arrays.toString(status.getMediaEntities()));
        if(status.getRateLimitStatus() != null)
        {
            System.out.println("status.getRateLimitStatus().getLimit() = " + status.getRateLimitStatus().getLimit());
            System.out.println("status.getRateLimitStatus().getRemaining() = " + status.getRateLimitStatus().getRemaining());
            System.out.println("status.getRateLimitStatus().getResetTimeInSeconds() = " + status.getRateLimitStatus().getResetTimeInSeconds());
            System.out.println("status.getRateLimitStatus().getSecondsUntilReset() = " + status.getRateLimitStatus().getSecondsUntilReset());
            System.out.println("status.getRateLimitStatus().getRemainingHits() = " + status.getRateLimitStatus().getRemaining());
        }
        System.out.println("status.getURLEntities() = " + Arrays.toString(status.getURLEntities()));
        System.out.println("status.getUserMentionEntities() = " + Arrays.toString(status.getUserMentionEntities()));
    }

}