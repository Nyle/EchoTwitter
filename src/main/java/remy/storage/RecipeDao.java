package remy.storage;

import com.amazon.speech.speechlet.Session;

/**
 * Contains the methods to interact with the persistence layer for Recipe in 
 * DynamoDB.
 */
public class RecipeDao {
        private final RecipeDynamoDbClient dynamoDbClient;

        public RecipeDao(RecipeDynamoDbClient dynamoDbClient) {
                this.dynamoDbClient = dynamoDbClient;
        }

        /**
         * Reads and returns the {@link RecipeSession} using user information from 
         * the session.
         * <p>
         * Returns null if the item could not be found in the database.
         * 
         * @param session
         * @return
         */
        public RecipeSession getRecipeSession(Session session) {
                RecipeUserDataItem item = new RecipeUserDataItem();
                item.setCustomerId(session.getUser().getUserId());

                item = dynamoDbClient.loadItem(item);

                if (item == null) {
                        return null;
                }

                return RecipeSession.newInstance(session, item.getSessionData());
        }

        /**
         * Saves the {@link RecipeSession} into the database.
         * 
         * @param sesh
         */
        public void saveRecipeSession(RecipeSession sesh) {
                RecipeUserDataItem item = new RecipeUserDataItem();
                item.setCustomerId(sesh.getSession().getUser().getUserId());
                item.setSessionData(sesh.getSessionData());

                dynamoDbClient.saveItem(item);
        }
}
