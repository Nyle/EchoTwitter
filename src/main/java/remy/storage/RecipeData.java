package remy.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains player and score data to represent a score keeper game.
 */
public class RecipeData {
        private String title;
        private List<String> steps;
        private List<String> ingredients;

        public RecipeData() {
                // public no-arg constructor required for DynamoDBMapper marshalling
        }
    
        /**
         * Creates a new instance of {@link RecipeBookData} with initialized but empty player and
         * score information.
         * 
         * @return
         */
        public static RecipeData newInstance() {
                RecipeData newInstance = new RecipeData();
                newInstance.setSteps(null);
                newInstance.setIngredients(null);
                return newInstance;
        }
    
        public List<String> getSteps() {
                return steps;
        }
    
        public void setSteps(List<String> steps) {
                this.steps = steps;
        }

        public List<String> getIngredients() {
                return ingredients;
        }
    
        public void setIngredients(List<String> ingredients) {
                this.ingredients = ingredients;
        }
    
}
