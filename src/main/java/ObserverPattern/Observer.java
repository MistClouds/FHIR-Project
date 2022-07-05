package ObserverPattern;

/**
 * abstract class that represents an Observer.
 */
public abstract class Observer {

	/**
	 * This method must exist. Observers update when they "observe" a change in the data source.
	 * Really, the Observable lets them know that something has changed and they just look at what has changed.
	 */
	public abstract void update();
}
