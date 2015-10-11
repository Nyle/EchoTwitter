package remy.storage;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshaller;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Model representing an item of the RecipeUserData table in DynamoDB for the Recipe
 * skill.
 */
@DynamoDBTable(tableName = "RecipeList")
public class RecipeDataItem {
        private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

        private String recipe;
        private RecipeData recipeData;

        @DynamoDBHashKey(attributeName = "Recipe")
        public String getRecipe() {
                return recipe;
        }

        public void setRecipe(String recipe) {
                this.recipe = recipe;
        }

        @DynamoDBAttribute(attributeName = "RecipeData")
        @DynamoDBMarshalling(marshallerClass = RecipeDataMarshaller.class)
        public RecipeData getRecipeData() {
                return recipeData;
        }

        public void setRecipeData(RecipeData recipeData) {
                this.recipeData = recipeData;
        }

        /**
         * A {@link DynamoDBMarshaller} that provides marshalling and unmarshalling logic for
         * {@link RecipeGameData} values so that they can be persisted in the database as String.
         */
        public static class RecipeDataMarshaller implements
                                                 DynamoDBMarshaller<RecipeData> {

                @Override
                public String marshall(RecipeData recipeData) {
                        try {
                                return OBJECT_MAPPER.writeValueAsString(
                                        recipeData);
                        } catch (JsonProcessingException e) {
                                throw new IllegalStateException("Unable to marshall recipe data", e);
                        }
                }

                @Override
                public RecipeData unmarshall(Class<RecipeData> clazz, String value) {
                        try {
                                return OBJECT_MAPPER.readValue(value, new TypeReference<RecipeData>() {});
                        } catch (Exception e) {
                                throw new IllegalStateException("Unable to unmarshall recipe data value", e);
                        }
                }
        }
}
