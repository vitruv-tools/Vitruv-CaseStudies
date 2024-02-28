package tools.vitruv.applications.viewfilter.views;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceCopier;
import tools.vitruv.applications.viewfilter.util.framework.impl.BasicView;
import tools.vitruv.applications.viewfilter.util.framework.impl.ChangeRecordingView;
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
	
	//private ResourceSet resourceSetFromBeginningOfRecording;

	protected FilterChangeRecordingView(BasicFilterView view, Map<EObject, EObject> mapCopy2OriginalObject) {
		super(view);
		this.mapCopy2OriginalObject = mapCopy2OriginalObject;
	}
	
	
	
	@Override
	public List<EChange<EObject>> setupChangeRecorder() {
		ResourceSet filteredModelsInResourceSet = getView().getFilteredModelsInResourceSet();
		changeRecorder = new ChangeRecorder(filteredModelsInResourceSet);
        changeRecorder.addToRecording(filteredModelsInResourceSet);
        ResourceSet resourceSetCopy = new ResourceSetImpl();
        //Map<Resource, Resource> copyResourceMapping = ResourceCopier.copyViewResources(filteredModelsInResourceSet.getResources(), resourceSetCopy);
        //resourceSetFromBeginningOfRecording = resourceSetCopy;
        
        return changeRecorder.beginRecording();
	}
	
	
	private ResourceSet copyResourceSet(ResourceSet resourceSetToBeCopied) {
		ResourceSet resourceSetCopy = new ResourceSetImpl();
        ResourceCopier.copyViewResources(resourceSetToBeCopied.getResources(), resourceSetCopy);
        return resourceSetCopy;
	}
	
	
	//private Map<EObject, >
	
	
	@Override
	public void commitChanges() {
		getView().checkNotClosed();
		recordedChange = changeRecorder.endRecording();
		
		ResourceSet filteredModelsInResourceSet = getFilteredModelsInResourceSet();
		ResourceSet unfilteredResourceSet = getUnfilteredResourceSet();
//		EObject class2 = filteredModelsInResourceSet.getResources().get(0).getContents().get(0).eContents().get(0);
//		EObject eObject = mapCopy2OriginalObject.get(class2);
		
		
		//TODO nbr: ich muss hier irgendwie an das view resourceSet kommen
		
		//VitruviusChangeResolver.forMappingFilteredObjects(getView().getFilteredModelsInResourceSet(), getView().getNonFilteredViewResourceSet(), mapCopy2OriginalObject);
		VitruviusChangeResolver<HierarchicalId> changeResolver = VitruviusChangeResolver.forHierarchicalIds(getView().getFilteredModelsInResourceSet());		
		VitruviusChange<HierarchicalId> unresolvedChanges = changeResolver.assignIds(recordedChange);
		getView().getViewType().commitViewChanges(this, unresolvedChanges);
		getView().setViewChanged(false);
		changeRecorder.beginRecording();
	}
	
	
	
	private BasicFilterView getView() {
		return (BasicFilterView) view;
	}



	@Override
	public ViewSelection getPreFilterSelection() {
		return getView().getPreFilterSelection();
	}



	@Override
	public ResourceSet getFilteredModelsInResourceSet() {
		return getView().getFilteredModelsInResourceSet();
	}
	
	
	public ResourceSet getFilteredModelsInResourceSetWithBackwardExecution() {
		ResourceSet resourceSet = getFilteredModelsInResourceSet();
		if (recordedChange != null) {
			//TODO nbr: What happens if changes have already been reverted?
			VitruviusChangeBackwardsExecutionHelper changeResolver = new VitruviusChangeBackwardsExecutionHelper(resourceSet);
			changeResolver.applyBackward(recordedChange);
		}
		return resourceSet;
	}
	
	
	public ResourceSet getUnfilteredResourceSet() {
		return getView().getNonFilteredViewResourceSet();
	}



	@Override
	public Map<EObject, EObject> getMapCopy2OriginalObject() {
		return mapCopy2OriginalObject;
	}

}
