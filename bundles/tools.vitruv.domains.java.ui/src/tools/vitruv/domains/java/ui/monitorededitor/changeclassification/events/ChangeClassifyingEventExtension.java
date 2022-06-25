package tools.vitruv.domains.java.ui.monitorededitor.changeclassification.events;

import tools.vitruv.domains.java.ui.monitorededitor.changeclassification.ChangeEventVisitor;

/**
 * Marker interface that is to be implemented by new
 * {@link ChangeClassifyingEvent}s that are not expected to be handled by the
 * {@link ChangeEventVisitor}. Should be dispatched to a
 * ChangeEventExtendedVisitor which states to be responsible for the change type
 * of this class.
 * 
 * @author messinger
 */
public interface ChangeClassifyingEventExtension {

}
