package tools.vitruv.applications.viewfilter.views;

import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.applications.viewfilter.util.framework.impl.ModifiableView;
import tools.vitruv.applications.viewfilter.util.framework.impl.ViewCreatingViewType;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.framework.views.ViewSelector;

/**
 * Instances of this interface can be used to determine changes. These changes can then be used 
 * for further steps like commits.
 */
public interface ChangesetDeterminableView extends ModifiableView {
	
	/**
	 * Returns the {@link ResourceSet} of the {@link ChangesetDeterminableView}
	 * 
	 * @return The {@link ResourceSet} object
	 */
	public ResourceSet getViewResourceSet();
	
	/**
	 * Ensures that this {@link ChangesetDeterminableView} has not already been closed.
	 */
	public void checkNotClosed();
	
	/**
	 * Method returns the {@link ViewType} of the {@link ChangesetDeterminableView} object.
	 * 
	 * @return The {@link ViewType}
	 */
	public ViewCreatingViewType<? extends ViewSelector, HierarchicalId> getViewType();
	
	/**
	 * Method sets the marker for changes of this {@link ChangesetDeterminableView} on the 
	 * given value.
	 * 
	 * @param value The new value for the marker
	 */
	public void setViewChanged(boolean value);

}
