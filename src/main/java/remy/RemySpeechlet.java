package remy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class RemySpeechlet implements Speechlet {
        private static final Logger log = LoggerFactory.getLogger(
                RemySpeechlet.class);

        @Override
        public void onSessionStarted(final SessionStartedRequest request,
                                     final Session session)
                throws SpeechletException {
                log.info("onSessionStarted requestId={}, sessionId={}",
                         request.getRequestId(), session.getSessionId());
                // any initialization logic goes here
        }

        @Override
        public SpeechletResponse onLaunch(final LaunchRequest request,
                                          final Session session)
                throws SpeechletException {
                log.info("onLaunch requestId={}, sessionId={}",
                         request.getRequestId(), session.getSessionId());
                return getHelpResponse();
        }

        @Override
        public SpeechletResponse onIntent(final IntentRequest request,
                                          final Session session)
                throws SpeechletException {
                log.info("onIntent requestId={}, sessionId={}",
                         request.getRequestId(), session.getSessionId());

                Intent intent = request.getIntent();
                String intentName = (intent != null) ? intent.getName() : null;

                if ("HelpIntent".equals(intentName)) {
                        return getHelpResponse();
                } else {
                        throw new SpeechletException("Invalid Intent");
                }
        }

        @Override
        public void onSessionEnded(final SessionEndedRequest request,
                                   final Session session)
                throws SpeechletException {
                log.info("onSessionEnded requestId={}, sessionId={}",
                         request.getRequestId(), session.getSessionId());
        }

        private SpeechletResponse getHelpResponse() {
                String speechText = "Hi I am Remy, and I know how to make all" +
                        " kinds of things but mostly grilled cheese";

                PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
                speech.setText(speechText);

                return SpeechletResponse.newTellResponse(speech);
        }
}
