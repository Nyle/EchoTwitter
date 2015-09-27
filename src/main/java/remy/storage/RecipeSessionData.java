package remy.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains player and score data to represent a score keeper game.
 */
public class RecipeSessionData {
    private int step;
    private String name;

    public RecipeSessionData() {
        // public no-arg constructor required for DynamoDBMapper marshalling
    }

    /**
     * 
     * @return
     */
    public static RecipeSessionData newInstance(String name) {
        RecipeSessionData newInstance = new RecipeSessionData();
        newInstance.setStep(0);
        newInstance.setName(name);
        return newInstance;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int st) {
        this.step = st;
    }

    public String getName() {
        return name;
    }

    public void setName(String nm) {
        this.name = nm;
    }

    @Override
    public String toString() {
        return "recipe: " + name + " step: " + step + ".";
    }
}
