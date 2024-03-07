package tools.vitruv.applications.viewfilter.views;

import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.applications.viewfilter.util.framework.impl.ViewCreatingViewType;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.composite.propagation.ChangePropagationListener;
import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewSelection;
import tools.vitruv.framework.views.ViewSelector;

//TODO nbr: Remove this class?
public class InformationDisplayView extends AbstractBasicView implements View, ChangePropagationListener {


    private ResourceSet viewResourceSet;
	
	private boolean closed = false;

//	private ViewCreatingViewType<? extends ViewSelector, HierarchicalId> viewType;
//
//	private ChangeableViewSource viewSource;
//
//	private ViewSelection viewSelection;
	
	
	
	
	public InformationDisplayView(ViewCreatingViewType<? extends ViewSelector, HierarchicalId> viewType, ChangeableViewSource viewSource, ViewSelection viewSelection) {
		super(viewType, viewSource, viewSelection);
//		this.viewType = viewType;
//		this.viewSource = viewSource;
//		this.viewSelection = viewSelection;
		
	}
	
	
//	@Override
//	public void close() throws Exception {
//		if (!closed) {
//			closed = true;
//			viewResourceSet.getResources().forEach(it -> it.unload());
//			viewResourceSet.getResources().clear();
//			removeChangeListeners(viewResourceSet);
//		}
//		getViewSource().removeChangePropagationListener(this);
//	}


	@Override
	public boolean isModified() {
		return false;
	}

	@Override
	public boolean isOutdated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public boolean isClosed() {
//		// TODO Auto-generated method stub
//		return false;
//	}


}
