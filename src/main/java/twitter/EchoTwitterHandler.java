package src;

import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

public class EchoTwitterHandler {
        APIKeys Keys;
        String consumerKey;
        String consumerSecret;
        String accessToken;
        String accessTokenSecret;
        TwitterFactory twitterFactory;
        Twitter twitter;

        public EchoTwitterHandler() {
                Keys = new APIKeys();
                //Your Twitter App's Consumer Key
                consumerKey = Keys.getconsumerKey();

                //Your Twitter App's Consumer Secret
                consumerSecret = Keys.getconsumerSecret();

                //Your Twitter Access Token
                accessToken = Keys.getaccessToken();

                //Your Twitter Access Token Secret
                accessTokenSecret = Keys.getaccessTokenSecret();

                //Instantiate a re-usable and thread-safe factory
                twitterFactory = new TwitterFactory();

                //Instantiate a new Twitter instance
                twitter = twitterFactory.getInstance();

                //setup OAuth Consumer Credentials
                twitter.setOAuthConsumer(consumerKey, consumerSecret);

                //setup OAuth Access Token
                twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));
        }

        public SpeechletResponse getTweetResponse(Intent intent, Session session)
                throws SpeechletException, TwitterException {
                //Instantiate and initialize a new twitter status update

                String tweet = intent.getSlot("Tweet").getValue();
                tweet = tweetParse(tweet);
                if (tweet.length > 140) {
                    //Something gets done here
                }
                StatusUpdate statusUpdate = new StatusUpdate(
                        tweet);

                //tweet or update status
                Status status = twitter.updateStatus(statusUpdate);

                PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
                speech.setText("It has been done");
                return SpeechletResponse.newTellResponse(speech);
        }

        /**
         * Creates a {@code SpeechletResponse} for the help intent.
         *
         * @return SpeechletResponse spoken and visual response for the given intent
         */
        public SpeechletResponse getHelpResponse() {
                String speechText = "You can ask me to tweet somthing for you";

                // Create the plain text output.
                PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
                speech.setText(speechText);
        
                return SpeechletResponse.newTellResponse(speech);

        }

        public String tweetParse(String tweet) {
            String[] arr = tweet.split(" ");
            for (int i = 0; i < arr.size() - 1; i++) {
                if (arr[i].equals("hashtag")) {
                    arr[i] = "";
                    arr[i+1] = "#" + arr[i+1];
                }
            }
            for (int i = 0; i < arr.size() - 2; i++) {
                if (arr[i].equals("hash") & arr[i+1].equals("tag")) {
                    arr[i] = "";
                    arr[i+1] = "";
                    arr[i+2] = "#" + arr[i+1];
                }
            }
            tweet = String.join(" ", arr);
        }

}
