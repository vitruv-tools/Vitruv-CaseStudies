package tools.vitruv.applications.viewfilter.views;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceCopier;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.composite.description.TransactionalChange;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.description.VitruviusChangeBackwardsExecutionHelper;
import tools.vitruv.change.composite.description.VitruviusChangeResolver;
import tools.vitruv.change.composite.recording.ChangeRecorder;
import tools.vitruv.framework.views.ViewSelection;

//TODO nbr: Hier nicht ChangeRecordingView überschreiben, sondern eigen implementieren und Sichtbarkeiten in ChangeRecordingView zurücksetzen
public class FilterChangeRecordingView extends ChangeRecordingView implements FilterableView {
	
	private Map<EObject, EObject> mapCopy2OriginalObject;
	private TransactionalChange<EObject> recordedChange = null;
	

	protected FilterChangeRecordingView(BasicFilterView view, Map<EObject, EObject> mapCopy2OriginalObject) {
		super(view);
		this.mapCopy2OriginalObject = mapCopy2OriginalObject;
	}
	
	
	
	@Override
	public List<EChange<EObject>> setupChangeRecorder() {
		ResourceSet filteredModelsInResourceSet = view.getViewResourceSet();
		changeRecorder = new ChangeRecorder(filteredModelsInResourceSet);
        changeRecorder.addToRecording(filteredModelsInResourceSet);
        ResourceSet resourceSetCopy = new ResourceSetImpl();
        
        return changeRecorder.beginRecording();
	}
	
	
	private ResourceSet copyResourceSet(ResourceSet resourceSetToBeCopied) {
		ResourceSet resourceSetCopy = new ResourceSetImpl();
        ResourceCopier.copyViewResources(resourceSetToBeCopied.getResources(), resourceSetCopy);
        return resourceSetCopy;
	}
	
	
	
	@Override
	public void commitChanges() {
		view.checkNotClosed();
		recordedChange = changeRecorder.endRecording();

		VitruviusChangeResolver<HierarchicalId> changeResolver = VitruviusChangeResolver.forHierarchicalIds(view.getViewResourceSet());		
		VitruviusChange<HierarchicalId> unresolvedChanges = changeResolver.assignIds(recordedChange);
		view.getViewType().commitViewChanges(this, unresolvedChanges);
		view.setViewChanged(false);
		changeRecorder.beginRecording();
	}
	
	
	
	private FilterableView getViewAsFilterableView() {
		return (FilterableView) view;
	}



	@Override
	public ViewSelection getPreFilterSelection() {
		return getViewAsFilterableView().getPreFilterSelection();
	}


	public ResourceSet getViewResourceSet() {
		return view.getViewResourceSet();
	}
	
	
	/**
	 * Executes the current recorded changes backwards. May only be executed when the 
	 * changes are currently really applied. This means that the method should not be 
	 * executed twice without forward executing the changes in between. Returns the 
	 * {@link ResourceSet} afterwards.
	 * 
	 * @return The filtered {@link ResourceSet} in the state before the current recorded changes have been applied
	 */
	public ResourceSet backwardExecuteChangesAndReturnResourceSet() {
		ResourceSet resourceSet = getViewResourceSet();
		if (recordedChange != null) {
			 VitruviusChangeBackwardsExecutionHelper.applyBackward(resourceSet, recordedChange);
		}
		return resourceSet;
	}
	
	
	@Override
	public ResourceSet getNonFilteredViewResourceSet() {
		return getViewAsFilterableView().getNonFilteredViewResourceSet();
	}



	@Override
	public Map<EObject, EObject> getMapCopy2OriginalObject() {
		return mapCopy2OriginalObject;
	}

}
