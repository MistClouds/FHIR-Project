package ObserverPattern;

import java.util.HashSet;

/**
 * This is the abstract class for the Observable. Has the code for attaching and detaching observers,
 * and notifying observers.
 */
public abstract class Observable {

	HashSet<Observer> Observers = new HashSet<Observer>();

	/**
	 * attaches an observer so that is knows to alert them when something is changed in the Observable
	 * @param o the observer o
	 */
	public void attach(Observer o) {
		
		Observers.add(o);
	}

	/**
	 * detaches the observer so it stops alerting them when the Observable is changed
	 * @param o the observer o
	 */
	public void detach(Observer o) {
		
		Observers.remove(o);
	}

	/**
	 * notifies all observers that the Observable has changed. calls update on all observers.
	 */
	public void notifyObservers() {
		
		for (Observer o : Observers) {
			o.update();
		}
	}

}
