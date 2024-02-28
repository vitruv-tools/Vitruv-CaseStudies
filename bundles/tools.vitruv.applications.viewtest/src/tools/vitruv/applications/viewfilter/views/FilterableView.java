package tools.vitruv.applications.viewfilter.views;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.applications.viewfilter.util.framework.impl.ModifiableView;
import tools.vitruv.framework.views.ViewSelection;

//TODO nbr: Add javadoc
public interface FilterableView extends ModifiableView {

	ViewSelection getPreFilterSelection();
	
	ResourceSet getFilteredModelsInResourceSet();
	
	Map<EObject, EObject> getMapCopy2OriginalObject();

	//ResourceSet getFilteredModelsInResourceSetWithBackwardExecution();
}
