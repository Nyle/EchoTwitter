package remy.storage;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshaller;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Model representing an item of the RecipeUserData table in DynamoDB for the
 * Recipe skill.
 */
@DynamoDBTable(tableName = "RecipeUserData")
public class RecipeUserDataItem {
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private String customerId;

	private RecipeSessionData seshData;

	@DynamoDBHashKey(attributeName = "CustomerId")
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@DynamoDBAttribute(attributeName = "Data")
	@DynamoDBMarshalling(marshallerClass = RecipeSessionDataMarshaller.class)
	public RecipeSessionData getSessionData() {
		return seshData;
	}

	public void setSessionData(RecipeSessionData seshData) {
		this.seshData = seshData;
	}

	/**
	 * A {@link DynamoDBMarshaller} that provides marshalling and unmarshalling
	 * logic for {@link RecipeGameData} values so that they can be persisted in
	 * the database as String.
	 */
	public static class RecipeSessionDataMarshaller implements
			DynamoDBMarshaller<RecipeSessionData> {

		@Override
		public String marshall(RecipeSessionData seshData) {
			try {
				return OBJECT_MAPPER.writeValueAsString(seshData);
			} catch (JsonProcessingException e) {
				throw new IllegalStateException("Unable to marshall game data",
						e);
			}
		}

		@Override
		public RecipeSessionData unmarshall(Class<RecipeSessionData> clazz,
				String value) {
			try {
				return OBJECT_MAPPER.readValue(value,
						new TypeReference<RecipeSessionData>() {
						});
			} catch (Exception e) {
				throw new IllegalStateException(
						"Unable to unmarshall user data value", e);
			}
		}
	}
}
