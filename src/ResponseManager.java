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
import remy.storage.Recipe;
import remy.storage.RecipeData;

import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.List;

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
        
        public ResponseManager(final AmazonDynamoDBClient amazonDynamoDbClient){
                RecipeDynamoDbClient dynamoDbClient =
                        new RecipeDynamoDbClient(amazonDynamoDbClient);
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
                RecipeSession sesh = recipeDao.getRecipeSession(session);
                if (sesh.getTitle() == null) {
                        return getAskSpeechletResponse(
                                "Select a recipe to get started or ask for " +
                                "help to get more information",
                                "Select a recipe to get started or ask for " +
                                "help to get more information");
                }
                return getAskSpeechletResponse(
                        "When we last talked you were on step " +
                        sesh.getStep() + " of " + sesh.getTitle() + ". " +
                        "if you want to continue say next step, otherwise " +
                        "say help for more options", "");
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
                String speechText = "Say things like, do you have a recipe for ramen, what is the next step, what was the previous step, list ingredients, restart";

                return getTellSpeechletResponse(speechText);
        }

        public SpeechletResponse setRecipeIntentResponse(Intent intent,
                                                         Session session) {
                String desiredRecipe = intent.getSlot("Recipe").getValue();
                RecipeSession sesh = recipeDao.getRecipeSession(session);
                Recipe recipe = recipeDao.getRecipe(desiredRecipe);
                if (recipe == null) {
                        return getAskSpeechletResponse(
                                "I'm sorry, I don't have a recipe for that " +
                                "would you like to look for something else?",
                                "please ask for something else to look for");
                }
                sesh.setTitle(desiredRecipe);
                sesh.setStep(0);
                recipeDao.saveRecipeSession(sesh);
                return getAskSpeechletResponse("I've got that! say next " +
                                                    "step to get started",
                                                    "say net step to get " +
                                                    "started");
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
                        sesh = RecipeSession.newInstance(
                                session, RecipeSessionData.newInstance(null));
                }
                if (sesh.getTitle() == null) {
                        return getAskSpeechletResponse(
                                "You haven't selected a recipe to cook yet " +
                                "ask about one to find out if I have it",
                                "ask about a recipe to get started");
                }
                Recipe recipe = recipeDao.getRecipe(sesh.getTitle());
                if (sesh.getStep() == 0) {
                        sesh.setStep(1);
                }
                recipeDao.saveRecipeSession(sesh);
                return getTellSpeechletResponse(recipe.getStep(sesh.getStep()));
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
                        sesh = RecipeSession.newInstance(
                                session, RecipeSessionData.newInstance(null));
                }
                if (sesh.getTitle() == null) {
                        return getAskSpeechletResponse(
                                "You haven't selected a recipe to cook yet " +
                                "ask about one to find out if I have it",
                                "ask about a recipe to get started");
                }
                Recipe recipe = recipeDao.getRecipe(sesh.getTitle());
                if (sesh.getStep() > recipe.getSize()) {
                        sesh.setStep(0);
                        return getTellSpeechletResponse(
                                "this is the end of the recipe. When you are " +
                                "done, ask me to reset to get ready to cook " +
                                "something else");
                }
                
                sesh.setStep(sesh.getStep() + 1);
                
                recipeDao.saveRecipeSession(sesh);
                return getTellSpeechletResponse(recipe.getStep(sesh.getStep()));
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
        public SpeechletResponse getPreviousStepIntentResponse(
                Intent intent, Session session) {
                RecipeSession sesh = recipeDao.getRecipeSession(session);
                if (sesh == null) {
                        sesh = RecipeSession.newInstance(
                                session, RecipeSessionData.newInstance(null));
                }
                if (sesh.getTitle() == null) {
                        return getAskSpeechletResponse(
                                "You haven't selected a recipe to cook yet " +
                                "ask about one to find out if I have it",
                                "ask about a recipe to get started");
                }
                Recipe recipe = recipeDao.getRecipe(sesh.getTitle());
                if (sesh.getStep() < 1) {
                        return getTellSpeechletResponse(
                                "you are already on the first step");
                }
                sesh.setStep(sesh.getStep() - 1);

                recipeDao.saveRecipeSession(sesh);
                return getTellSpeechletResponse(recipe.getStep(sesh.getStep()));
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
                if (sesh.getTitle() == null) {
                        sesh = RecipeSession.newInstance(
                                session, RecipeSessionData.newInstance(null));
                }
                Recipe recipe = recipeDao.getRecipe(sesh.getTitle());
                sesh.setStep(0);
                sesh.setTitle(null);
                
                recipeDao.saveRecipeSession(sesh);
                return getTellSpeechletResponse(
                        "The current recipe and step have been cleared");
        }

        public SpeechletResponse listIngredientsIntentResponse(Intent intent,
                                                              Session session) {
                RecipeSession sesh = recipeDao.getRecipeSession(session);
                if (sesh == null) {
                        sesh = RecipeSession.newInstance(
                                session, RecipeSessionData.newInstance(null));
                }
                if (sesh.getTitle() == null) {
                        return getAskSpeechletResponse(
                                "You haven't selected a recipe to cook yet " +
                                "ask about one to find out if I have it",
                                "ask about a recipe to get started");
                }
                Recipe recipe = recipeDao.getRecipe(sesh.getTitle());

                recipeDao.saveRecipeSession(sesh);
                List<String> ingredientsList = recipe.getIngredients();
                String ingredients = String.join(
                        ", ",
                        ingredientsList.subList(0,
                                                ingredientsList.size() - 1)) +
                        " and " + ingredientsList.subList(
                                ingredientsList.size() - 1,
                                ingredientsList.size());
                return getTellSpeechletResponse("the ingredients are " +
                                                ingredients);
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

                PlainTextOutputSpeech repromptSpeech =
                        new PlainTextOutputSpeech();
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
