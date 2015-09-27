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
	private String title;

	public RecipeSessionData() {
		// public no-arg constructor required for DynamoDBMapper marshalling
	}

	/**
	 * 
	 * @return
	 */
	public static RecipeSessionData newInstance(String title) {
		RecipeSessionData newInstance = new RecipeSessionData();
		newInstance.setStep(0);
		newInstance.setTitle(title);
		return newInstance;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int st) {
		this.step = st;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String nm) {
		this.title = nm;
	}

	@Override
	public String toString() {
		return "recipe: " + title + " step: " + step + ".";
	}
}
