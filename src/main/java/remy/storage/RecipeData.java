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
        newInstance.setTitle("");
        newInstance.setSteps(null);
        return newInstance;
    }
    
    public String getTitle() {
    	return title;
    }
    
    public List<String> getSteps() {
    	return steps;
    }
    
    public void setTitle(String title) {
    	this.title = title;
    }
    
    public void setSteps(List<String> steps) {
    	this.steps = steps;
    }
    
}
