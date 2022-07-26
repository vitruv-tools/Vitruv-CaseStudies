package tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events;

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeEventVisitor;

/**
 * 
 * Abstract superclass for all AST change events. Sets a timestamp on
 * construction, accepts a {@link ChangeEventVisitor} according to the visitor
 * pattern. A {@link ChangeClassifyingEvent} subclass contains fields for by the
 * change affected AST nodes and overrides toString() for log/debug purposes.
 * 
 * @author messinger
 */
public abstract class ChangeClassifyingEvent {

	private final long timestamp;

	public ChangeClassifyingEvent() {
		this.timestamp = System.currentTimeMillis();
	}

	public long getTimestamp() {
		return this.timestamp;
	}

	public abstract <T> T accept(ChangeEventVisitor<T> visitor);

}
