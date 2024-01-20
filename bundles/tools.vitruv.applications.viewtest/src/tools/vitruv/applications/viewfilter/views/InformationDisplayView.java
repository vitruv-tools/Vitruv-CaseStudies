package tools.vitruv.applications.viewfilter.views;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;

import tools.vitruv.applications.viewfilter.util.framework.impl.BasicView;
import tools.vitruv.applications.viewfilter.util.framework.impl.ViewCreatingViewType;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.propagation.ChangePropagationListener;
import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewSelection;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.views.changederivation.StateBasedChangeResolutionStrategy;

public class InformationDisplayView extends BasicView implements View, ChangePropagationListener {


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
