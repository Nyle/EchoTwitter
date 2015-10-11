/**
   Copyright 2014-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
   Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with the License. A copy of the License is located at
   http://aws.amazon.com/apache2.0/
   or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
*/
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
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

public class RemySpeechlet implements Speechlet {
	private static final Logger log = LoggerFactory
                .getLogger(RemySpeechlet.class);
	private AmazonDynamoDBClient amazonDynamoDBClient;
	private ResponseManager responseManager;

	@Override
	public void onSessionStarted(final SessionStartedRequest request,
                                     final Session session) throws SpeechletException {
		log.info("onSessionStarted requestId={}, sessionId={}",
                         request.getRequestId(), session.getSessionId());
		initializeComponents();
	}

	@Override
	public SpeechletResponse onLaunch(final LaunchRequest request,
                                          final Session session) throws SpeechletException {
		log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),
                         session.getSessionId());
		return responseManager.getLaunchResponse(request, session);
	}

	@Override
	public SpeechletResponse onIntent(IntentRequest request, Session session)
                throws SpeechletException {
		log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
                         session.getSessionId());
		initializeComponents();

		Intent intent = request.getIntent();
		if ("HelpIntent".equals(intent.getName())) {
			return responseManager.getHelpIntentResponse(
                                intent, session);
		} else if ("SetRecipeIntent".equals(intent.getName())) {
			return responseManager.setRecipeIntentResponse(
                                intent, session);
                } else if ("GetStepIntent".equals(intent.getName())) {
			return responseManager.getStepIntentResponse(
                                intent, session);
		} else if ("GetNextStepIntent".equals(intent.getName())) {
			return responseManager.getNextStepIntentResponse(
                                intent, session);
		} else if ("GetPreviousStepIntent".equals(intent.getName())) {
			return responseManager.getPreviousStepIntentResponse(
                                intent, session);
		} else if ("ResetStepIntent".equals(intent.getName())) {
			return responseManager.resetStepIntentResponse(
                                intent, session);
		} else if ("ListIngredientsIntent".equals(intent.getName())) {
			return responseManager.listIngredientsIntentResponse(
                                intent, session);
                        
                } else {
			throw new IllegalArgumentException("Unrecognized intent: "
                                                           + intent.getName());
		}
	}

	@Override
	public void onSessionEnded(final SessionEndedRequest request,
                                   final Session session) throws SpeechletException {
		log.info("onSessionEnded requestId={}, sessionId={}",
                         request.getRequestId(), session.getSessionId());
	}

	/**
	 * Initializes the instance components if needed.
	 */
	private void initializeComponents() {
		if (amazonDynamoDBClient == null) {
			amazonDynamoDBClient = new AmazonDynamoDBClient();
			responseManager = new ResponseManager(amazonDynamoDBClient);
		}
	}
}
