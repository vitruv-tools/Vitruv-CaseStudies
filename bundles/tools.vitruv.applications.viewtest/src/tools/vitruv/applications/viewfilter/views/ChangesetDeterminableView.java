package tools.vitruv.applications.viewfilter.views;

import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.applications.viewfilter.util.framework.impl.ModifiableView;
import tools.vitruv.applications.viewfilter.util.framework.impl.ViewCreatingViewType;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.composite.propagation.ChangePropagationListener;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewType;

//TODO nbr: Add javadoc
public interface ChangesetDeterminableView extends ModifiableView {
	
	public ResourceSet getViewResourceSet();
	
	public void checkNotClosed();
	
	public ViewCreatingViewType<? extends ViewSelector, HierarchicalId> getViewType();
	
	public void setViewChanged(boolean value);

}
