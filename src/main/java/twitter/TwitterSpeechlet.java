package src;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.TwitterException;

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

/**
 * This sample shows how to create a simple speechlet for handling speechlet requests.
 */
public class TwitterSpeechlet implements Speechlet {
        private static final Logger log = LoggerFactory.getLogger(TwitterSpeechlet.class);
        EchoTwitterHandler handler = new EchoTwitterHandler();
        
        @Override
        public void onSessionStarted(final SessionStartedRequest request, final Session session)
                throws SpeechletException {
                log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(),
                         session.getSessionId());
                // any initialization logic goes here
        }

        @Override
        public SpeechletResponse onLaunch(final LaunchRequest request, final Session session)
                throws SpeechletException {
                log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),
                         session.getSessionId());
                return handler.getHelpResponse();
        }

        @Override
        public SpeechletResponse onIntent(final IntentRequest request, final Session session)
                throws SpeechletException {
                try {
                        log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
                                 session.getSessionId());

                        Intent intent = request.getIntent();
                        String intentName = (intent != null) ? intent.getName() : null;

                        if ("NewTweetIntent".equals(intentName)) {
                                return handler.getTweetResponse(intent, session);
                        } else if ("HelpIntent".equals(intentName)) {
                                return handler.getHelpResponse();
                        } else {
                                throw new SpeechletException("Invalid Intent");
                        }
                } catch (TwitterException e) {
                        if (e.getErrorMessage().equals("Status is a duplicate.")) {
                                PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
                                speech.setText("I'm sorry, you recently tweeted the same message. Please tweet a new message");
                                return SpeechletResponse.newTellResponse(speech);
                        }
                        throw new SpeechletException("Twitter Error " + e.getErrorMessage());
                }
        }

        @Override
        public void onSessionEnded(final SessionEndedRequest request, final Session session)
                throws SpeechletException {
                log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(),
                         session.getSessionId());
                // any cleanup logic goes here
        }
}
