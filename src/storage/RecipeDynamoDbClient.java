package remy.storage;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

/**
 * Client for DynamoDB persistance layer for the Score Keeper skill.
 */
public class RecipeDynamoDbClient {
	private final AmazonDynamoDBClient dynamoDBClient;

	public RecipeDynamoDbClient(final AmazonDynamoDBClient dynamoDBClient) {
		this.dynamoDBClient = dynamoDBClient;
	}

	/**
	 * Loads an item from DynamoDB by primary Hash Key. Callers of this method
	 * should pass in an object which represents an item in the DynamoDB table
	 * item with the primary key populated.
	 * 
	 * @param tableItem
	 * @return
	 */
	public RecipeUserDataItem loadItem(final RecipeUserDataItem tableItem) {
		DynamoDBMapper mapper = createDynamoDBMapper();
		RecipeUserDataItem item = mapper.load(tableItem);
		return item;
	}

        public RecipeDataItem loadRecipe(final RecipeDataItem recipeItem) {
                DynamoDBMapper mapper = createDynamoDBMapper();
                RecipeDataItem item = mapper.load(recipeItem);
                return item;
        }

	/**
	 * Stores an item to DynamoDB.
	 * 
	 * @param tableItem
	 */
	public void saveItem(final RecipeUserDataItem tableItem) {
		DynamoDBMapper mapper = createDynamoDBMapper();
		mapper.save(tableItem);
	}

	/**
	 * Creates a {@link DynamoDBMapper} using the default configurations.
	 * 
	 * @return
	 */
	private DynamoDBMapper createDynamoDBMapper() {
		return new DynamoDBMapper(dynamoDBClient);
	}
}
