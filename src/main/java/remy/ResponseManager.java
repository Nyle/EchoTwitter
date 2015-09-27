/**
   Copyright 2014-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
   Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with the License. A copy of the License is located at
   http://aws.amazon.com/apache2.0/
   or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package remy;

import remy.storage.RecipeDao;
import remy.storage.RecipeSession;
import remy.storage.RecipeSessionData;
import remy.storage.RecipeDynamoDbClient;

import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

/**
 * The {@link ResponseManager} receives various events and intents and manages
 * the flow of the game.
 */
public class ResponseManager {
	private final RecipeDao recipeDao;

	public ResponseManager(final AmazonDynamoDBClient amazonDynamoDbClient) {
		RecipeDynamoDbClient dynamoDbClient = new RecipeDynamoDbClient(
				amazonDynamoDbClient);
		recipeDao = new RecipeDao(dynamoDbClient);
	}

	/**
	 * Creates and returns response for Launch request.
	 * 
	 * @param request
	 *            {@link LaunchRequest} for this request
	 * @param session
	 *            Speechlet {@link Session} for this request
	 * @return response for launch request
	 */
	public SpeechletResponse getLaunchResponse(LaunchRequest request,
			Session session) {
		return getAskSpeechletResponse(
				"let's make a sandwitch! ask for the next step to "
						+ "get started or for help to get more options",
				"ask for help if you need it");
	}

	/**
	 * Creates and returns response for the help intent.
	 * 
	 * @param intent
	 *            {@link Intent} for this request
	 * @param session
	 *            {@link Session} for this request
	 * @return response for the help intent
	 */
	public SpeechletResponse getHelpIntentResponse(Intent intent,
			Session session) {
		String speechText = "Hi! I'm Remy, you can ask me for the "
				+ "next step, to repeat the current or "
				+ "previous step, or to restart from the first step";

		return getTellSpeechletResponse(speechText);
	}

	/**
	 * Creates and returns response for the getStep intent.
	 * 
	 * @param intent
	 *            {@link Intent} for this request
	 * @param session
	 *            {@link Session} for this request
	 * @return response for the help intent
	 */
	public SpeechletResponse getStepIntentResponse(Intent intent,
			Session session) {
		RecipeSession sesh = recipeDao.getRecipeSession(session);
		if (sesh == null) {
			sesh = RecipeSession.newInstance(session,
					RecipeSessionData.newInstance(null));
		}

		recipeDao.saveRecipeSession(sesh);
		return getTellSpeechletResponse("We are on step " + sesh.getStep());
	}

	/**
	 * Creates and returns response for the getNextStep intent.
	 * 
	 * @param intent
	 *            {@link Intent} for this request
	 * @param session
	 *            {@link Session} for this request
	 * @return response for the help intent
	 */
	public SpeechletResponse getNextStepIntentResponse(Intent intent,
			Session session) {
		RecipeSession sesh = recipeDao.getRecipeSession(session);
		if (sesh == null) {
			sesh = RecipeSession.newInstance(session,
					RecipeSessionData.newInstance(null));
		}
		sesh.setStep(sesh.getStep() + 1);

		recipeDao.saveRecipeSession(sesh);
		return getTellSpeechletResponse("We are now on step " + sesh.getStep());
	}

	/**
	 * Creates and returns response for the getPreviousStep intent.
	 * 
	 * @param intent
	 *            {@link Intent} for this request
	 * @param session
	 *            {@link Session} for this request
	 * @return response for the help intent
	 */
	public SpeechletResponse getPreviousStepIntentResponse(Intent intent,
			Session session) {
		RecipeSession sesh = recipeDao.getRecipeSession(session);
		if (sesh == null) {
			sesh = RecipeSession.newInstance(session,
					RecipeSessionData.newInstance(null));
		}
		sesh.setStep(sesh.getStep() - 1);

		recipeDao.saveRecipeSession(sesh);
		return getTellSpeechletResponse("We are now on step " + sesh.getStep());
	}

	/**
	 * Creates and returns response for the restartStep intent.
	 * 
	 * @param intent
	 *            {@link Intent} for this request
	 * @param session
	 *            {@link Session} for this request
	 * @return response for the help intent
	 */
	public SpeechletResponse resetStepIntentResponse(Intent intent,
			Session session) {
		RecipeSession sesh = recipeDao.getRecipeSession(session);
		if (sesh == null) {
			sesh = RecipeSession.newInstance(session,
					RecipeSessionData.newInstance(null));
		}
		sesh.setStep(0);
		return getTellSpeechletResponse("We are now on step " + sesh.getStep());
	}

	/**
	 * Returns an ask Speechlet response for a speech and reprompt text.
	 * 
	 * @param speechText
	 *            Text for speech output
	 * @param repromptText
	 *            Text for reprompt output
	 * @return ask Speechlet response for a speech and reprompt text
	 */
	private SpeechletResponse getAskSpeechletResponse(String speechText,
			String repromptText) {
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
		repromptSpeech.setText(repromptText);
		Reprompt reprompt = new Reprompt();
		reprompt.setOutputSpeech(repromptSpeech);

		return SpeechletResponse.newAskResponse(speech, reprompt);
	}

	/**
	 * Returns a tell Speechlet response for a speech and reprompt text.
	 * 
	 * @param speechText
	 *            Text for speech output
	 * @return a tell Speechlet response for a speech and reprompt text
	 */
	private SpeechletResponse getTellSpeechletResponse(String speechText) {
		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		return SpeechletResponse.newTellResponse(speech);
	}
}
