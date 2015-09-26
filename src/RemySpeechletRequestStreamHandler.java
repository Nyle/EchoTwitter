package remy;

import java.util.HashSet;
import java.util.Set;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

public final class RemySpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
        private static final Set<String> supportedApplicationIds = new HashSet<String>();
        static {
                /*
                 * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
                 * Alexa Skill and put the relevant Application Ids in this Set.
                 */
                supportedApplicationIds.add("amzn1.echo-sdk-ams.app.1064698b-a5d7-46ca-861d-183a9442b80d");
        }

        public RemySpeechletRequestStreamHandler() {
                super(new RemySpeechlet(), supportedApplicationIds);
        }
}
