package remy.storage;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.amazon.speech.speechlet.Session;

/**
 * Represents a score keeper game.
 */
public final class RecipeSession {
	private Session session;
	private RecipeSessionData seshData;

	private RecipeSession() {
	}

	/**
	 * Creates a new instance of {@link RecipeSession} with the provided
	 * {@link Session} and {@link RecipeSessionData}.
	 * <p>
	 * To create a new instance of {@link RecipeSessionData}, see
	 * {@link RecipeSessionData#newInstance()}
	 * 
	 * @param session
	 * @param seshData
	 * @return
	 * @see RecipeSessionData#newInstance()
	 */
	public static RecipeSession newInstance(Session session,
                                                RecipeSessionData seshData) {
		RecipeSession sesh = new RecipeSession();
		sesh.setSession(session);
		sesh.setSessionData(seshData);
		return sesh;
	}

	protected void setSession(Session session) {
		this.session = session;
	}

	protected Session getSession() {
		return session;
	}

	protected RecipeSessionData getSessionData() {
		return seshData;
	}

	protected void setSessionData(RecipeSessionData seshData) {
		this.seshData = seshData;
	}

	/**
	 * Returns the number of players in the game.
	 * 
	 * @return the number of players in the game
	 */
	public int getStep() {
		return seshData.getStep();
	}

	/**
	 * Changes the step
	 * 
	 * @param i
	 *            the int to which the Step is changed
	 */
	public void setStep(int i) {
		seshData.setStep(i);
	}

	/**
	 * Changes the name
	 * 
	 * @param str
	 *            is the new name
	 */
	public void setTitle(String str) {
		seshData.setTitle(str);
	}

	/**
	 * Returns the name of the recipe
	 * 
	 * @return the name of the recipe
	 */
	public String getTitle() {
		return seshData.getTitle();
	}
}
