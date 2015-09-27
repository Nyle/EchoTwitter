package remy.storage;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.amazon.speech.speechlet.Session;


public final class Recipe {
	private RecipeData bookData;
	
	private Recipe() {
		//Intentionally Blank
	}
	
	/**
	 * Creates a new instance of {@link Recipe} with the provided
	 * {@link Session} and {@link RecipeBookData}.
	 * <p>
	 * To create a new instance of {@link RecipeBookData}, see
	 * {@link RecipeBookData#newInstance()}
	 * 
	 * @param session
	 * @param bookData
	 * @return
	 * @see RecipeBookData#newInstance()
	 */
	public static Recipe newInstance(RecipeData bookData) {
		Recipe book = new Recipe();
		book.setRecipeData(bookData);
		return book;
	}
	
	
	protected void setRecipeData(RecipeData bookData) {
		this.bookData = bookData;
	}
	
	protected RecipeData getRecipeData() {
		return bookData;
	}
	
	public String getTitle() {
		return bookData.getTitle();
	}
	
	public List<String> getSteps() {
		return bookData.getSteps();
	}
	
	public String getStep(int i) {
		return bookData.getSteps().get(i);
	}
	
	public int getSize() {
		return bookData.getSteps().size();
	}
}